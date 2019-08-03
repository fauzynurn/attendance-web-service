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
@Table(name = "mahasiswa")
public class Mahasiswa {
	@Id
	@Column(name = "NIM", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String nim;
	
	@Column(name = "NAMA_MHS")
	private String namaMhs;
		
	@Column(name = "IMEI_MHS")
	private String imeiMhs;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KODE_KELAS")
	@Fetch(FetchMode.JOIN)
	private Kelas kelas;
	
	@OneToMany(targetEntity = Absensi.class, mappedBy = "mhs", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Absensi> absensi;
	
	public Mahasiswa() {
		
	}
	
	public Mahasiswa(String nim, String namaMhs, String imeiMhs, Kelas kelas) {
		super();
		this.nim = nim;
		this.namaMhs = namaMhs;
		this.imeiMhs = imeiMhs;
		this.kelas = kelas;
	}
	
	public String getNim() {
		return nim;
	}
	
	public void setNim(String nim) {
		this.nim = nim;
	}
	
	public String getNamaMhs() {
		return namaMhs;
	}
	
	public void setNamaMhs(String namaMhs) {
		this.namaMhs = namaMhs;
	}
		
	public String getImeiMhs() {
		return imeiMhs;
	}
	
	public void setImeiMhs(String imeiMhs) {
		this.imeiMhs = imeiMhs;
	}
		
	public Kelas getKelas() {
		return kelas;
	}
	
	public void setKelas(Kelas kelas) {
		this.kelas = kelas;
	}
}
