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
	@Query("SELECT jk.matkul.namaMatkul AS namaMatkul, jk.matkul.jenisMatkul AS jenisMatkul, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran = 1 THEN 1 ELSE 0 END) AS char) AS jumlahHadir, " +
			"CAST(SUM(CASE WHEN a.statusKehadiran != 1 THEN 1 ELSE 0 END) AS char) AS jumlahTdkHadir " +
			"FROM Absensi a " +
			"LEFT JOIN a.beritaAcara.jadwalPengganti jp " +
			"INNER JOIN JadwalKuliah jk ON jk.idJadwal = a.beritaAcara.jadwalKuliah.idJadwal " +
			"OR jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"WHERE a.mhs.nim = ?1 " +
			"GROUP BY jk.matkul.idMatkul " +
			"ORDER BY jk.matkul.kdMatkul")
	List<Map> getKehadiran(String nim);
	
	@Query("SELECT a.mhs.nim AS nim, a.mhs.namaMhs AS namaMhs, " +
			"CASE WHEN a.statusKehadiran = 1 THEN 'hadir' " +
			"WHEN a.statusKehadiran = 2 THEN 'sakit' " +
			"WHEN a.statusKehadiran = 3 THEN 'izin' " +
			"WHEN a.statusKehadiran = 4 THEN 'alpa' END AS statusKehadiran " +
			"FROM Absensi a " +
			"LEFT JOIN a.beritaAcara.jadwalPengganti jp " +
			"INNER JOIN JadwalKuliah jk ON jk.idJadwal = a.beritaAcara.jadwalKuliah.idJadwal " +
			"OR jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"INNER JOIN Jam j ON j.jamKe = jk.jam.jamKe OR j.jamKe = jp.jam.jamKe " +
			"WHERE a.mhs.kelas.kdKelas = ?1 AND DATE(a.beritaAcara.tglAbsensi) = ?2 AND j.jamKe = ?3 " +
			"ORDER BY a.mhs.nim")
	List<Map<String, String>> getListAbsensiHarian(String kdKelas, Date tgl, int jamKe);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"LEFT JOIN a.beritaAcara.jadwalPengganti jp " +
			"INNER JOIN JadwalKuliah jk ON jk.idJadwal = a.beritaAcara.jadwalKuliah.idJadwal " +
			"OR jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"INNER JOIN Jam j ON j.jamKe = jk.jam.jamKe OR j.jamKe = jp.jam.jamKe " +
			"WHERE a.mhs.nim = ?1 AND DATE(a.beritaAcara.tglAbsensi) = ?2 AND j.jamKe = ?3")
	Absensi getAbsensiHarian(String nim, Date tgl, int jamKe);
	
	@Query("SELECT SUM(CASE WHEN a.statusKehadiran = 2 THEN 1 ELSE 0 END) AS sakit, " +
			"SUM(CASE WHEN a.statusKehadiran = 3 THEN 1 ELSE 0 END) AS izin, " +
			"SUM(CASE WHEN a.statusKehadiran = 4 THEN 1 ELSE 0 END) AS alpa " +
			"FROM Absensi a " +
			"WHERE a.mhs.nim = ?1")
	Map<String, String> getKetidakhadiran(String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalKuliah.jam.jamKe = ?2 AND a.mhs.nim = ?3")
	Absensi getAbsensiKuliah(Date tglAbsensi, int jamKe, String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalKuliah.jam.jamKe < ?2 AND a.mhs.nim = ?3")
	List<Absensi> getListAbsensiKuliah(Date tglAbsensi, int jamKe, String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalPengganti.jam.jamKe = ?2 AND a.mhs.nim = ?3")
	Absensi getAbsensiPengganti(Date tglAbsensi, int jamKe, String nim);
	
	@Query("SELECT a " +
			"FROM Absensi a " +
			"WHERE DATE(a.beritaAcara.tglAbsensi) = ?1 AND a.beritaAcara.jadwalPengganti.jam.jamKe < ?2 AND a.mhs.nim = ?3")
	List<Absensi> getListAbsensiPengganti(Date tglAbsensi, int jamKe, String nim);
	
	@Query("SELECT a.mhs.namaMhs AS namaMhs, " +
			"CASE WHEN a.statusKehadiran = 1 THEN true ELSE false END AS status " +
			"FROM Absensi a " +
			"WHERE a.beritaAcara.jadwalKuliah.idJadwal = ?1 " +
			"ORDER BY a.mhs.nim ASC")
	List<Map> getStatusKehadiranKuliah(int idJadwal);
	
	@Query("SELECT a.beritaAcara.jadwalKuliah.jam.jamKe AS sesi, " +
			"CASE WHEN a.statusKehadiran = 1 THEN true ELSE false END AS statusKehadiran " +
			"FROM Absensi a " +
			"WHERE a.beritaAcara.jadwalKuliah.idJadwal IN(?1) AND a.mhs.nim = ?2 " +
			"ORDER BY a.beritaAcara.jadwalKuliah.jam.jamKe")
	List<Map> getStatusMhsKuliah(List<Integer> idJadwal, String nim);
	
	@Query("SELECT a.mhs.namaMhs AS namaMhs, " +
			"CASE WHEN a.statusKehadiran = 1 THEN true ELSE false END AS status " +
			"FROM Absensi a " +
			"WHERE a.beritaAcara.jadwalPengganti.idPengganti = ?1 " +
			"ORDER BY a.mhs.nim")
	List<Map> getStatusKehadiranPengganti(int idPengganti);
	
	@Query("SELECT a.beritaAcara.jadwalPengganti.jam.jamKe AS jamKe, " +
			"CASE WHEN a.statusKehadiran = 1 THEN true ELSE false END AS statusKehadiran " +
			"FROM Absensi a " +
			"WHERE a.beritaAcara.jadwalPengganti.idPengganti IN(?1) AND a.mhs.nim = ?2 " +
			"ORDER BY a.beritaAcara.jadwalPengganti.jam.jamKe")
	List<Map> getStatusMhsPengganti(List<Integer> idPengganti, String nim);
	
	@Query("SELECT CASE WHEN a.beritaAcara.jadwalPengganti.idPengganti != null THEN jp.jam.jamKe " +
			"ELSE jk.jam.jamKe END AS jamJam " +
			"FROM Absensi a " +
			"LEFT JOIN a.beritaAcara.jadwalPengganti jp " +
			"INNER JOIN JadwalKuliah jk ON jk.idJadwal = a.beritaAcara.jadwalKuliah.idJadwal " +
			"OR jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"INNER JOIN Jam j ON j.jamKe = jk.jam.jamKe OR j.jamKe = jp.jam.jamKe " +
			"WHERE a.mhs.kelas.kdKelas = ?1 AND DATE(a.beritaAcara.tglAbsensi) = ?2 " +
			"GROUP BY CASE WHEN a.beritaAcara.jadwalPengganti.idPengganti != null THEN jp.jam.jamKe ELSE jk.jam.jamKe END " +
			"ORDER BY CASE WHEN a.beritaAcara.jadwalPengganti.idPengganti != null THEN jp.jam.jamKe ELSE jk.jam.jamKe END")
	List<Integer> dropdownJamKe(String kdKelas, Date tgl);
	
	@Query("SELECT jk.jam.jamKe AS sesi, jk.jam.jamMulai AS jamMulai, jk.jam.jamSelesai AS jamSelesai, " +
			"CASE WHEN a.statusKehadiran = 1 THEN true ELSE false END AS status " +
			"FROM Absensi a " +
			"INNER JOIN a.beritaAcara.jadwalKuliah jk " +
			"LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE jk.hari = ?2 AND jk.kelas.kdKelas = ?3 AND jk.matkul.idMatkul = ?4 AND a.mhs.nim = ?5 " +
			"AND jp.jadwalKuliah.idJadwal IS NULL " +
			"ORDER BY jk.jam.jamKe ASC")
	List<Map> getJamKuliah(Date tglKuliah, String hari, String kdKelas, int idMatkul, String nim);
	
	@Query("SELECT jp.jam.jamKe AS sesi, jp.jam.jamMulai AS jamMulai, jp.jam.jamSelesai AS jamSelesai, " +
			"CASE WHEN a.statusKehadiran = 1 THEN true ELSE false END AS status " +
			"FROM Absensi a " +
			"INNER JOIN a.beritaAcara.jadwalPengganti jp " +
			"WHERE jp.tglPengganti = ?1 AND jp.jadwalKuliah.kelas.kdKelas = ?2 AND jp.jadwalKuliah.matkul.idMatkul = ?3 " +
			"AND a.mhs.nim = ?4 " +
			"ORDER BY jp.jam.jamKe ASC")
	List<Map> getJamPengganti(Date tglPengganti, String kdKelas, int idMatkul, String nim);
}
