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
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Home extends Activity {
	
	LinearLayout home, tukang, akun, cart;
	private String TAG = Home.class.getSimpleName();
	private ListView listView;
	private ProgressDialog pDialog;
	
	private static String URL = Connection.URL+"list_material.php";
	
	ArrayList<HashMap<String, String>> listMaterial;
	ArrayList<Material_model> materialList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		
		
		materialList = new ArrayList<Material_model>();
		listMaterial = new ArrayList<HashMap<String,String>>();
		
		listView = (ListView) findViewById(R.id.list_material);
		
		new GetData().execute();
		
		akun = (LinearLayout)findViewById(R.id.menu_akun);
		home = (LinearLayout)findViewById(R.id.menu_home);
		tukang = (LinearLayout)findViewById(R.id.menu_tukang);
		cart = (LinearLayout)findViewById(R.id.menu_cart);
		
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this, Home.class);
				startActivity(i);
			}
		});
		
		akun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this, Akun.class);
				startActivity(i);
			}
		});
		
		tukang.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this, Tukang.class);
				startActivity(i);
			}
		});
		
		cart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this, Keranjang.class);
				startActivity(i);
			}
		});
	}
	
	
	private class GetData extends AsyncTask<Void, Void, Void> {
		
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Home.this);
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
                        
                        Material_model m = new Material_model(
                        		objek.getString("id_material"),
                        		objek.getString("nama"),
                        		objek.getString("deskripsi"),
                        		objek.getString("harga"),
                        		objek.getString("stok"),
                        		objek.getString("kategori"),
                        		objek.getString("toko"),
                        		objek.getString("satuan"),
                        		objek.getString("rating"),
                        		objek.getString("alamat"));
                        
                        String id_material = objek.getString("id_material");
                        String nama = objek.getString("nama");
                        String deskripsi = objek.getString("deskripsi");
                        String harga = objek.getString("harga");
                        String stok = objek.getString("stok");
                        String toko = objek.getString("toko");
                        String kategori = objek.getString("kategori");
                        String satuan = objek.getString("satuan");
                        String rating = objek.getString("rating");
                        String alamat = objek.getString("alamat");

                        HashMap<String, String> material = new HashMap<String, String>();

                        material.put("id_material", id_material);
                        material.put("nama", nama);
                        material.put("deskripsi", deskripsi);
                        material.put("harga", harga);
                        material.put("stok", stok);
                        material.put("toko", toko);
                        material.put("kategori", kategori);
                        material.put("satuan", satuan);
                        material.put("rating", rating);
                        material.put("alamat", alamat);

                        listMaterial.add(material);
                        materialList.add(m);
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
                    Home.this, listMaterial,
                    R.layout.custom_list_material, new String[]{"nama", "harga", "toko"}, new int[]{R.id.nama,
                    R.id.harga, R.id.toko});

            listView.setAdapter(adapter);
            
            listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                	Intent intent = new Intent(Home.this, Detail_material.class);
                	intent.putExtra("id_material", materialList.get(position).getId());
                	intent.putExtra("nama", materialList.get(position).getNama_material());
                	intent.putExtra("deskripsi", materialList.get(position).getDeskripsi());
                	intent.putExtra("harga", materialList.get(position).getHarga());
                	intent.putExtra("stok", materialList.get(position).getStok());
                	intent.putExtra("kategori", materialList.get(position).getKategori());
                	intent.putExtra("toko", materialList.get(position).getToko());
                	intent.putExtra("satuan", materialList.get(position).getSatuan());
                	intent.putExtra("rating", materialList.get(position).getRating());
                	intent.putExtra("alamat", materialList.get(position).getAlamat());
                  startActivity(intent);
                }});
        }
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
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
