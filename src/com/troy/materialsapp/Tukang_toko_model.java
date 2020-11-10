package com.troy.materialsapp;

public class Tukang_toko_model {
	
	private String nama;
	private String spesialis;
	private String pengalaman;
	private String id_tukang_toko;
	private String no_telpon_tt;
	
	public Tukang_toko_model() {};
	
	public Tukang_toko_model(String nama, String spesialis, String pengalaman, String id_tukang_toko, String no_telpon_tt) {
		this.nama = nama;
		this.spesialis = spesialis;
		this.pengalaman = pengalaman;
		this.id_tukang_toko = id_tukang_toko;
		this.no_telpon_tt = no_telpon_tt;
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

	public String getPengalaman() {
		return pengalaman;
	}

	public void setPengalaman(String pengalaman) {
		this.pengalaman = pengalaman;
	}

	public String getId_tukang_toko() {
		return id_tukang_toko;
	}

	public void setId_tukang_toko(String id_tukang_toko) {
		this.id_tukang_toko = id_tukang_toko;
	}

	public String getNomor_telpon() {
		return no_telpon_tt;
	}

	public void setNomor_telpon(String no_telpon_tt) {
		this.no_telpon_tt = no_telpon_tt;
	}
	
	
}
