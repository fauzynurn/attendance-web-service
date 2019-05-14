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
@Table(name="ruang")
public class Ruang {
	@Id
	@Column(name="KODE_RUANG")
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private String kdRuang;
	
	@Column(name="NAMA_RUANG")
	private String namaRuang;
	
	@Column(name="BEACON")
	private String beacon;
	
	@OneToMany(targetEntity=JadwalKuliah.class, mappedBy="ruang", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<JadwalKuliah> jdwlKuliah;
	
	public Ruang() {
		
	}
	
	public Ruang(String kdRuang, String namaRuang, String beacon) {
		super();
		this.kdRuang = kdRuang;
		this.namaRuang = namaRuang;
		this.beacon = beacon;
	}
	
	public String getKdRuang() {
		return kdRuang;
	}
	
	public void setKdRuang(String kdRuang) {
		this.kdRuang = kdRuang;
	}
	
	public String getNamaRuang() {
		return namaRuang;
	}
	
	public void setNamaRuang(String namaRuang) {
		this.namaRuang = namaRuang;
	}
	
	public String getBeacon() {
		return beacon;
	}
	
	public void setBeacon(String beacon) {
		this.beacon = beacon;
	}

}
