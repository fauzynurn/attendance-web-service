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
			"CASE WHEN js.jam.jamKe IS NULL THEN jk.jam.jamKe ELSE js.jam.jamKe END AS jamKe, " +
			"CASE WHEN js.ruangan.kdRuangan IS NULL THEN jk.ruangan.kdRuangan ELSE js.ruangan.kdRuangan END AS kodeRuangan " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalSementara js " +
			"WHERE (jk.hari = ?1 OR js.tglKuliah = ?2) AND (js.tglKuliah IS NULL OR js.tglKuliah = ?2) AND jk.kelas.kdKelas = ?3")
	List<Map> getJadwalMhs(String hari, Date tglKuliah, String kdKelas);
	
	@Query("SELECT jk.matkul.namaMatkul AS namaMatkul, jk.matkul.jenisMatkul AS jenisMatkul, jk.kelas.kdKelas AS kodeKelas, " +
			"CASE WHEN js.jam.jamKe IS NULL THEN jk.jam.jamKe ELSE js.jam.jamKe END AS jamKe, " +
			"CASE WHEN js.ruangan.kdRuangan IS NULL THEN jk.ruangan.kdRuangan ELSE js.ruangan.kdRuangan END AS kodeRuangan, " +
			"CASE WHEN js.jadwalKuliah.idJadwal IS NULL THEN true ELSE false END AS kodeJadwal " +
			"FROM JadwalKuliah jk LEFT JOIN jk.jadwalSementara js INNER JOIN jk.dosen d " +
			"WHERE (jk.hari = ?1 OR js.tglKuliah = ?2) AND (js.tglKuliah IS NULL OR js.tglKuliah = ?2) AND d.kdDosen = ?3")
	List<Map> getJadwalDosen(String hari, Date tglKuliah, String kdDosen);
	
	@Query("SELECT j.jamMulai AS jamMulai, j.jamSelesai AS jamSelesai, r.macAddress AS macAddress " +
			"FROM Jam j, Ruangan r " +
			"WHERE j.jamKe = ?1 AND r.kdRuangan = ?2")
	Map getJamRuangan(int jamKe, String kdRuangan);
	
	@Query("SELECT d.namaDosen " +
			"FROM JadwalKuliah jk INNER JOIN jk.dosen d " +
			"WHERE jk.idJadwal = ?1")
	List<String>getNamaDosen(int idJadwal);
}
