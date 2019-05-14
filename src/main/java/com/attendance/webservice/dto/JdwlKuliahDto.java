package com.attendance.webservice.dto;

import java.util.Date;

public class JdwlKuliahDto {
	
	private String kdKelas;
	private String namaMatkul;
	private boolean tePr;
	private String jamKe;
	private Date wktMulai;
	private Date wktSelesai;
	private String kdRuang;
	private String namaDosen;
	private String hari;
	
	public JdwlKuliahDto() {
		
	}

	public JdwlKuliahDto(String kdKelas, String hari, String namaMatkul, boolean tePr, String jamKe, Date wktMulai,
			Date wktSelesai, String namaDosen, String kdRuang) {
		this.kdKelas = kdKelas;
		this.namaMatkul = namaMatkul;
		this.jamKe = jamKe;
		this.tePr = tePr;
		this.wktMulai = wktMulai;
		this.wktSelesai = wktSelesai;
		this.namaDosen = namaDosen;
		this.hari = hari;
		this.kdRuang = kdRuang;
	}
	
	public String getJamKe() {
		return jamKe;
	}
	
	public void setJamKe(String jamKe) {
		this.jamKe = jamKe;
	}
	
	public boolean getTePr() {
		return tePr;
	}
	
	public void setTePr(boolean tePr) {
		this.tePr = tePr;
	}
	
	public String getNamaMatkul() {
		return namaMatkul;
	}
	
	public void setNamaMatkul(String namaMatkul) {
		this.namaMatkul = namaMatkul;
	}
	
	public Date getWktMulai() {
		return wktMulai;
	}
	
	public void setWktMulai(Date wktMulai) {
		this.wktMulai = wktMulai;
	}
		
	public Date getWktSelesai() {
		return wktSelesai;
	}
	
	public void setWktSelesai(Date wktSelesai) {
		this.wktSelesai = wktSelesai;
	}
	
	public String getKdKelas() {
		return kdKelas;
	}
	
	public void setKdKelas(String kdKelas) {
		this.kdKelas = kdKelas;
	}
	
	public String getKdRuang() {
		return kdRuang;
	}
	
	public void setKdRuang(String kdRuang) {
		this.kdRuang = kdRuang;
	}
	
	public String getNamaDosen() {
		return namaDosen;
	}
	
	public void setNamaDosen(String namaDosen) {
		this.namaDosen = namaDosen;
	}
	
	public String getHari() {
		return hari;
	}
	
	public void setHari(String hari) {
		this.hari = hari;
	}
}
