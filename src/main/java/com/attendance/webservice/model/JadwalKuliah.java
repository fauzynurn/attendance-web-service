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
@Table(name="jadwal_kuliah")
public class JadwalKuliah {
	@Id
	@Column(name="ID_JADWAL")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idJadwal;
	
	@Column(name="HARI")
	private String hari;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="KODE_DOSEN", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Dosen dosen;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="KODE_KELAS", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Kelas kelas;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="KODE_RUANG", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Ruang ruang;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATKUL", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Matakuliah matkul;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="JAM_KE", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Sesi sesi;
	
	public JadwalKuliah() {
		
	}
	
	
	public JadwalKuliah(int idJadwal, Sesi sesi, Matakuliah matkul, Kelas kelas, Ruang ruang,
			String hari, Dosen dosen) {
		super();
		this.idJadwal = idJadwal;
		this.sesi = sesi;
		this.ruang = ruang;
		this.kelas = kelas;
		this.matkul = matkul;
		this.hari = hari;
		this.dosen = dosen;
	}
	
	public int getIdJadwal() {
		return idJadwal;
	}
	
	public void setIdJadwal(int idJadwal) {
		this.idJadwal = idJadwal;
	}
	
	public Sesi getSesi() {
		return sesi;
	}
	
	public void setSesi(Sesi sesi) {
		this.sesi = sesi;
	}
	
	public Ruang getRuang() {
		return ruang;
	}
	
	public void setRuang(Ruang ruang) {
		this.ruang = ruang;
	}
	
	public Kelas getKelas() {
		return kelas;
	}
	
	public void setKelas(Kelas kelas) {
		this.kelas = kelas;
	}
	
	public Matakuliah getMatkul() {
		return matkul;
	}
	
	public void setMatkul(Matakuliah matkul) {
		this.matkul = matkul;
	}
	
	public String getHari() {
		return hari;
	}
	
	public void setHari(String hari) {
		this.hari = hari;
	}
	
	public Dosen getDosen() {
		return dosen;
	}
	
	public void setDosen(Dosen dosen) {
		this.dosen = dosen;
	}
}
