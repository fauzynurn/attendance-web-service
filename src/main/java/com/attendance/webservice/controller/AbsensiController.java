package com.attendance.webservice.controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Absensi;
import com.attendance.webservice.repository.AbsensiRepository;

@RestController
public class AbsensiController {
	@Autowired
	AbsensiRepository absensiRepository;
	
	@GetMapping("/getkehadiran")
	public List<Map> getKehadiran(@RequestBody Map<String, String> request) {
		return absensiRepository.getKehadiran(request.get("nim"));
	}
	
	@GetMapping("/getketidakhadiran")
	public Map<String, String> getKetidakhadiran(@RequestBody Map<String, String> request) {
		return absensiRepository.getKetidakhadiran(request.get("nim"));
	}
	
	@PostMapping("/catatkehadiran")
	public Map<String, String> catatKehadiran(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl = sdf1.parse(request.get("tgl"));
		tgl = sdf2.parse(sdf2.format(tgl));
		java.sql.Date tglAbsensi = new java.sql.Date(tgl.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jamAbsensi = new Time(sdf3.parse(request.get("jam")).getTime());
		
		Absensi absensi = absensiRepository.getAbsensiPengganti(tglAbsensi, jamAbsensi, request.get("nim"));
		if(absensi == null) {
			absensi = absensiRepository.getAbsensiKuliah(tglAbsensi, jamAbsensi, request.get("nim"));
			if(absensi == null) {
				map.put("status","404");
		        map.put("message", "Failed");
		        return map;
			}
		}
		absensi.setStatusKehadiran(1);
		absensiRepository.save(absensi);
			
		map.put("status","200");
	    map.put("message", "Success");
	    return map;
	}
}
