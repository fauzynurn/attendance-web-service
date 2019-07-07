package com.attendance.webservice.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dosen")
public class Dosen {
	@Id
	@Column(name = "KODE_DOSEN")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private String kdDosen;
	
	@Column(name = "NAMA_DOSEN")
	private String namaDosen;
	
	@Column(name = "PASSWORD_DOSEN")
	private String passwordDosen;

	@Column(name = "IMEI_DOSEN")
	private String imeiDosen;
	
	@OneToMany(targetEntity = Kelas.class, mappedBy = "dosen1", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Kelas> kelas1;
	
	@OneToMany(targetEntity = Kelas.class, mappedBy = "dosen2", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Kelas> kelas2;
	
	@ManyToMany
	@JoinTable(
		name = "dosen_jadwal",
		joinColumns = @JoinColumn(name = "KODE_DOSEN"),
		inverseJoinColumns = @JoinColumn(name = "ID_JADWAL"))
	private Set<JadwalKuliah> jadwalKuliah;
	
	public Dosen() {
		
	}
	
	public Dosen(String kdDosen, String namaDosen, String passwordDosen, String imeiDosen) {
		super();
		this.kdDosen = kdDosen;
		this.namaDosen = namaDosen;
		this.passwordDosen = passwordDosen;
		this.imeiDosen = imeiDosen;
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
	
	public String getPasswordDosen() {
		return passwordDosen;
	}
	
	public void setPasswordDosen(String passwordDosen) {
		this.passwordDosen = passwordDosen;
	}
	
	public String getImeiDosen() {
		return imeiDosen;
	}
	
	public void setImeiDosen(String imeiDosen) {
		this.imeiDosen = imeiDosen;
	}
}
