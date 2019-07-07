package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Ruangan;

@Repository("RuanganRepository")
public interface RuanganRepository extends JpaRepository<Ruangan, Serializable> {
	@Query("SELECT r.kdRuangan " +
			"FROM Ruangan r LEFT JOIN r.jadwalKuliah jk ON jk.hari = ?1 AND jk.jam.jamKe IN(?2) LEFT JOIN jk.jadwalPengganti jp " +
			"ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL OR jp.idPengganti IS NOT NULL) AND r.kdRuangan NOT IN(SELECT r.kdRuangan FROM Ruangan r " +
			"LEFT JOIN r.jadwalPengganti jp ON jp.tglPengganti = ?3 AND jp.jam.jamKe IN(?2) WHERE jp.idPengganti IS NOT NULL) " +
			"ORDER BY r.kdRuangan ASC")
	List<String> getKdRuanganNotEqualsIsEmpty(String hari, List<Integer> jamKe, Date tgl);
	
	@Query("SELECT r.kdRuangan " +
			"FROM Ruangan r LEFT JOIN r.jadwalKuliah jk ON jk.hari = ?1 AND jk.jam.jamKe IN(?2) LEFT JOIN jk.jadwalPengganti jp " +
			"ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL OR jp.idPengganti IS NOT NULL) AND r.kdRuangan NOT IN(SELECT r.kdRuangan FROM Ruangan r " +
			"LEFT JOIN r.jadwalPengganti jp ON jp.tglPengganti = ?3 AND jp.jam.jamKe IN(?2) AND jp.idPengganti NOT IN(?4) " +
			"WHERE jp.idPengganti IS NOT NULL) " +
			"ORDER BY r.kdRuangan ASC")
	List<String> getKdRuanganNotEqualsNotEmpty(String hari, List<Integer> jamKe, Date tgl, List<Integer> idPengganti);
	
	@Query("SELECT r.kdRuangan " +
			"FROM Ruangan r LEFT JOIN r.jadwalKuliah jk ON jk.hari = ?1 AND jk.jam.jamKe IN(?2) AND jk.idJadwal NOT IN(?4) " +
			"LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL OR jp.idPengganti IS NOT NULL) AND r.kdRuangan NOT IN(SELECT r.kdRuangan FROM Ruangan r " +
			"LEFT JOIN r.jadwalPengganti jp ON jp.tglPengganti = ?3 AND jp.jam.jamKe IN(?2) WHERE jp.idPengganti IS NOT NULL) " +
			"ORDER BY r.kdRuangan ASC")
	List<String> getKdRuanganIsEqualsIsEmpty(String hari, List<Integer> jamKe, Date tgl, List<Integer> idJadwal);
	
	@Query("SELECT r.kdRuangan " +
			"FROM Ruangan r LEFT JOIN r.jadwalKuliah jk ON jk.hari = ?1 AND jk.jam.jamKe IN(?2) AND jk.idJadwal NOT IN(?4) " +
			"LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?3 " +
			"WHERE (jk.idJadwal IS NULL OR jp.idPengganti IS NOT NULL) AND r.kdRuangan NOT IN(SELECT r.kdRuangan FROM Ruangan r " +
			"LEFT JOIN r.jadwalPengganti jp ON jp.tglPengganti = ?3 AND jp.jam.jamKe IN(?2) AND jp.idPengganti NOT IN (?5) " +
			"WHERE jp.idPengganti IS NOT NULL) " +
			"ORDER BY r.kdRuangan ASC")
	List<String> getKdRuanganIsEqualsNotEmpty(String hari, List<Integer> jamKe, Date tgl, List<Integer> idJadwal,
			List<Integer> idPengganti);
}
