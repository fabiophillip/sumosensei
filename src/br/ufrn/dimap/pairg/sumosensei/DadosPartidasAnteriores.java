package br.ufrn.dimap.pairg.sumosensei;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import br.ufrn.dimap.pairg.sumosensei.app.R;


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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
		
		//precisarei pegar a lista de kanjis mais porque preciso saber as traducoes dos kanjis na hora de mostrar detalhes de uma partida
		this.loadingKanjisDoBd = ProgressDialog.show(DadosPartidasAnteriores.this, getResources().getString(R.string.carregando_dados_das_partidas), getResources().getString(R.string.por_favor_aguarde));
		SolicitaKanjisParaTreinoTask pegarKanjisTreino = new SolicitaKanjisParaTreinoTask(this.loadingKanjisDoBd, this);
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
		
		AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
		Account[] list = manager.getAccounts();
		String emailJogador = "";
		for(Account account: list)
		{
		    if(account.type.equalsIgnoreCase("com.google"))
		    {
		        emailJogador = account.name;
		        break;
		    }
		}
		
		taskPegaUltimasPartidas.execute(emailJogador);
		
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
	        					labelAdversario + this.partidasAnteriores.get(i).geteMailAdversario() + "\n" +
	        					labelCategorias + categoriasComVirgulas;
	        	values[percorredorValues] = oQueApareceraComoItemNaLista;
	        	
	        	percorredorValues = percorredorValues + 1;
	        }
	        

	        // Define a new Adapter
	        // First parameter - Context
	        // Second parameter - Layout for the row
	        // Third parameter - ID of the TextView to which the data is written
	        // Forth - the Array of data
	        	
	        
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>
	        (this,android.R.layout.simple_list_item_1, android.R.id.text1, values);


	        // Assign adapter to ListView
	        this.listView = (ListView) findViewById(R.id.listaDasUltimasPartidas);
	        listView.setAdapter(adapter);  
	        
			
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
	   					new Intent(DadosPartidasAnteriores.this, MostrarDadosUmaPartida.class);
	   				startActivity(criaMostrarDadosUmaPartida);
	               
	               // ListView Clicked item value
	               String  itemValue    = (String) listView.getItemAtPosition(position);
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
						Date dataUmDado = new SimpleDateFormat("dd-MMM-yyyy").parse(umDado.getData());
						Date dataDadoMaisRecente = new SimpleDateFormat("dd-MMM-yyyy").parse(dadoMaisRecente.getData());
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
			String ganhou = getResources().getString(R.string.ganhou);
			if(dadosUmaPartida.getVoceGanhouOuPerdeu().compareTo(ganhou) == 0)
			{
				quantasVitorias = quantasVitorias + 1;
			}
		}
		
		TextView textViewVitorias = (TextView) findViewById(R.id.vitoriasmodomultiplayer);
		String labelVitorias = getResources().getString(R.string.vitorias_modo_multiplayer);
		textViewVitorias.setText(labelVitorias + String.valueOf(quantasVitorias));
	}
	
	private void atualizarDerrotasModoMultiplayer()
	{
		int quantasDerrotas = 0;
		
		for(int i = 0; i < this.partidasAnteriores.size(); i++)
		{
			DadosPartidaParaOLog dadosUmaPartida = this.partidasAnteriores.get(i);
			String perdeu = getResources().getString(R.string.perdeu);
			if(dadosUmaPartida.getVoceGanhouOuPerdeu().compareTo(perdeu) == 0)
			{
				quantasDerrotas = quantasDerrotas + 1;
			}
		}
		
		TextView textViewDerrotas = (TextView) findViewById(R.id.derrotasmodomultiplayer);
		String labelDerrotas = getResources().getString(R.string.derrotas_modo_multiplayer);
		textViewDerrotas.setText(labelDerrotas + String.valueOf(quantasDerrotas));
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
