package com.troy.materialsapp;

public class Material_model {
 
	private String nama_material ;
	private String deskripsi;
	private String harga;
	private String stok;
	private String kategori;
	private String toko;
	private String satuan;
	private String rating;
	private String id_material;
	private String alamat;
	
	public Material_model() {
		// TODO Auto-generated constructor stub
	};
	
	public Material_model(String id_material, String nama_material, String deskripsi, String harga, String stok, String kategori, String toko, String satuan, String rating, String alamat){
		this.id_material = id_material;
		this.nama_material = nama_material;
		this.deskripsi = deskripsi;
		this.harga = harga;
		this.toko = toko;
		this.stok = stok;
		this.kategori = kategori;
		this.satuan = satuan;
		this.rating = rating;
		this.alamat = alamat;
		
	}
	
	public String getId() {
		return id_material;
	}
	
	public void setId(String id_material) {
		this.id_material = id_material;
	}

	public String getNama_material() {
		return nama_material;
	}

	public void setNama_material(String nama_material) {
		this.nama_material = nama_material;
	}
	
	public String getDeskripsi() {
		return deskripsi;
	}
	
	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	
	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}

	public String getStok() {
		return stok;
	}

	public void setStok(String stok) {
		this.stok = stok;
	}
	
	public String getKategori() {
		return kategori;
	}

	public void setKategori(String kategori) {
		this.kategori = kategori;
	}
	
	public String getToko() {
		return toko;
	}
	
	public void setToko(String toko) {
		this.toko = toko;
	}
	
	public String getSatuan() {
		return satuan;
	}
	
	public void setSatuan(String satuan) {
		this.satuan = satuan;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public String getAlamat() {
		return alamat;
	}
	
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
}
