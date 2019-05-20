package com.attendance.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.webservice.model.Dosen;
import com.attendance.webservice.repository.DosenRepository;

@Service
public class DosenService {
	@Autowired
	DosenRepository dosenRepository;
	
	public Dosen findByKdDosen(String kdDosen) {
		return dosenRepository.findByKdDosen(kdDosen);
	}
	
	public Dosen save(Dosen dosen) {
		return dosenRepository.save(dosen);
	}
}
