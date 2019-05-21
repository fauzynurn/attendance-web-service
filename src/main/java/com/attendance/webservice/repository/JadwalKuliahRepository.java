package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.JadwalKuliah;

@Repository("JadwalKuliahRepository")
public interface JadwalKuliahRepository extends JpaRepository<JadwalKuliah, Serializable> {
	@Query("SELECT mk.namaMatkul AS namaMatkul, mk.jenisMatkul AS jenisMatkul, d.namaDosen AS namaDosen, j.jamMulai AS " +
			"jamMulai, j.jamSelesai AS jamSelesai, r.kdRuangan AS kodeRuangan, r.macAddress AS macAddress FROM JadwalKuliah jk " +
			"INNER JOIN jk.jam j INNER JOIN jk.matkul mk INNER JOIN jk.ruangan r INNER JOIN jk.kelas k INNER JOIN jk.dosen d " +
			"WHERE k.kdKelas = ?1 AND jk.hari = ?2")
	List<Map> fetchJadwalMhs(String kdKelas, String hari);
	
	@Query("SELECT mk.namaMatkul AS namaMatkul, mk.jenisMatkul AS jenisMatkul, k.kdKelas AS kodeKelas, j.jamMulai AS jamMulai, " +
			"j.jamSelesai AS jamSelesai, r.kdRuangan AS kodeRuangan, r.macAddress AS macAddress FROM JadwalKuliah jk INNER JOIN " +
			"jk.jam j INNER JOIN jk.matkul mk INNER JOIN jk.ruangan r INNER JOIN jk.kelas k INNER JOIN jk.dosen d WHERE " +
			"d.kdDosen = ?1 AND jk.hari = ?2")
	List<Map> fetchJadwalDosen(String kdDosen, String hari);
}
