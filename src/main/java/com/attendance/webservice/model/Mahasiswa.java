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
@Table(name="mahasiswa")
public class Mahasiswa {
	@Id
	@Column(name="NIM")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String nim;
	
	@Column(name="NAMA_MHS")
	private String namaMhs;
	
	@Column(name="PASSWORD_MHS")
	private String passwordMhs;
	
	@Column(name="IMEI_MHS")
	private String imeiMhs;
	
	@Column(name="PUBLIC_KEY_MHS")
	private String pubKeyMhs;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="KODE_KELAS", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Kelas kelas;
	
	@OneToMany(targetEntity=Absensi.class, mappedBy="mhs", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<Absensi> absensi;
	
	public Mahasiswa() {
		
	}
	
	public Mahasiswa(String nim, String namaMhs, String passwordMhs, String imeiMhs, String pubKeyMhs, Kelas kelas) {
		super();
		this.nim = nim;
		this.namaMhs = namaMhs;
		this.passwordMhs = passwordMhs;
		this.imeiMhs = imeiMhs;
		this.pubKeyMhs = pubKeyMhs;
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
	
	public String getPasswordMhs() {
		return passwordMhs;
	}
	
	public void setPasswordMhs(String passwordMhs) {
		this.passwordMhs = passwordMhs;
	}
	
	public String getImeiMhs() {
		return imeiMhs;
	}
	
	public void setImeiMhs(String imeiMhs) {
		this.imeiMhs = imeiMhs;
	}
	
	public String getPubKeyMhs() {
		return pubKeyMhs;
	}
	
	public void setPubKeyMhs(String pubKeyMhs) {
		this.pubKeyMhs = pubKeyMhs;
	}
	
	public Kelas getKelas() {
		return kelas;
	}
	
	public void setKelas(Kelas kelas) {
		this.kelas = kelas;
	}
}
