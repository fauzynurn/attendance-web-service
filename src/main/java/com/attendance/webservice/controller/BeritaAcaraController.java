package com.attendance.webservice.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.BeritaAcara;
import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.service.BeritaAcaraService;

@RestController
public class BeritaAcaraController {
	@Autowired
	BeritaAcaraService beritaService;
	
	@PostMapping("/aktifabsensi")
	public Map<String, String> attendanceActive(@RequestBody HashMap<String, String> request) {
		HashMap<String, String> map = new HashMap<>();
		List<BeritaAcara> berita = new ArrayList<>();
		List idJadwal = beritaService.fetchIdJadwalDataInnerJoin(request.get("namaMatkul"), request.get("kdKelas"),
				request.get("hari"));
		Date now = new Date(System.currentTimeMillis());

		for(int i = 0; i < idJadwal.size(); i++) {
			BeritaAcara brt = new BeritaAcara();
			JadwalKuliah jdwl = new JadwalKuliah();
			brt.setTglAbsensi(now);
			jdwl.setIdJadwal((int) idJadwal.get(i));
			brt.setJadwalKuliah(jdwl);
			berita.add(brt);
		}
		
		for(int i = 0; i < berita.size(); i++) {
			beritaService.save(berita.get(i));
		}
		
        map.put("status","200");
        map.put("message", "Success");
        return map;
	}
}
