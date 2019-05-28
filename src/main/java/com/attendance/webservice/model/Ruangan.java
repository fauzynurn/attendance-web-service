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
@Table(name = "ruangan")
public class Ruangan {
	@Id
	@Column(name = "KODE_RUANGAN")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private String kdRuangan;
	
	@Column(name = "NAMA_RUANGAN")
	private String namaRuangan;
	
	@Column(name = "MAC_ADDRESS")
	private String macAddress;
	
	@OneToMany(targetEntity = JadwalKuliah.class, mappedBy = "ruangan", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<JadwalKuliah> jadwalKuliah;
	
	@OneToMany(targetEntity = JadwalPengganti.class, mappedBy = "ruangan", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<JadwalPengganti> jadwalPengganti;
	
	public Ruangan() {
		
	}
	
	public Ruangan(String kdRuangan, String namaRuangan, String macAddress) {
		super();
		this.kdRuangan = kdRuangan;
		this.namaRuangan = namaRuangan;
		this.macAddress = macAddress;
	}
	
	public String getKdRuangan() {
		return kdRuangan;
	}
	
	public void setKdRuangan(String kdRuangan) {
		this.kdRuangan = kdRuangan;
	}
	
	public String getNamaRuangan() {
		return namaRuangan;
	}
	
	public void setNamaRuangan(String namaRuangan) {
		this.namaRuangan = namaRuangan;
	}
	
	public String getMacAddress() {
		return macAddress;
	}
	
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
}
