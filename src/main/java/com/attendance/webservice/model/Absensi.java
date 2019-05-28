package com.attendance.webservice.model;

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
@Table(name = "absensi")
public class Absensi {
	@Id
	@Column(name = "ID_ABSENSI")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int idAbsensi;
	
	@Column(name = "STATUS_KEHADIRAN")
	private int statusKehadiran;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_BERITA")
	@Fetch(FetchMode.JOIN)
	private BeritaAcara beritaAcara;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "NIM")
	@Fetch(FetchMode.JOIN)
	private Mahasiswa mhs;
	
	public Absensi() {
		
	}
	
	public Absensi(int statusKehadiran, BeritaAcara beritaAcara, Mahasiswa mhs) {
		super();
		this.statusKehadiran = statusKehadiran;
		this.beritaAcara = beritaAcara;
		this.mhs = mhs;
	}
	
	public int getIdAbsensi() {
		return idAbsensi;
	}
	
	public void setIdAbsensi(int idAbsensi) {
		this.idAbsensi = idAbsensi;
	}
	
	public int getStatusKehadiran() {
		return statusKehadiran;
	}
	
	public void setStatusKehadiran(int statusKehadiran) {
		this.statusKehadiran = statusKehadiran;
	}
	
	public BeritaAcara getBeritaAcara() {
		return beritaAcara;
	}
	
	public void setBeritaAcara(BeritaAcara beritaAcara) {
		this.beritaAcara = beritaAcara;
	}
	
	public Mahasiswa getMhs() {
		return mhs;
	}
	
	public void setMhs(Mahasiswa mhs) {
		this.mhs = mhs;
	}
}
