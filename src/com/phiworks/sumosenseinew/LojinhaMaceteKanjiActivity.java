package com.phiworks.sumosenseinew;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

import lojinha.AdapterListViewItensLojinhaMaceteKanji;
import lojinha.ComprasDaLojinhaDeKanjis;
import lojinha.ConcreteDAOAcessaComprasMaceteKanji;
import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import lojinha.CorrigeNomesDosMacetesComAcento;
import lojinha.DAOAcessaComprasMaceteKanji;
import lojinha.DAOAcessaDinheiroDoJogador;
import lojinha.MaceteKanjiParaListviewSelecionavel;
import bancodedados.MyCustomAdapter;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class LojinhaMaceteKanjiActivity extends ActivityDoJogoComSom {

	private AdapterListViewItensLojinhaMaceteKanji dataAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lojinha_macete_kanji);
		
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		TextView labelDinheiro = (TextView)findViewById(R.id.label_dinheiro);
		String textoLabelDinheiro = getResources().getString(R.string.label_dinheiro);
		DAOAcessaDinheiroDoJogador acessaDinheiroDoJogador = ConcreteDAOAcessaDinheiroDoJogador.getInstance();
		int creditoJogador = acessaDinheiroDoJogador.getCreditoQuePossui(this);
		textoLabelDinheiro = textoLabelDinheiro +  creditoJogador + getResources().getString(R.string.moeda_do_jogo); 
		labelDinheiro.setText(textoLabelDinheiro);
		carregarItensDaLojinha();
	}

	private void carregarItensDaLojinha() {
		ArrayList<MaceteKanjiParaListviewSelecionavel> arrayListMacetesKanji = new ArrayList<MaceteKanjiParaListviewSelecionavel>();
		LinkedList<String> nomesMacetesKanjis = new LinkedList<String>();
		
		DAOAcessaComprasMaceteKanji daoConheceMacetesComprados = ConcreteDAOAcessaComprasMaceteKanji.getInstance();
		ComprasDaLojinhaDeKanjis historicoDeComprasDaLojinha = daoConheceMacetesComprados.getDadosComprasMaceteKanji(getApplicationContext());
		
		Class resources = R.drawable.class;
		Field[] fields = resources.getFields();
		for (Field field : fields) {
			if(field.getName().contains("macete"))
			{
				String nomeUrlDoMaceteKanji = field.getName();
				
				String nomeUrlMaceteSemNomeMacete = nomeUrlDoMaceteKanji.replace("macete_", "");
				nomeUrlMaceteSemNomeMacete = CorrigeNomesDosMacetesComAcento.corrigirNomesMaceteInserirAcento(nomeUrlMaceteSemNomeMacete, this);
				String labelDoKanjiNaLojinha = nomeUrlMaceteSemNomeMacete.replace("_", " - ");
				labelDoKanjiNaLojinha = labelDoKanjiNaLojinha + " - 1500";
				nomesMacetesKanjis.add(labelDoKanjiNaLojinha);
				boolean jogadorJahComprouOMacete = historicoDeComprasDaLojinha.usuarioJahComprouOMacete(labelDoKanjiNaLojinha);
				MaceteKanjiParaListviewSelecionavel maceteKanji = new 
						MaceteKanjiParaListviewSelecionavel(labelDoKanjiNaLojinha, jogadorJahComprouOMacete, nomeUrlDoMaceteKanji);
				
				arrayListMacetesKanji.add(maceteKanji);
			}
		    
		}
		
		dataAdapter = new AdapterListViewItensLojinhaMaceteKanji
				(this, R.layout.lojinha_list_item, arrayListMacetesKanji, this);
			  ListView listView = (ListView) findViewById(R.id.listaMacetes);
			  // Assign adapter to ListView
			  listView.setAdapter(dataAdapter);
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lojinha_macete_kanji, menu);
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
					R.layout.fragment_lojinha_macete_kanji, container, false);
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
