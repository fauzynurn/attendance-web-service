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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Absensi;
import com.attendance.webservice.model.BeritaAcara;
import com.attendance.webservice.model.Dosen;
import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.model.JadwalPengganti;
import com.attendance.webservice.model.Jam;
import com.attendance.webservice.model.Mahasiswa;
import com.attendance.webservice.repository.AbsensiRepository;
import com.attendance.webservice.repository.BeritaAcaraRepository;
import com.attendance.webservice.repository.JadwalKuliahRepository;
import com.attendance.webservice.repository.JadwalPenggantiRepository;
import com.attendance.webservice.repository.MahasiswaRepository;

@RestController
public class JadwalKuliahController {
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	@Autowired
	JadwalPenggantiRepository penggantiRepository;
	@Autowired
	AbsensiRepository absensiRepository;
	@Autowired
	BeritaAcaraRepository beritaRepository;
	@Autowired
	MahasiswaRepository mhsRepository;
	
	@PostMapping("/getjadwalmhs")
	public Map<String, List<Map>> getJadwalMhs(@RequestBody Map<String, String> request) throws ParseException {
		List<Map> listKuliah = new ArrayList<>();
		List<Map> listPengganti = new ArrayList<>();
		Map<String, List<Map>> jadwal = new LinkedHashMap<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tgl = new java.sql.Date(tgl2.getTime());
		
		Mahasiswa mhs = mhsRepository.findByNim(request.get("nim"));
		List<JadwalKuliah> kuliah = jadwalRepository.getListJadwalMhs(tgl, hari, mhs.getKelas().getKdKelas());
		List<JadwalPengganti> pengganti = penggantiRepository.getListJadwalMhs(tgl, mhs.getKelas().getKdKelas());
		
		for(JadwalKuliah item : kuliah) {
			Map map = new LinkedHashMap<>();
			List<Map> listJam = new ArrayList<>();
			List<String> listDosen = new ArrayList<>();
			Map<String, String> ruangan = new LinkedHashMap<>();
			
			List<Jam> jam = jadwalRepository.getListJam(tgl, hari, mhs.getKelas().getKdKelas(), item.getMatkul().getIdMatkul());
			List<Dosen> dosen = jadwalRepository.getListDosen(item.getIdJadwal());
			BeritaAcara berita = beritaRepository.getBeritaAcaraKuliah(tgl, item.getIdJadwal());
			for(Dosen itemDosen : dosen) {
				listDosen.add(itemDosen.getNamaDosen());
			}
			
			map.put("namaMatkul", item.getMatkul().getNamaMatkul());
			map.put("jenisMatkul", item.getMatkul().getJenisMatkul());
			map.put("kodeMatkul", item.getMatkul().getKdMatkul());
			map.put("dosen", listDosen);
			map.put("jamMulai", jam.get(0).getJamMulai());
			map.put("jamSelesai", jam.get(jam.size() - 1).getJamSelesai());
			ruangan.put("kodeRuangan", item.getRuangan().getKdRuangan());
			ruangan.put("macAddress", item.getRuangan().getMacAddress());
			map.put("ruangan", ruangan);
			if(berita != null) {
				Time t = new Time(berita.getTglAbsensi().getTime());
				map.put("jamMulaiOlehDosen", t);
				for(Jam itemJam : jam) {
					Map mapJam = new LinkedHashMap<>();
					mapJam.put("sesi", itemJam.getJamKe());
					mapJam.put("jamMulai", itemJam.getJamMulai());
					mapJam.put("jamSelesai", itemJam.getJamSelesai());
					Absensi absensi = absensiRepository.getAbsensiKuliah(tgl, itemJam.getJamKe(), request.get("nim"));
					if(absensi.getStatusKehadiran() == 1) {
						mapJam.put("status", true);						
					} else {
						mapJam.put("status", false);
					}
					listJam.add(mapJam);
				}
			} else {
				map.put("jamMulaiOlehDosen", "");
				for(Jam itemJam : jam) {
					Map mapJam = new LinkedHashMap<>();
					mapJam.put("sesi", itemJam.getJamKe());
					mapJam.put("jamMulai", itemJam.getJamMulai());
					mapJam.put("jamSelesai", itemJam.getJamSelesai());
					mapJam.put("status", false);
					listJam.add(mapJam);
				}
			}
			map.put("listSesi", listJam);
			listKuliah.add(map);
		}
		
		for(JadwalPengganti item : pengganti) {
			Map map = new LinkedHashMap<>();
			List<Map> listJam = new ArrayList<>();
			List<String> listDosen = new ArrayList<>();
			Map<String, String> ruangan = new LinkedHashMap<>();
			
			List<Jam> jam = penggantiRepository.getListJam(tgl, mhs.getKelas().getKdKelas(),
					item.getJadwalKuliah().getMatkul().getIdMatkul());
			List<Dosen> dosen = penggantiRepository.getListDosen(item.getIdPengganti());
			BeritaAcara berita = beritaRepository.getBeritaAcaraPengganti(tgl, item.getIdPengganti());
			for(Dosen itemDosen : dosen) {
				listDosen.add(itemDosen.getNamaDosen());
			}
			
			map.put("namaMatkul", item.getJadwalKuliah().getMatkul().getNamaMatkul());
			map.put("jenisMatkul", item.getJadwalKuliah().getMatkul().getJenisMatkul());
			map.put("kodeMatkul", item.getJadwalKuliah().getMatkul().getKdMatkul());
			map.put("dosen", listDosen);
			map.put("jamMulai", jam.get(0).getJamMulai());
			map.put("jamSelesai", jam.get(jam.size() - 1).getJamSelesai());
			ruangan.put("kodeRuangan", item.getRuangan().getKdRuangan());
			ruangan.put("macAddress", item.getRuangan().getMacAddress());
			map.put("ruangan", ruangan);
			if(berita != null) {
				Time t = new Time(berita.getTglAbsensi().getTime());
				map.put("jamMulaiOlehDosen", t);
				for(Jam itemJam : jam) {
					Map mapJam = new LinkedHashMap<>();
					mapJam.put("sesi", itemJam.getJamKe());
					mapJam.put("jamMulai", itemJam.getJamMulai());
					mapJam.put("jamSelesai", itemJam.getJamSelesai());
					Absensi absensi = absensiRepository.getAbsensiPengganti(tgl, itemJam.getJamKe(), request.get("nim"));
					if(absensi.getStatusKehadiran() == 1) {
						mapJam.put("status", true);						
					} else {
						mapJam.put("status", false);
					}
					listJam.add(mapJam);
				}
			} else {
				map.put("jamMulaiOlehDosen", "");
				for(Jam itemJam : jam) {
					Map mapJam = new LinkedHashMap<>();
					mapJam.put("sesi", itemJam.getJamKe());
					mapJam.put("jamMulai", itemJam.getJamMulai());
					mapJam.put("jamSelesai", itemJam.getJamSelesai());
					mapJam.put("status", false);
					listJam.add(mapJam);
				}
			}
			map.put("listSesi", listJam);
			listPengganti.add(map);
		}
		
		jadwal.put("jadwalReguler", listKuliah);
		jadwal.put("jadwalPengganti", listPengganti);
		return jadwal;
	}
	
	@PostMapping("/getjadwaldosen")
	public Map<String, List<Map>> getJadwalDosen(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, List<Map>> jadwal = new LinkedHashMap<>();
		List<Map> listKuliah = new ArrayList<>();
		List<Map> listPengganti = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tgl = new java.sql.Date(tgl2.getTime());
		
		List<JadwalKuliah> kuliah = jadwalRepository.getListJadwalDosen(tgl, hari, request.get("kdDosen"));
		List<JadwalPengganti> pengganti = penggantiRepository.getListJadwalDosen(tgl, request.get("kdDosen"));
		
		for(JadwalKuliah item : kuliah) {
			Map map = new LinkedHashMap<>();
			Map<String, String> ruangan = new LinkedHashMap<>();
			
			List<Jam> jam = jadwalRepository.getListJam(tgl, hari, item.getKelas().getKdKelas(), item.getMatkul().getIdMatkul());
			BeritaAcara berita = beritaRepository.getBeritaAcaraKuliah(tgl, item.getIdJadwal());
			
			map.put("namaMatkul", item.getMatkul().getNamaMatkul());
			map.put("jenisMatkul", item.getMatkul().getJenisMatkul());
			map.put("kodeMatkul", item.getMatkul().getKdMatkul());
			map.put("kelas", item.getKelas().getKdKelas());
			map.put("jamMulai", jam.get(0).getJamMulai());
			map.put("jamSelesai", jam.get(jam.size() - 1).getJamSelesai());
			ruangan.put("kodeRuangan", item.getRuangan().getKdRuangan());
			ruangan.put("macAddress", item.getRuangan().getMacAddress());
			map.put("ruangan", ruangan);
			if(berita != null) {
				Time t = new Time(berita.getTglAbsensi().getTime());
				map.put("jamMulaiOlehDosen", t);
			} else {
				map.put("jamMulaiOlehDosen", "");
			}
			listKuliah.add(map);
		}
		
		for(JadwalPengganti item : pengganti) {
			Map map = new LinkedHashMap<>();
			Map<String, String> ruangan = new LinkedHashMap<>();
			
			List<Jam> jam = penggantiRepository.getListJam(tgl, item.getJadwalKuliah().getKelas().getKdKelas(),
					item.getJadwalKuliah().getMatkul().getIdMatkul());
			BeritaAcara berita = beritaRepository.getBeritaAcaraPengganti(tgl, item.getIdPengganti());
			
			map.put("namaMatkul", item.getJadwalKuliah().getMatkul().getNamaMatkul());
			map.put("jenisMatkul", item.getJadwalKuliah().getMatkul().getJenisMatkul());
			map.put("kodeMatkul", item.getJadwalKuliah().getMatkul().getKdMatkul());
			map.put("kelas", item.getJadwalKuliah().getKelas().getKdKelas());
			map.put("jamMulai", jam.get(0).getJamMulai());
			map.put("jamSelesai", jam.get(jam.size() - 1).getJamSelesai());
			ruangan.put("kodeRuangan", item.getRuangan().getKdRuangan());
			ruangan.put("macAddress", item.getRuangan().getMacAddress());
			map.put("ruangan", ruangan);
			if(berita != null) {
				Time t = new Time(berita.getTglAbsensi().getTime());
				map.put("jamMulaiOlehDosen", t);
			} else {
				map.put("jamMulaiOlehDosen", "");
			}
			listPengganti.add(map);
		}
		
		jadwal.put("jadwalReguler", listKuliah);
		jadwal.put("jadwalPengganti", listPengganti);
		return jadwal;
	}
	
	@CrossOrigin
	@PostMapping("/getdaftarjadwal")
	public List<Map> getDaftarJadwal(@RequestBody Map<String, String> request) throws ParseException {
		List<Map> maps = new ArrayList<>();
		
		List<String> hari = jadwalRepository.getHari(request.get("kdKelas"));
		for(String item : hari) {
			Map map = new LinkedHashMap<>();
			List<Map> listJadwal = new ArrayList<>();
			
			List<JadwalKuliah> jadwal = jadwalRepository.getListJadwalByHari(item, request.get("kdKelas"));
			for(JadwalKuliah itemKuliah : jadwal) {
				List<Map<String, String>> listDosen = new ArrayList<>();
				Map mapKuliah = new LinkedHashMap<>();
				
				List<Dosen> dosen = jadwalRepository.getListDosen(itemKuliah.getIdJadwal());
				for(Dosen itemDosen : dosen) {
					Map<String, String> mapDosen = new LinkedHashMap<>();
					mapDosen.put("kodeDosen", itemDosen.getKdDosen());
					mapDosen.put("namaDosen", itemDosen.getNamaDosen());
					listDosen.add(mapDosen);
				}
				
				mapKuliah.put("jamKe", itemKuliah.getJam().getJamKe());
				mapKuliah.put("jamMulai", itemKuliah.getJam().getJamMulai());
				mapKuliah.put("jamSelesai", itemKuliah.getJam().getJamSelesai());
				mapKuliah.put("kodeMatkul", itemKuliah.getMatkul().getKdMatkul());
				mapKuliah.put("namaMatkul", itemKuliah.getMatkul().getNamaMatkul());
				mapKuliah.put("jenisMatkul", itemKuliah.getMatkul().getJenisMatkul());
				mapKuliah.put("dosen", listDosen);
				mapKuliah.put("kodeRuangan", itemKuliah.getRuangan().getKdRuangan());
				listJadwal.add(mapKuliah);
			}
			map.put("hari", item);
			map.put("listJadwal", listJadwal);
			maps.add(map);
		}
		return maps;
	}
}
