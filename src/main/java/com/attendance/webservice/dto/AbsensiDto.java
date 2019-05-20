package com.attendance.webservice.dto;

public class AbsensiDto {
	private String namaMatkul;
	private boolean jenisMatkul;
	private long hadir;
	private long tidakHadir;
	
	public AbsensiDto() {
		
	}
	
	public AbsensiDto(String namaMatkul, boolean jenisMatkul, long hadir, long tidakHadir) {
		this.namaMatkul = namaMatkul;
		this.jenisMatkul = jenisMatkul;
		this.hadir = hadir;
		this.tidakHadir = tidakHadir;
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
	
	public long getHadir() {
		return hadir;
	}
	
	public void setHadir(long hadir) {
		this.hadir = hadir;
	}
	
	public long getTidakHadir() {
		return tidakHadir;
	}
	
	public void setTidakHadir(long tidakHadir) {
		this.tidakHadir = tidakHadir;
	}
}
