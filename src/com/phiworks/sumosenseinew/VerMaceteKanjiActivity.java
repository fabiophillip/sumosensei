package com.phiworks.sumosenseinew;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class VerMaceteKanjiActivity extends ActivityDoJogoComSom {
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_macete_kanji);
		String urlDoMaceteKanji;
		if (savedInstanceState == null) {
		    Bundle extras = getIntent().getExtras();
		    if(extras == null) {
		        urlDoMaceteKanji= null;
		    } else {
		        urlDoMaceteKanji= extras.getString("urlDoMacete");
		    }
		} else {
		    urlDoMaceteKanji= (String) savedInstanceState.getSerializable("urlDoMacete");
		}
		try
		{
			int idImagemDoMaceteKanji = getResources().getIdentifier(urlDoMaceteKanji, "drawable", getPackageName());
			ImageView imageviewMaceteKanji = (ImageView)findViewById(R.id.imagem_do_macete_do_kanji);
			imageviewMaceteKanji.setImageResource(idImagemDoMaceteKanji);
			int idTextoMaceteKanji = getResources().getIdentifier(urlDoMaceteKanji, "string", getPackageName());
			String textoDoMacete = getResources().getString(idTextoMaceteKanji);
			TextView textviewDoMacete = (TextView) findViewById(R.id.texto_do_macete_do_kanji);
			textviewDoMacete.setText(textoDoMacete);
		}
		catch(Exception exc)
		{
			Toast.makeText(this, exc.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		

		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ver_macete_kanji, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_ver_macete_kanji, container, false);
			return rootView;
		}
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

}
