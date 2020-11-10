package com.troy.materialsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

public class Detail_tukang extends Activity {
	
	TextView nama, spesialis, status, no_telpon, umur, pengalaman, alamat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_tukang);
		
		nama = (TextView)findViewById(R.id.nama);
		spesialis = (TextView)findViewById(R.id.spesialis);
		status = (TextView)findViewById(R.id.status);
		no_telpon = (TextView)findViewById(R.id.no_telpon);
		umur = (TextView)findViewById(R.id.umur);
		pengalaman = (TextView)findViewById(R.id.pengalaman);
		alamat = (TextView)findViewById(R.id.alamat);
		
		String d_nama = getIntent().getExtras().getString("nama");
		String d_spesialis = getIntent().getExtras().getString("spesialis");
		String d_status = getIntent().getExtras().getString("status");
		String d_no_telpon = getIntent().getExtras().getString("no_telpon");
		String d_umur = getIntent().getExtras().getString("umur");
		String d_pengalaman = getIntent().getExtras().getString("pengalaman");
		String d_alamat = getIntent().getExtras().getString("alamat");
		
		nama.setText(d_nama);
		spesialis.setText(d_spesialis);
		no_telpon.setText(d_no_telpon);
		umur.setText(d_umur);
		pengalaman.setText(d_pengalaman);
		alamat.setText(d_alamat);
		status.setText(d_status);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_tukang, menu);
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
