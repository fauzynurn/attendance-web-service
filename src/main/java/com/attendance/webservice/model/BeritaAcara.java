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
@Table(name="berita_acara")
public class BeritaAcara {
	@Id
	@Column(name="ID_BERITA")
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private int idBerita;
	
	@Column(name="TGL_ABSENSI")
	private Date tglAbsensi;
	
	@Column(name="TATAP_MUKA")
	private String tatapMuka;
	
	@Column(name="TUGAS_TERSTRUKTUR")
	private String tgsTerstruktur;
	
	@Column(name="PARAF_DOSEN")
	private boolean parafDosen;
	
	@Column(name="PARAF_KETUA_KELAS")
	private boolean parafKetuaKelas;
	
	@Column(name="PARAF_DOSEN_WALI")
	private boolean parafDosenWali;

	@Column(name="PARAF_KAPROD")
	private boolean parafKaprod;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_JADWAL")
	@Fetch(FetchMode.JOIN)
	private JadwalKuliah jadwalKuliah;
	
	@OneToMany(targetEntity=Absensi.class, mappedBy="beritaAcara", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<Absensi> absensi;
	
	public BeritaAcara() {
		
	}
	
	public BeritaAcara(Date tglAbsensi, String tatapMuka, String tgsTerstruktur, boolean parafDosen, boolean parafKetuaKelas,
			boolean parafDosenWali, boolean parafKaprod, JadwalKuliah jadwalKuliah) {
		super();
		this.tglAbsensi = tglAbsensi;
		this.tatapMuka = tatapMuka;
		this.tgsTerstruktur = tgsTerstruktur;
		this.parafDosen = parafDosen;
		this.parafKetuaKelas = parafKetuaKelas;
		this.parafDosenWali = parafDosenWali;
		this.parafKaprod = parafKaprod;
		this.jadwalKuliah = jadwalKuliah;
	}
	
	public int getIdBerita() {
		return idBerita;
	}
	
	public void setIdBerita(int idBerita) {
		this.idBerita = idBerita;
	}
	
	public Date getTglAbsensi() {
		return tglAbsensi;
	}
	
	public void setTglAbsensi(Date tglAbsensi) {
		this.tglAbsensi = tglAbsensi;
	}
	
	public String getTatapMuka() {
		return tatapMuka;
	}
	
	public void setTatapMuka(String tatapMuka) {
		this.tatapMuka = tatapMuka;
	}
	
	public String getTgsTerstruktur() {
		return tgsTerstruktur;
	}
	
	public void setTgsTerstruktur(String tgsTerstruktur) {
		this.tgsTerstruktur = tgsTerstruktur;
	}
	
	public boolean getParafDosen() {
		return parafDosen;
	}
	
	public void setParafDosen(boolean parafDosen) {
		this.parafDosen = parafDosen;
	}
	
	public boolean getKetuaKelas() {
		return parafKetuaKelas;
	}
	
	public void setParafKetuaKelas(boolean parafKetuaKelas) {
		this.parafKetuaKelas = parafKetuaKelas;
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
}
