package com.troy.materialsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Detail_material extends Activity {
	
	TextView nama, harga, kategori, deskripsi, toko, alamat;
	Button tombolPesan;
	Animation slide_in_left, slide_out_right;
	ViewFlipper vf;
	String d_stok, d_satuan, d_nama, d_harga, d_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_material);
		
		vf = (ViewFlipper) this.findViewById(R.id.viewFlipper);
		slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
		slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
		
		vf.setInAnimation(slide_in_left);
		vf.setOutAnimation(slide_out_right);
		
		vf.setAutoStart(true);
		vf.setFlipInterval(3000);
		vf.startFlipping();
		
		nama = (TextView)findViewById(R.id.nama);
		harga = (TextView)findViewById(R.id.harga);
		//stok = (TextView)findViewById(R.id.stok);
		kategori = (TextView)findViewById(R.id.kategori);
		deskripsi = (TextView)findViewById(R.id.deskripsi);
		toko = (TextView)findViewById(R.id.toko);
		//satuan = (TextView)findViewById(R.id.satuan);
		tombolPesan = (Button)findViewById(R.id.btn_pesan);
		alamat = (TextView)findViewById(R.id.alamat);
		
		d_id = getIntent().getExtras().getString("id_material");
		d_nama = getIntent().getExtras().getString("nama");
		d_harga = getIntent().getExtras().getString("harga");
		d_stok = getIntent().getExtras().getString("stok");
		String d_kategori = getIntent().getExtras().getString("kategori");
		String d_toko = getIntent().getExtras().getString("toko");
		String d_deskripsi = getIntent().getExtras().getString("deskripsi");
		d_satuan = getIntent().getExtras().getString("satuan");
		String d_alamat = getIntent().getExtras().getString("alamat");
		
		nama.setText(d_nama);
		harga.setText("Rp."+d_harga);
		toko.setText(d_toko);
		kategori.setText(d_kategori);
		deskripsi.setText(d_deskripsi);
		alamat.setText(d_alamat);
		
		
		tombolPesan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Detail_material.this, Kuantitas.class);
				Bundle b = new Bundle();
				b.putString("id_material", d_id);
				b.putString("nama", d_nama);
				b.putString("harga", d_harga);
				b.putString("stok", d_stok);
				b.putString("satuan", d_satuan);
				i.putExtras(b);
				startActivity(i);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_material, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
