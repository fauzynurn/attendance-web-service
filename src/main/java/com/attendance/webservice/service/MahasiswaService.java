package com.attendance.webservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.webservice.model.Mahasiswa;
import com.attendance.webservice.repository.MahasiswaRepository;

@Service
public class MahasiswaService {
	@Autowired
	MahasiswaRepository mhsRepository;
	
	public Mahasiswa findNim(String nim) {
		return mhsRepository.findByNim(nim);
	}
	
	public Mahasiswa saveMhs(Mahasiswa mhs) {
		return mhsRepository.save(mhs);
	}
}
