package com.attendance.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.webservice.repository.AbsensiRepository;

@Service
public class AbsensiService {
	@Autowired
	AbsensiRepository absensiRepository;
	
	public List fetchStatusKehadiran(String nim) {
		return absensiRepository.fetchStatusKehadiran(nim);
	}
}
