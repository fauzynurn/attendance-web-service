package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Jam;

@Repository("JamRepository")
public interface JamRepository extends JpaRepository<Jam, Serializable> {
	@Query("SELECT j.jamKe " +
			"FROM Jam j " +
			"WHERE j.jamMulai <= ?1 AND j.jamSelesai > ?1")
	Integer getJamKe(Time jam);
	
	@Query("SELECT j " +
			"FROM Jam j LEFT JOIN j.jadwalKuliah jk ON jk.hari = ?1 AND jk.kelas.kdKelas = ?2 LEFT JOIN jk.jadwalPengganti jp " +
			"ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL AND j.jamMulai NOT IN(SELECT j.jamMulai FROM Jam j LEFT JOIN j.jadwalPengganti jp " +
			"ON jp.tglPengganti = ?3 AND jp.jadwalKuliah.kelas.kdKelas = ?2 WHERE jp.idPengganti IS NOT NULL)) " +
			"OR jp.idPengganti IS NOT NULL " +
			"ORDER BY j.jamMulai ASC")
	List<Jam> getJam(String hari, String kdKelas, Date tgl);
}
