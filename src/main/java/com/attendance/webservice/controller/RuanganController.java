package com.attendance.webservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.model.Ruangan;
import com.attendance.webservice.repository.RuanganRepository;

@RestController
public class RuanganController {
	@Autowired
	RuanganRepository ruanganRepository;
	
	@CrossOrigin
	@GetMapping("/getdaftarruangan")
	public List<Ruangan> getDaftarRuangan() {
        return ruanganRepository.findAll();
	}
}
