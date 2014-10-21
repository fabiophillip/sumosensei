package com.phiworks.sumosenseinew;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.domodocasual.AdapterListViewIconeETexto;
import com.phiworks.domodocasual.AssociaCategoriaComIcone;
import com.phiworks.domodocasual.BuscaSalasModoCasualComArgumentoTask;

import bancodedados.SolicitaKanjisParaTreinoTask;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.KanjiTreinar;
import bancodedados.MyCustomAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;



public class EscolhaNivelActivity extends ActivityDoJogoComSom implements ActivityQueEsperaAtePegarOsKanjis{
	  
	 //private MyCustomAdapter dataAdapter = null;
	 private ProgressDialog loadingKanjisDoBd; 
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_escolha_nivel);
	  
	  TextView tituloEscolhaCategorias = (TextView) findViewById(R.id.textoTituloEscolhaCategorias);
	  String fontpath = "fonts/Wonton.ttf";
	  Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
	  tituloEscolhaCategorias.setTypeface(tf);
	  
	  //espera pegar do BD remoto os kanjis para treinamento...
	  solicitarPorKanjisPraTreino();
	  
	  //Generate list View from ArrayList
	 
	  
	  //adicionarListenerBotao(); ANTIGO
	  
	 }

	private void solicitarPorKanjisPraTreino() {
		this.loadingKanjisDoBd = ProgressDialog.show(EscolhaNivelActivity.this, getResources().getString(R.string.carregando_kanjis_remotamente), getResources().getString(R.string.por_favor_aguarde));
		  SolicitaKanjisParaTreinoTask armazenarMinhasFotos = new SolicitaKanjisParaTreinoTask(this.loadingKanjisDoBd, this);
		  armazenarMinhasFotos.execute("");
		 
	}
	  
	 public void mostrarListaComKanjisAposCarregar() {
	  
		 /* ANTIGO
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
	   }
	  }); */
		 
		 LinkedList<String> categorias = 
				  ArmazenaKanjisPorCategoria.pegarInstancia().getCategoriasDeKanjiArmazenadas("5");
		int tamanhoLista1 = (categorias.size()/2) + 1;
		final String[] arrayCategorias = new String[tamanhoLista1];
		final String[] arrayCategorias2 = new String[categorias.size() - tamanhoLista1];
		final String[] arrayCategoriasParaListview = new String[tamanhoLista1]; // esses daqui tem quantos kanjis e a categoria em kanji tb
		final String[] arrayCategorias2ParaListView = new String[categorias.size() - tamanhoLista1];
		
		int iteradorCategorias1 = 0;
		int iteradorCategorias2 = 0;
		
		for(int i = 0; i < categorias.size(); i++)
		{
			String umaCategoria = categorias.get(i);
			if(iteradorCategorias1 < arrayCategoriasParaListview.length)
			{
				int quantasPalavrasTemACategoria = 
						ArmazenaKanjisPorCategoria.pegarInstancia().quantasPalavrasTemACategoria(umaCategoria);
				String textoDaCategoria = umaCategoria + " (" + String.valueOf(quantasPalavrasTemACategoria) + ")";
				String categoriaEscritaEmKanji = RetornaNomeCategoriaEscritaEmKanji.retornarNomeCategoriaEscritaEmKanji(umaCategoria);
				textoDaCategoria = textoDaCategoria + "\n" + categoriaEscritaEmKanji;
				arrayCategoriasParaListview[iteradorCategorias1] = textoDaCategoria;
				arrayCategorias[iteradorCategorias1] = umaCategoria;
				iteradorCategorias1 = iteradorCategorias1 + 1;
			}
			else
			{
				int quantasPalavrasTemACategoria = 
						ArmazenaKanjisPorCategoria.pegarInstancia().quantasPalavrasTemACategoria(umaCategoria);
				String textoDaCategoria = umaCategoria + " (" + String.valueOf(quantasPalavrasTemACategoria) + ")";
				String categoriaEscritaEmKanji = RetornaNomeCategoriaEscritaEmKanji.retornarNomeCategoriaEscritaEmKanji(umaCategoria);
				textoDaCategoria = textoDaCategoria + "\n" + categoriaEscritaEmKanji;
				arrayCategorias2ParaListView[iteradorCategorias2] = textoDaCategoria;
				arrayCategorias2[iteradorCategorias2] = umaCategoria;
				iteradorCategorias2 = iteradorCategorias2 + 1;
			}
		}
		Integer[] imageId = new Integer[arrayCategoriasParaListview.length];
		Integer[] imageId2 = new Integer[arrayCategorias2ParaListView.length];
		
		for(int j = 0; j < arrayCategorias.length; j++)
		{
			String umaCategoria = arrayCategorias[j];
			int idImagem = AssociaCategoriaComIcone.pegarIdImagemDaCategoriaTeppo(getApplicationContext(),umaCategoria);
			imageId[j] = idImagem;
		}
		for(int k = 0; k < arrayCategorias2.length; k++)
		{
			String umaCategoria = arrayCategorias2[k];
			int idImagem = AssociaCategoriaComIcone.pegarIdImagemDaCategoriaTeppo(getApplicationContext(),umaCategoria);
			imageId2[k] = idImagem;
		}

		
	    // arraylist to keep the selected items
	   
		
		
	    
	    final boolean[] categoriaEstahSelecionada = new boolean[arrayCategoriasParaListview.length];
	    final boolean[] categoriaEstahSelecionada2 = new boolean[arrayCategorias2ParaListView.length];
		for(int l = 0; l < arrayCategoriasParaListview.length; l++)
		{
			categoriaEstahSelecionada[l] = false;
		}
		for(int m = 0; m < arrayCategorias2ParaListView.length; m++)
		{
			categoriaEstahSelecionada2[m] = false;
		}
		Typeface typeFaceFonteTextoListViewIconeETexto = this.escolherFonteDoTextoListViewIconeETexto();
		AdapterListViewIconeETexto adapter = new AdapterListViewIconeETexto(EscolhaNivelActivity.this, arrayCategoriasParaListview, imageId,typeFaceFonteTextoListViewIconeETexto,true);
	    adapter.setLayoutUsadoParaTextoEImagem(R.layout.list_item_icone_e_texto_menor);
		ListView list=(ListView)this.findViewById(R.id.listaCategoriasPesquisaSalas1);
	    
	        list.setAdapter(adapter);
	        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	                @Override
	                public void onItemClick(AdapterView<?> parent, View view,
	                                        int position, long id) 
	                {
	                    if(categoriaEstahSelecionada[position] == false)
	                    {
	                    	categoriaEstahSelecionada[position] = true;
	                    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
	                    	imageView.setAlpha(255);
	                    }
	                    else
	                    {
	                    	categoriaEstahSelecionada[position] = false;
	                    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
	                    	TextView texto = (TextView) view.findViewById(R.id.txt);
	                    	texto.setAlpha(70);
	                    	imageView.setAlpha(70);
	                    	
	                    }
	                }
	            });
	        
	        
	        AdapterListViewIconeETexto adapter2 = new AdapterListViewIconeETexto(EscolhaNivelActivity.this, arrayCategorias2ParaListView, imageId2,typeFaceFonteTextoListViewIconeETexto,true);
	        adapter2.setLayoutUsadoParaTextoEImagem(R.layout.list_item_icone_e_texto_menor);
	        ListView list2=(ListView)this.findViewById(R.id.listaCategoriasPesquisaSalas2);
		        list2.setAdapter(adapter2);
		        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		                @Override
		                public void onItemClick(AdapterView<?> parent, View view,
		                                        int position, long id) 
		                {
		                    if(categoriaEstahSelecionada2[position] == false)
		                    {
		                    	categoriaEstahSelecionada2[position] = true;
		                    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
		                    	imageView.setAlpha(255);
		                    }
		                    else
		                    {
		                    	categoriaEstahSelecionada2[position] = false;
		                    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
		                    	TextView texto = (TextView) view.findViewById(R.id.txt);
		                    	texto.setAlpha(70);
		                    	imageView.setAlpha(70);
		                    }
		                }
		            });

		        //this.popupPesquisarSalaPorCategoria = builder.create();//AlertDialog dialog; create like this outside onClick
		      //falta definir a ação para o botão ok desse popup das categorias
			  	  Button botaoOk = (Button) this.findViewById(R.id.confirmar_escolha_categorias);
			  	  botaoOk.setOnClickListener(new Button.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						 LinkedList<String> categoriasDeKanjiSelecionadas = pegarCategoriasSelecionadasDuasListas(arrayCategorias, arrayCategorias2, categoriaEstahSelecionada, categoriaEstahSelecionada2);
						    //o que fazer depois de que o usuario terminou de selecionar categorias?
							  if(categoriasDeKanjiSelecionadas.size() > 0)
							  {
								  GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
								  guardaDadosDaPartida.limparDadosPartida();//limpar dados da partida anterior
								  guardaDadosDaPartida.setCategoriasSelecionadasPraPartida(categoriasDeKanjiSelecionadas);
								  mudarMusicaDeFundo(R.raw.headstart);
								  Intent iniciaTelaTeppo = new Intent(EscolhaNivelActivity.this, TreinoTeppo.class);
								  startActivity(iniciaTelaTeppo);
								  finish();
								  
							  }
							  else
							  {
								  Toast.makeText(getApplicationContext(), getResources().getString(R.string.aviso_nao_selecionou_categorias), Toast.LENGTH_SHORT).show();
							  }
						
					}
			  		  
			  	  });
		      
		 
	  
	 }
	 
	 private Typeface escolherFonteDoTextoListViewIconeETexto()
	 {
	 	String fontPath = "fonts/Wonton.ttf";
	     Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	     return tf;
	 }
	 
	 private LinkedList<String> pegarCategoriasSelecionadasDuasListas(final String[] arrayCategorias, 
				final String[] arrayCategorias2, final boolean[] categoriaEstahSelecionada,
				final boolean[] categoriaEstahSelecionada2) 
		{
			LinkedList<String> categoriasEscolhidas = new LinkedList<String>();
			for(int n = 0; n < categoriaEstahSelecionada.length; n++)
			{
				if(categoriaEstahSelecionada[n] == true)
				{
					//o usuario quer procurar com essa categoria
					String umaCategoria = arrayCategorias[n];
							
					categoriasEscolhidas.add(umaCategoria);
							
				}
			}
			for(int o = 0; o < categoriaEstahSelecionada2.length; o++)
			{
				if(categoriaEstahSelecionada2[o] == true)
				{
					//o usuario quer procurar com essa categoria
					String umaCategoria = arrayCategorias2[o];
							
					categoriasEscolhidas.add(umaCategoria);
							
				}
			}
					
					
			return categoriasEscolhidas;  
					
			//A STRING SCIMA ESTAH NORMAL COM AS CATEGORIAS. POR ALGUM MOTIVO O LISTVIEW NAO ESTAH SENDO ATUALIZADO COM O RESULTADO DA BUSCA
		}

	  
	 
	  
	 /* ANTIGO
	  * private void adicionarListenerBotao() {
	  
	  
	  Button myButton = (Button) findViewById(R.id.confirmar_escolha_categorias);
	  myButton.setOnClickListener(new OnClickListener() {
	  
		  @Override
		   public void onClick(View v){
			  LinkedList<String> categoriasDeKanjiSelecionadas = pegarCategoriasDeKanjiSelecionadas();
		    //o que fazer depois de que o usuario terminou de selecionar categorias?
			  if(categoriasDeKanjiSelecionadas.size() > 0)
			  {
				  GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
				  guardaDadosDaPartida.limparDadosPartida();//limpar dados da partida anterior
				  guardaDadosDaPartida.setCategoriasSelecionadasPraPartida(categoriasDeKanjiSelecionadas);
				  mudarMusicaDeFundo(R.raw.headstart);
				  Intent iniciaTelaTeppo = new Intent(EscolhaNivelActivity.this, TreinoTeppo.class);
				  startActivity(iniciaTelaTeppo);
				  finish();
				  
			  }
			  else
			  {
				  Toast.makeText(getApplicationContext(), getResources().getString(R.string.aviso_nao_selecionou_categorias), Toast.LENGTH_SHORT).show();
			  }
		    
		   }
	  });
	  
	 }*/

	
	
	/* ANTIGO
	 private LinkedList<String> pegarCategoriasDeKanjiSelecionadas()
	{	
		ArrayList<CategoriaDeKanjiParaListviewSelecionavel> categoriaDeKanjiList = dataAdapter.getCategoriaDeKanjiList();
 	    LinkedList<String> categoriasDeKanjiSelecionadas = new LinkedList<String>();
 	    for(int j = 0; j < categoriaDeKanjiList.size(); j++)
 	    {
 	    	CategoriaDeKanjiParaListviewSelecionavel categoriaDekanji = categoriaDeKanjiList.get(j);
 	    	if(categoriaDekanji.isSelected()){
 	    		String categoriaDeKanjiSemParenteses = categoriaDekanji.getName().split("\\(")[0]; 
 	    		categoriasDeKanjiSelecionadas.add(categoriaDeKanjiSemParenteses);
 	    	}
 	    }
 	   return categoriasDeKanjiSelecionadas;
	} */

	@Override
	public void mandarMensagemMultiplayer(String mensagem) {
		// TODO Auto-generated method stub
		
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
