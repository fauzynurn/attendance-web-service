package com.attendance.webservice.controller;

import java.sql.Time;
import java.sql.Timestamp;
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
		
		LocalDate now = LocalDate.parse(request.get("tgl"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String hari = now.format(DateTimeFormatter.ofPattern("EEEE", new Locale("in", "ID")));
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date tgl = sdf1.parse(request.get("tgl"));
		tgl = sdf2.parse(sdf2.format(tgl));
		SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss");
		Time jam = new Time(sdftime.parse(request.get("jam")).getTime());
		
		long d = tgl.getTime() / 86400000l * 86400000l;
		long t = jam.getTime() - (jam.getTime() / 86400000l * 86400000l);
		Timestamp tglAbsensi = new Timestamp(d + t);
		
		List<Integer> idJadwal = penggantiRepository.getIdJadwal(jam, tgl, request.get("namaMatkul"), request.get("kdKelas"));
		
		if(idJadwal.isEmpty()) {
			idJadwal = jadwalRepository.getIdJadwal(tgl, jam, request.get("namaMatkul"), request.get("kdKelas"), hari);
			for(int i = 0; i < idJadwal.size(); i++) {
				BeritaAcara brt = new BeritaAcara();
				JadwalKuliah jdwl = new JadwalKuliah();
				brt.setTglAbsensi(tglAbsensi);
				jdwl.setIdJadwal(idJadwal.get(i));
				brt.setJadwalKuliah(jdwl);
				berita.add(brt);
			}
		} else {
			for(int i = 0; i < idJadwal.size(); i++) {
				BeritaAcara brt = new BeritaAcara();
				JadwalPengganti jdwl = new JadwalPengganti();
				brt.setTglAbsensi(tglAbsensi);
				jdwl.setIdPengganti(idJadwal.get(i));
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
