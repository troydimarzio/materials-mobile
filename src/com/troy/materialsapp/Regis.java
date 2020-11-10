package com.troy.materialsapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.troy.materialsapp.JSONParser;

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
import android.widget.TextView;
import android.widget.Toast;

public class Regis extends Activity {
	
	private TextView link_login;
	private EditText email, pass, nama_lengkap, alamat_lengkap, no_telepon;
	private Button buttonDaftar;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	String var_nama, var_alamat, var_no_telpon, var_email, var_pass;
	
	private static final String URL = Connection.URL+"regis.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_regis);
		
		email        = (EditText)findViewById(R.id.email);
		pass         = (EditText)findViewById(R.id.password);
		nama_lengkap = (EditText)findViewById(R.id.nama);
		alamat_lengkap = (EditText)findViewById(R.id.alamat);
		no_telepon    = (EditText)findViewById(R.id.no_telpon);
		buttonDaftar = (Button)findViewById(R.id.btn_regis);
		
		link_login = (TextView)findViewById(R.id.link_login);
		link_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Regis.this, Login.class);
				startActivity(i);
			}
		});
		
		buttonDaftar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new CreateUser().execute();	
			}
		});
	}

	
	class CreateUser extends AsyncTask<String, String, String> {
		
		boolean failure = false;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Regis.this);
			pDialog.setMessage("Sedang Memproses...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			int success;
			var_nama = nama_lengkap.getText().toString();
			var_email = email.getText().toString();
			var_pass = pass.getText().toString();
			var_alamat = alamat_lengkap.getText().toString();
			var_no_telpon = no_telepon.getText().toString();
			
			try {
				
				// buliding parameters
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("nama", var_nama));
				params.add(new BasicNameValuePair("username", var_email));
				params.add(new BasicNameValuePair("password", var_pass));
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
					
					Log.d("User Created!", json.toString());
					Intent i = new Intent(Regis.this, Login.class);
					finish();
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
				Toast.makeText(Regis.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.regis, menu);
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
