package com.attendance.webservice.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.repository.AbsensiRepository;
import com.attendance.webservice.repository.JadwalKuliahRepository;
import com.attendance.webservice.repository.JadwalPenggantiRepository;

@RestController
public class JadwalKuliahController {
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	@Autowired
	JadwalPenggantiRepository penggantiRepository;
	@Autowired
	AbsensiRepository absensiRepository;
	
	@PostMapping("/getjadwalmhs")
	public Map<String, List<Map>> getJadwalMhs(@RequestBody HashMap<String, String> request) throws ParseException {
		Map<String, List<Map>> jadwal = new LinkedHashMap<>();
		List<Map> maps1 = new ArrayList<>();
		List<Map> maps2 = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglKuliah = new java.sql.Date(tgl2.getTime());
		
		List<Map> jadwalKuliah = jadwalRepository.getJadwalMhs(tglKuliah, hari, request.get("kdKelas"));
		List<Map> jadwalPengganti = penggantiRepository.getJadwalMhs(tglKuliah, request.get("kdKelas"));
		
		for(Map item : jadwalKuliah) {
			Map map = new LinkedHashMap<>();
//			List<Map> jam = absensiRepository.getJamKuliah(tglKuliah, hari, request.get("kdKelas"), (int) item.get("idMatkul"),
//					request.get("nim"));
			List<Map> jam = jadwalRepository.getJam(tglKuliah, hari, request.get("kdKelas"), (int) item.get("idMatkul"));
			List<String> namaDosen = jadwalRepository.getNamaDosen((int) item.get("idJadwal"));
			
			Map<String, String> ruangan = new LinkedHashMap<>();
			ruangan.put("kodeRuangan", (String) item.get("kodeRuangan"));
			ruangan.put("macAddress", (String) item.get("macAddress"));
			
			map.put("namaMatkul", item.get("namaMatkul"));
			map.put("jenisMatkul", item.get("jenisMatkul"));
			map.put("kodeMatkul", item.get("kodeMatkul"));
			map.put("dosen", namaDosen);
			map.put("jamMulai", jam.get(0).get("jamMulai"));
			map.put("jamSelesai", jam.get(jam.size() - 1).get("jamSelesai"));
			map.put("ruangan", ruangan);
			if(item.get("tglAbsensi") != null) {
				map.put("jamMulaiOlehDosen", item.get("tglAbsensi"));
			} else {
				map.put("jamMulaiOlehDosen", "");
			}
			map.put("listSesi", jam);
			maps1.add(map);
		}
		
		for(Map item : jadwalPengganti) {
			Map map = new LinkedHashMap<>();
//			List<Map> jam = absensiRepository.getJamPengganti(tglKuliah, request.get("kdKelas"), (int) item.get("idMatkul"),
//					request.get("nim"));
			List<Map> jam = penggantiRepository.getJam(tglKuliah, request.get("kdKelas"), (int) item.get("idMatkul"));
			List<String> namaDosen = penggantiRepository.getNamaDosen((int) item.get("idPengganti"));
			
			Map<String, String> ruangan = new LinkedHashMap<>();
			ruangan.put("kodeRuangan", (String) item.get("kodeRuangan"));
			ruangan.put("macAddress", (String) item.get("macAddress"));
			
			map.put("namaMatkul", item.get("namaMatkul"));
			map.put("jenisMatkul", item.get("jenisMatkul"));
			map.put("kodeMatkul", item.get("kodeMatkul"));
			map.put("dosen", namaDosen);
			map.put("jamMulai", jam.get(0).get("jamMulai"));
			map.put("jamSelesai", jam.get(jam.size() - 1).get("jamSelesai"));
			map.put("ruangan", ruangan);
			if(item.get("tglAbsensi") != null) {
				map.put("jamMulaiOlehDosen", item.get("tglAbsensi"));
			} else {
				map.put("jamMulaiOlehDosen", "");
			}
			map.put("listSesi", jam);
			maps2.add(map);
		}
		
		jadwal.put("jadwalReguler", maps1);
		jadwal.put("jadwalPengganti", maps2);
		return jadwal;
	}
	
	@PostMapping("/getjadwaldsn")
	public Map<String, List<Map>> getJadwalDsn(@RequestBody HashMap<String, String> request) throws ParseException {
		Map<String, List<Map>> jadwal = new LinkedHashMap<>();
		List<Map> maps1 = new ArrayList<>();
		List<Map> maps2 = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglKuliah = new java.sql.Date(tgl2.getTime());
		
		List<Map> jadwalKuliah = jadwalRepository.getJadwalDosen(tglKuliah, hari, request.get("kddsn"));
		List<Map> jadwalPengganti = penggantiRepository.getJadwalDosen(tglKuliah, request.get("kddsn"));
		
		for(Map item : jadwalKuliah) {
			Map map = new LinkedHashMap<>();
			List<Map> jam = jadwalRepository.getJam(tglKuliah, hari, (String) item.get("kodeKelas"), (int) item.get("idMatkul"));
			
			Map<String, String> ruangan = new LinkedHashMap<>();
			ruangan.put("kodeRuangan", (String) item.get("kodeRuangan"));
			ruangan.put("macAddress", (String) item.get("macAddress"));
			
			map.put("namaMatkul", item.get("namaMatkul"));
			map.put("jenisMatkul", item.get("jenisMatkul"));
			map.put("kodeMatkul", item.get("kodeMatkul"));
			map.put("kodeKelas", item.get("kodeKelas"));
			map.put("jamMulai", jam.get(0).get("jamMulai"));
			map.put("jamSelesai", jam.get(jam.size() - 1).get("jamSelesai"));
			map.put("ruangan", ruangan);
			if(item.get("tglAbsensi") != null) {
				map.put("jamMulaiOlehDosen", item.get("tglAbsensi"));
			} else {
				map.put("jamMulaiOlehDosen", "");
			}
			maps1.add(map);
		}
		
		for(Map item : jadwalPengganti) {
			Map map = new LinkedHashMap<>();
			List<Map> jam = penggantiRepository.getJam(tglKuliah, (String) item.get("kodeKelas"), (int) item.get("idMatkul"));
			
			Map<String, String> ruangan = new LinkedHashMap<>();
			ruangan.put("kodeRuangan", (String) item.get("kodeRuangan"));
			ruangan.put("macAddress", (String) item.get("macAddress"));
			
			map.put("namaMatkul", item.get("namaMatkul"));
			map.put("jenisMatkul", item.get("jenisMatkul"));
			map.put("kodeMatkul", item.get("kodeMatkul"));
			map.put("kodeKelas", item.get("kodeKelas"));
			map.put("jamMulai", jam.get(0).get("jamMulai"));
			map.put("jamSelesai", jam.get(jam.size() - 1).get("jamSelesai"));
			map.put("ruangan", ruangan);
			if(item.get("tglAbsensi") != null) {
				map.put("jamMulaiOlehDosen", item.get("tglAbsensi"));
			} else {
				map.put("jamMulaiOlehDosen", "");
			}
			maps2.add(map);
		}
		
		jadwal.put("jadwalReguler", maps1);
		jadwal.put("jadwalPengganti", maps2);
		return jadwal;
	}
}
