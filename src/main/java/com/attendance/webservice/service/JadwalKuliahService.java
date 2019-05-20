package com.attendance.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.webservice.repository.JadwalKuliahRepository;

@Service
public class JadwalKuliahService {
	@Autowired
	JadwalKuliahRepository jadwalRepository;
	
	public List fetchJadwalKuliah(String kdKelas, String hari) {
		return jadwalRepository.fetchJadwalKuliah(kdKelas, hari);
	}
}
