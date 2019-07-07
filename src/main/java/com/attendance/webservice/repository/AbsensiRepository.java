package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Absensi;

@Repository("AbsensiRepository")
public interface AbsensiRepository extends JpaRepository<Absensi, Serializable> {
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalKuliah.jam.jamKe = ?2 AND a.mhs.nim = ?3")
	Absensi getAbsensiKuliah(Date tgl, int jamKe, String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalPengganti.jam.jamKe = ?2 AND a.mhs.nim = ?3")
	Absensi getAbsensiPengganti(Date tgl, int jamKe, String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalKuliah.jam.jamKe <= ?2 AND a.mhs.nim = ?3 " +
			"ORDER BY a.beritaAcara.jadwalKuliah.jam.jamKe ASC")
	List<Absensi> getListAbsensiByNimKuliah(Date tgl, int jamKe, String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalPengganti.jam.jamKe <= ?2 AND a.mhs.nim = ?3 " +
			"ORDER BY a.beritaAcara.jadwalPengganti.jam.jamKe ASC")
	List<Absensi> getListAbsensiByNimPengganti(Date tgl, int jamKe, String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalKuliah.jam.jamKe = ?2 " +
			"AND a.beritaAcara.jadwalKuliah.kelas.kdKelas = ?3 " +
			"ORDER BY a.mhs.nim ASC")
	List<Absensi> getListAbsensiByKelasKuliah(Date tgl, int jamKe, String kdKelas);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalPengganti.jam.jamKe = ?2 " +
			"AND a.beritaAcara.jadwalPengganti.jadwalKuliah.kelas.kdKelas = ?3 " +
			"ORDER BY a.mhs.nim ASC")
	List<Absensi> getListAbsensiByKelasPengganti(Date tgl, int jamKe, String kdKelas);
	
	@Query("SELECT jk.matkul.namaMatkul AS namaMatkul, jk.matkul.jenisMatkul AS jenisMatkul, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran = 1 THEN 1 ELSE 0 END) AS char) AS jumlahHadir, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran != 1 THEN 1 ELSE 0 END) AS char) AS jumlahTdkHadir " +
			"FROM Absensi a " +
			"LEFT JOIN a.beritaAcara.jadwalPengganti jp " +
			"INNER JOIN JadwalKuliah jk ON jk.idJadwal = a.beritaAcara.jadwalKuliah.idJadwal " +
			"OR jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"INNER JOIN Jam j ON j.jamKe = jk.jam.jamKe OR j.jamKe = jp.jam.jamKe " +
			"WHERE a.mhs.nim = ?1 AND DATE(a.beritaAcara.tglAbsensi) <= ?2 AND (j.jamKe < ?3 " +
			"OR (j.jamKe <= ?3 AND a.statusKehadiran != 4)) " +
			"GROUP BY jk.matkul.idMatkul " +
			"ORDER BY jk.matkul.namaMatkul ASC, jk.matkul.jenisMatkul DESC")
	List<Map> getRekapKehadiran(String nim, Date tgl, Integer jamKe);
	
	@Query("SELECT SUM(CASE WHEN a.statusKehadiran = 2 THEN 1 ELSE 0 END) AS sakit, " +
			"SUM(CASE WHEN a.statusKehadiran = 3 THEN 1 ELSE 0 END) AS izin, " +
			"SUM(CASE WHEN a.statusKehadiran = 4 THEN 1 ELSE 0 END) AS alpa " +
			"FROM Absensi a " +
			"LEFT JOIN a.beritaAcara.jadwalPengganti jp " +
			"INNER JOIN JadwalKuliah jk ON jk.idJadwal = a.beritaAcara.jadwalKuliah.idJadwal " +
			"OR jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"INNER JOIN Jam j ON j.jamKe = jk.jam.jamKe OR j.jamKe = jp.jam.jamKe " +
			"WHERE a.mhs.nim = ?1 AND DATE(a.beritaAcara.tglAbsensi) <= ?2 AND (j.jamKe < ?3 " +
			"OR (j.jamKe <= ?3 AND a.statusKehadiran != 4))")
	Map<String, String> getRekapKetidakhadiran(String nim, Date tgl, Integer jamKe);
}
