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

public class Konfirmasi extends Activity {
	
	Button btn_batal, btn_back;
	TextView id_biodata, rekomendasi_tukang;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	String get_id_biodata;
	
	private static final String URL = Connection.URL+"batal_transaksi.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_konfirmasi);
		
		Bundle b = getIntent().getExtras();
		get_id_biodata = b.getString("id_biodata");
		
		btn_batal = (Button)findViewById(R.id.btn_batal);
		btn_back = (Button)findViewById(R.id.btn_back);
		id_biodata = (TextView)findViewById(R.id.id_biodata);
		rekomendasi_tukang = (TextView)findViewById(R.id.rekomendasi_tukang);
		
		id_biodata.setText(get_id_biodata);
		id_biodata.setVisibility(id_biodata.INVISIBLE);
		
		btn_batal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Batal().execute();
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Konfirmasi.this, Home.class);
				startActivity(i);
			}
		});
		
		rekomendasi_tukang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Konfirmasi.this, Tukang_toko.class);
				startActivity(i);
			}
		});
	}
	
	
	class Batal extends AsyncTask<String, String, String> {
		
		boolean failure = false;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Konfirmasi.this);
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
			
			try {
				
				// buliding parameters
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("id_biodata", get_id_biodata));
				
				Log.d("request!", "starting");
				
				// posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
				
				// full json response
				Log.d("Login attempt", json.toString());
				
				// json success element
				success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					
					Log.d("User Created!", json.toString());
					Intent i = new Intent(Konfirmasi.this, Home.class);
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
				Toast.makeText(Konfirmasi.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.konfirmasi, menu);
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
