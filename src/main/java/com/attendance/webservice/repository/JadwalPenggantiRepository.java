package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.JadwalPengganti;;

@Repository("JadwalPenggantiRepository")
public interface JadwalPenggantiRepository extends JpaRepository<JadwalPengganti, Serializable> {
	@Query("SELECT jp.jadwalKuliah.idJadwal AS idJadwal, jp.jadwalKuliah.matkul.namaMatkul AS namaMatkul, " +
			"jp.jadwalKuliah.matkul.jenisMatkul AS jenisMatkul, jp.jam.jamMulai AS jamMulai, jp.jam.jamSelesai AS jamSelesai, " +
			"jp.ruangan.kdRuangan AS kodeRuangan, jp.ruangan.macAddress AS macAddress, TIME(ba.tglAbsensi) AS tglAbsensi " +
			"FROM JadwalPengganti jp LEFT JOIN jp.beritaAcara ba ON ba.jadwalPengganti.idPengganti = jp.idPengganti " +
			"AND DATE(ba.tglAbsensi) = ?1 " +
			"WHERE jp.tglPengganti = ?1")
	List<Map> getJadwalPenggantiMhs(Date tglPengganti);
}
