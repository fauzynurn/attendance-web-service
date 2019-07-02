package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.BeritaAcara;

@Repository("BeritaAcaraRepository")
public interface BeritaAcaraRepository extends JpaRepository<BeritaAcara, Serializable> {
	@Query("SELECT ba " +
			"FROM BeritaAcara ba " +
			"WHERE DATE(ba.tglAbsensi) = ?1 AND ba.jadwalKuliah.idJadwal = ?2")
	BeritaAcara getBeritaAcaraKuliah(Date tgl, int idJadwal);
	
	@Query("SELECT ba " +
			"FROM BeritaAcara ba " +
			"WHERE DATE(ba.tglAbsensi) = ?1 AND ba.jadwalPengganti.idPengganti = ?2")
	BeritaAcara getBeritaAcaraPengganti(Date tgl, int idPengganti);
}
