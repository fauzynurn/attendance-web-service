package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.JadwalKuliah;

@Repository("JadwalKuliahRepository")
public interface JadwalKuliahRepository extends JpaRepository<JadwalKuliah, Serializable> {
	@Query("SELECT jk.idJadwal AS idJadwal, jk.matkul.namaMatkul AS namaMatkul, jk.matkul.jenisMatkul AS jenisMatkul, " +
			"jk.matkul.kdMatkul AS kodeMatkul, jk.jam.jamMulai AS jamMulai, jk.jam.jamSelesai AS jamSelesai, " +
			"jk.ruangan.kdRuangan AS kodeRuangan, jk.ruangan.macAddress AS macAddress, TIME(ba.tglAbsensi) AS tglAbsensi " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"AND jp.tglKuliah = ?1 LEFT JOIN jk.beritaAcara ba ON ba.jadwalKuliah.idJadwal = jk.idJadwal " +
			"AND DATE(ba.tglAbsensi) = ?1 " +
			"WHERE jk.hari = ?2 AND jk.kelas.kdKelas = ?3 AND jp.jadwalKuliah.idJadwal IS NULL")
	List<Map> getJadwalMhs(Date tglKuliah, String hari, String kdKelas);
	
	@Query("SELECT jk.matkul.namaMatkul AS namaMatkul, jk.matkul.jenisMatkul AS jenisMatkul, jk.matkul.kdMatkul AS kodeMatkul, " +
			"jk.kelas.kdKelas AS kodeKelas, jk.jam.jamMulai AS jamMulai, jk.jam.jamSelesai AS jamSelesai, " +
			"jk.ruangan.kdRuangan AS kodeRuangan, jk.ruangan.macAddress AS macAddress, TIME(ba.tglAbsensi) AS tglAbsensi " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalPengganti jp ON jk.idJadwal = jp.jadwalKuliah.idJadwal " +
			"AND jp.tglKuliah = ?1 LEFT JOIN jk.beritaAcara ba ON ba.jadwalKuliah.idJadwal = jk.idJadwal " +
			"AND DATE(ba.tglAbsensi) = ?1 INNER JOIN jk.dosen d " +
			"WHERE jk.hari = ?2 AND d.kdDosen = ?3 AND jp.jadwalKuliah.idJadwal IS NULL")
	List<Map> getJadwalDosen(Date tglKuliah, String hari, String kdDosen);
	
	@Query("SELECT d.namaDosen " +
			"FROM JadwalKuliah jk INNER JOIN jk.dosen d " +
			"WHERE jk.idJadwal = ?1")
	List<String>getNamaDosen(int idJadwal);
	
	@Query("SELECT jk.idJadwal " +
			"FROM JadwalKuliah jk " +
			"WHERE jk.matkul.namaMatkul = ?1 AND jk.kelas.kdKelas = ?2 AND jk.hari = ?3")
	List<Integer> getIdJadwalKuliah(String namaMatkul, String kdKelas, String hari);
	
	@Query("SELECT jk.idJadwal " +
			"FROM JadwalKuliah jk " +
			"WHERE jk.matkul.namaMatkul = ?1 AND jk.kelas.kdKelas = ?2 AND jk.hari = ?3")
	List<Integer> getIdJadwalSementara(String namaMatkul, String kdKelas, String hari);
}
