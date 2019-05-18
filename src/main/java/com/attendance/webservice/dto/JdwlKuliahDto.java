package com.attendance.webservice.dto;

import java.util.Date;

public class JdwlKuliahDto {
	
	private String namaMatkul;
	private boolean jenisMatkul;
	private Date jamMulai;
	private Date jamSelesai;
	private String kodeRuangan;
	private String macAddress;
	
	public JdwlKuliahDto() {
		
	}
	
	public JdwlKuliahDto(String namaMatkul, boolean jenisMatkul, Date jamMulai, Date jamSelesai, String kodeRuangan,
			String macAddress) {
		this.namaMatkul = namaMatkul;
		this.jenisMatkul = jenisMatkul;
		this.jamMulai = jamMulai;
		this.jamSelesai = jamSelesai;
		this.kodeRuangan = kodeRuangan;
		this.macAddress = macAddress;
	}
	
	public String getNamaMatkul() {
		return namaMatkul;
	}
	
	public void setNamaMatkul(String namaMatkul) {
		this.namaMatkul = namaMatkul;
	}
	
	public boolean getJenisMatkul() {
		return jenisMatkul;
	}
	
	public void setJenisMatkul(boolean jenisMatkul) {
		this.jenisMatkul = jenisMatkul;
	}
	
	public Date getJamMulai() {
		return jamMulai;
	}
	
	public void setJamMulai(Date jamMulai) {
		this.jamMulai = jamMulai;
	}
		
	public Date getJamSelesai() {
		return jamSelesai;
	}
	
	public void setJamSelesai(Date jamSelesai) {
		this.jamSelesai = jamSelesai;
	}
	
		
	public String getKodeRuangan() {
		return kodeRuangan;
	}
	
	public void setKodeRuangan(String kodeRuangan) {
		this.kodeRuangan = kodeRuangan;
	}
	
	public String getMacAddress() {
		return macAddress;
	}
	
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
}
