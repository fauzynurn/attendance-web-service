package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendance.webservice.model.Mahasiswa;

@Repository("MahasiswaRepository")
public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Serializable> {
	Mahasiswa findByNim(String nim);
	
	@Query("SELECT m " +
			"FROM Mahasiswa m " +
			"WHERE m.kelas.kdKelas = ?1 " +
			"ORDER BY m.nim ASC")
	List<Mahasiswa> getListMhs(String kdKelas);
	
	@Transactional
	@Modifying
	@Query(value = "insert into mahasiswa (NIM, NAMA_MHS, KODE_KELAS, IMEI_MHS) values (:NIM, :NAMA_MHS, :KODE_KELAS, :IMEI_MHS)",
		nativeQuery = true)
	void insertMhs(@Param("NIM") String nim, @Param("NAMA_MHS") String namaMhs, @Param("KODE_KELAS") String kdKelas,
			@Param("IMEI_MHS") String imeiMhs);
}
