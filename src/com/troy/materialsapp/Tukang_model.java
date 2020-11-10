package com.troy.materialsapp;

public class Tukang_model {
	
	private String nama;
	private String spesialis;
	private String status;
	private String no_telpon;
	private String umur;
	private String pengalaman;
	private String alamat;
	
	public Tukang_model() {};
	
	public Tukang_model(String nama, String spesialis, String status, String no_telpon, String umur, String pengalaman, String alamat) {
		this.nama = nama;
		this.spesialis = spesialis;
		this.status = status;
		this.no_telpon = no_telpon;
		this.umur = umur;
		this.pengalaman = pengalaman;
		this.alamat = alamat;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getSpesialis() {
		return spesialis;
	}

	public void setSpesialis(String spesialis) {
		this.spesialis = spesialis;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNo_telpon() {
		return no_telpon;
	}

	public void setNo_telpon(String no_telpon) {
		this.no_telpon = no_telpon;
	}

	public String getUmur() {
		return umur;
	}

	public void setUmur(String umur) {
		this.umur = umur;
	}

	public String getPengalaman() {
		return pengalaman;
	}

	public void setPengalaman(String pengalaman) {
		this.pengalaman = pengalaman;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	
}
