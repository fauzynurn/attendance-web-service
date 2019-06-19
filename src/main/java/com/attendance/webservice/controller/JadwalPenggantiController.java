package com.attendance.webservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.model.JadwalPengganti;
import com.attendance.webservice.model.Jam;
import com.attendance.webservice.model.Ruangan;
import com.attendance.webservice.repository.JadwalKuliahRepository;
import com.attendance.webservice.repository.JadwalPenggantiRepository;

@RestController
public class JadwalPenggantiController {
	@Autowired
	JadwalPenggantiRepository penggantiRepository;
	@Autowired
	JadwalKuliahRepository jadwalRepository;

	@PostMapping("/createpengganti")
	public Map<String, String> createPengganti(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		List<JadwalPengganti> jadwal = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tglKuliah"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tglKuliah"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglKuliah = new java.sql.Date(tgl2.getTime());
		
		List<Integer> idJadwal = jadwalRepository.getIdJadwalByMatkul(request.get("namaMatkul"),
				Boolean.parseBoolean(request.get("jenisMatkul")), request.get("kdKelas"), hari);
		
		if(!penggantiRepository.checkJadwal(idJadwal, tglKuliah).isEmpty()) {
			map.put("status", "404");
			map.put("message", "Failed");
			return map;
		}

		tgl2 = sdf1.parse(request.get("tglPengganti"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglPengganti = new java.sql.Date(tgl2.getTime());

		for(int i = 0; i < idJadwal.size(); i++) {
			JadwalPengganti jdwl = new JadwalPengganti();
			JadwalKuliah kuliah = new JadwalKuliah();
			Jam jam = new Jam();
			Ruangan ruangan = new Ruangan();
			
			kuliah.setIdJadwal(idJadwal.get(i));
			jam.setJamKe(Integer.parseInt(request.get("jamKe")) + i);
			ruangan.setKdRuangan(request.get("kdRuangan"));
		
			jdwl.setTglKuliah(tglKuliah);
			jdwl.setTglPengganti(tglPengganti);
			jdwl.setJadwalKuliah(kuliah);
			jdwl.setJam(jam);
			jdwl.setRuangan(ruangan);
			jadwal.add(jdwl);
		}
		penggantiRepository.saveAll(jadwal);
		
		map.put("status", "200");
		map.put("message", "Success");
		return map;
	}
}
