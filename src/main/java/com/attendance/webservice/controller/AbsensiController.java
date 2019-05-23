package com.attendance.webservice.controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Absensi;
import com.attendance.webservice.model.BeritaAcara;
import com.attendance.webservice.model.Mahasiswa;
import com.attendance.webservice.repository.AbsensiRepository;

@RestController
public class AbsensiController {
	@Autowired
	AbsensiRepository absensiRepository;
	
	@GetMapping("/getmatkulkehadiran")
	public List<Map> getMatkulKehadiran(@RequestBody HashMap<String, String> request) {
		return absensiRepository.fetchMatkulKehadiran(request.get("nim"));
	}
	
	@GetMapping("/getallkehadiran")
	public List<Map> getAllKehadiran(@RequestBody HashMap<String, String> request) {
		return absensiRepository.fetchAllKehadiran(request.get("nim"));
	}
	
	@PostMapping("/getabsen")
	public Map<String, String> getAbsen(@RequestBody HashMap<String, String> request) throws ParseException {
		HashMap<String, String> map = new HashMap<>();
		Absensi absensi = new Absensi();
		BeritaAcara berita = new BeritaAcara();
		Mahasiswa mhs = new Mahasiswa();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
		Date tgl = sdf1.parse(request.get("tgl"));
		Time jam = new Time(sdf2.parse(request.get("jam")).getTime());
		
		Integer idBerita = absensiRepository.fetchIdBerita(request.get("namaMatkul"), tgl, jam, request.get("nim"));
		
		if(absensiRepository.fetchIdAbsensi(request.get("nim"), idBerita) == null) {
			absensi.setStatusKehadiran(1);
			berita.setIdBerita(idBerita);
			absensi.setBeritaAcara(berita);
			mhs.setNim(request.get("nim"));
			absensi.setMhs(mhs);
			
			absensiRepository.save(absensi);
			
			map.put("status","200");
	        map.put("message", "Success");
	        return map;
		} else {
			map.put("status","404");
	        map.put("message", "Failed");
	        return map;
		}		
	}
}
