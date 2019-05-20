package com.attendance.webservice.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attendance.webservice.service.AbsensiService;

@RestController
public class AbsensiController {
	@Autowired
	AbsensiService absensiService;
	
	@GetMapping("/getabsensi")
	public List getAttendance(@RequestBody HashMap<String, String> request) {
		return absensiService.fetchStatusKehadiran(request.get("nim"));
	}
}
