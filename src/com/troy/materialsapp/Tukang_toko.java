package com.troy.materialsapp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Tukang_toko extends Activity {
	
	private String TAG = Tukang_toko.class.getSimpleName();
	private ListView listView;
	private ProgressDialog pDialog;
	
	private static String URL = Connection.URL+"list_tukang_toko.php";
	
	ArrayList<HashMap<String, String>> listTukang;
	ArrayList<Tukang_toko_model> tukangList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tukang_toko);
		

		tukangList = new ArrayList<Tukang_toko_model>();
		listTukang = new ArrayList<HashMap<String,String>>();
		
		listView = (ListView) findViewById(R.id.list_tukang_toko);
		
		new GetDataToko().execute();
		
	}
	
	
	private class GetDataToko extends AsyncTask<Void, Void, Void> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Tukang_toko.this);
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
                        
                        Tukang_toko_model t = new Tukang_toko_model(
                        		objek.getString("nama"),
                        		objek.getString("spesialis"),
                        		objek.getString("pengalaman"),
                        		objek.getString("no_telpon_tt"),
                        		objek.getString("id_tukang_toko")
                        );
                        
                        String nama = objek.getString("nama");
                        String spesialis = objek.getString("spesialis");
                        String pengalaman = objek.getString("pengalaman");
                        String no_telpon_tt = objek.getString("no_telpon_tt");
                        String id_tukang_toko = objek.getString("id_tukang_toko");

                        HashMap<String, String> tukang = new HashMap<String, String>();

                        tukang.put("nama", nama);
                        tukang.put("spesialis", spesialis);
                        tukang.put("pengalaman", pengalaman);
                        tukang.put("no_telpon_tt", no_telpon_tt);
                        tukang.put("id_tukang_toko", id_tukang_toko);

                        listTukang.add(tukang);
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
                    Tukang_toko.this, listTukang,
                    R.layout.custom_tukang_toko, new String[]{"nama", "spesialis", "pengalaman", "no_telpon_tt"}, new int[]{R.id.nama_tukang,
                    R.id.spesialis, R.id.pengalaman_kerja, R.id.no_telpon_tt});

            listView.setAdapter(adapter);
           
        }
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tukang_toko, menu);
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
