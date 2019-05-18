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
	@Query("SELECT new com.attendance.webservice.dto.JdwlKuliahDto(mk.namaMatkul, mk.jenisMatkul, d.namaDosen, j.jamMulai, " +
			"j.jamSelesai, r.kdRuangan, r.macAddress) FROM JadwalKuliah jk INNER JOIN jk.jam j INNER JOIN jk.matkul mk " +
			"INNER JOIN jk.ruangan r INNER JOIN jk.dosen d INNER JOIN jk.kelas k WHERE k.kdKelas = ?1 AND jk.hari = ?2")
	List<JdwlKuliahDto> fetchJdwlKuliahDataInnerJoin(String kdKelas, String hari);
}
