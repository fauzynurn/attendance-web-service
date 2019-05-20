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
@Table(name="jam")
public class Jam {
	@Id
	@Column(name="JAM_KE")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String jamKe;
	
	@Column(name="JAM_MULAI")
	private Time jamMulai;
	
	@Column(name="JAM_SELESAI")
	private Time jamSelesai;
	
	@OneToMany(targetEntity=JadwalKuliah.class, mappedBy="jam", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<JadwalKuliah> jadwalKuliah;
	
	public Jam() {
		
	}
	
	public Jam(String jamKe, Time jamMulai, Time jamSelesai) {
		super();
		this.jamKe = jamKe;
		this.jamMulai = jamMulai;
		this.jamSelesai = jamSelesai;
	}
	
	public String getJamKe() {
		return jamKe;
	}
	
	public void setJamKe(String jamKe) {
		this.jamKe = jamKe;
	}
	
	public Time getJamMulai() {
		return jamMulai;
	}
	
	public void setJamMulai(Time jamMulai) {
		this.jamMulai = jamMulai;
	}
		
	public Time getJamSelesai() {
		return jamSelesai;
	}
	
	public void setJamSelesai(Time jamSelesai) {
		this.jamSelesai = jamSelesai;
	}
}
