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
import com.attendance.webservice.model.Mahasiswa;
import com.attendance.webservice.repository.AbsensiRepository;
import com.attendance.webservice.repository.BeritaAcaraRepository;
import com.attendance.webservice.repository.JadwalKuliahRepository;
import com.attendance.webservice.repository.JadwalPenggantiRepository;
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
	
	@PostMapping("/mulaikbm")
	public Map<String, String> mulaiKbm(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		List<BeritaAcara> berita = new ArrayList<>();
		List<Absensi> absensi = new ArrayList<>();
		
		LocalDate tgl1 = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = tgl1.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl2 = sdf1.parse(request.get("tgl"));
		tgl2 = sdf2.parse(sdf2.format(tgl2));
		java.sql.Date tglKuliah = new java.sql.Date(tgl2.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jamAbsensi = new Time(sdf3.parse(request.get("jam")).getTime());
		
		Calendar calendarDate = Calendar.getInstance();
		Calendar calendarTime = Calendar.getInstance();
		calendarDate.setTime(tgl2);
		calendarTime.setTime(jamAbsensi);
		calendarDate.set(Calendar.HOUR_OF_DAY, calendarTime.get(Calendar.HOUR_OF_DAY));
		calendarDate.set(Calendar.MINUTE, calendarTime.get(Calendar.MINUTE));
		calendarDate.set(Calendar.SECOND, calendarTime.get(Calendar.SECOND));
		Timestamp tglAbsensi = new Timestamp(calendarDate.getTimeInMillis());
		
		List<Integer> idJadwal = penggantiRepository.getIdPengganti(jamAbsensi, tglKuliah, request.get("kdKelas"),
				request.get("kdMatkul"));
		
		if(idJadwal.isEmpty()) {
			idJadwal = jadwalRepository.getIdJadwal(tglKuliah, jamAbsensi, hari, request.get("kdKelas"),
					request.get("kdMatkul"));
			for(int i = 0; i < idJadwal.size(); i++) {
				BeritaAcara brt = new BeritaAcara();
				JadwalKuliah jdwl = new JadwalKuliah();
				jdwl.setIdJadwal(idJadwal.get(i));
				
				brt.setTglAbsensi(tglAbsensi);
				brt.setJadwalKuliah(jdwl);
				berita.add(brt);
			}
		} else {
			for(int i = 0; i < idJadwal.size(); i++) {
				BeritaAcara brt = new BeritaAcara();
				JadwalPengganti jdwl = new JadwalPengganti();
				jdwl.setIdPengganti(idJadwal.get(i));
				
				brt.setTglAbsensi(tglAbsensi);
				brt.setJadwalPengganti(jdwl);
				berita.add(brt);
			}
		}
		beritaRepository.saveAll(berita);
		
		List<String> nim = mhsRepository.getNimByKelas(request.get("kdKelas"));
		for(BeritaAcara item : berita) {
			for(int i = 0; i < nim.size(); i++) {
				Absensi abs = new Absensi();
				Mahasiswa mhs = new Mahasiswa();
				BeritaAcara brt = new BeritaAcara();
				brt.setIdBerita(item.getIdBerita());
				mhs.setNim(nim.get(i));
				
				abs.setStatusKehadiran(4);
				abs.setBeritaAcara(brt);
				abs.setMhs(mhs);
				absensi.add(abs);
			}
		}
		absensiRepository.saveAll(absensi);
		
        map.put("status","200");
        map.put("message", "Success");
		return map;
	}
}
