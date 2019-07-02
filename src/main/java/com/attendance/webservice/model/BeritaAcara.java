package com.attendance.webservice.model;

import java.sql.Timestamp;
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
@Table(name = "berita_acara")
public class BeritaAcara {
	@Id
	@Column(name = "ID_BERITA")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int idBerita;
	
	@Column(name = "TGL_ABSENSI")
	private Timestamp tglAbsensi;
	
	@Column(name = "PARAF_DOSEN_WALI")
	private boolean parafDosenWali;

	@Column(name = "PARAF_KAPROD")
	private boolean parafKaprod;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_JADWAL")
	@Fetch(FetchMode.JOIN)
	private JadwalKuliah jadwalKuliah;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PENGGANTI")
	@Fetch(FetchMode.JOIN)
	private JadwalPengganti jadwalPengganti;
	
	@OneToMany(targetEntity = Absensi.class, mappedBy = "beritaAcara", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<Absensi> absensi;
	
	public BeritaAcara() {
		
	}
	
	public BeritaAcara(Timestamp tglAbsensi, boolean parafDosenWali, boolean parafKaprod, JadwalKuliah jadwalKuliah,
			JadwalPengganti jadwalPengganti) {
		super();
		this.tglAbsensi = tglAbsensi;
		this.parafDosenWali = parafDosenWali;
		this.parafKaprod = parafKaprod;
		this.jadwalKuliah = jadwalKuliah;
		this.jadwalPengganti = jadwalPengganti;
	}
	
	public int getIdBerita() {
		return idBerita;
	}
	
	public void setIdBerita(int idBerita) {
		this.idBerita = idBerita;
	}
	
	public Timestamp getTglAbsensi() {
		return tglAbsensi;
	}
	
	public void setTglAbsensi(Timestamp tglAbsensi) {
		this.tglAbsensi = tglAbsensi;
	}
	
	public boolean getParafDosenWali() {
		return parafDosenWali;
	}
	
	public void setParafDosenWali(boolean parafDosenWali) {
		this.parafDosenWali = parafDosenWali;
	}
	
	public boolean getParafKaprod() {
		return parafKaprod;
	}
	
	public void setParafKaprod(boolean parafKaprod) {
		this.parafKaprod = parafKaprod;
	}
	
	public JadwalKuliah getJadwalKuliah() {
		return jadwalKuliah;
	}
	
	public void setJadwalKuliah(JadwalKuliah jadwalKuliah) {
		this.jadwalKuliah = jadwalKuliah;
	}
	
	public JadwalPengganti getJadwalPengganti() {
		return jadwalPengganti;
	}
	
	public void setJadwalPengganti(JadwalPengganti jadwalPengganti) {
		this.jadwalPengganti = jadwalPengganti;
	}
}
