package com.attendance.webservice.controller;

import java.sql.Date;
import java.sql.Time;
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
import com.attendance.webservice.repository.BeritaAcaraRepository;

@RestController
public class BeritaAcaraController {
	@Autowired
	BeritaAcaraRepository beritaRepository;
	
//	@PostMapping("/aktifabsensi")
//	public Map<String, String> aktifAbsensi(@RequestBody HashMap<String, String> request) {
//		HashMap<String, String> map = new HashMap<>();
//		List<BeritaAcara> berita = new ArrayList<>();
//		List<Integer> idJadwal = beritaRepository.fetchIdJadwal(request.get("namaMatkul"), request.get("kdKelas"),
//				request.get("hari"));
//		Date date = new Date(System.currentTimeMillis());
//		Time time = new Time(System.currentTimeMillis());
//		String now = time.toString();
//
//		for(int i = 0; i < idJadwal.size(); i++) {
//			BeritaAcara brt = new BeritaAcara();
//			JadwalKuliah jdwl = new JadwalKuliah();
//			brt.setTglAbsensi(date);
//			jdwl.setIdJadwal(idJadwal.get(i));
//			brt.setJadwalKuliah(jdwl);
//			berita.add(brt);
//		}
//		
//		for(int i = 0; i < berita.size(); i++) {
//			beritaRepository.save(berita.get(i));
//		}
//		
//        map.put("status","200");
//        map.put("message", now);
//        return map;
//	}
}
