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
@Table(name="matakuliah")
public class Matakuliah {
	@Id
	@Column(name="ID_MATKUL")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMatkul;
	
	@Column(name="KODE_MATKUL")
	private String kdMatkul;
	
	@Column(name="TEORI_PRAKTIK")
	private boolean tePr;
	
	@Column(name="NAMA_MATKUL")
	private String namaMatkul;
	
	@OneToMany(targetEntity=JadwalKuliah.class, mappedBy="matkul", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<JadwalKuliah> jdwlKuliah;
	
	public Matakuliah() {
		
	}
	
	public Matakuliah(int idMatkul, String kdMatkul, boolean tePr, String namaMatkul) {
		super();
		this.idMatkul = idMatkul;
		this.kdMatkul = kdMatkul;
		this.tePr = tePr;
		this.namaMatkul = namaMatkul;
	}
	
	public int getIdMatkul() {
		return idMatkul;
	}
	
	public void setIdMatkul(int idMatkul) {
		this.idMatkul = idMatkul;
	}
	
	public String getKdMatkul() {
		return kdMatkul;
	}
	
	public void setKdMatkul(String kdMatkul) {
		this.kdMatkul = kdMatkul;
	}
	
	public boolean getTePr() {
		return tePr;
	}
	
	public void setTePr(boolean tePr) {
		this.tePr = tePr;
	}
		
	public String getNamaMatkul() {
		return namaMatkul;
	}
	
	public void setNamaMatkul(String namaMatkul) {
		this.namaMatkul = namaMatkul;
	}
}
