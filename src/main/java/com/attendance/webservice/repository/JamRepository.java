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
			"WHERE (TIME('18:20:00') <= ?1 AND j.jamKe = 12) OR (TIME('07:00:00') >= ?1 AND j.jamKe = 1) " +
			"OR (TIME('07:00:00') < ?1 AND TIME('18:20:00') > ?1 AND j.jamMulai <= ?1 AND j.jamSelesai > ?1)")
	Integer getJamKe(Time jam);
	
	@Query("SELECT j " +
			"FROM Jam j LEFT JOIN j.jadwalKuliah jk ON jk.hari = ?1 AND jk.kelas.kdKelas = ?2 LEFT JOIN jk.jadwalPengganti jp " +
			"ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL OR jp.idPengganti IS NOT NULL) AND j.jamKe NOT IN(SELECT j.jamKe FROM Jam j LEFT JOIN " +
			"j.jadwalPengganti jp ON jp.tglPengganti = ?3 AND jp.jadwalKuliah.kelas.kdKelas = ?2 WHERE jp.idPengganti IS NOT NULL) " +
			"ORDER BY j.jamKe ASC")
	List<Jam> getJamNotEqualsIsEmpty(String hari, String kdKelas, Date tgl);
	
	@Query("SELECT j " +
			"FROM Jam j LEFT JOIN j.jadwalKuliah jk ON jk.hari = ?1 AND jk.kelas.kdKelas = ?2 LEFT JOIN jk.jadwalPengganti jp " +
			"ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL OR jp.idPengganti IS NOT NULL) AND j.jamKe NOT IN(SELECT j.jamKe FROM Jam j LEFT JOIN " +
			"j.jadwalPengganti jp ON jp.tglPengganti = ?3 AND jp.jadwalKuliah.kelas.kdKelas = ?2 AND jp.idPengganti NOT IN(?4) " +
			"WHERE jp.idPengganti IS NOT NULL) " +
			"ORDER BY j.jamKe ASC")
	List<Jam> getJamNotEqualsNotEmpty(String hari, String kdKelas, Date tgl, List<Integer> idPengganti);
	
	@Query("SELECT j " +
			"FROM Jam j LEFT JOIN j.jadwalKuliah jk ON jk.hari = ?1 AND jk.kelas.kdKelas = ?2 AND jk.idJadwal NOT IN(?4) " +
			"LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL OR jp.idPengganti IS NOT NULL) AND j.jamKe NOT IN(SELECT j.jamKe FROM Jam j LEFT JOIN " +
			"j.jadwalPengganti jp ON jp.tglPengganti = ?3 AND jp.jadwalKuliah.kelas.kdKelas = ?2 WHERE jp.idPengganti IS NOT NULL) " +
			"ORDER BY j.jamKe ASC")
	List<Jam> getJamIsEqualsIsEmpty(String hari, String kdKelas, Date tgl, List<Integer> idJadwal);
	
	@Query("SELECT j " +
			"FROM Jam j LEFT JOIN j.jadwalKuliah jk ON jk.hari = ?1 AND jk.kelas.kdKelas = ?2 AND jk.idJadwal NOT IN(?5) " +
			"LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL OR jp.idPengganti IS NOT NULL) AND j.jamKe NOT IN(SELECT j.jamKe FROM Jam j LEFT JOIN " +
			"j.jadwalPengganti jp ON jp.tglPengganti = ?3 AND jp.jadwalKuliah.kelas.kdKelas = ?2 AND jp.idPengganti NOT IN(?4) " +
			"WHERE jp.idPengganti IS NOT NULL) " +
			"ORDER BY j.jamKe ASC")
	List<Jam> getJamIsEqualsNotEmpty(String hari, String kdKelas, Date tgl, List<Integer> idPengganti, List<Integer> idJadwal);
}
