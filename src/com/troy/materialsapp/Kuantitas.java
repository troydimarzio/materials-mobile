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
import android.widget.TextView;
import android.widget.Toast;

public class Kuantitas extends Activity {
	
	TextView nama, harga, stok, jumlah, total;
	Button kurang, tambah, btn_pesan;
	int quantity, total_harga, i_stok, i_harga, i_id_material;
	String get_nama, get_harga, get_stok, get_satuan, get_id_material;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_kuantitas);
		
		nama = (TextView)findViewById(R.id.nama);
		harga = (TextView)findViewById(R.id.harga);
		stok = (TextView)findViewById(R.id.stok);
		jumlah = (TextView)findViewById(R.id.jumlah);
		total = (TextView)findViewById(R.id.total);
		kurang = (Button)findViewById(R.id.kurang);
		tambah = (Button)findViewById(R.id.tambah);
		btn_pesan = (Button)findViewById(R.id.btn_pesan);
		quantity = 1;
		
		Bundle b = getIntent().getExtras();
		get_nama = b.getString("nama");
		get_harga = b.getString("harga");
		get_stok = b.getString("stok");
		get_satuan = b.getString("satuan");
		get_id_material = b.getString("id_material");
		
		nama.setText(get_nama);
		harga.setText("Rp."+get_harga);
		stok.setText(get_stok+" "+get_satuan);
		jumlah.setText(""+quantity);
		kurang.setEnabled(false);
		
		i_stok = Integer.parseInt(get_stok);
		i_harga = Integer.parseInt(get_harga);
		i_id_material = Integer.parseInt(get_id_material);
		total_harga = i_harga;
		total.setText("Rp."+total_harga);
		
		kurang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				quantity--;
				if(quantity == 1){
					kurang.setEnabled(false);
				}
				if(quantity < i_stok){
					tambah.setEnabled(true);
				}
				total_harga = quantity*i_harga;
				jumlah.setText(""+quantity);
				total.setText("Rp."+total_harga);
			}
		});
		
		tambah.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				quantity++;
				if(quantity > 1){
					kurang.setEnabled(true);
				}
				if(quantity >= i_stok) {
					tambah.setEnabled(false);
					Toast.makeText(getApplicationContext(), "Tidak boleh melebihi batas stok!", Toast.LENGTH_LONG).show();
				}
				total_harga = quantity*i_harga;
				jumlah.setText(""+quantity);
				total.setText("Rp."+total_harga);
			}
		});

		btn_pesan.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Kuantitas.this, Pengiriman.class);
				Bundle b = new Bundle();
				b.putString("nama_material", get_nama);
				b.putString("harga", get_harga);
				b.putInt("id_material", i_id_material);
				b.putInt("jumlah", quantity);
				b.putInt("total_harga", total_harga);
				b.putString("satuan", get_satuan);
				i.putExtras(b);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kuantitas, menu);
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
