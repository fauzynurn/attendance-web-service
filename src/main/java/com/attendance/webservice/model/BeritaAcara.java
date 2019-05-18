package com.attendance.webservice.model;

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
	private boolean parafKetuaKls;
	
	@Column(name="PARAF_DOSEN_WALI")
	private boolean parafDsnWali;

	@Column(name="PARAF_KAPROD")
	private boolean parafKaprod;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_JADWAL")
	@Fetch(FetchMode.JOIN)
	private JadwalKuliah jdwlKuliah;
	
	public BeritaAcara() {
		
	}
	
	public BeritaAcara(Date tglAbsensi, String tatapMuka, String tgsTerstruktur, boolean parafDosen,
			boolean parafKetuaKls, boolean parafDsnWali, boolean parafKaprod) {
		super();
		this.tglAbsensi = tglAbsensi;
		this.jdwlKuliah = new JadwalKuliah();
		this.tatapMuka = tatapMuka;
		this.tgsTerstruktur = tgsTerstruktur;
		this.parafDosen = parafDosen;
		this.parafKetuaKls = parafKetuaKls;
		this.parafDsnWali = parafDsnWali;
		this.parafKaprod = parafKaprod;
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
	
	public JadwalKuliah getJdwlKuliah() {
		return jdwlKuliah;
	}
	
	public void setJdwlKuliah(JadwalKuliah jdwlKuliah) {
		this.jdwlKuliah = jdwlKuliah;
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
	
	public boolean getKetuaKls() {
		return parafKetuaKls;
	}
	
	public void setParafKetuaKls(boolean parafKetuaKls) {
		this.parafKetuaKls = parafKetuaKls;
	}
	
	public boolean getParafDsnWali() {
		return parafDsnWali;
	}
	
	public void setParafDsnWali(boolean parafDsnWali) {
		this.parafDsnWali = parafDsnWali;
	}
	
	public boolean getParafKaprod() {
		return parafKaprod;
	}
	
	public void setParafKaprod(boolean parafKaprod) {
		this.parafKaprod = parafKaprod;
	}
}
