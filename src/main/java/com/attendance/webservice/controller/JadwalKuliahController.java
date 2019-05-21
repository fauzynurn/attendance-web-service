package com.attendance.webservice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.repository.JadwalKuliahRepository;

@RestController
public class JadwalKuliahController {
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	
	@GetMapping("/getjadwal")
	public List<Map> getJadwal(@RequestBody HashMap<String, String> request) {
//		List<Map> maps = new ArrayList<>();
		LocalDate now = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = now.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		List<Map> jadwal = jadwalRepository.fetchJadwalMhs(request.get("kdKelas"), hari);
//		for(Map item : jadwal) {
//			Map map = new HashMap<>();
//			map.put("namaMatkul", item.get("namaMatkul"));
//			map.put("jenisMatkul", item.get("jenisMatkul"));
//			map.put("namaDosen", item.get("namaDosen"));
//			map.put("jamMulai", item.get("jamMulai"));
//			map.put("jamSelesai", item.get("jamSelesai"));
//			map.put("kodeRuangan", item.get("kodeRuangan"));
//			map.put("macAddress", item.get("macAddress"));
//			maps.add(map);
//		}
		return jadwal;
	}
	
	@GetMapping("/getjadwaldosen")
	public List<Map> getJadwalDosen(@RequestBody HashMap<String, String> request) {
		LocalDate now = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = now.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));

		List<Map> jadwal = jadwalRepository.fetchJadwalDosen(request.get("kdDosen"), hari);
		return jadwal;
	}
}
