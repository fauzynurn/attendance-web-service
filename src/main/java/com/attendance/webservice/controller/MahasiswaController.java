package com.attendance.webservice.controller;

import java.text.ParseException;
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
	@PostMapping("/resetmhs")
	public Map<String, String> resetMhs(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		Mahasiswa mhs = mhsRepository.findByNim(request.get("nim"));
		
		mhs.setImeiMhs(null);
		mhsRepository.save(mhs);
		
		map.put("status", "200");
		map.put("message", "Success");
        return map;
	}
	
	@CrossOrigin
	@PostMapping("/importmhs")
	public Map<String, String> importMhs(@RequestBody Map<String, List<Map<String, String>>> request) {
		Map<String, String> map = new LinkedHashMap<>();
		List<Mahasiswa> listMhs1 = new ArrayList<>();
		List<Mahasiswa> listMhs2 = new ArrayList<>();
		List<Map<String, String>> listRequest = request.get("listMhs");
		
		for(Map<String, String> r : listRequest) {
			Mahasiswa mahasiswa = mhsRepository.findByNim(r.get("nim"));
			if(mahasiswa == null) {
				Mahasiswa mhs = new Mahasiswa();
				Kelas kelas = new Kelas();
				kelas.setKdKelas(r.get("kdKelas"));
				
				mhs.setNim(r.get("nim"));
				mhs.setNamaMhs(r.get("namaMhs"));
				mhs.setKelas(kelas);
				listMhs1.add(mhs);
			} else {
				Kelas kelas = new Kelas();
				kelas.setKdKelas(r.get("kdKelas"));
				
				mahasiswa.setNamaMhs(r.get("namaMhs"));
				mahasiswa.setKelas(kelas);
				listMhs2.add(mahasiswa);
			}				
		}

		try {
			if(!listMhs1.isEmpty()) {
				for(Mahasiswa item : listMhs1) {
					mhsRepository.insertMhs(item.getNim(), item.getNamaMhs(), item.getKelas().getKdKelas(), null);
				}
			}
				
			if(!listMhs2.isEmpty()) {
				mhsRepository.saveAll(listMhs2);	
			}
			
			map.put("status", "200");
			map.put("message", "Success");
		} catch(Exception e) {
			map.put("status", "404");
			map.put("message", "Import Fail");
		}
		return map;
	}
}
