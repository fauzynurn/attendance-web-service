package com.attendance.webservice.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="jadwal_kuliah")
public class JadwalKuliah {
	@Id
	@Column(name = "ID_JADWAL")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idJadwal;
	
	@Column(name = "HARI")
	private String hari;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KODE_KELAS")
	@Fetch(FetchMode.JOIN)
	private Kelas kelas;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MATKUL")
	@Fetch(FetchMode.JOIN)
	private Matakuliah matkul;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "JAM_KE")
	@Fetch(FetchMode.JOIN)
	private Jam jam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "KODE_RUANGAN")
	@Fetch(FetchMode.JOIN)
	private Ruangan ruangan;

	@ManyToMany(mappedBy = "jadwalKuliah")
	private Set<Dosen> dosen;
	
	@OneToMany(targetEntity = BeritaAcara.class, mappedBy = "jadwalKuliah", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<BeritaAcara> beritaAcara;
	
	@OneToMany(targetEntity = JadwalSementara.class, mappedBy = "jadwalKuliah", orphanRemoval = false, fetch = FetchType.LAZY)
	private Set<JadwalSementara> jadwalSementara;
	
	public JadwalKuliah() {
		
	}
	
	public JadwalKuliah(String hari, Kelas kelas, Matakuliah matkul, Jam jam, Ruangan ruangan) {
		super();
		this.hari = hari;
		this.kelas = kelas;
		this.matkul = matkul;
		this.jam = jam;
		this.ruangan = ruangan;
	}
	
	public int getIdJadwal() {
		return idJadwal;
	}
	
	public void setIdJadwal(int idJadwal) {
		this.idJadwal = idJadwal;
	}
	
	public String getHari() {
		return hari;
	}
	
	public void setHari(String hari) {
		this.hari = hari;
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
