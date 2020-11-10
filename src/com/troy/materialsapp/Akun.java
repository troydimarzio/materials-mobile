package com.troy.materialsapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Akun extends Activity {
	
	LinearLayout home, tukang, akun, cart;
	SessionManager session;
	Button logout, riwayat_pemesanan;
	TextView id, emails, nama, alamat, no_telpon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_akun);
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		Toast.makeText(getApplicationContext(), "login status: "+session.isLoggedIn(), Toast.LENGTH_LONG).show();
		
		home = (LinearLayout)findViewById(R.id.menu_home);
		tukang = (LinearLayout)findViewById(R.id.menu_tukang);
		akun = (LinearLayout)findViewById(R.id.menu_akun);
		cart = (LinearLayout)findViewById(R.id.menu_cart);
		logout = (Button)findViewById(R.id.btn_logout);
		riwayat_pemesanan = (Button)findViewById(R.id.btn_riwayat_pemesanan);
		
		//id = (TextView)findViewById(R.id.id_biodata);
		emails = (TextView)findViewById(R.id.email);
		nama = (TextView)findViewById(R.id.nama_lengkap);
		alamat = (TextView)findViewById(R.id.alamat);
		no_telpon = (TextView)findViewById(R.id.no_telpon);
		
		User user = session.getUserData();
		//String id_biodata = user.id_biodata;
		String email = user.email;
		String nama_lengkap = user.nama_lengkap;
		String alamat = user.alamat;
		String no_telpon = user.no_telpon;
		
		//id.setText("Id Biodata: "+id_biodata);
		emails.setText("Email: "+email);
		nama.setText("Nama: "+nama_lengkap);
		this.alamat.setText("Alamat: "+alamat);
		this.no_telpon.setText("No. Telpon: "+no_telpon);
		
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				session.logoutUser();
				Toast.makeText(getApplicationContext(), "Anda Sudah Keluar!", Toast.LENGTH_LONG).show();
			}
		});
		
		riwayat_pemesanan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Akun.this, Riwayat_pemesanan.class);
				startActivity(i);
			}
		});
		
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Akun.this, Home.class);
				startActivity(i);
			}
		});
		
		tukang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Akun.this, Tukang.class);
				startActivity(i);
			}
		});

		akun.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Akun.this, Akun.class);
				startActivity(i);
			}
		});
		
		cart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Akun.this, Keranjang.class);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.akun, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
