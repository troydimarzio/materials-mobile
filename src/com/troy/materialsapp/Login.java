package com.troy.materialsapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.troy.materialsapp.JSONParser;
import com.troy.materialsapp.SessionManager;

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

public class Login extends Activity {
	
	private TextView link_regis;
	private EditText edit_email, edit_pass;
	private Button buttonLogin;
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	SessionManager session;
	
	ArrayList<HashMap<String, String>> profil;
	
	private static final String URL = Connection.URL+"login.php";
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		session = new SessionManager(getApplicationContext());
		Toast.makeText(getApplicationContext(), "login status: "+session.isLoggedIn(), Toast.LENGTH_LONG).show();
		
		edit_email       = (EditText)findViewById(R.id.email);
		edit_pass        = (EditText)findViewById(R.id.password);
		buttonLogin = (Button)findViewById(R.id.btn_login);
	
		link_regis = (TextView)findViewById(R.id.link_regis);
		link_regis.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Login.this, Regis.class);
				startActivity(i);
			}
		});
		
		
		buttonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AttemptLogin().execute();
			}
		});
	
	}
	
	
	class AttemptLogin extends AsyncTask<String, String, String> {
		
		boolean failure = false;
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Sedang Memproses...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {
			
			int success;
			String email     = edit_email.getText().toString();
			String password = edit_pass.getText().toString();
			
			String id_biodata, nama_lengkap, alamat, no_telpon;
			
			try {
				
				// building parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user", email));
				params.add(new BasicNameValuePair("password", password));
				
				Log.d("request!", "starting");
				// get product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
				
				// check your log for json response
				Log.d("Login attempt", json.toString());
				
				// json sukses tag
				success = json.getInt(TAG_SUCCESS);
				
				if (success == 1) {
					
					Log.d("Login Berhasil!", json.toString());
					id_biodata = json.getString("id_biodata");
					nama_lengkap = json.getString("nama_lengkap");
					alamat = json.getString("alamat");
					no_telpon = json.getString("no_telpon");
					
					session.createLoginSession(id_biodata, email, nama_lengkap, alamat, no_telpon);
					
					Intent i = new Intent(Login.this, Akun.class);
					
					startActivity(i);
					
							
				} else {
					
					Log.d("Login Gagal!", json.getString(TAG_MESSAGE));
				}
				
			} catch (JSONException e){
				e.printStackTrace();
			}	
			
			
			return null;
		}
		
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
			}
		}
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
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
