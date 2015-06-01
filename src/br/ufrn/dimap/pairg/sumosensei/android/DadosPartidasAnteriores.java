package br.ufrn.dimap.pairg.sumosensei.android;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;

import docompeticao.AdapterListViewHistorico;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;

import br.ufrn.dimap.pairg.sumosensei.android.R;


import bancodedados.DadosPartidaParaOLog;
import bancodedados.PegarDadosUltimasPartidasTask;
import bancodedados.SolicitaKanjisParaTreinoTask;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class DadosPartidasAnteriores extends ActivityDoJogoComSom implements ActivityQueEsperaAtePegarOsKanjis
{
	LinkedList<DadosPartidaParaOLog> partidasAnteriores;
	private ProgressDialog loadingKanjisDoBd;
	private ListView listView; //listview com as ultimas partidas
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dados_partidas_anteriores);
		
		String fontPath = "fonts/Wonton.ttf";
	    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	    TextView titulo = (TextView) findViewById(R.id.titulo_historico);
	    titulo.setTypeface(tf);
	    TextView tituloPartidasAnteriores = (TextView) findViewById(R.id.textView4);
	    tituloPartidasAnteriores.setTypeface(tf);
		
		//precisarei pegar a lista de kanjis mais porque preciso saber as traducoes dos kanjis na hora de mostrar detalhes de uma partida
		this.loadingKanjisDoBd = ProgressDialog.show(DadosPartidasAnteriores.this, getResources().getString(R.string.carregando_dados_das_partidas), getResources().getString(R.string.por_favor_aguarde));
		SolicitaKanjisParaTreinoTask pegarKanjisTreino = new SolicitaKanjisParaTreinoTask(this.loadingKanjisDoBd, this, this);
		pegarKanjisTreino.execute("");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dados_partidas_anteriores, menu);
		return true;
	}
	

	@Override
	public void mostrarListaComKanjisAposCarregar() 
	{
		PegarDadosUltimasPartidasTask taskPegaUltimasPartidas = new PegarDadosUltimasPartidasTask(this);
		
		SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(getApplicationContext());
		
		taskPegaUltimasPartidas.execute(nomeJogadorArmazenado);
		
	}
	
	public void atualizarListViewComAsUltimasPartidas(LinkedList<DadosPartidaParaOLog> dadosPartidasAnteriores)
	{
		this.partidasAnteriores = dadosPartidasAnteriores;
		this.enfileirarPartidasAnteriores(); //vou enfileirar do mais recente para o menos
		
		try
		{
			// Defined Array values to show in ListView
			int tamanhoValues = 0;
			
			if(this.partidasAnteriores.size() > 5)
			{
				tamanhoValues = 5;
			}
			else
			{
				tamanhoValues = this.partidasAnteriores.size();
			}
			
			
			String[] values = new String[tamanhoValues];
	        int percorredorValues = 0;
			
	        for(int i = 0; i < tamanhoValues; i++)
	        {
	        	String labelAdversario = getResources().getString(R.string.label_adversario);
	        	String labelCategorias = getResources().getString(R.string.categorias);
	        	String labelDataPartida = getResources().getString(R.string.data_da_partida);
	        	
	        	String categoriasComVirgulas = this.partidasAnteriores.get(i).getCategoria().replaceAll(";", ",");
	        	String ultimoCaractereDasCategorias = String.valueOf(categoriasComVirgulas.charAt(categoriasComVirgulas.length() - 1));
	        	if(ultimoCaractereDasCategorias.compareTo(",") == 0)
	        	{
	        		categoriasComVirgulas = categoriasComVirgulas.substring(0,categoriasComVirgulas.length() - 1); //vamos tirar o ultimo caractere da string
	        	}
	        	
	        	String oQueApareceraComoItemNaLista =
	        			labelDataPartida + this.partidasAnteriores.get(i).getData() + "\n" +
	        					labelAdversario + this.partidasAnteriores.get(i).getUsernameAdversario() + "\n" +
	        					labelCategorias + categoriasComVirgulas;
	        	values[percorredorValues] = oQueApareceraComoItemNaLista;
	        	
	        	percorredorValues = percorredorValues + 1;
	        }
	        

	        // Define a new Adapter
	        // First parameter - Context
	        // Second parameter - Layout for the row
	        // Third parameter - ID of the TextView to which the data is written
	        // Forth - the Array of data
	        	
	        
	        AdapterListViewHistorico adapter = new AdapterListViewHistorico(getApplicationContext(), R.layout.item_partida_do_historico, dadosPartidasAnteriores, this);


	        // Assign adapter to ListView
	        this.listView = (ListView) findViewById(R.id.listaDasUltimasPartidas);
	        listView.setAdapter(adapter);  
	        listView.setOnScrollListener(new OnScrollListener() {
	    		
	    		@Override
	    		public void onScrollStateChanged(AbsListView view, int scrollState) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	    		
	    		@Override
	    		public void onScroll(AbsListView view, int firstVisibleItem,
	    				int visibleItemCount, int totalItemCount) {
	    			boolean usuarioEstahNoComecoDaLista = false;
	    			if(firstVisibleItem == 0)
	    			{
	    				usuarioEstahNoComecoDaLista = true;
	    			}
	    			boolean usuarioEstahNoFimDaLista = false;
	    			final int lastItem = firstVisibleItem + visibleItemCount;
	    	        if(lastItem == totalItemCount) {
	    	        	usuarioEstahNoFimDaLista = true;	
	    	        }
	    	        
	    	        ImageView setaApontaTemItem = (ImageView) findViewById(R.id.imagem_seta_continua_listview);
	    	        
	    	        if(usuarioEstahNoComecoDaLista == true && usuarioEstahNoFimDaLista == false)
	    	        {
	    	        	//setaApontaTemItem.setImageAlpha(1);
	    	        	setaApontaTemItem.setImageResource(R.drawable.seta_listview_baixo);
	    	        	
	    	        }
	    	        else if(usuarioEstahNoComecoDaLista == false && usuarioEstahNoFimDaLista == false)
	    	        {
	    	        	//setaApontaTemItem.setImageAlpha(1);
	    	        	setaApontaTemItem.setImageResource(R.drawable.seta_listview_cimabaixo);
	    	        }
	    	        else if(usuarioEstahNoComecoDaLista == false && usuarioEstahNoFimDaLista == true)
	    	        {
	    	        	//setaApontaTemItem.setImageAlpha(1);
	    	        	setaApontaTemItem.setImageResource(R.drawable.seta_listview_cima);
	    	        }
	    	        else
	    	        {
	    	        	setaApontaTemItem.setImageResource(R.drawable.seta_listview_invisivel);
	    	        	//setaApontaTemItem.setImageAlpha(0);
	    	        	//Toast.makeText(getApplicationContext(), "seta nom precisa aparecer", Toast.LENGTH_SHORT).show();
	    	        }
	    			
	    		}
	    	});
	      //PARTE REFERENTE AO SCROLL DA LISTA POR TOQUE
	        ImageView setaApontaTemItem = (ImageView) findViewById(R.id.imagem_seta_continua_listview);
	        setaApontaTemItem.setOnTouchListener(new OnTouchListener() {
				boolean setaDescendo = true;//pro scroll da lista no toque
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					//Toast.makeText(getApplicationContext(), "clicou na seta de menu" + listaKanjisMemorizar.isInTouchMode(), Toast.LENGTH_SHORT).show();
					boolean usuarioEstahNoComecoDaLista = false;
					int firstVisibleItem = listView.getFirstVisiblePosition();
					int visibleItemCount = 0;
					int totalItemCount = listView.getAdapter().getCount();
					for (int i = 0; i <= listView.getLastVisiblePosition(); i++)
					{
					    if (listView.getChildAt(i) != null)
					    {
					        visibleItemCount++;  // saying that view that counts is the one that is not null, 
					                  // because sometimes you have partially visible items....
					    }
					}
					if(firstVisibleItem == 0)
					{
						usuarioEstahNoComecoDaLista = true;
					}
					boolean usuarioEstahNoFimDaLista = false;
					final int lastItem = firstVisibleItem + visibleItemCount;
			        if(lastItem == totalItemCount) {
			        	usuarioEstahNoFimDaLista = true;	
			        }
			        
			        if(usuarioEstahNoComecoDaLista == true && usuarioEstahNoFimDaLista == false)
			        {
			        	//setaApontaTemItem.setImageAlpha(1);
			        	listView.smoothScrollBy(20, 20); // For increment. 
			        	setaDescendo = true;
			        	
			        }
			        else if(usuarioEstahNoComecoDaLista == false && usuarioEstahNoFimDaLista == false)
			        {
			        	//setaApontaTemItem.setImageAlpha(1);
			        	if(setaDescendo == true)
			        	{
			        		listView.smoothScrollBy(20, 20); // For increment. 
			        	}
			        	else
			        	{
			        		listView.smoothScrollBy(-20, 20); // For increment.
			        	}
			        	
			        }
			        else if(usuarioEstahNoComecoDaLista == false && usuarioEstahNoFimDaLista == true)
			        {
			        	//setaApontaTemItem.setImageAlpha(1);
			        	listView.smoothScrollBy(-20, 20); // For increment.
			        	setaDescendo = false;
			        }
			        else
			        {
			        	if(setaDescendo == true)
			        	{
			        		listView.smoothScrollBy(20, 20); // For increment. 
			        	}
			        	else
			        	{
			        		listView.smoothScrollBy(-20, 20); // For increment.
			        	}
			        }
			        
	                return true;
				}
			});
	        
			
			listView.setOnItemClickListener(new OnItemClickListener() 
	        {

	              @Override
	              public void onItemClick(AdapterView<?> parent, View view,
	                 int position, long id) {
	                
	               // ListView Clicked item index
	               int itemPosition     = position;
	               
	               SingletonDadosDeUmaPartidaASerMostrada armazenaDadosUmaPartidaASerMostrada =
	            		   						SingletonDadosDeUmaPartidaASerMostrada.getInstance();
	               armazenaDadosUmaPartidaASerMostrada.setDadosUmaPartida(partidasAnteriores.get(itemPosition));
	               
	               Intent criaMostrarDadosUmaPartida =
	   					new Intent(DadosPartidasAnteriores.this, TelaPrincipalHistoricoUmaPartida.class);
	   				startActivity(criaMostrarDadosUmaPartida);
	               
	              }
	        }); 
			
			this.atualizarVitoriasModoMultiplayer();
			this.atualizarDerrotasModoMultiplayer();
			        
		}
		catch(Exception e)
	    {
	    	Writer writer = new StringWriter();
	    	PrintWriter printWriter = new PrintWriter(writer);
	    	e.printStackTrace(printWriter);
	    	String s = writer.toString();
	    	Context context = getApplicationContext();
	        Toast t = Toast.makeText(context, s, Toast.LENGTH_LONG);
	        t.show();
	    }
		
		
	}
	
	/*vou enfileirar as ultimas partidas*/
	private void enfileirarPartidasAnteriores()
	{
		if(this.partidasAnteriores.size() > 1)
		{
			LinkedList<DadosPartidaParaOLog> partidasAnterioresEnfileiradas = new LinkedList<DadosPartidaParaOLog>();
			LinkedList<DadosPartidaParaOLog> copiaPartidasAnteriores = new LinkedList<DadosPartidaParaOLog>();
			
			for(int i = 0; i < this.partidasAnteriores.size(); i++)
			{
				DadosPartidaParaOLog umDadoPartida = this.partidasAnteriores.get(i);
				copiaPartidasAnteriores.add(umDadoPartida);
			}
			
			while(copiaPartidasAnteriores.size() > 0)
			{
				DadosPartidaParaOLog dadoMaisRecente = copiaPartidasAnteriores.getFirst();
				
				for(int j = 1; j < copiaPartidasAnteriores.size(); j++)
				{
					DadosPartidaParaOLog umDado = copiaPartidasAnteriores.get(j);
					try 
					{
						Date dataUmDado = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH).parse(umDado.getData());
						Date dataDadoMaisRecente = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH).parse(dadoMaisRecente.getData());
						if(dataDadoMaisRecente.before(dataUmDado) == true)
						{
							//achamos uma data mais recente ainda
							dadoMaisRecente = umDado;
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				partidasAnterioresEnfileiradas.add(dadoMaisRecente);
				copiaPartidasAnteriores.remove(dadoMaisRecente);
			}
			
			//agora que ja temos a lista enfileirada...
			this.partidasAnteriores.clear();
			
			for(int k = 0; k < partidasAnterioresEnfileiradas.size(); k++)
			{
				this.partidasAnteriores.add(partidasAnterioresEnfileiradas.get(k));
			}
		}
	}
	
	private void atualizarVitoriasModoMultiplayer()
	{
		int quantasVitorias = 0;
		
		for(int i = 0; i < this.partidasAnteriores.size(); i++)
		{
			DadosPartidaParaOLog dadosUmaPartida = this.partidasAnteriores.get(i);
			String ganhou = "ganhou";
			String ganhouIngles = "won";
			String ganhouEspanhol = "ganó";
			if(dadosUmaPartida.getVoceGanhouOuPerdeu().compareTo(ganhou) == 0 || dadosUmaPartida.getVoceGanhouOuPerdeu().compareTo(ganhouIngles) == 0 
					|| dadosUmaPartida.getVoceGanhouOuPerdeu().compareTo(ganhouEspanhol) == 0)
			{
				quantasVitorias = quantasVitorias + 1;
			}
		}
		
		TextView textViewVitorias = (TextView) findViewById(R.id.vitoriasmodomultiplayer);
		textViewVitorias.setText(String.valueOf(quantasVitorias));
	}
	
	private void atualizarDerrotasModoMultiplayer()
	{
		int quantasDerrotas = 0;
		
		for(int i = 0; i < this.partidasAnteriores.size(); i++)
		{
			DadosPartidaParaOLog dadosUmaPartida = this.partidasAnteriores.get(i);
			String perdeu = "perdeu";
			String perdeuIngles = "lost";
			String perdeuEspanhol = "perdió";
			if(dadosUmaPartida.getVoceGanhouOuPerdeu().compareTo(perdeu) == 0 || dadosUmaPartida.getVoceGanhouOuPerdeu().compareTo(perdeuIngles) == 0
					|| dadosUmaPartida.getVoceGanhouOuPerdeu().compareTo(perdeuEspanhol) == 0)
			{
				quantasDerrotas = quantasDerrotas + 1;
			}
		}
		
		TextView textViewDerrotas = (TextView) findViewById(R.id.derrotasmodomultiplayer);
		textViewDerrotas.setText(String.valueOf(quantasDerrotas));
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mandarMensagemMultiplayer(String mensagem) {
		// TODO Auto-generated method stub
		
	}

	
	

}
