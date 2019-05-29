package com.attendance.webservice.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attendance.webservice.model.BeritaAcara;

@Repository("BeritaAcaraRepository")
public interface BeritaAcaraRepository extends JpaRepository<BeritaAcara, Serializable> {

}
