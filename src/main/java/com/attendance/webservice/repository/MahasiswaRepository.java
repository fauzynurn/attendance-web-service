package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Mahasiswa;

@Repository("MahasiswaRepository")
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Serializable> {
	Mahasiswa findByNim(String nim);
	
	@Query("SELECT m.nim " +
			"FROM Mahasiswa m " +
			"WHERE m.kelas.kdKelas = ?1 " +
			"ORDER BY m.nim")
	List<String> getNimByKelas(String kdKelas);
}
