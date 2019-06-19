package com.attendance.webservice.repository;

import java.io.Serializable;
import java.sql.Time;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.Jam;

@Repository("JamRepository")
public interface JamRepository extends JpaRepository<Jam, Serializable> {
	@Query("SELECT j.jamKe " +
			"FROM Jam j " +
			"WHERE j.jamMulai <= ?1 AND j.jamSelesai > ?1")
	int getJamKe(Time jam);
}
