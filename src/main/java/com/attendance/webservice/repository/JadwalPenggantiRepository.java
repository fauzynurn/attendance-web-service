package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.JadwalPengganti;;

@Repository("JadwalPenggantiRepository")
public interface JadwalPenggantiRepository extends JpaRepository<JadwalPengganti, Serializable> {
	@Query("SELECT jp.jadwalKuliah.matkul.idMatkul AS idMatkul, jp.jadwalKuliah.matkul.namaMatkul AS namaMatkul, " +
			"jp.jadwalKuliah.matkul.kdMatkul AS kodeMatkul, jp.jadwalKuliah.matkul.jenisMatkul AS jenisMatkul, " +
			"TIME(ba.tglAbsensi) AS tglAbsensi, jp.ruangan.kdRuangan AS kodeRuangan, jp.ruangan.macAddress AS macAddress " +
			"FROM JadwalPengganti jp LEFT JOIN jp.beritaAcara ba ON DATE(ba.tglAbsensi) = ?1 " +
			"WHERE jp.tglPengganti = ?1 AND jp.jadwalKuliah.kelas.kdKelas = ?2 " +
			"GROUP BY jp.jadwalKuliah.matkul.idMatkul " +
			"ORDER BY jp.jam.jamKe ASC")
	List<Map> getJadwalMhs(Date tglPengganti, String kdKelas);
	
	@Query("SELECT jp.jadwalKuliah.matkul.idMatkul AS idMatkul, jp.jadwalKuliah.matkul.namaMatkul AS namaMatkul, " +
			"jp.jadwalKuliah.matkul.kdMatkul AS kodeMatkul, jp.jadwalKuliah.matkul.jenisMatkul AS jenisMatkul, " +
			"TIME(ba.tglAbsensi) AS tglAbsensi, jp.ruangan.kdRuangan AS kodeRuangan, jp.ruangan.macAddress AS macAddress, " +
			"jp.jadwalKuliah.kelas.kdKelas AS kodeKelas " +
			"FROM JadwalPengganti jp LEFT JOIN jp.beritaAcara ba ON DATE(ba.tglAbsensi) = ?1 INNER JOIN jp.jadwalKuliah.dosen d " +
			"WHERE jp.tglPengganti = ?1 AND d.kdDosen = ?2 " +
			"GROUP BY jp.jadwalKuliah.kelas.kdKelas, jp.jadwalKuliah.matkul.idMatkul " +
			"ORDER BY jp.jam.jamKe ASC")
	List<Map> getJadwalDosen(Date tglPengganti, String kdKelas);
	
	@Query("SELECT jp.jam.jamKe AS sesi, jp.jam.jamMulai AS jamMulai, jp.jam.jamSelesai AS jamSelesai " +
			"FROM JadwalPengganti jp " +
			"WHERE jp.tglPengganti = ?1 AND jp.jadwalKuliah.kelas.kdKelas = ?2 AND jp.jadwalKuliah.matkul.idMatkul = ?3 " +
			"ORDER BY jp.jam.jamKe ASC")
	List<Map> getJam(Date tglPengganti, String kdKelas, int idMatkul);
	
	@Query("SELECT jp.idPengganti " +
			"FROM JadwalPengganti jp " +
			"WHERE EXISTS(SELECT jp.idPengganti FROM JadwalPengganti jp WHERE jp.jam.jamMulai <= ?1 " +
			"AND jp.jam.jamSelesai >= ?1 AND jp.tglPengganti = ?2 AND jp.jadwalKuliah.kelas.kdKelas = ?3) " +
			"AND jp.tglPengganti = ?2 AND jp.jadwalKuliah.kelas.kdKelas = ?3 AND jp.jadwalKuliah.matkul.kdMatkul = ?4 " +
			"ORDER BY jp.jam.jamKe ASC")
	List<Integer> getIdPengganti(Time jamAbsensi, Date tglPengganti, String kdKelas, String kdMatkul);
}
