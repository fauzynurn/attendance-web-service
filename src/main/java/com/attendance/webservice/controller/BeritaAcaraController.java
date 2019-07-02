package com.attendance.webservice.controller;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Absensi;
import com.attendance.webservice.model.BeritaAcara;
import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.model.JadwalPengganti;
import com.attendance.webservice.model.Jam;
import com.attendance.webservice.model.Mahasiswa;
import com.attendance.webservice.model.Ruangan;
import com.attendance.webservice.repository.AbsensiRepository;
import com.attendance.webservice.repository.BeritaAcaraRepository;
import com.attendance.webservice.repository.JadwalKuliahRepository;
import com.attendance.webservice.repository.JadwalPenggantiRepository;
import com.attendance.webservice.repository.JamRepository;
import com.attendance.webservice.repository.MahasiswaRepository;

@RestController
public class BeritaAcaraController {
	@Autowired
	BeritaAcaraRepository beritaRepository;
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	@Autowired
	JadwalPenggantiRepository penggantiRepository;
	@Autowired
	MahasiswaRepository mhsRepository;
	@Autowired
	AbsensiRepository absensiRepository;
	@Autowired
	JamRepository jamRepository;
	
	@PostMapping("/mulaikbm")
	public Map<String, String> mulaiKbm(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		List<BeritaAcara> listBerita = new ArrayList<>();
		List<Absensi> listAbsensi = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tgl = new java.sql.Date(tgl2.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jam = new Time(sdf3.parse(request.get("jamSkrng")).getTime());
		
		Calendar calendarDate = Calendar.getInstance();
		Calendar calendarTime = Calendar.getInstance();
		calendarDate.setTime(tgl2);
		calendarTime.setTime(jam);
		calendarDate.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY));
		calendarDate.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
		calendarDate.set(Calendar.SECOND, calendarTime.get(Calendar.SECOND));
		Timestamp tglAbsensi = new Timestamp(calendarDate.getTimeInMillis());
		
		List<JadwalPengganti> listPengganti = new ArrayList<>();
		List<JadwalKuliah> listKuliah = new ArrayList<>();
		listPengganti = penggantiRepository.getListJadwalByMatkul(tgl, jam, request.get("kdKelas"),
				request.get("kdMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));
		if(listPengganti.isEmpty()) {
			listKuliah = jadwalRepository.getListJadwalByJam(tgl, hari, jam, request.get("kdKelas"),
					request.get("kdMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));
			if(!listKuliah.get(0).getRuangan().getKdRuangan().equals(request.get("kdRuangan"))) {
				for(JadwalKuliah item : listKuliah) {
					JadwalPengganti pengganti = new JadwalPengganti();
					JadwalKuliah kuliah = new JadwalKuliah();
					Jam j = new Jam();
					Ruangan ruangan = new Ruangan();
					
					kuliah.setIdJadwal(item.getIdJadwal());
					j.setJamKe(item.getJam().getJamKe());
					ruangan.setKdRuangan(request.get("kdRuangan"));
				
					pengganti.setTglKuliah(tgl);
					pengganti.setTglPengganti(tgl);
					pengganti.setJadwalKuliah(kuliah);
					pengganti.setJam(j);
					pengganti.setRuangan(ruangan);
					listPengganti.add(pengganti);
				}
				penggantiRepository.saveAll(listPengganti);
			}
		} else {
			if(!listPengganti.get(0).getRuangan().getKdRuangan().equals(request.get("kdRuangan"))) {
				for(JadwalPengganti item : listPengganti) {
					Ruangan ruangan = new Ruangan();
				
					ruangan.setKdRuangan(request.get("kdRuangan"));			
					item.setRuangan(ruangan);
				}
				penggantiRepository.saveAll(listPengganti);
			}
		}
		listKuliah.clear();
		listPengganti.clear();
		
		listPengganti = penggantiRepository.getListJadwalByMatkul(tgl, jam, request.get("kdKelas"),
				request.get("kdMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));						
		if(listPengganti.isEmpty()) {
			listKuliah = jadwalRepository.getListJadwalByJam(tgl, hari, jam, request.get("kdKelas"),
					request.get("kdMatkul"), Boolean.parseBoolean(request.get("jenisMatkul")));
			for(JadwalKuliah item : listKuliah) {
				BeritaAcara berita = new BeritaAcara();
				JadwalKuliah kuliah = new JadwalKuliah();

				kuliah.setIdJadwal(item.getIdJadwal());
				berita.setTglAbsensi(tglAbsensi);
				berita.setJadwalKuliah(kuliah);
				listBerita.add(berita);
			}
		} else {
			int i = 0;
			boolean b = true;
			java.sql.Date tglKuliah = null;
			while(b && i < listPengganti.size()) {
				if(listPengganti.get(i).getJam().getJamKe() == jamRepository.getJamKe(jam)) {
					tglKuliah = listPengganti.get(i).getTglKuliah();
					b = false;
				}
				i++;
			}
			for(JadwalPengganti item : listPengganti) {
				if(item.getTglKuliah().equals(tglKuliah)) {
					BeritaAcara berita = new BeritaAcara();
					JadwalPengganti pengganti = new JadwalPengganti();

					pengganti.setIdPengganti(item.getIdPengganti());				
					berita.setTglAbsensi(tglAbsensi);
					berita.setJadwalPengganti(pengganti);
					listBerita.add(berita);	
				}
			}
		}
		beritaRepository.saveAll(listBerita);
		
		List<Mahasiswa> listMhs = mhsRepository.getListMhs(request.get("kdKelas"));
		for(BeritaAcara item : listBerita) {
			for(Mahasiswa itemMhs : listMhs) {
				Absensi absensi = new Absensi();
				Mahasiswa mhs = new Mahasiswa();
				BeritaAcara berita = new BeritaAcara();
				
				berita.setIdBerita(item.getIdBerita());
				mhs.setNim(itemMhs.getNim());
				absensi.setStatusKehadiran(4);
				absensi.setBeritaAcara(berita);
				absensi.setMhs(mhs);
				listAbsensi.add(absensi);
			}
		}
		absensiRepository.saveAll(listAbsensi);
		
        map.put("status","200");
        map.put("message", "Success");
		return map;
	}
}
