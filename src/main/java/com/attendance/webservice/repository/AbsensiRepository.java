package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Absensi;

@Repository("AbsensiRepository")
public interface AbsensiRepository extends JpaRepository<Absensi, Serializable> {
	@Query("SELECT mk.namaMatkul AS namaMatkul, mk.jenisMatkul AS jenisMatkul, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran = 1 THEN 1 ELSE 0 END) AS char) AS hadir, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran != 1 THEN 1 ELSE 0 END) AS char) AS tidakHadir " +
			"FROM Absensi a INNER JOIN a.mhs m INNER JOIN a.beritaAcara ba INNER JOIN ba.jadwalKuliah jk INNER JOIN " +
			"jk.matkul mk WHERE m.nim = ?1 GROUP BY mk.idMatkul")
	List<Map> fetchMatkulKehadiran(String nim);
	
	@Query("SELECT CAST(SUM(CASE WHEN a.statusKehadiran = 2 THEN 1 ELSE 0 END) AS char) AS sakit, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran = 3 THEN 1 ELSE 0 END) AS char) AS izin, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran = 4 THEN 1 ELSE 0 END) AS char) AS alpa " +
			"FROM Absensi a INNER JOIN a.mhs m WHERE m.nim = ?1")
	List<Map> fetchAllKehadiran(String nim);
	
	@Query("SELECT ba.idBerita " +
			"FROM BeritaAcara ba INNER JOIN ba.jadwalKuliah jk INNER JOIN jk.matkul mk INNER JOIN jk.jam j " +
			"INNER JOIN jk.kelas k INNER JOIN k.mhs m " +
			"WHERE mk.namaMatkul = ?1 AND ba.tglAbsensi = ?2 AND j.jamMulai <= ?3 AND j.jamSelesai > ?3 AND m.nim = ?4")
	Integer fetchIdBerita(String namaMatkul, Date tglAbsensi, Time jam, String nim);
	
	@Query("SELECT a.idAbsensi " +
			"FROM Absensi a " +
			"WHERE a.mhs.nim = ?1 AND a.beritaAcara.idBerita = ?2")
	Integer fetchIdAbsensi(String nim, int idBerita);
	
	Absensi save(Absensi absensi);
}
