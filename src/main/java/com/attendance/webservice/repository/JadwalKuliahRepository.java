package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.JadwalKuliah;

@Repository("JadwalKuliahRepository")
public interface JadwalKuliahRepository extends JpaRepository<JadwalKuliah, Serializable> {
	@Query("SELECT jk.matkul.idMatkul AS idMatkul, jk.matkul.namaMatkul AS namaMatkul, jk.matkul.kdMatkul AS kodeMatkul, " +
			"jk.matkul.jenisMatkul AS jenisMatkul, TIME(ba.tglAbsensi) AS tglAbsensi, jk.ruangan.kdRuangan AS kodeRuangan, " +
			"jk.ruangan.macAddress AS macAddress " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 LEFT JOIN jk.beritaAcara ba " +
			"ON DATE(ba.tglAbsensi) = ?1 " +
			"WHERE jk.hari = ?2 AND jk.kelas.kdKelas = ?3 AND jp.jadwalKuliah.idJadwal IS NULL " +
			"GROUP BY jk.matkul.idMatkul " +
			"ORDER BY jk.jam.jamKe ASC")
	List<Map> getJadwalMhs(Date tglKuliah, String hari, String kdKelas);
	
	@Query("SELECT jk.matkul.idMatkul AS idMatkul, jk.matkul.namaMatkul AS namaMatkul, jk.matkul.kdMatkul AS kodeMatkul, " +
			"jk.matkul.jenisMatkul AS jenisMatkul, TIME(ba.tglAbsensi) AS tglAbsensi, jk.ruangan.kdRuangan AS kodeRuangan, " +
			"jk.ruangan.macAddress AS macAddress, jk.kelas.kdKelas AS kodeKelas " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 LEFT JOIN jk.beritaAcara ba " +
			"ON DATE(ba.tglAbsensi) = ?1 INNER JOIN jk.dosen d " +
			"WHERE jk.hari = ?2 AND d.kdDosen = ?3 AND jp.jadwalKuliah.idJadwal IS NULL " +
			"GROUP BY jk.kelas.kdKelas, jk.matkul.idMatkul " +
			"ORDER BY jk.jam.jamKe ASC")
	List<Map> getJadwalDosen(Date tglKuliah, String hari, String kdDosen);
	
	@Query("SELECT jk.jam.jamKe AS sesi, jk.jam.jamMulai AS jamMulai, jk.jam.jamSelesai AS jamSelesai " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE jk.hari = ?2 AND jk.kelas.kdKelas = ?3 AND jk.matkul.idMatkul = ?4 AND jp.jadwalKuliah.idJadwal IS NULL " +
			"ORDER BY jk.jam.jamKe ASC")
	List<Map> getJam(Date tglKuliah, String hari, String kdKelas, int idMatkul);
	
	@Query("SELECT jk.idJadwal " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE EXISTS(SELECT jk.idJadwal FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE jk.jam.jamMulai <= ?2 AND jk.jam.jamSelesai >= ?2 AND jk.hari = ?3 AND jk.kelas.kdKelas = ?4 " +
			"AND jp.jadwalKuliah.idJadwal IS NULL) AND jk.hari = ?3 AND jk.kelas.kdKelas = ?4 AND jk.matkul.kdMatkul = ?5 " +
			"AND jp.jadwalKuliah.idJadwal IS NULL " +
			"ORDER BY jk.jam.jamKe ASC")
	List<Integer> getIdJadwal(Date tglKuliah, Time jamAbsensi, String hari, String kdKelas, String kdMatkul);
	
//	@Query("SELECT d.namaDosen " +
//			"FROM JadwalKuliah jk INNER JOIN jk.dosen d " +
//			"WHERE jk.idJadwal = ?1")
//	List<String>getNamaDosen(int idJadwal);
}
