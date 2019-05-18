package com.attendance.webservice.model;

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
@Table(name="jadwal_kuliah")
public class JadwalKuliah {
	@Id
	@Column(name="ID_JADWAL")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idJadwal;
	
	@Column(name="HARI")
	private String hari;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="KODE_KELAS", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Kelas kelas;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATKUL", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Matakuliah matkul;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="KODE_DOSEN", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Dosen dosen;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="JAM_KE", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Jam jam;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="KODE_RUANGAN", insertable=false, updatable=false)
	@Fetch(FetchMode.JOIN)
	private Ruangan ruangan;
	
	@OneToMany(targetEntity=BeritaAcara.class, mappedBy="jdwlKuliah", orphanRemoval=false, fetch=FetchType.LAZY)
	private Set<BeritaAcara> beritaAcara;
	
	public JadwalKuliah() {
		
	}
	
	public JadwalKuliah(int idJadwal, Kelas kelas, String hari, Matakuliah matkul, Dosen dosen, Jam jam, Ruangan ruangan) {
		super();
		this.idJadwal = idJadwal;
		this.kelas = kelas;
		this.hari = hari;
		this.matkul = matkul;
		this.dosen = dosen;
		this.jam = jam;
		this.ruangan = ruangan;
	}
	
	public int getIdJadwal() {
		return idJadwal;
	}
	
	public void setIdJadwal(int idJadwal) {
		this.idJadwal = idJadwal;
	}
	
	public Kelas getKelas() {
		return kelas;
	}
	
	public void setKelas(Kelas kelas) {
		this.kelas = kelas;
	}
	
	public String getHari() {
		return hari;
	}
	
	public void setHari(String hari) {
		this.hari = hari;
	}
	
	public Matakuliah getMatkul() {
		return matkul;
	}
	
	public void setMatkul(Matakuliah matkul) {
		this.matkul = matkul;
	}
	
	public Dosen getDosen() {
		return dosen;
	}
	
	public void setDosen(Dosen dosen) {
		this.dosen = dosen;
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
