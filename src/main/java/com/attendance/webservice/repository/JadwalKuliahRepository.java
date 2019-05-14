package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.JadwalKuliah;
import com.attendance.webservice.dto.JdwlKuliahDto;

@Repository("JadwalKuliahRepository")
public interface JadwalKuliahRepository extends JpaRepository<JadwalKuliah, Serializable> {
	@Query("SELECT new com.attendance.webservice.dto.JdwlKuliahDto(k.kdKelas, jk.hari, mk.namaMatkul, mk.tePr, s.jamKe, " +
			"s.wktMulai, s.wktSelesai, d.namaDosen, r.kdRuang) FROM JadwalKuliah jk INNER JOIN jk.sesi s INNER JOIN " +
			"jk.matkul mk INNER JOIN jk.ruang r INNER JOIN jk.dosen d INNER JOIN jk.kelas k WHERE k.kdKelas = ?1")
	List<JdwlKuliahDto> fetchJdwlKuliahDataInnerJoin(String kdKelas);
}
