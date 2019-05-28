package com.attendance.webservice.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.BeritaAcara;
import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.repository.BeritaAcaraRepository;
import com.attendance.webservice.repository.JadwalKuliahRepository;

@RestController
public class BeritaAcaraController {
	@Autowired
	BeritaAcaraRepository beritaRepository;
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	
//	@PostMapping("/mulaikbm")
//	public Map<String, String> mulaiKbm(@RequestBody Map<String, String> request) {
//		Map<String, String> map = new LinkedHashMap<>();
//		List<BeritaAcara> berita = new ArrayList<>();
//		if(request.get("kdJadwal") == "1") {
//			List<Integer> idJadwal = jadwalRepository.getIdJadwalKuliah(request.get("kdMatkul"), request.get("kdKelas"),
//				request.get("hari"));
//		} else if(request.get("kdJadwal") == "2") {
//			List<Integer> idJadwal = jadwalRepository.getIdJadwalSementara(request.get("namaMatkul"), request.get("kdKelas"),
//				request.get("hari"));
//		}
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
