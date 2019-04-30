package com.attendance.webservice.presence.controller;

import com.attendance.webservice.utils.Base64;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import com.attendance.webservice.model.Sign;
import com.attendance.webservice.model.PublicKeyData;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@RestController
public class PresenceController {
    private static final Logger log = LoggerFactory.getLogger(PresenceController.class);
    private PublicKey savedPubKey;

    @CrossOrigin
    @PostMapping("checkmhs")
    public Map<String,String> validateMhs(@RequestBody HashMap<String,String> request){
        HashMap<String, String> map = new HashMap<>();
        log.info("nim: "+request.get("nim"));
        log.info("pass: "+request.get("password"));

        map.put("status","200");
        if(request.get("nim").equals("161511040")){
            map.put("message", "User is active");
        }else {
            map.put("message", "User is not active");
        }
        return map;
    }

    @CrossOrigin
    @PostMapping("registermhs")
    public Map<String,String> registerMhs(@RequestBody HashMap<String,String> request){
        HashMap<String, String> map = new HashMap<>();
        log.info("publicKey: "+request.get("publicKey"));
        log.info("publicKey length: "+request.get("publicKey").length());
        log.info("nim: "+request.get("nim"));
        log.info("imei: "+request.get("imei"));

        map.put("status","200");
        map.put("message","User is not active");
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

    @CrossOrigin
    @PostMapping("/getPublicKey")
    public Map<String, String> getPublicKey(@RequestBody PublicKeyData pubKey) {
        HashMap<String, String> map = new HashMap<>();
        try {
            savedPubKey = KeyFactory.getInstance("RSA").generatePublic(
                    new X509EncodedKeySpec(Base64.decode(pubKey.getPubKey(), Base64.DEFAULT)));
            map.put("responseFromServer", "We got your public key!");
            return map;
        } catch (Exception e) {
            map.put("responseFromServer", e.getMessage());
            return map;
        }
    }
}
