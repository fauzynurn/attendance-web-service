package com.attendance.webservice.controller;

import java.sql.Time;
import java.sql.Timestamp;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Absensi;
import com.attendance.webservice.repository.AbsensiRepository;
import com.attendance.webservice.repository.JadwalKuliahRepository;
import com.attendance.webservice.repository.JadwalPenggantiRepository;
import com.attendance.webservice.repository.JamRepository;
import com.attendance.webservice.repository.MahasiswaRepository;

@RestController
public class AbsensiController {
	@Autowired
	AbsensiRepository absensiRepository;
	@Autowired
	JadwalPenggantiRepository penggantiRepository;
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	@Autowired
	JamRepository jamRepository;
	@Autowired
	MahasiswaRepository mhsRepository;
	
	@PostMapping("/getkehadiran")
	public List<Map> getKehadiran(@RequestBody HashMap<String, String> request) {
		return absensiRepository.getKehadiran(request.get("nim"));
	}
	
	@PostMapping("/getketidakhadiran")
	public Map<String, String> getKetidakhadiran(@RequestBody HashMap<String, String> request) {
		return absensiRepository.getKetidakhadiran(request.get("nim"));
	}
	
	@PostMapping("/catatkehadiran")
	public Map<String, String> catatKehadiran(@RequestBody Map<String, List<Map<String, String>>> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> r = request.get("listKehadiran");
		for(Map<String, String> req : r) {
		List<Absensi> abs1 = new ArrayList<>();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl = sdf1.parse(req.get("tgl"));
		tgl = sdf2.parse(sdf2.format(tgl));
		java.sql.Date tglAbsensi = new java.sql.Date(tgl.getTime());
		
		Absensi absensi = absensiRepository.getAbsensiPengganti(tglAbsensi, Integer.parseInt(req.get("jamKe")),
				req.get("nim"));
		if(absensi == null) {
			absensi = absensiRepository.getAbsensiKuliah(tglAbsensi, Integer.parseInt(req.get("jamKe")), req.get("nim"));
			if(absensi == null) {
				map.put("status","404");
		        map.put("message", "Failed");
		        return map;
			} else {
				Timestamp ts = absensi.getBeritaAcara().getTglAbsensi();
				Time t = new Time(ts.getTime());
				int jamKe = jamRepository.getJamKe(t);
				if(absensi.getBeritaAcara().getJadwalKuliah().getJam().getJamKe() == jamKe) {
					List<Absensi> abs2 = absensiRepository.getListAbsensiKuliah(tglAbsensi, jamKe, req.get("nim"));
					if(!abs2.isEmpty()) {
						for(Absensi item : abs2) {
							item.setStatusKehadiran(1);
							abs1.add(item);
						}
					}
				}
			}
		} else {
			Timestamp ts = absensi.getBeritaAcara().getTglAbsensi();
			Time t = new Time(ts.getTime());
			int jamKe = jamRepository.getJamKe(t);
			if(absensi.getBeritaAcara().getJadwalPengganti().getJam().getJamKe() == jamKe) {
				List<Absensi> abs2 = absensiRepository.getListAbsensiPengganti(tglAbsensi, jamKe, req.get("nim"));
				if(!abs2.isEmpty()) {
					for(Absensi item : abs2) {
						item.setStatusKehadiran(1);
						abs1.add(item);
					}
				}
			}
		}
		absensi.setStatusKehadiran(1);
		abs1.add(absensi);
		absensiRepository.saveAll(abs1);
		}
			
		map.put("status","200");
	    map.put("message", "Success");
//		return abs1;
	    return map;
	}
	
	@PostMapping("/getdaftarhadir")
	public Map getDaftarHadir(@RequestBody HashMap<String, String> request) throws ParseException {	
		Map map = new LinkedHashMap<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglKuliah = new java.sql.Date(tgl2.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jam = new Time(sdf3.parse(request.get("jamMulai")).getTime());
		Time jam2 = new Time(sdf3.parse(request.get("jamSkrng")).getTime());
		
		List<Map<String, Integer>> idJadwal = penggantiRepository.getIdPenggantiJamKe(jam, tglKuliah, request.get("kdKelas"),
				request.get("kdMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));
		int jamKe = jamRepository.getJamKe(jam2);
		
		if(idJadwal.isEmpty()) {
			idJadwal = jadwalRepository.getIdJadwalJamKe(tglKuliah, jam, hari, request.get("kdKelas"), request.get("kodeMatkul"),
					Boolean.parseBoolean(request.get("jenisMatkul")));
			
			for(Map<String, Integer> item : idJadwal) {
				if((int) item.get("jamKe") == jamKe) {
					map.put("jamKe", item.get("jamKe"));
					map.put("statusKehadiran", absensiRepository.getStatusKehadiranKuliah((int) item.get("idJadwal")));
					return map;
				} else {
					map.put("jamKe", item.get("jamKe"));
					map.put("mhsList", absensiRepository.getStatusKehadiranKuliah((int) item.get("idJadwal")));
				}
			}
			return map;
		} else {			
			for(Map<String, Integer> item : idJadwal) {
				if((int) item.get("jamKe") == jamKe) {
					map.put("jamKe", item.get("jamKe"));
					map.put("statusKehadiran", absensiRepository.getStatusKehadiranPengganti((int) item.get("idPengganti")));
					return map;
				} else {
					map.put("jamKe", item.get("jamKe"));
					map.put("statusKehadiran", absensiRepository.getStatusKehadiranPengganti((int) item.get("idPengganti")));
				}
			}
			return map;
		}
	}
	
	@PostMapping("/getdetailjadwal")
	public List<Map> getDetailJadwal(@RequestBody HashMap<String, String> request) throws ParseException {
		LocalDate tgl1 = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglKuliah = new java.sql.Date(tgl2.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jam = new Time(sdf3.parse(request.get("jamMulai")).getTime());
		
		String kdKelas = mhsRepository.getKdKelas(request.get("nim"));
		List<Map<String, Integer>> idJadwal = penggantiRepository.getIdPenggantiJamKe(jam, tglKuliah, kdKelas,
				request.get("kdMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));

		if(idJadwal.isEmpty()) {
			idJadwal = jadwalRepository.getIdJadwalJamKe(tglKuliah, jam, hari, kdKelas, request.get("kdMatkul"),
					Boolean.parseBoolean(request.get("jenisMatkul")));
			List<Integer> id = new ArrayList<>();
			for(Map<String, Integer> item : idJadwal) {
				id.add((int) item.get("idJadwal"));
			}
			
			return absensiRepository.getStatusMhsKuliah(id, request.get("nim"));
		} else {
			List<Integer> id = new ArrayList<>();
			for(Map<String, Integer> item : idJadwal) {
				id.add((int) item.get("idPengganti"));
			}
			
			return absensiRepository.getStatusMhsPengganti(id, request.get("nim"));
		}
	}
	
	@GetMapping("/getabsensiharian")
	public List<Map<String, String>> getAbsensiHarian(@RequestBody Map<String, String> request) throws ParseException {		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tgl = new java.sql.Date(tgl2.getTime());
		
		return absensiRepository.getListAbsensiHarian(request.get("kdKelas"), tgl, Integer.parseInt(request.get("jamKe")));
	}
	
//	@CrossOrigin
	@PostMapping("/ubahkehadiran")
	public Map<String, String> ubahKehadiran(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tanggal"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tgl = new java.sql.Date(tgl2.getTime());
		
		Absensi absensi = absensiRepository.getAbsensiHarian(request.get("nim"), tgl, Integer.parseInt(request.get("sesi")));
		switch(request.get("status")) {
			case "hadir":
				absensi.setStatusKehadiran(1);
				break;
			case "sakit":
				absensi.setStatusKehadiran(2);
				break;
			case "izin":
				absensi.setStatusKehadiran(3);
				break;
			case "alpa":
				absensi.setStatusKehadiran(4);
				break;
		}
		absensiRepository.save(absensi);
		
		map.put("status", "200");
		map.put("message", "Success");
		return map;
//		return absensi;
	}
	
	@GetMapping("/dropdownjamke")
	public List<Integer> dropdownJamKe(@RequestBody Map<String, String> request) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tanggal"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tgl = new java.sql.Date(tgl2.getTime());
		
		return absensiRepository.dropdownJamKe(request.get("kdKelas"), tgl);
	}
}
