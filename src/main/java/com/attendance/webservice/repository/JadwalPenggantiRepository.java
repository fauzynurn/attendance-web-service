package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.JadwalPengganti;;

@Repository("JadwalPenggantiRepository")
public interface JadwalPenggantiRepository extends JpaRepository<JadwalPengganti, Serializable> {
	@Query("SELECT jp.jadwalKuliah.idJadwal AS idJadwal, jp.jadwalKuliah.matkul.namaMatkul AS namaMatkul, " +
			"jp.jadwalKuliah.matkul.jenisMatkul AS jenisMatkul, jp.jadwalKuliah.matkul.kdMatkul AS kodeMatkul, " +
			"jp.jam.jamMulai AS jamMulai, jp.jam.jamSelesai AS jamSelesai, jp.ruangan.kdRuangan AS kodeRuangan, " +
			"jp.ruangan.macAddress AS macAddress, TIME(ba.tglAbsensi) AS tglAbsensi " +
			"FROM JadwalPengganti jp LEFT JOIN jp.beritaAcara ba ON ba.jadwalPengganti.idPengganti = jp.idPengganti " +
			"AND DATE(ba.tglAbsensi) = ?1 " +
			"WHERE jp.tglPengganti = ?1 AND jp.jadwalKuliah.kelas.kdKelas = ?2")
	List<Map> getJadwalPenggantiMhs(Date tglPengganti, String kdKelas);
	
	@Query("SELECT jp.jadwalKuliah.matkul.namaMatkul AS namaMatkul, jp.jadwalKuliah.matkul.jenisMatkul AS jenisMatkul, " +
			"jp.jadwalKuliah.matkul.kdMatkul AS kodeMatkul, jp.jadwalKuliah.kelas.kdKelas AS kodeKelas, " +
			"jp.jam.jamMulai AS jamMulai, jp.jam.jamSelesai AS jamSelesai, jp.ruangan.kdRuangan AS kodeRuangan, " +
			"jp.ruangan.macAddress AS macAddress, TIME(ba.tglAbsensi) AS tglAbsensi " +
			"FROM JadwalPengganti jp LEFT JOIN jp.beritaAcara ba ON ba.jadwalPengganti.idPengganti = jp.idPengganti " +
			"AND DATE(ba.tglAbsensi) = ?1 INNER JOIN jp.jadwalKuliah.dosen d " +
			"WHERE jp.tglPengganti = ?1 AND d.kdDosen = ?2")
	List<Map> getJadwalPenggantiDosen(Date tglPengganti, String kdKelas);
	
	@Query("SELECT jp.idPengganti " +
			"FROM JadwalPengganti jp " +
			"WHERE EXISTS(SELECT jp.idPengganti FROM JadwalPengganti jp WHERE jp.jam.jamMulai <= ?1 " +
			"AND jp.jam.jamSelesai >= ?1) AND jp.tglPengganti = ?2 AND jp.jadwalKuliah.matkul.namaMatkul = ?3 " +
			"AND jp.jadwalKuliah.kelas.kdKelas = ?4")
	List<Integer> getIdJadwal(Time jamAbsensi, Date tglPengganti, String namaMatkul, String kdKelas);
}
