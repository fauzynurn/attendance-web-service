package com.attendance.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.webservice.model.Dosen;
import com.attendance.webservice.repository.DosenRepository;

@Service
public class DosenService {
	@Autowired
	DosenRepository dsnRepository;
	
	public Dosen findKdDosen(String kdDsn) {
		return dsnRepository.findByKdDosen(kdDsn);
	}
	
	public Dosen saveDsn(Dosen dsn) {
		return dsnRepository.save(dsn);
	}
}
