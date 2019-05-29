package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Mahasiswa;

@Repository("MahasiswaRepository")
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Serializable> {
	Mahasiswa findByNim(String nim);
	Mahasiswa save(Mahasiswa mhs);
	
	@Query("SELECT m.nim " +
			"FROM Mahasiswa m " +
			"WHERE m.kelas.kdKelas = ?1")
	List<String> getNimByKelas(String kdKelas);
}
