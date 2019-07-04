package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Dosen;
import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.model.Jam;

@Repository("JadwalKuliahRepository")
public interface JadwalKuliahRepository extends JpaRepository<JadwalKuliah, Serializable> {
	@Query("SELECT MIN(jk) " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE jk.hari = ?2 AND jk.kelas.kdKelas = ?3 AND jp.jadwalKuliah.idJadwal IS NULL " +
			"GROUP BY jk.matkul.idMatkul " +
			"ORDER BY MIN(jk.jam.jamKe) ASC")
	List<JadwalKuliah> getListJadwalMhs(Date tgl, String hari, String kdKelas);
	
	@Query("SELECT jk " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 INNER JOIN jk.dosen d " +
			"WHERE jk.hari = ?2 AND d.kdDosen = ?3 AND jp.jadwalKuliah.idJadwal IS NULL " +
			"GROUP BY jk.kelas.kdKelas, jk.matkul.idMatkul " +
			"ORDER BY jk.jam.jamKe ASC")
	List<JadwalKuliah> getListJadwalDosen(Date tgl, String hari, String kdDosen);
	
	@Query("SELECT jk.jam " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE jk.hari = ?2 AND jk.kelas.kdKelas = ?3 AND jk.matkul.idMatkul = ?4 AND jp.jadwalKuliah.idJadwal IS NULL " +
			"ORDER BY jk.jam.jamKe ASC")
	List<Jam> getListJam(Date tgl, String hari, String kdKelas, int idMatkul);
	
	@Query("SELECT jk " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE EXISTS(SELECT jk.idJadwal FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE jk.hari = ?2 AND jk.jam.jamMulai <= ?3 AND jk.jam.jamSelesai > ?3 AND jk.kelas.kdKelas = ?4 " +
			"AND jp.jadwalKuliah.idJadwal IS NULL) AND jk.hari = ?2 AND jk.kelas.kdKelas = ?4 AND jk.matkul.kdMatkul = ?5 " +
			"AND jk.matkul.jenisMatkul = ?6 AND jp.jadwalKuliah.idJadwal IS NULL " +
			"ORDER BY jk.jam.jamKe ASC")
	List<JadwalKuliah> getListJadwalByJam(Date tgl, String hari, Time jam, String kdKelas, String kdMatkul, boolean jenisMatkul);
	
	@Query("SELECT jk " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jp.tglKuliah = ?1 " +
			"WHERE jk.hari = ?2 AND jk.kelas.kdKelas = ?3 AND jk.matkul.namaMatkul = ?4 AND jk.matkul.jenisMatkul = ?5 " +
			"AND jp.jadwalKuliah.idJadwal IS NULL " +
			"ORDER BY jk.jam.jamKe ASC")
	List<JadwalKuliah> getListJadwalByMatkul(Date tgl, String hari, String kdKelas, String namaMatkul, boolean jenisMatkul);
	
	@Query("SELECT d " +
			"FROM JadwalKuliah jk INNER JOIN jk.dosen d " +
			"WHERE jk.idJadwal = ?1 " +
			"ORDER BY d.namaDosen ASC")
	List<Dosen> getListDosen(int idJadwal);
	
	@Query("SELECT jk " +
			"FROM JadwalKuliah jk INNER JOIN jk.dosen d " +
			"WHERE d.kdDosen = ?1 " +
			"GROUP BY jk.matkul.idMatkul " +
			"ORDER BY jk.matkul.namaMatkul ASC, jk.matkul.jenisMatkul DESC")
	List<JadwalKuliah> getListJadwalByDosen(String kdDosen);
	
	@Query("SELECT jk.kelas.kdKelas " +
			"FROM JadwalKuliah jk INNER JOIN jk.dosen d " +
			"WHERE d.kdDosen = ?1 AND jk.matkul.idMatkul = ?2 " +
			"GROUP BY jk.kelas.kdKelas " +
			"ORDER BY jk.kelas.kdKelas ASC")
	List<String> getKdKelas(String kdDosen, int idMatkul);
	
	@Query("SELECT COUNT(jk.idJadwal) " +
			"FROM JadwalKuliah jk " +
			"WHERE jk.kelas.kdKelas = ?1 AND jk.matkul.namaMatkul = ?2 AND jk.matkul.jenisMatkul = ?3")
	int getJumlahJam(String kdKelas, String namaMatkul, boolean jenisMatkul);
	
	@Query("SELECT jk.matkul.namaMatkul AS namaMatkul, jk.matkul.jenisMatkul AS jenisMatkul, " +
			"'0' AS jumlahHadir, '0' AS jumlahTdkHadir " +
			"FROM JadwalKuliah jk " +
			"WHERE jk.kelas.kdKelas = ?1 " +
			"GROUP BY jk.matkul.idMatkul " +
			"ORDER BY jk.matkul.namaMatkul, jk.matkul.jenisMatkul DESC")
	List<Map> getListJadwalByKelas(String kdKelas);
	
	@Query("SELECT jk.hari " +
			"FROM JadwalKuliah jk " +
			"WHERE jk.kelas.kdKelas = ?1 " +
			"GROUP BY jk.hari " +
			"ORDER BY jk.hari DESC")
	List<String> getHari(String kdKelas);
	
	@Query("SELECT jk " +
			"FROM JadwalKuliah jk " +
			"WHERE jk.hari = ?1 AND jk.kelas.kdKelas = ?2 " +
			"ORDER BY jk.jam.jamKe ASC")
	List<JadwalKuliah> getListJadwalByHari(String hari, String kdKelas);
}
