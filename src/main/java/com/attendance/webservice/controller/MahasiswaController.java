package com.attendance.webservice.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Kelas;
import com.attendance.webservice.model.Mahasiswa;
import com.attendance.webservice.repository.MahasiswaRepository;

@RestController
public class MahasiswaController {
	@Autowired
	MahasiswaRepository mhsRepository;
	
	@PostMapping("/checkmhs")
	public Map checkMhs(@RequestBody Map<String, String> request) {
		Map map = new LinkedHashMap<>();
		Map<String, String> data = new LinkedHashMap<>();
		
        Mahasiswa mhs = mhsRepository.findByNim(request.get("nim"));
    	if(mhs == null) {
    		map.put("status", "404");
    		map.put("message", "User is not recognized");
    	} else {
    		if(mhs.getImeiMhs() != null) {
    			if(mhs.getImeiMhs().equals(request.get("imei"))) {
    				data.put("nama", mhs.getNamaMhs());
    				data.put("kelas", mhs.getKelas().getKdKelas());
    				
					map.put("status", "200");
					map.put("message", "Imei match");
					map.put("data", data);
				} else {
					map.put("status", "200");
					map.put("message", "User is active");
				}
    		} else {
    			map.put("status", "200");
    			map.put("message", "User is not active");
    		}
		}
        return map;
	}

	@PostMapping("/registermhs")
	public Map registerMhs(@RequestBody Map<String, String> request) {
		Map map = new LinkedHashMap<>();
		Map<String, String> data = new LinkedHashMap<>();
        
		Mahasiswa mhs = mhsRepository.findByNim(request.get("nim"));
        mhs.setImeiMhs(request.get("imei"));
        mhsRepository.save(mhs);
        
		data.put("nama", mhs.getNamaMhs());
		data.put("kelas", mhs.getKelas().getKdKelas());
		
		map.put("status", "200");
		map.put("message", "Success");
		map.put("data", data);
        return map;
	}
	
	@CrossOrigin
	@PostMapping("/getdaftarmhs")
	public List<Map<String, String>> getDaftarMhs(@RequestBody Map<String, String> request) {
		List<Map<String, String>> maps = new ArrayList<>();
		List<Mahasiswa> mhs = mhsRepository.getListMhs(request.get("kdKelas"));
		
		for(Mahasiswa item : mhs) {
			Map<String, String> map = new LinkedHashMap<>();
			
			map.put("nim", item.getNim());
			map.put("nama", item.getNamaMhs());
			map.put("imei", item.getImeiMhs());
			
			maps.add(map);
		}
        return maps;
	}
	
	@CrossOrigin
	@PostMapping("/buatmhs")
	public Map<String, String> buatMhs(@RequestBody Map<String, String> request) {
		Map<String, String> map = new LinkedHashMap<>();
		Mahasiswa mhs = new Mahasiswa();
		Kelas kelas = new Kelas();
		
		if(mhsRepository.findByNim(request.get("nim")) == null) {
			kelas.setKdKelas(request.get("kdKelas"));
			mhs.setNamaMhs(request.get("namaMhs"));
			mhs.setNim(request.get("nim"));
			mhs.setKelas(kelas);
			mhsRepository.save(mhs);
		
			map.put("status", "200");
			map.put("message", "Success");
		} else {
			map.put("status", "404");
			map.put("message", "Failed");
		}
        return map;
	}
}
