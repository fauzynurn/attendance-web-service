package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.BeritaAcara;

@Repository("BeritaAcaraRepository")
public interface BeritaAcaraRepository extends JpaRepository<BeritaAcara, Serializable> {
	BeritaAcara save(BeritaAcara berita);
	
	@Query("SELECT jk.idJadwal FROM JadwalKuliah jk INNER JOIN jk.matkul mk " +
	"INNER JOIN jk.kelas k WHERE mk.kdMatkul = ?1 AND k.kdKelas = ?2 AND jk.hari = ?3")
	List fetchIdJadwalDataInnerJoin(String kdMatkul, String kdKelas, String hari);
}
