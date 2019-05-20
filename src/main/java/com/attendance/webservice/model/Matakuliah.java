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
	
	@Column(name="JENIS_MATKUL")
	private boolean jenisMatkul;
	
	@Column(name="NAMA_MATKUL")
	private String namaMatkul;
	
	@OneToMany(targetEntity=JadwalKuliah.class, mappedBy="matkul", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<JadwalKuliah> jadwalKuliah;
	
	public Matakuliah() {
		
	}
	
	public Matakuliah(String kdMatkul, boolean jenisMatkul, String namaMatkul) {
		super();
		this.kdMatkul = kdMatkul;
		this.jenisMatkul = jenisMatkul;
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
	
	public boolean getJenisMatkul() {
		return jenisMatkul;
	}
	
	public void setJenisMatkul(boolean jenisMatkul) {
		this.jenisMatkul = jenisMatkul;
	}
		
	public String getNamaMatkul() {
		return namaMatkul;
	}
	
	public void setNamaMatkul(String namaMatkul) {
		this.namaMatkul = namaMatkul;
	}
}
