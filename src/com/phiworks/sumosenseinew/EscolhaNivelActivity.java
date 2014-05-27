package com.phiworks.sumosenseinew;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import bancodedados.SolicitaKanjisParaTreinoTask;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.KanjiTreinar;
import bancodedados.MyCustomAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;



public class EscolhaNivelActivity extends Activity implements ActivityQueEsperaAtePegarOsKanjis{
	  
	 private MyCustomAdapter dataAdapter = null;
	 private ProgressDialog loadingKanjisDoBd; 
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_escolha_nivel);
	  
	  //espera pegar do BD remoto os kanjis para treinamento...
	  solicitarPorKanjisPraTreino();
	  
	  //Generate list View from ArrayList
	 
	  
	  adicionarListenerBotao();
	  
	 }

	private void solicitarPorKanjisPraTreino() {
		this.loadingKanjisDoBd = ProgressDialog.show(EscolhaNivelActivity.this, getResources().getString(R.string.carregando_kanjis_remotamente), getResources().getString(R.string.por_favor_aguarde));
		  SolicitaKanjisParaTreinoTask armazenarMinhasFotos = new SolicitaKanjisParaTreinoTask(this.loadingKanjisDoBd, this);
		  armazenarMinhasFotos.execute("");
		 
	}
	  
	 public void mostrarListaComKanjisAposCarregar() {
	  
	  //Array list of countries
	  ArrayList<CategoriaDeKanjiParaListviewSelecionavel> listaDeCategorias = new ArrayList<CategoriaDeKanjiParaListviewSelecionavel>();
	  
	  LinkedList<String> categoriasDosKanjis = 
			  ArmazenaKanjisPorCategoria.pegarInstancia().getCategoriasDeKanjiArmazenadas("5");
	  
	  for(int i = 0; i < categoriasDosKanjis.size(); i++)
	  {
		  String categoriaDeKanji = categoriasDosKanjis.get(i);
		  LinkedList<KanjiTreinar> kanjisDaCategoria = ArmazenaKanjisPorCategoria.pegarInstancia().getListaKanjisTreinar(categoriaDeKanji);
		  String labelCategoriaDeKanji = categoriaDeKanji + "(" + kanjisDaCategoria.size() + getResources().getString(R.string.contagem_kanjis) + ")";
		  CategoriaDeKanjiParaListviewSelecionavel novaCategoria = new CategoriaDeKanjiParaListviewSelecionavel(labelCategoriaDeKanji, false);
		  listaDeCategorias.add(novaCategoria);
	  }
	 
	  
	  //create an ArrayAdaptar from the String Array
	  dataAdapter = new MyCustomAdapter(this,
	    R.layout.categoria_de_kanji_na_lista, listaDeCategorias, true, this);
	  ListView listView = (ListView) findViewById(R.id.listaCategorias);
	  // Assign adapter to ListView
	  listView.setAdapter(dataAdapter);
	  
	  
	  listView.setOnItemClickListener(new OnItemClickListener() {
	   public void onItemClick(AdapterView parent, View view,
	     int position, long id) {
	    // When clicked, show a toast with the TextView text
	    CategoriaDeKanjiParaListviewSelecionavel categoriaDeKanji = (CategoriaDeKanjiParaListviewSelecionavel) parent.getItemAtPosition(position);
	    Toast.makeText(getApplicationContext(),
	      "Clicked on Row: " + categoriaDeKanji.getName(),
	      Toast.LENGTH_LONG).show();
	   }
	  });
	  
	 }
	  
	 
	  
	 private void adicionarListenerBotao() {
	  
	  
	  Button myButton = (Button) findViewById(R.id.ok_button);
	  myButton.setOnClickListener(new OnClickListener() {
	  
	   @Override
	   public void onClick(View v) {
	   StringBuffer responseText = new StringBuffer();
	    responseText.append("The following were selected...\n");
	  
	    ArrayList<CategoriaDeKanjiParaListviewSelecionavel> categoriaDeKanjiList = dataAdapter.getCategoriaDeKanjiList();
	    for(int j = 0; j < categoriaDeKanjiList.size(); j++)
	    {
	    	CategoriaDeKanjiParaListviewSelecionavel categoriaDekanji = categoriaDeKanjiList.get(j);
	    	if(categoriaDekanji.isSelected()){
	    		responseText.append("\n" + categoriaDekanji.getName());
	    	}
	    }
	  
	    Toast.makeText(getApplicationContext(),
	      responseText, Toast.LENGTH_LONG).show();
	  
	   }
	  });
	  
	 }

	@Override
	public void mandarMensagemMultiplayer(String mensagem) {
		// TODO Auto-generated method stub
		
	}

	
	  
	}
