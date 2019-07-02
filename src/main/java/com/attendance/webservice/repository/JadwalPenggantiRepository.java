package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Dosen;
import com.attendance.webservice.model.JadwalPengganti;
import com.attendance.webservice.model.Jam;;

@Repository("JadwalPenggantiRepository")
public interface JadwalPenggantiRepository extends JpaRepository<JadwalPengganti, Serializable> {
	@Query("SELECT jp " +
			"FROM JadwalPengganti jp " +
			"WHERE jp.tglPengganti = ?1 AND jp.jadwalKuliah.kelas.kdKelas = ?2 " +
			"GROUP BY jp.jadwalKuliah.matkul.idMatkul " +
			"ORDER BY jp.jam.jamKe ASC")
	List<JadwalPengganti> getListJadwalMhs(Date tgl, String kdKelas);
	
	@Query("SELECT jp " +
			"FROM JadwalPengganti jp INNER JOIN jp.jadwalKuliah.dosen d " +
			"WHERE jp.tglPengganti = ?1 AND d.kdDosen = ?2 " +
			"GROUP BY jp.jadwalKuliah.kelas.kdKelas, jp.jadwalKuliah.matkul.idMatkul " +
			"ORDER BY jp.jam.jamKe ASC")
	List<JadwalPengganti> getListJadwalDosen(Date tgl, String kdDosen);
	
	@Query("SELECT jp.jam " +
			"FROM JadwalPengganti jp " +
			"WHERE jp.tglPengganti = ?1 AND jp.jadwalKuliah.kelas.kdKelas = ?2 AND jp.jadwalKuliah.matkul.idMatkul = ?3 " +
			"ORDER BY jp.jam.jamKe ASC")
	List<Jam> getListJam(Date tgl, String kdKelas, int idMatkul);
	
	@Query("SELECT jp " +
			"FROM JadwalPengganti jp " +
			"WHERE EXISTS(SELECT jp.idPengganti FROM JadwalPengganti jp WHERE jp.tglPengganti = ?1 AND jp.jam.jamMulai <= ?2 " +
			"AND jp.jam.jamSelesai > ?2 AND jp.jadwalKuliah.kelas.kdKelas = ?3) AND jp.tglPengganti = ?1 " +
			"AND jp.jadwalKuliah.kelas.kdKelas = ?3 AND jp.jadwalKuliah.matkul.kdMatkul = ?4 " +
			"AND jp.jadwalKuliah.matkul.jenisMatkul = ?5 " +
			"ORDER BY jp.jam.jamKe ASC")
	List<JadwalPengganti> getListJadwalByMatkul(Date tgl, Time jam, String kdKelas, String kdMatkul, boolean jenisMatkul);
	
	@Query("SELECT jp " +
			"FROM JadwalPengganti jp " +
			"WHERE jp.tglPengganti >= ?1 AND jp.jam.jamKe >= ?2 " +
			"GROUP BY jp.tglKuliah, jp.jadwalKuliah.matkul.idMatkul " +
			"ORDER BY jp.jam.jamKe ASC")
	List<JadwalPengganti> getListJadwalByJam(Date tgl, int jamKe);
	
	@Query("SELECT d " +
			"FROM JadwalPengganti jp INNER JOIN jp.jadwalKuliah.dosen d " +
			"WHERE jp.idPengganti = ?1 " +
			"ORDER BY d.namaDosen ASC")
	List<Dosen> getListDosen(int idPengganti);
}
