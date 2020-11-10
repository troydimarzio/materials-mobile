package com.troy.materialsapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Transaksi extends Activity {
	
	String get_id_biodata, get_nama_lengkap, get_alamat, get_no_telpon, get_satuan, get_nama_material,
	get_harga;
	int get_jumlah, get_total_harga, get_id_material;
	
	String var_jumlah, var_total, var_id_material;
	
	SessionManager session;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	Button btn_transaksi, btn_ubah_pengiriman;
	TextView data_pengiriman, nama_material, harga, jumlah, total_harga, total_harga1, id_material, id_biodata;
	
	
	private static final String URL = Connection.URL+"transaksi.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_transaksi);
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		
		Bundle b = getIntent().getExtras();
		get_id_material = b.getInt("id_material");
		get_id_biodata = b.getString("id_biodata");
		get_nama_lengkap = b.getString("nama_lengkap");
		get_alamat = b.getString("alamat");
		get_no_telpon = b.getString("no_telpon");
		get_satuan = b.getString("satuan");
		get_nama_material = b.getString("nama_material");
		get_harga = b.getString("harga");
		get_jumlah = b.getInt("jumlah");
		get_total_harga = b.getInt("total_harga");
		
		
		var_jumlah = String.valueOf(get_jumlah);
		var_total = String.valueOf(get_total_harga);
		var_id_material = String.valueOf(get_id_material);
		
		btn_transaksi = (Button)findViewById(R.id.btn_transaksi);
		btn_ubah_pengiriman = (Button)findViewById(R.id.btn_ubah_pengiriman);
		nama_material = (TextView)findViewById(R.id.nama_material);
		harga = (TextView)findViewById(R.id.harga);
		jumlah = (TextView)findViewById(R.id.jumlah);
		data_pengiriman = (TextView)findViewById(R.id.data_pengiriman);
		total_harga = (TextView)findViewById(R.id.total_harga);
		total_harga1 = (TextView)findViewById(R.id.total_harga1);
		id_biodata = (TextView)findViewById(R.id.id_biodata);
		id_material = (TextView)findViewById(R.id.id_material);
		
		data_pengiriman.setText(get_nama_lengkap+", "+get_alamat+", "+get_no_telpon);
		nama_material.setText(get_nama_material);
		harga.setText("Rp."+get_harga);
		jumlah.setText(var_jumlah);
		total_harga.setText("Rp."+get_total_harga);
		total_harga1.setText(var_total);
		id_material.setText(""+get_id_material);
		id_biodata.setText(get_id_biodata);
		id_biodata.setVisibility(id_biodata.INVISIBLE);
		id_material.setVisibility(id_material.INVISIBLE);
		total_harga1.setVisibility(total_harga1.INVISIBLE);
		
		
		btn_ubah_pengiriman.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_transaksi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CreateTransaksi().execute();
			}
		});
		
		
	}
	
	
	class CreateTransaksi extends AsyncTask<String, String, String> {
		
		boolean failure = false;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Transaksi.this);
			pDialog.setMessage("Sedang Memproses...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int success;
			get_id_biodata = id_biodata.getText().toString();
			var_id_material = id_material.getText().toString();
			var_jumlah = jumlah.getText().toString();
			var_total = total_harga1.getText().toString();
			
			
			try {
				
				// buliding parameters
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id_biodata", get_id_biodata));
				params.add(new BasicNameValuePair("id_material", var_id_material));
				params.add(new BasicNameValuePair("jumlah", var_jumlah));
				params.add(new BasicNameValuePair("total_harga", var_total));
				
				Log.d("request!", "starting");
				
				// posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
				
				// full json response
				Log.d("Login attempt", json.toString());
				
				// json success element
				success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					
					Log.d("User Created!", json.toString());
					Intent i = new Intent(Transaksi.this, Konfirmasi.class);
					Bundle b = new Bundle();
					b.putString("id_biodata", get_id_biodata);
					i.putExtras(b);
					startActivity(i);
					return json.getString(TAG_MESSAGE);
					
				} else {
					
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
					
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(Transaksi.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaksi, menu);
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
