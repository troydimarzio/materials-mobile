package com.troy.materialsapp;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	
	// shared preferences
	SharedPreferences pref;
	// editor for shared preferences
	Editor editor;
	Context _context;
	
	// shared pref mode
	int PRIVATE_MODE = 0;
	
	// shared pref file name
	private static final String PREF_NAME = "AndroidHivePref";
	
	// all shared preferences keys
	private static final String IS_LOGIN = "IsLoggedIn";
	
	// user name (membuat variabel public di akses dari luar)
	public static final String KEY_ID = "id_biodata";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_ALAMAT = "alamat";
	public static final String KEY_NOTELPON = "no_telpon";
	public static final String KEY_NAMA = "nama_lengkap";
	
	// construktor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	// create login session
	public void createLoginSession(String id_biodata, String email, String nama_lengkap, String alamat, String no_telpon){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		// Storing name in pref
		editor.putString(KEY_ID, id_biodata);
		editor.putString(KEY_NAMA, nama_lengkap);
		editor.putString(KEY_ALAMAT, alamat);
		editor.putString(KEY_NOTELPON, no_telpon);
		editor.putString(KEY_EMAIL, email);
		//  commit changes
		editor.commit();
	}
	
	/**
	 * check login method will check user login status
	 * if false it will redirect user to login page
	 * else wont do enything
	 *  **/
	
	public void checkLogin(){
		// check login status
		if(!this.isLoggedIn()){
			
			Intent i = new Intent(_context, Login.class);
			// tutup semua aktivity
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(i);
		}
	}
	
	// get stored session data
	public HashMap<String, String> getUserDetails(){
		
		HashMap<String, String> user = new HashMap<String, String>();
		
		user.put(KEY_ID, pref.getString(KEY_ID, null));
//		user.put(KEY_NAMA, pref.getString(KEY_NAMA, null));
//		user.put(KEY_ALAMAT, pref.getString(KEY_ALAMAT, null));
//		user.put(KEY_NOTELPON, pref.getString(KEY_NOTELPON, null));
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		return user;
	}
	
	// clear session details
	public void logoutUser(){
		// clearing all data from shared preferences
		editor.clear();
		editor.commit();
		
		// after logout redirect user to login activity
		Intent i = new Intent(_context, Login.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		_context.startActivity(i);
	}
	
	// quick check for login
	// get login state
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
	
	
	
	
	public void setUserData(User user) {
        editor.putString("id_biodata", user.id_biodata);
        editor.putString("email", user.email);
        editor.commit();
    }
	
	public User getUserData(){
        String id_biodata = pref.getString("id_biodata", "");
        String email = pref.getString("email", "");
        String nama_lengkap = pref.getString("nama_lengkap", "");
        String alamat = pref.getString("alamat", "");
        String no_telpon = pref.getString("no_telpon", "");

        User user = new User(id_biodata, email, nama_lengkap, alamat, no_telpon);
        return user;
	}
}
