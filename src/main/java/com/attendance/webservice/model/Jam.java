package com.attendance.webservice.model;

import java.sql.Time;
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
@Table(name="sesi")
public class Sesi {
	@Id
	@Column(name="JAM_KE")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String jamKe;
	
	@Column(name="WAKTU_MULAI")
	private Time wktMulai;
	
	@Column(name="WAKTU_SELESAI")
	private Time wktSelesai;
	
	@OneToMany(targetEntity=JadwalKuliah.class, mappedBy="sesi", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<JadwalKuliah> jdwlKuliah;
	
	public Sesi() {
		
	}
	
	public Sesi(String jamKe, Time wktMulai, Time wktSelesai) {
		super();
		this.jamKe = jamKe;
		this.wktMulai = wktMulai;
		this.wktSelesai = wktSelesai;
	}
	
	public String getJamKe() {
		return jamKe;
	}
	
	public void setJamKe(String jamKe) {
		this.jamKe = jamKe;
	}
	
	public Time getWktMulai() {
		return wktMulai;
	}
	
	public void setWktMulai(Time wktMulai) {
		this.wktMulai = wktMulai;
	}
		
	public Time getWktSelesai() {
		return wktSelesai;
	}
	
	public void setWktSelesai(Time wktSelesai) {
		this.wktSelesai = wktSelesai;
	}
}
