package com.attendance.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.webservice.model.BeritaAcara;
import com.attendance.webservice.repository.BeritaAcaraRepository;;

@Service
public class BeritaAcaraService {
	@Autowired
	BeritaAcaraRepository beritaRepository;
	
	public BeritaAcara save(BeritaAcara berita) {
		return beritaRepository.save(berita);
	}
	
	public List fetchIdJadwalDataInnerJoin(String namaMatkul, String kdKelas, String hari) {
		return beritaRepository.fetchIdJadwalDataInnerJoin(namaMatkul, kdKelas, hari);
	}
}
