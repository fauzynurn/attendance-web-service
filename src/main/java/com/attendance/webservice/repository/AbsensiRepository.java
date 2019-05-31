package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Absensi;

@Repository("AbsensiRepository")
public interface AbsensiRepository extends JpaRepository<Absensi, Serializable> {
	@Query("SELECT jk.matkul.namaMatkul AS namaMatkul, jk.matkul.jenisMatkul AS jenisMatkul, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran = 1 THEN 1 ELSE 0 END) AS char) AS hadir, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran != 1 THEN 1 ELSE 0 END) AS char) AS tidakHadir " +
			"FROM Absensi a " +
			"LEFT JOIN a.beritaAcara.jadwalPengganti jp " +
			"INNER JOIN JadwalKuliah jk ON jk.idJadwal = a.beritaAcara.jadwalKuliah.idJadwal " +
			"OR jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"WHERE a.mhs.nim = ?1 " +
			"GROUP BY jk.matkul.idMatkul " +
			"ORDER BY jk.matkul.kdMatkul")
	List<Map> getKehadiran(String nim);
	
	@Query("SELECT SUM(CASE WHEN a.statusKehadiran = 2 THEN 1 ELSE 0 END) AS sakit, " +
			"SUM(CASE WHEN a.statusKehadiran = 3 THEN 1 ELSE 0 END) AS izin, " +
			"SUM(CASE WHEN a.statusKehadiran = 4 THEN 1 ELSE 0 END) AS alpa " +
			"FROM Absensi a " +
			"WHERE a.mhs.nim = ?1")
	Map<String, String> getKetidakhadiran(String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalKuliah.jam.jamMulai <= ?2 " +
			"AND a.beritaAcara.jadwalKuliah.jam.jamSelesai > ?2 AND a.mhs.nim = ?3")
	Absensi getAbsensiKuliah(Date tglAbsensi, Time jamAbsensi, String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalPengganti.jam.jamMulai <= ?2 " +
			"AND a.beritaAcara.jadwalPengganti.jam.jamSelesai > ?2 AND a.mhs.nim = ?3")
	Absensi getAbsensiPengganti(Date tglAbsensi, Time jamAbsensi, String nim);
}
