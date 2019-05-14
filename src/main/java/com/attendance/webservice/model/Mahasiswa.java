package com.attendance.webservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@Column(name="PUBLIC_KEY_MHS")
	private String pubKeyMhs;
	
	@Column(name="IMEI_MHS")
	private String imeiMhs;
	
	@Column(name="PASSWORD_MHS")
	private String pwdMhs;
	
	@Column(name="NAMA_MHS")
	private String namaMhs;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="KODE_KELAS", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Kelas kelas;
	
	public Mahasiswa() {
		
	}
	
	public Mahasiswa(String nim, String pwdMhs, String pubKeyMhs, String imeiMhs, Kelas kelas) {
		super();
		this.nim = nim;
		this.imeiMhs = imeiMhs;
		this.kelas = kelas;
		this.pubKeyMhs = pubKeyMhs;
		this.pwdMhs = pwdMhs;
	}
	
	public String getNim() {
		return nim;
	}
	
	public void setNim(String nim) {
		this.nim = nim;
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
	
	public String getPubKeyMhs() {
		return pubKeyMhs;
	}
	
	public void setPubKeyMhs(String pubKeyMhs) {
		this.pubKeyMhs = pubKeyMhs;
	}
	
	public String getPwdMhs() {
		return pwdMhs;
	}
	
	public void setPwdMhs(String pwdMhs) {
		this.pwdMhs = pwdMhs;
	}
}
