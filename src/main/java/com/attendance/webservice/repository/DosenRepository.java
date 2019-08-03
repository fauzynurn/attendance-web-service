package com.attendance.webservice.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendance.webservice.model.Dosen;

@Repository("DosenRepository")
public interface DosenRepository extends JpaRepository<Dosen, Serializable> {
	Dosen findByKdDosen(String kdDosen);
	
	@Transactional
	@Modifying
	@Query(value = "insert into dosen (KODE_DOSEN, NAMA_DOSEN, IMEI_DOSEN) values (:KODE_DOSEN, :NAMA_DOSEN, :IMEI_DOSEN)",
		nativeQuery = true)
	void insertDosen(@Param("KODE_DOSEN") String kdDosen, @Param("NAMA_DOSEN") String namaDosen,
			@Param("IMEI_DOSEN") String imeiDosen);
}
