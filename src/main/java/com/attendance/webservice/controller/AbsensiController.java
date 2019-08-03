package com.attendance.webservice.controller;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	JadwalKuliahRepository jadwalRepository;
	@Autowired
	JadwalPenggantiRepository penggantiRepository;
	@Autowired
	JamRepository jamRepository;
	@Autowired
	MahasiswaRepository mhsRepository;
	
//	@PostMapping("/getdetailrekap")
//	public List<Map> getDetailRekap(@RequestBody Map<String, String> request) throws ParseException {
//		List<Map> maps = new ArrayList<>();
//		
//		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
//		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date tgl1 = sdf1.parse(request.get("tgl"));
//		tgl1 = sdf2.parse(sdf2.format(tgl1));
//		java.sql.Date tgl = new java.sql.Date(tgl1.getTime());
//		
//		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
//		Time jam = new Time(sdf3.parse(request.get("jamSkrng")).getTime());
//		
//		Integer jamKe = jamRepository.getJamKe(jam);
//		Mahasiswa mhs = mhsRepository.findByNim(request.get("nim"));
//		List<Map> maps1 = jadwalRepository.getListJadwalByKelas(mhs.getKelas().getKdKelas());
//		List<Map> maps2 = absensiRepository.getRekapKehadiran(request.get("nim"), tgl, jamKe);
//		for(Map item1 : maps1) {
//			Map map = new LinkedHashMap<>();
//				
//			map.put("namaMatkul", item1.get("namaMatkul"));
//			map.put("jenisMatkul", item1.get("jenisMatkul"));
//			map.put("jumlahHadir", item1.get("jumlahHadir"));
//			map.put("jumlahTdkHadir", item1.get("jumlahTdkHadir"));
//			if(!maps2.isEmpty()) {
//				for(Map item2 : maps2) {
//					if(item1.get("namaMatkul").toString().equals((item2.get("namaMatkul").toString())) 
//							&& (boolean) item1.get("jenisMatkul") == (boolean) item2.get("jenisMatkul")) {
//						map.put("jumlahHadir", item2.get("jumlahHadir"));
//						map.put("jumlahTdkHadir", item2.get("jumlahTdkHadir"));	
//					}
//				}
//			}
//			maps.add(map);
//		}
//		return maps;
//	}
	
	@PostMapping("/getrekapketidakhadiran")
	public Map<String, String> getRekapKetidakhadiran(@RequestBody Map<String, String> request) throws ParseException {
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl1 = sdf1.parse(request.get("tgl"));
		tgl1 = sdf2.parse(sdf2.format(tgl1));
		java.sql.Date tgl = new java.sql.Date(tgl1.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jam = new Time(sdf3.parse(request.get("jamSkrng")).getTime());
		
		Integer jamKe = jamRepository.getJamKe(jam);
		Map<String, String> map1 = absensiRepository.getRekapKetidakhadiran(request.get("nim"), tgl, jamKe);
		if(map1.get("sakit") == null && map1.get("izin") == null && map1.get("alpa") == null) {
			Map<String, String> map2 = new LinkedHashMap<>();
			map2.put("sakit", "0");
			map2.put("izin", "0");
			map2.put("alpa", "0");
			return map2;
		} else {
			return map1;	
		}
	}
	
	@PostMapping("/catatkehadiran")
	public Map<String, String> catatKehadiran(@RequestBody Map<String, List<Map<String, String>>> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		List<Map<String, String>> listRequest = request.get("listKehadiran");
		
		for(Map<String, String> r : listRequest) {
			List<Absensi> listAbsensi = new ArrayList<>();
		
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date tgl1 = sdf1.parse(r.get("tgl"));
			tgl1 = sdf2.parse(sdf2.format(tgl1));
			java.sql.Date tgl = new java.sql.Date(tgl1.getTime());
		
			Absensi absensi = absensiRepository.getAbsensiPengganti(tgl, Integer.parseInt(r.get("jamKe")), r.get("nim"));
			if(absensi == null) {
				absensi = absensiRepository.getAbsensiKuliah(tgl, Integer.parseInt(r.get("jamKe")), r.get("nim"));
				if(absensi == null) {
					map.put("status","404");
					map.put("message", "Failed");
					return map;
				} else {
					Time t = new Time(absensi.getBeritaAcara().getTglAbsensi().getTime());
					int jamKe = jamRepository.getJamKe(t);
					if(absensi.getBeritaAcara().getJadwalKuliah().getJam().getJamKe() >= jamKe) {
						if(absensi.getBeritaAcara().getJadwalKuliah().getJam().getJamKe() == jamKe) {
							List<Absensi> abs = absensiRepository.getListAbsensiByNimKuliah(tgl, jamKe, r.get("nim"));
							for(Absensi item : abs) {
								if(item.getBeritaAcara().getTglAbsensi().equals(absensi.getBeritaAcara().getTglAbsensi())) {
									item.setStatusKehadiran(1);
									listAbsensi.add(item);	
								}
							}
						} else {
							absensi.setStatusKehadiran(1);
							listAbsensi.add(absensi);
						}
						absensiRepository.saveAll(listAbsensi);
					}
				}
			} else {
				Time t = new Time(absensi.getBeritaAcara().getTglAbsensi().getTime());
				int jamKe = jamRepository.getJamKe(t);
				if(absensi.getBeritaAcara().getJadwalPengganti().getJam().getJamKe() >= jamKe) {
					if(absensi.getBeritaAcara().getJadwalPengganti().getJam().getJamKe() == jamKe) {
						List<Absensi> abs = absensiRepository.getListAbsensiByNimPengganti(tgl, jamKe, r.get("nim"));
						for(Absensi item : abs) {
							if(item.getBeritaAcara().getTglAbsensi().equals(absensi.getBeritaAcara().getTglAbsensi())) {
								item.setStatusKehadiran(1);
								listAbsensi.add(item);	
							}
						}
					} else {
						absensi.setStatusKehadiran(1);
						listAbsensi.add(absensi);
					}
					absensiRepository.saveAll(listAbsensi);
				}
			}
		}
			
		map.put("status", "200");
	    map.put("message", "Success");
	    return map;
	}
	
	@PostMapping("/getdaftarhadir")
	public Map getDaftarHadir(@RequestBody Map<String, String> request) throws ParseException {	
		Map map = new LinkedHashMap<>();
		List<Map> maps = new ArrayList<>();
				
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl1 = sdf1.parse(request.get("tgl"));
		tgl1 = sdf2.parse(sdf2.format(tgl1));
		java.sql.Date tgl = new java.sql.Date(tgl1.getTime());
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
		Time jam = new Time(sdf3.parse(request.get("jamSkrng")).getTime());
		
		int jamKe = jamRepository.getJamKe(jam);
		List<Absensi> absensi = absensiRepository.getListAbsensiByKelasPengganti(tgl, jamKe, request.get("kdKelas"));
		if(absensi.isEmpty()) {
			absensi = absensiRepository.getListAbsensiByKelasKuliah(tgl, jamKe, request.get("kdKelas"));
		}
		
		for(Absensi item : absensi) {
			Map abs = new LinkedHashMap<>();
			abs.put("namaMhs", item.getMhs().getNamaMhs());
			if(item.getStatusKehadiran() == 1) {
				abs.put("status", true);
			} else {
				abs.put("status", false);
			}
			maps.add(abs);
		}
		
		map.put("jamKe", jamKe);
		map.put("mhsList", maps);		
		return map;
	}
	
	@CrossOrigin
	@PostMapping("/getabsensiharian")
	public List<Map<String, String>> getAbsensiHarian(@RequestBody Map<String, String> request) throws ParseException {
		List<Map<String, String>> maps = new ArrayList<>();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl1 = sdf1.parse(request.get("tgl"));
		tgl1 = sdf2.parse(sdf2.format(tgl1));
		java.sql.Date tgl = new java.sql.Date(tgl1.getTime());
		
		List<Absensi> absensi = absensiRepository.getListAbsensiByKelasPengganti(tgl, Integer.parseInt(request.get("jamKe")),
				request.get("kdKelas"));
		if(absensi.isEmpty()) {
			absensi = absensiRepository.getListAbsensiByKelasKuliah(tgl, Integer.parseInt(request.get("jamKe")),
					request.get("kdKelas"));
		}
		
		for(Absensi item : absensi) {
			Map<String, String> map = new LinkedHashMap<>();

			map.put("nim", item.getMhs().getNim());
			map.put("namaMhs", item.getMhs().getNamaMhs());
			switch(item.getStatusKehadiran()) {
				case 1:
					map.put("statusKehadiran", "Hadir");
					break;
				case 2:
					map.put("statusKehadiran", "Sakit");
					break;
				case 3:
					map.put("statusKehadiran", "Izin");
					break;
				case 4:
					map.put("statusKehadiran", "Alpa");
					break;
			}
			maps.add(map);
		}
		
		return maps;
	}
	
	@CrossOrigin
	@PutMapping("/ubahkehadiran")
	public Map<String, String> ubahKehadiran(@RequestBody Map<String, String> request) throws ParseException {
		Map<String, String> map = new LinkedHashMap<>();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tgl1 = sdf1.parse(request.get("tgl"));
		tgl1 = sdf2.parse(sdf2.format(tgl1));
		java.sql.Date tgl = new java.sql.Date(tgl1.getTime());
		
		Absensi absensi = absensiRepository.getAbsensiPengganti(tgl, Integer.parseInt(request.get("jamKe")), request.get("nim"));
		if(absensi == null) {
			absensi = absensiRepository.getAbsensiKuliah(tgl, Integer.parseInt(request.get("jamKe")), request.get("nim"));
		}
		
		switch(request.get("statusKehadiran")) {
			case "Hadir":
				absensi.setStatusKehadiran(1);
				break;
			case "Sakit":
				absensi.setStatusKehadiran(2);
				break;
			case "Izin":
				absensi.setStatusKehadiran(3);
				break;
			case "Alpa":
				absensi.setStatusKehadiran(4);
				break;
		}
		absensiRepository.save(absensi);
		
		map.put("status", "200");
		map.put("message", "Success");
		return map;
	}
	
	@CrossOrigin
	@PostMapping("/getrekapkelas")
	public List<Map<String, String>> getRekapKelas(@RequestBody Map<String, String> request) throws ParseException {
		return absensiRepository.getRekapByKelas(request.get("kdKelas"));
	}
}
