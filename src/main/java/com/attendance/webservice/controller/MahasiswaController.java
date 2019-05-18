package com.attendance.webservice.controller;

import java.security.PublicKey;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Mahasiswa;
import com.attendance.webservice.model.Sign;
import com.attendance.webservice.service.MahasiswaService;
import com.attendance.webservice.utils.Base64;

@RestController
public class MahasiswaController {
	@Autowired
	MahasiswaService mhsService;
	private PublicKey savedPubKey;
	
	@PostMapping("/checkmhs")
	public Map<String, String> checkMhs(@RequestBody HashMap<String, String> request) {
		HashMap<String, String> map = new HashMap<>();
        Mahasiswa mhs = mhsService.findNim(request.get("nim"));
    	if(mhs == null) {
    		map.put("status", "404");
    		map.put("message", "User is not recognized");
    	} else {
    		if(mhs.getPwdMhs().equals(request.get("password"))) {
    			if(mhs.getImeiMhs() != null) {
    				map.put("status", "200");
    				map.put("message", "User is active");
    			} else {
    				map.put("status", "200");
    				map.put("message", "User is not active");
    			}
    		} else {
    			map.put("status", "404");
        		map.put("message", "User is not recognized");
    		}
		}
        return map;
	}
	
	@PostMapping("/registermhs")
	public Map<String, String> registerMhs(@RequestBody HashMap<String, String> request) {
		HashMap<String, String> map = new HashMap<>();
        Mahasiswa mhs = mhsService.findNim(request.get("nim"));
        mhs.setPubKeyMhs(request.get("publicKey"));
        mhs.setImeiMhs(request.get("imei"));
        mhsService.saveMhs(mhs);
        map.put("status","200");
        map.put("message", mhs.getKelas().getKdKelas());
        return map;
	}
	
	@CrossOrigin
    @PostMapping("/attendancesign")
    public Map<String, String> decrypt(@RequestBody Sign sign) {
        HashMap<String, String> map = new HashMap<>();
        try {
            Signature verificationFunction = Signature.getInstance("SHA1WithRSA");
            verificationFunction.initVerify(savedPubKey);
            verificationFunction.update(sign.getData().getBytes());
            if (verificationFunction.verify(Base64.decode(sign.getSignature(), 0))) {
                map.put("responseFromServer", "It's from server. You're verified!");
            } else {
                map.put("responseFromServer", "You're not verified!!");
            }
            return map;
        } catch (Exception e) {
            map.put("responseFromServer", e.toString());
            return map;
        }
	}
}
