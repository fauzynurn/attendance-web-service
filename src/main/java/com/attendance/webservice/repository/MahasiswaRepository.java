package com.attendance.webservice.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Mahasiswa;

@Repository("MahasiswaRepository")
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Serializable> {
	Mahasiswa findByNim(String nim);
	Mahasiswa save(Mahasiswa mhs);
}
