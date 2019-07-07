package com.attendance.webservice.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "kelas")
public class Kelas {
	@Id
	@Column(name = "KODE_KELAS")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String kdKelas;
	
	@Column(name = "PROGRAM_STUDI")
	private String prodi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DOSEN_WALI")
	@Fetch(FetchMode.JOIN)
	private Dosen dosen1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KETUA_PRODI")
	@Fetch(FetchMode.JOIN)
	private Dosen dosen2;
	
	@OneToMany(targetEntity = Mahasiswa.class, mappedBy = "kelas", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Mahasiswa> mhs;
	
	@OneToMany(targetEntity = JadwalKuliah.class, mappedBy = "kelas", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<JadwalKuliah> jadwalKuliah;
	
	public Kelas() {
		
	}
	
	public Kelas(String kdKelas, String prodi, Dosen dosen1, Dosen dosen2) {
		super();
		this.kdKelas = kdKelas;
		this.prodi = prodi;
		this.dosen1 = dosen1;
		this.dosen2 = dosen2;
	}
	
	public String getKdKelas() {
		return kdKelas;
	}
	
	public void setKdKelas(String kdKelas) {
		this.kdKelas = kdKelas;
	}
	
	public String getProdi() {
		return prodi;
	}
	
	public void setProdi(String prodi) {
		this.prodi = prodi;
	}
	
	public Dosen getDosen1() {
		return dosen1;
	}
	
	public void setDosen1(Dosen dosen1) {
		this.dosen1 = dosen1;
	}
	
	public Dosen getDosen2() {
		return dosen2;
	}
	
	public void setDosen2(Dosen dosen2) {
		this.dosen2 = dosen2;
	}
}
