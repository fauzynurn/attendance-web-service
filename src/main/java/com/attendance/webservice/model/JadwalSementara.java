package com.attendance.webservice.model;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name="jadwal_sementara")
public class JadwalSementara implements Serializable {
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_JADWAL")
	@Fetch(FetchMode.JOIN)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private JadwalKuliah jadwalKuliah;
	
	@Column(name = "TGL_KULIAH")
	private Date tglKuliah;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JAM_KE")
	@Fetch(FetchMode.JOIN)
	private Jam jam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KODE_RUANGAN")
	@Fetch(FetchMode.JOIN)
	private Ruangan ruangan;
	
	public JadwalSementara() {
		
	}
	
	public JadwalSementara(JadwalKuliah jadwalKuliah, Date tglKuliah, Jam jam, Ruangan ruangan) {
		super();
		this.jadwalKuliah = jadwalKuliah;
		this.tglKuliah = tglKuliah;
		this.jam = jam;
		this.ruangan = ruangan;
	}
	
	public JadwalKuliah getJadwalKuliah() {
		return jadwalKuliah;
	}
	
	public void setJadwalKuliah(JadwalKuliah jadwalKuliah) {
		this.jadwalKuliah = jadwalKuliah;
	}
	
	public Date getTglKuliah() {
		return tglKuliah;
	}
	
	public void setTglKuliah(Date tglKuliah) {
		this.tglKuliah = tglKuliah;
	}
	
	public Jam getJam() {
		return jam;
	}
	
	public void setJam(Jam jam) {
		this.jam = jam;
	}
	
	public Ruangan getRuangan() {
		return ruangan;
	}
	
	public void setRuangan(Ruangan ruangan) {
		this.ruangan = ruangan;
	}
}
