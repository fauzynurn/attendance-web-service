package com.attendance.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.webservice.repository.JadwalKuliahRepository;

@Service
public class JadwalKuliahService {
	@Autowired
	JadwalKuliahRepository jdwlKuliahRepository;
	
	public List fetchJdwlKuliahDataInnerJoin(String kdKelas, String hari) {
		return jdwlKuliahRepository.fetchJdwlKuliahDataInnerJoin(kdKelas, hari);
	}
}