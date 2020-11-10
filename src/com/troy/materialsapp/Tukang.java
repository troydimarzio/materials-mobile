package com.troy.materialsapp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Tukang extends Activity {
	
	LinearLayout home, tukang, akun, cart;
	private String TAG = Tukang.class.getSimpleName();
	private ListView listView;
	private ProgressDialog pDialog;
	
	private static String URL = Connection.URL+"list_tukang.php";
	
	ArrayList<HashMap<String, String>> listTukangg;
	ArrayList<Tukang_model> tukangList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tukang);
		

		tukangList = new ArrayList<Tukang_model>();
		listTukangg = new ArrayList<HashMap<String,String>>();
		
		listView = (ListView) findViewById(R.id.list_tukang);
		
		new GetData().execute();
		
		akun = (LinearLayout)findViewById(R.id.menu_akun);
		home = (LinearLayout)findViewById(R.id.menu_home);
		tukang = (LinearLayout)findViewById(R.id.menu_tukang);
		cart = (LinearLayout)findViewById(R.id.menu_cart);
		
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Tukang.this, Home.class);
				startActivity(i);
			}
		});
		
		akun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Tukang.this, Akun.class);
				startActivity(i);
			}
		});
		
		tukang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Tukang.this, Tukang.class);
				startActivity(i);
			}
		});
		
		tukang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Tukang.this, Keranjang.class);
				startActivity(i);
			}
		});
	}
	
	
	private class GetData extends AsyncTask<Void, Void, Void> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Tukang.this);
			pDialog.setMessage("Tunggu Sebentar..");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			
			JSONParser json = new JSONParser();
			
            String jsonStr = json.makeServiceCall(URL);
            
            Log.e(TAG, "Response from url: " + jsonStr);
            
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObj.getJSONArray("post");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objek = jsonArray.getJSONObject(i);
                        
                        Tukang_model t = new Tukang_model(
                        		objek.getString("nama_tukang"),
                        		objek.getString("spesialis_tukang"),
                        		objek.getString("status"),
                        		objek.getString("no_telpon"),
                        		objek.getString("umur"),
                        		objek.getString("pengalaman"),
                        		objek.getString("alamat")
                        );
                        
                        String nama = objek.getString("nama_tukang");
                        String spesialis = objek.getString("spesialis_tukang");
                        String status = objek.getString("status");
                        String no_telpon = objek.getString("no_telpon");
                        String umur = objek.getString("umur");
                        String pengalaman = objek.getString("pengalaman");
                        String alamat = objek.getString("alamat");

                        HashMap<String, String> tukang = new HashMap<String, String>();

                        tukang.put("nama_tukang", nama);
                        tukang.put("spesialis_tukang", spesialis);
                        tukang.put("status", status);
                        tukang.put("no_telpon", no_telpon);
                        tukang.put("umur", umur);
                        tukang.put("pengalaman", pengalaman);
                        tukang.put("alamat", alamat);

                        listTukangg.add(tukang);
                        tukangList.add(t);
                    }
                    
                } catch (final JSONException e) {
                	Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Ada kesalahan!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
			return null;
		}
		
		@Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    Tukang.this, listTukangg,
                    R.layout.custom_list_tukang, new String[]{"nama_tukang", "spesialis_tukang", "status"}, new int[]{R.id.nama_tukangg,
                    R.id.spesialiss, R.id.status_kerjaa});

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent i = new Intent(Tukang.this, Detail_tukang.class);
					i.putExtra("nama", tukangList.get(position).getNama());
					i.putExtra("spesialis", tukangList.get(position).getSpesialis());
					i.putExtra("status", tukangList.get(position).getStatus());
					i.putExtra("no_telpon", tukangList.get(position).getNo_telpon());
					i.putExtra("umur", tukangList.get(position).getUmur());
					i.putExtra("pengalaman", tukangList.get(position).getPengalaman());
					i.putExtra("alamat", tukangList.get(position).getAlamat());
					startActivity(i);
				}
            	
			});
        }
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tukang, menu);
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
