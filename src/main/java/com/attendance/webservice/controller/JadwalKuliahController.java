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
		
		LocalDate now = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = now.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date tgl = sdf1.parse(request.get("tgl"));
		tgl = sdf2.parse(sdf2.format(tgl));
		List<Map> jadwalKuliah = jadwalRepository.getJadwalMhs(tgl, hari, request.get("kdKelas"));
		List<Map> jadwalPengganti = penggantiRepository.getJadwalPenggantiMhs(tgl, request.get("kdKelas"));
		
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
	
	@GetMapping("/getjadwaldosen")
	public Map<String, List<Map>> getJadwalDosen(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, List<Map>> jadwal = new LinkedHashMap<>();
		List<Map> jadwalMaps = new ArrayList<>();
		List<Map> penggantiMaps = new ArrayList<>();
		
		LocalDate now = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = now.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date tgl = sdf1.parse(request.get("tgl"));
		tgl = sdf2.parse(sdf2.format(tgl));
		List<Map> jadwalKuliah = jadwalRepository.getJadwalDosen(tgl, hari, request.get("kdDosen"));
		List<Map> jadwalPengganti = penggantiRepository.getJadwalPenggantiDosen(tgl, request.get("kdDosen"));
		
		for(Map item : jadwalKuliah) {
			Map map = new LinkedHashMap<>();
			
			map.put("namaMatkul", item.get("namaMatkul"));
			map.put("jenisMatkul", item.get("jenisMatkul"));
			map.put("kodeKelas", item.get("kodeKelas"));
			map.put("jamMulai", item.get("jamMulai"));
			map.put("jamSelesai", item.get("jamSelesai"));
			map.put("kodeRuangan", item.get("kodeRuangan"));
			map.put("macAddress", item.get("macAddress"));
			map.put("jamMulaiOlehDosen", item.get("tglAbsensi"));
			jadwalMaps.add(map);
		}
		
		for(Map item : jadwalPengganti) {
			Map map = new LinkedHashMap<>();
			
			map.put("namaMatkul", item.get("namaMatkul"));
			map.put("jenisMatkul", item.get("jenisMatkul"));
			map.put("kodeKelas", item.get("kodeKelas"));
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
}
