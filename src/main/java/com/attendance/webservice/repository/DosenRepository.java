package com.attendance.webservice.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Dosen;

@Repository("DosenRepository")
public interface DosenRepository extends JpaRepository<Dosen, Serializable> {
	Dosen findByKdDosen(String kdDosen);
	Dosen save(Dosen dosen);
}
