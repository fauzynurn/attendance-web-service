package com.attendance.webservice.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Absensi;

@Repository("AbsensiRepository")
public interface AbsensiRepository extends JpaRepository<Absensi, Serializable> {
	@Query("SELECT new com.attendance.webservice.dto.AbsensiDto(mk.namaMatkul, mk.jenisMatkul, " +
			"SUM(CASE WHEN a.statusKehadiran = 1 THEN 1 ELSE 0 END), SUM(CASE WHEN a.statusKehadiran != 1 THEN 1 ELSE 0 END)) " +
			"FROM Absensi a INNER JOIN a.mhs m INNER JOIN a.beritaAcara ba INNER JOIN ba.jadwalKuliah jk INNER JOIN jk.matkul " +
			"mk WHERE m.nim = ?1 GROUP BY mk.idMatkul")
	List fetchStatusKehadiran(String nim);
}
