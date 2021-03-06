package com.attendance.webservice.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "kelas")
public class Kelas {
	@Id
	@Column(name = "KODE_KELAS")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String kdKelas;
	
	@OneToMany(targetEntity = Mahasiswa.class, mappedBy = "kelas", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Mahasiswa> mhs;
	
	@OneToMany(targetEntity = JadwalKuliah.class, mappedBy = "kelas", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<JadwalKuliah> jadwalKuliah;
	
	public Kelas() {
		
	}
	
	public Kelas(String kdKelas) {
		super();
		this.kdKelas = kdKelas;
	}
	
	public String getKdKelas() {
		return kdKelas;
	}
	
	public void setKdKelas(String kdKelas) {
		this.kdKelas = kdKelas;
	}
}
