package com.attendance.webservice.controller;

import java.sql.Time;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.BeritaAcara;
import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.model.JadwalPengganti;
import com.attendance.webservice.model.Jam;
import com.attendance.webservice.model.Ruangan;
import com.attendance.webservice.repository.BeritaAcaraRepository;
import com.attendance.webservice.repository.JadwalKuliahRepository;
import com.attendance.webservice.repository.JadwalPenggantiRepository;
import com.attendance.webservice.repository.JamRepository;
import com.attendance.webservice.repository.RuanganRepository;

@RestController
public class JadwalPenggantiController {
	@Autowired
	JadwalPenggantiRepository penggantiRepository;
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	@Autowired
	JamRepository jamRepository;
	@Autowired
	RuanganRepository ruanganRepository;
	@Autowired
	BeritaAcaraRepository beritaRepository;

	@PostMapping("/buatjadwalpengganti")
	public Map<String, String> buatJadwalPengganti(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		List<JadwalPengganti> listPengganti = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tglKuliah"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tglKuliah"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglKuliah = new java.sql.Date(tgl2.getTime());
		
		tgl2 = sdf1.parse(request.get("tglPengganti"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglPengganti = new java.sql.Date(tgl2.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jamMulai = new Time(sdf3.parse(request.get("jamMulai")).getTime());
		
		List<JadwalKuliah> listKuliah = jadwalRepository.getListJadwalByMatkul(tglKuliah, hari, request.get("kdKelas"),
				request.get("namaMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));
		
		if(listKuliah.isEmpty()) {
			listPengganti = penggantiRepository.getListJadwalByTgl(tglKuliah, request.get("kdKelas"), request.get("namaMatkul"),
					Boolean.parseBoolean(request.get("jenisMatkul")));
			if(listPengganti.isEmpty()) {
				map.put("status", "404");
				map.put("message", "Failed");	
			} else {
				int i = 0;
				for(JadwalPengganti item : listPengganti) {
					Jam jam = new Jam();
					Ruangan ruangan = new Ruangan();
					
					jam.setJamKe(jamRepository.getJamKe(jamMulai) + i);
					ruangan.setKdRuangan(request.get("kdRuangan"));
					
					item.setTglPengganti(tglPengganti);
					item.setJam(jam);
					item.setRuangan(ruangan);
					i++;
				}
				penggantiRepository.saveAll(listPengganti);
				
				map.put("status", "200");
				map.put("message", "Success");
			}
		} else {
			int i = 0;
			for(JadwalKuliah item : listKuliah) {
				JadwalPengganti pengganti = new JadwalPengganti();
				JadwalKuliah kuliah = new JadwalKuliah();
				Jam jam = new Jam();
				Ruangan ruangan = new Ruangan();
				
				kuliah.setIdJadwal(item.getIdJadwal());
				jam.setJamKe(jamRepository.getJamKe(jamMulai) + i);
				ruangan.setKdRuangan(request.get("kdRuangan"));
				
				pengganti.setTglKuliah(tglKuliah);
				pengganti.setTglPengganti(tglPengganti);
				pengganti.setJadwalKuliah(kuliah);
				pengganti.setJam(jam);
				pengganti.setRuangan(ruangan);
				listPengganti.add(pengganti);
				i++;
			}
			penggantiRepository.saveAll(listPengganti);
			
			map.put("status", "200");
			map.put("message", "Success");
		}
		return map;
	}
	
	@PostMapping("/getjadwalpengganti")
	public Map<String, List<Map>> getJadwalPengganti(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, List<Map>> jadwal = new LinkedHashMap<>();
		List<Map> maps = new ArrayList<>();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tgl = new java.sql.Date(tgl2.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jamSkrng = new Time(sdf3.parse(request.get("jamSkrng")).getTime());
		
		Integer jamKe = jamRepository.getJamKe(jamSkrng);
		List<JadwalPengganti> pengganti = penggantiRepository.getListJadwalByJam(tgl, jamKe, request.get("kdDosen"));
		for(JadwalPengganti item : pengganti) {
			Map map = new LinkedHashMap<>();
			Map<String, String> mapTgl = new LinkedHashMap<>();
			Map<String, String> ruangan = new LinkedHashMap<>();
				
			List<Jam> jam = penggantiRepository.getListJam(item.getTglPengganti(), item.getJadwalKuliah().getKelas().getKdKelas(),
					item.getJadwalKuliah().getMatkul().getIdMatkul(), item.getTglKuliah());
				
			map.put("namaMatkul", item.getJadwalKuliah().getMatkul().getNamaMatkul());
			map.put("jenisMatkul", item.getJadwalKuliah().getMatkul().getJenisMatkul());
			map.put("kodeMatkul", item.getJadwalKuliah().getMatkul().getKdMatkul());
			map.put("kelas", item.getJadwalKuliah().getKelas().getKdKelas());
			map.put("jamMulai", jam.get(0).getJamMulai());
			map.put("jamSelesai", jam.get(jam.size() - 1).getJamSelesai());
			ruangan.put("kodeRuangan", item.getRuangan().getKdRuangan());
			ruangan.put("macAddress", item.getRuangan().getMacAddress());
			map.put("ruangan", ruangan);
			map.put("jamMulaiOlehDosen", "");
			mapTgl.put("tglKuliah", sdf1.format(item.getTglKuliah()));
			mapTgl.put("tglPengganti", sdf1.format(item.getTglPengganti()));
			map.put("tglJwlPengganti", mapTgl);
			maps.add(map);
		}
		jadwal.put("listJadwal", maps);
		return jadwal;
	}
	
	@DeleteMapping("/hapusjadwalpengganti")
	public Map hapusJadwalPengganti(@RequestBody Map<String, String> request) throws ParseException {
		Map map = new LinkedHashMap<>();
		Map<String, String> data = new LinkedHashMap<>();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tglKuliah"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tgl = new java.sql.Date(tgl2.getTime());
				
		List<JadwalPengganti> pengganti = penggantiRepository.getListJadwalByTgl(tgl, request.get("kdKelas"),
				request.get("namaMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));
		for(JadwalPengganti item : pengganti) {
			penggantiRepository.delete(item);
		}
		
		map.put("status", "200");
		map.put("message", "Success");
		map.put("data", data);
		return map;
	}
	
	@PostMapping("/dropdownmatkul")
	public Map<String, List<Map>> dropdownMatkul(@RequestBody Map<String, String> request) {
		List<Map> maps = new ArrayList<>();
		Map<String, List<Map>> matkul = new LinkedHashMap<>();

		List<JadwalKuliah> jadwal = jadwalRepository.getListJadwalByDosen(request.get("kdDosen"));
		for(JadwalKuliah item : jadwal) {
			Map map = new LinkedHashMap<>();
			map.put("namaMatkul", item.getMatkul().getNamaMatkul());
			map.put("jenisMatkul", item.getMatkul().getJenisMatkul());
			map.put("listKelas", jadwalRepository.getKdKelas(request.get("kdDosen"), item.getMatkul().getIdMatkul()));
			maps.add(map);
		}
		matkul.put("listMatkul", maps);
		return matkul;
	}
	
	@PostMapping("/dropdownjam")
	public Map<String, List<String>> dropdownJam(@RequestBody Map<String, String> request) throws ParseException {
		List<String> listJam = new ArrayList<>();
		Map<String, List<String>> map = new LinkedHashMap<>();
		List<Jam> jam = new ArrayList<>();
		List<Integer> idJadwal = new ArrayList<>();
		List<Integer> idPengganti = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tglPengganti"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
		java.util.Date tgl2 = sdf1.parse(request.get("tglPengganti"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglPengganti = new java.sql.Date(tgl2.getTime());
		
		java.util.Date tgl3 = sdf1.parse(request.get("tglKuliah"));
		tgl3 = sdf2.parse(sdf2.format(tgl3));
		java.sql.Date tglKuliah = new java.sql.Date(tgl3.getTime());
		
		List<JadwalKuliah> kuliah = jadwalRepository.getListJadwalByKelas(request.get("kdKelas"), request.get("namaMatkul"),
				Boolean.parseBoolean(request.get("jenisMatkul")));
		for(JadwalKuliah item : kuliah) {
			idJadwal.add(item.getIdJadwal());
		}
		List<JadwalPengganti> pengganti = penggantiRepository.getListJadwalByTgl(tglKuliah, request.get("kdKelas"),
				request.get("namaMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));
		if(!tglKuliah.equals(tglPengganti)) {
			if(pengganti.isEmpty()) {
				jam = jamRepository.getJamNotEqualsIsEmpty(hari, request.get("kdKelas"), tglPengganti);
			} else {
				for(JadwalPengganti item : pengganti) {
					idPengganti.add(item.getIdPengganti());
				}
				jam = jamRepository.getJamNotEqualsNotEmpty(hari, request.get("kdKelas"), tglPengganti, idPengganti);
			}
		} else {
			if(pengganti.isEmpty()) {
				jam = jamRepository.getJamIsEqualsIsEmpty(hari, request.get("kdKelas"), tglPengganti, idJadwal);
			} else {
				for(JadwalPengganti item : pengganti) {
					idPengganti.add(item.getIdPengganti());
				}
				jam = jamRepository.getJamIsEqualsNotEmpty(hari, request.get("kdKelas"), tglPengganti, idPengganti, idJadwal);
			}
		}
		if(jam.size() >= kuliah.size()) {
			for(int i = 0; i < jam.size(); i++) {
				int j = 1;
				boolean b = true;
				while(j < kuliah.size() && b && jam.size() >= i + kuliah.size()) {
					if(jam.get(i).getJamKe() + j != jam.get(i + j).getJamKe()) {
						b = false;
					}
					j++;
				}
				if(b && jam.size() >= i + kuliah.size()) {
					listJam.add(sdf3.format(jam.get(i).getJamMulai()));
				}
			}
		}
		
		map.put("jamKosong", listJam);
		return map;
	}
	
	@PostMapping("/dropdownruangan")
	public Map<String, List<String>> dropdownRuangan(@RequestBody Map<String, String> request) throws ParseException {
		List<Integer> listJam = new ArrayList<>();
		Map<String, List<String>> map = new LinkedHashMap<>();		
		List<String> ruangan = new ArrayList<>();
		List<Integer> idJadwal = new ArrayList<>();
		List<Integer> idPengganti = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tglPengganti"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tglPengganti"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglPengganti = new java.sql.Date(tgl2.getTime());
		
		java.util.Date tgl3 = sdf1.parse(request.get("tglKuliah"));
		tgl3 = sdf2.parse(sdf2.format(tgl3));
		java.sql.Date tglKuliah = new java.sql.Date(tgl3.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jamMulai = new Time(sdf3.parse(request.get("jamMulai")).getTime());

		int jamKe = jamRepository.getJamKe(jamMulai);
		List<JadwalKuliah> kuliah = jadwalRepository.getListJadwalByKelas(request.get("kdKelas"), request.get("namaMatkul"),
				Boolean.parseBoolean(request.get("jenisMatkul")));
		int i = 0;
		for(JadwalKuliah item : kuliah) {
			idJadwal.add(item.getIdJadwal());
			listJam.add(jamKe + i);
			i++;
		}
		List<JadwalPengganti> pengganti = penggantiRepository.getListJadwalByTgl(tglKuliah, request.get("kdKelas"),
				request.get("namaMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));
		if(!tglKuliah.equals(tglPengganti)) {
			if(pengganti.isEmpty()) {
				ruangan = ruanganRepository.getKdRuanganNotEqualsIsEmpty(hari, listJam, tglPengganti);
			} else {
				for(JadwalPengganti item : pengganti) {
					idPengganti.add(item.getIdPengganti());
				}
				ruangan = ruanganRepository.getKdRuanganNotEqualsNotEmpty(hari, listJam, tglPengganti, idPengganti);
			}
		} else {
			if(pengganti.isEmpty()) {
				ruangan = ruanganRepository.getKdRuanganIsEqualsIsEmpty(hari, listJam, tglPengganti, idJadwal);
			} else {
				for(JadwalPengganti item : pengganti) {
					idPengganti.add(item.getIdPengganti());
				}
				ruangan = ruanganRepository.getKdRuanganIsEqualsNotEmpty(hari, listJam, tglPengganti, idJadwal, idPengganti);
			}
		}
		
		map.put("ruanganKosong", ruangan);
		return map;
	}
}
