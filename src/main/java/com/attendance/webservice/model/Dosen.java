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
@Table(name="dosen")
public class Dosen {
	@Id
	@Column(name="KODE_DOSEN")
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private String kdDosen;
	
	@Column(name="PASSWORD_DOSEN")
	private String pwdDosen;
	
	@Column(name="NAMA_DOSEN")
	private String namaDosen;
	
	@Column(name="IMEI_DOSEN")
	private String imeiDosen;
	
	@Column(name="PUBLIC_KEY_DOSEN")
	private String pubKeyDosen;
	
	@OneToMany(targetEntity=JadwalKuliah.class, mappedBy="dosen", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<JadwalKuliah> jdwlKuliah;
	
	public Dosen() {
		
	}
	
	public Dosen(String kdDosen, String pwdDosen, String namaDosen, String imeiDosen, String pubKeyDosen) {
		super();
		this.kdDosen = kdDosen;
		this.pwdDosen = pwdDosen;
		this.namaDosen = namaDosen;
		this.imeiDosen = imeiDosen;
		this.pubKeyDosen = pubKeyDosen;
	}
	
	public String getKdDosen() {
		return kdDosen;
	}
	
	public void setKdDosen(String kdDosen) {
		this.kdDosen = kdDosen;
	}
	
	public String getNamaDosen() {
		return namaDosen;
	}
		
	public void setNamaDosen(String namaDosen) {
		this.namaDosen = namaDosen;
	}
	
	public String getPubKeyDosen() {
		return pubKeyDosen;
	}
	
	public void setPubKeyDosen(String pubKeyDosen) {
		this.pubKeyDosen = pubKeyDosen;
	}
	
	public String getImeiDosen() {
		return imeiDosen;
	}
	
	public void setImeiDosen(String imeiDosen) {
		this.imeiDosen = imeiDosen;
	}
	
	public String getPwdDosen() {
		return pwdDosen;
	}
	
	public void setPwdDosen(String pwdDosen) {
		this.pwdDosen = pwdDosen;
	}
}
