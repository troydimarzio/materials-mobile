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
import android.widget.EditText;
import android.widget.Toast;

public class Pengiriman extends Activity {
	
	SessionManager session;
	EditText alamat, nama, no_telpon, id_biodata;
	Button btn_kirim;
	int get_jumlah, get_total, get_id_material;
	String get_satuan, get_nama_material, get_harga, var_nama_lengkap, var_alamat, var_no_telpon, var_id_biodata;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	
	private static final String URL = Connection.URL+"edit_pengiriman.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pengiriman);
		
		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		
		alamat = (EditText)findViewById(R.id.alamat);
		nama = (EditText)findViewById(R.id.nama_lengkap);
		no_telpon = (EditText)findViewById(R.id.no_telpon);
		id_biodata = (EditText)findViewById(R.id.id_biodata);
		btn_kirim = (Button)findViewById(R.id.btn_kirim);
		
		Bundle b = getIntent().getExtras();
		get_id_material = b.getInt("id_material");
		get_jumlah = b.getInt("jumlah");
		get_total = b.getInt("total_harga");
		get_satuan = b.getString("satuan");
		get_nama_material = b.getString("nama_material");
		get_harga = b.getString("harga");
		
		User user = session.getUserData();
		var_id_biodata = user.id_biodata;
		var_nama_lengkap = user.nama_lengkap;
		var_alamat = user.alamat;
		var_no_telpon = user.no_telpon;
		
		this.id_biodata.setText(var_id_biodata);
		id_biodata.setVisibility(id_biodata.INVISIBLE);
		nama.setText(var_nama_lengkap);
		this.alamat.setText(var_alamat);
		this.no_telpon.setText(var_no_telpon);
		
		btn_kirim.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new EditPengiriman().execute();
			}
		});
	}
	
	
	class EditPengiriman extends AsyncTask<String, String, String> {
		
		boolean failure = false;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Pengiriman.this);
			pDialog.setMessage("Tunggu...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int success;
			var_id_biodata = id_biodata.getText().toString();
			var_nama_lengkap = nama.getText().toString();
			var_alamat = alamat.getText().toString();
			var_no_telpon = no_telpon.getText().toString();
			
			try {
				
				// buliding parameters
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id_biodata", var_id_biodata));
				params.add(new BasicNameValuePair("nama_lengkap", var_nama_lengkap));
				params.add(new BasicNameValuePair("alamat", var_alamat));
				params.add(new BasicNameValuePair("no_telpon", var_no_telpon));
				
				Log.d("request!", "starting");
				
				// posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
				
				// full json response
				Log.d("Login attempt", json.toString());
				
				// json success element
				success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					
					Log.d("berhasil!", json.toString());
					Intent i = new Intent(Pengiriman.this, Transaksi.class);
					Bundle b = new Bundle();
					b.putString("id_biodata", var_id_biodata);
					b.putString("nama_lengkap", var_nama_lengkap);
					b.putString("alamat", var_alamat);
					b.putString("no_telpon", var_no_telpon);
					b.putInt("id_material", get_id_material);
					b.putInt("jumlah", get_jumlah);
					b.putInt("total_harga", get_total);
					b.putString("satuan", get_satuan);
					b.putString("nama_material", get_nama_material);
					b.putString("harga", get_harga);
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
				Toast.makeText(Pengiriman.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pengiriman, menu);
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
