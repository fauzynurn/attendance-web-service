package com.attendance.webservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.repository.JadwalKuliahRepository;
import com.attendance.webservice.repository.JadwalPenggantiRepository;

@RestController
public class JadwalKuliahController {
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	@Autowired
	JadwalPenggantiRepository penggantiRepository;
	
	@GetMapping("/getjadwalmhs")
	public Map<String, List<Map>> getJadwalMhs(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, List<Map>> jadwal = new LinkedHashMap<>();
		List<Map> jadwalMaps = new ArrayList<>();
		List<Map> penggantiMaps = new ArrayList<>();
		
		LocalDate now = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String hari = now.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date tgl = sdf.parse(request.get("tgl"));
		List<Map> jadwalKuliah = jadwalRepository.getJadwalMhs(tgl, hari, request.get("kdKelas"));
		List<Map> jadwalPengganti = penggantiRepository.getJadwalPenggantiMhs(tgl);
		
		for(Map item : jadwalKuliah) {
			Map map = new LinkedHashMap<>();
			List<String> dosen = new ArrayList<>();
			List<String> namaDosen = jadwalRepository.getNamaDosen((int) item.get("idJadwal"));
			for(int i = 0; i < namaDosen.size(); i++) {
				dosen.add(namaDosen.get(i));
			}
			
			map.put("namaMatkul", item.get("namaMatkul"));
			map.put("jenisMatkul", item.get("jenisMatkul"));
			map.put("namaDosen", dosen);
			map.put("jamMulai", item.get("jamMulai"));
			map.put("jamSelesai", item.get("jamSelesai"));
			map.put("kodeRuangan", item.get("kodeRuangan"));
			map.put("macAddress", item.get("macAddress"));
			map.put("jamMulaiOlehDosen", item.get("tglAbsensi"));
			jadwalMaps.add(map);
		}
		
		for(Map item : jadwalPengganti) {
			Map map = new LinkedHashMap<>();
			List<String> dosen = new ArrayList<>();
			List<String> namaDosen = jadwalRepository.getNamaDosen((int) item.get("idJadwal"));
			for(int i = 0; i < namaDosen.size(); i++) {
				dosen.add(namaDosen.get(i));
			}
			
			map.put("namaMatkul", item.get("namaMatkul"));
			map.put("jenisMatkul", item.get("jenisMatkul"));
			map.put("namaDosen", dosen);
			map.put("jamMulai", item.get("jamMulai"));
			map.put("jamSelesai", item.get("jamSelesai"));
			map.put("kodeRuangan", item.get("kodeRuangan"));
			map.put("macAddress", item.get("macAddress"));
			map.put("jamMulaiOlehDosen", item.get("tglAbsensi"));
			penggantiMaps.add(map);
		}
		
		jadwal.put("jadwalReguler", jadwalMaps);
		jadwal.put("jadwalPengganti", penggantiMaps);
		return jadwal;
	}
	
//	@GetMapping("/getjadwaldosen")
//	public List<Map> getJadwalDosen(@RequestBody Map<String, String> request) throws ParseException {
//		List<Map> maps = new ArrayList<>();
//		LocalDate now = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//		String hari = now.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		Date tgl = sdf.parse(request.get("tgl"));
//		List<Map> jadwal = jadwalRepository.getJadwalDosen(hari, tgl, request.get("kdDosen"));
//		
//		for(Map item : jadwal) {
//			Map map = new LinkedHashMap<>();
//			Map jamRuangan= jadwalRepository.getJamRuangan((int) item.get("jamKe"), (String) item.get("kodeRuangan"));
//			
//			map.put("namaMatkul", item.get("namaMatkul"));
//			map.put("jenisMatkul", item.get("jenisMatkul"));
//			map.put("kodeKelas", item.get("kodeKelas"));
//			map.put("jamMulai", jamRuangan.get("jamMulai"));
//			map.put("jamSelesai", jamRuangan.get("jamSelesai"));
//			map.put("kodeRuangan", item.get("kodeRuangan"));
//			map.put("macAddress", jamRuangan.get("macAddress"));
//			map.put("kodeJadwal", item.get("kodeJadwal"));
//			maps.add(map);
//		}
//		return maps;
//	}
}
