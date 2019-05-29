package com.attendance.webservice.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="jadwal_pengganti")
public class JadwalPengganti {
	@Id
	@Column(name = "ID_PENGGANTI")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int idPengganti;
	
	@Column(name = "TGL_KULIAH")
	private Date tglKuliah;
	
	@Column(name = "TGL_PENGGANTI")
	private Date tglPengganti;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_JADWAL")
	@Fetch(FetchMode.JOIN)
	private JadwalKuliah jadwalKuliah;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JAM_KE")
	@Fetch(FetchMode.JOIN)
	private Jam jam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KODE_RUANGAN")
	@Fetch(FetchMode.JOIN)
	private Ruangan ruangan;
	
	@OneToMany(targetEntity = BeritaAcara.class, mappedBy = "jadwalPengganti", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<BeritaAcara> beritaAcara;
	
	public JadwalPengganti() {
		
	}
	
	public JadwalPengganti(Date tglKuliah, Date tglPengganti, JadwalKuliah jadwalKuliah, Jam jam, Ruangan ruangan) {
		super();
		this.tglKuliah = tglKuliah;
		this.tglPengganti = tglPengganti;
		this.jadwalKuliah = jadwalKuliah;
		this.jam = jam;
		this.ruangan = ruangan;
	}
	
	public int getIdPengganti() {
		return idPengganti;
	}
	
	public void setIdPengganti(int idPengganti) {
		this.idPengganti = idPengganti;
	}
	
	public Date getTglKuliah() {
		return tglKuliah;
	}
	
	public void setTglKuliah(Date tglKuliah) {
		this.tglKuliah = tglKuliah;
	}
	
	public Date getTglPengganti() {
		return tglPengganti;
	}
	
	public void setTglPengganti(Date tglPengganti) {
		this.tglPengganti = tglPengganti;
	}
	
	public JadwalKuliah getJadwalKuliah() {
		return jadwalKuliah;
	}
	
	public void setJadwalKuliah(JadwalKuliah jadwalKuliah) {
		this.jadwalKuliah = jadwalKuliah;
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
