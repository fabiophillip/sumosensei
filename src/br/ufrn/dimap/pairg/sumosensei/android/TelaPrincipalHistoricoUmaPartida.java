package br.ufrn.dimap.pairg.sumosensei.android;

import java.util.LinkedList;

import com.phiworks.dapartida.GuardaDadosDaPartida;

import bancodedados.DadosPartidaParaOLog;
import bancodedados.KanjiTreinar;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import br.ufrn.dimap.pairg.sumosensei.android.R;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

public class TelaPrincipalHistoricoUmaPartida extends  ActivityDoJogoComSom implements View.OnClickListener{
	
	private DadosPartidaParaOLog dadosUmaPartida;
	private String oQueAtualmenteListViewMostra; //pode ter 3 valores: palavrastreinadas,palavrasacertadas e palavraserradas

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_principal_historico_uma_partida);
		SingletonDadosDeUmaPartidaASerMostrada sabeDadosPartidaClicada = SingletonDadosDeUmaPartidaASerMostrada.getInstance();
		DadosPartidaParaOLog dadosDeUmaPartida = sabeDadosPartidaClicada.getDadosUmaPartida();
		String nomeAdversario = dadosDeUmaPartida.getUsernameAdversario();
		String suaPontuacao =  "" + dadosDeUmaPartida.getPontuacao();
		
		String fontPath = "fonts/Wonton.ttf";
	    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	    
	    TextView textoLabelVoce = (TextView) findViewById(R.id.label_voce_historico);
	    textoLabelVoce.setTypeface(tf);
	    TextView textoTitulo = (TextView) findViewById(R.id.titulo_historico);
	    textoTitulo.setTypeface(tf);
	    
	    Button botaoPalavrasTreinadas = (Button) findViewById(R.id.botao_palavras_treinadas);
	    botaoPalavrasTreinadas.setTypeface(tf);
	   
	    
		TextView textoNomeAdversario = (TextView) findViewById(R.id.label_adversario_historico);
		textoNomeAdversario.setText(nomeAdversario);
		textoNomeAdversario.setTypeface(tf);
		TextView textoPontuacao = (TextView) findViewById(R.id.label_sua_pontuacao_historico);
		textoPontuacao.setText(suaPontuacao);
		textoPontuacao.setTypeface(tf);
		
		String dataDaPartidaJUnto = dadosDeUmaPartida.getData();
		String [] dataDaPartidaSeparado = dataDaPartidaJUnto.split(" ");
		String diaDaPartida = dataDaPartidaSeparado[0];
		String textoPraLabelDiaDaPartida = getResources().getString(R.string.em) + " " + diaDaPartida;
		String horaDaPartida = "";
		if(dataDaPartidaSeparado.length > 1)
		{
			horaDaPartida = dataDaPartidaSeparado[1];
		}
		TextView textViewdiaDaPartida = (TextView) findViewById(R.id.label_data_da_partida);
		textViewdiaDaPartida.setTypeface(tf);
		textViewdiaDaPartida.setText(textoPraLabelDiaDaPartida);
		TextView textViewHoraDaPartida = (TextView) findViewById(R.id.label_hora_da_partida);
		textViewHoraDaPartida.setTypeface(tf);
		textViewHoraDaPartida.setText(horaDaPartida);
		
		
		 String categoriasComVirgulas = dadosDeUmaPartida.getCategoria();
		 String [] categoriasSemVirgulas = categoriasComVirgulas.split(",");
		 LinkedList<String> categoriasLinkedList = new LinkedList<String>();
		 for(int k = 0; k < categoriasSemVirgulas.length; k++)
		 {
			 String umaCategoria = categoriasSemVirgulas[k];
			 categoriasLinkedList.add(umaCategoria);
		 }
		 LinkedList<Integer> idsCategoriasSelecionadas = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdsCategorias(categoriasLinkedList);
		 Integer [] indicesIconesCategoriasDoJogo = PegaIdsIconesDasCategoriasSelecionadas.pegarIndicesIconesDasCategoriasSelecionadas(idsCategoriasSelecionadas, this.getApplicationContext());
		 this.setarCategoriasTreinadasNoTeppoActivity(indicesIconesCategoriasDoJogo); 
		 
		 //por fim, setar imagens de acordo com se o jogador ganhou ou não
		 ImageView imagemSumoJogador = (ImageView) findViewById(R.id.imagem_sumo_voce);
		 ImageView imagemSumoAdversario = (ImageView) findViewById(R.id.imagem_sumo_adversario);
		 String ganhouOuPerdeu = dadosDeUmaPartida.getVoceGanhouOuPerdeu();
		 if(ganhouOuPerdeu.compareToIgnoreCase("ganhou") == 0)
		 {
			 imagemSumoJogador.setImageResource(R.drawable.ganhador1);
			 imagemSumoAdversario.setImageResource(R.drawable.perdedor2);
		 }
		 else if(ganhouOuPerdeu.compareToIgnoreCase("perdeu") == 0)
		 {
			 imagemSumoJogador.setImageResource(R.drawable.perdedor1);
			 imagemSumoAdversario.setImageResource(R.drawable.ganhador2);
		 }
		 else
		 {
			 imagemSumoJogador.setImageResource(R.drawable.ganhador1);
			 imagemSumoAdversario.setImageResource(R.drawable.ganhador2);
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
	
	public void setarCategoriasTreinadasNoTeppoActivity(Integer [] indicesImagensCategoriasTreinadas)
	{
		LinearLayout layoutBotoesCategorias = (LinearLayout) findViewById(R.id.botoesCategorias);
		for(int i = 0; i < indicesImagensCategoriasTreinadas.length; i++)
		{
			int umIndiceImagemCategoriaSelecionada = indicesImagensCategoriasTreinadas[i];
			ImageView imageViewCategoria = new ImageView(this);
			imageViewCategoria.setId(i);
			imageViewCategoria.setImageResource(umIndiceImagemCategoriaSelecionada);
			LayoutParams params = new LayoutParams(
			        LayoutParams.WRAP_CONTENT,      
			        LayoutParams.WRAP_CONTENT
			);
			if(umIndiceImagemCategoriaSelecionada > 0)
			{
				Resources r = getResources();
				int px = (int) TypedValue.applyDimension(
				        TypedValue.COMPLEX_UNIT_DIP,
				        5, 
				        r.getDisplayMetrics()
				);
				params.setMargins(px, 0, 0, 0);
			}
			imageViewCategoria.setLayoutParams(params);
			
			layoutBotoesCategorias.addView(imageViewCategoria);
			
			
		}
		
	}
	
	Dialog popupPalavrasTreinadas;
	public void mostrarPalavrasTreinadasPartidaAnterior(View v)
	{
		SingletonDadosDeUmaPartidaASerMostrada conheceDadosUmaPartida = SingletonDadosDeUmaPartidaASerMostrada.getInstance();
		this.dadosUmaPartida = conheceDadosUmaPartida.getDadosUmaPartida();
		this.popupPalavrasTreinadas = new Dialog(TelaPrincipalHistoricoUmaPartida.this);
		this.popupPalavrasTreinadas.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// Include dialog.xml file
		this.popupPalavrasTreinadas.setContentView(R.layout.popup_mostrar_dados_uma_partida);
		
		Drawable d = new ColorDrawable(Color.parseColor("#800000c0"));
		this.popupPalavrasTreinadas.getWindow().setBackgroundDrawable(d);
	
		
		this.fazerListViewMostrarPalavrasTreinadas();
		
		this.popupPalavrasTreinadas.findViewById(R.id.setaEsquerda).setOnClickListener(this);
		this.popupPalavrasTreinadas.findViewById(R.id.setaDireita).setOnClickListener(this);
		
		ImageView botaoFecharChat = (ImageView) this.popupPalavrasTreinadas.findViewById(R.id.botao_fechar_mostrar_dados_uma_partida);
		botaoFecharChat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				popupPalavrasTreinadas.dismiss();
				
			}
		});
		//e mudar o typeface
		String fontPath = "fonts/Wonton.ttf";
	    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		TextView tituloPalavrasTreinadas = (TextView) popupPalavrasTreinadas.findViewById(R.id.tituloListView);
		tituloPalavrasTreinadas.setTypeface(tf);
		
		
		
		popupPalavrasTreinadas.show();
		
		// Assign adapter to ListView
        final ListView listView = (ListView) this.popupPalavrasTreinadas.findViewById(R.id.listViewPalavrasAcertadasErradasTreinadas);
        
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
    	        
    	        ImageView setaApontaTemItem = (ImageView) popupPalavrasTreinadas.findViewById(R.id.imagem_seta_continua_listview);
    	        
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
        popupPalavrasTreinadas.findViewById(R.id.imagem_seta_continua_listview).setOnTouchListener(new OnTouchListener() {
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
		 
	}
	
	
	private void fazerListViewMostrarPalavrasTreinadas()
	{
		this.oQueAtualmenteListViewMostra = "palavrastreinadas";
		
		TextView textViewTituloListView = (TextView)this.popupPalavrasTreinadas.findViewById(R.id.tituloListView);
		String stringPalavrasTreinadas = getResources().getString(R.string.palavrasTreinadas);
		textViewTituloListView.setText(stringPalavrasTreinadas);
		
		LinkedList<KanjiTreinar> palavrasTreinadas = this.dadosUmaPartida.getPalavrasJogadas();
		int tamanhoValues = palavrasTreinadas.size();
		String[] values = new String[tamanhoValues];
        int percorredorValues = 0;
        
        for(int i = 0; i < palavrasTreinadas.size(); i++)
        {
        	KanjiTreinar umaPalavraTreinada = palavrasTreinadas.get(i);
        	String oQueApareceraComoItemNaLista = umaPalavraTreinada.getKanji() + " - " +
        					umaPalavraTreinada.getHiraganaDoKanji() + " - " +
        					umaPalavraTreinada.getTraducao();
        	values[percorredorValues] = oQueApareceraComoItemNaLista;
        	percorredorValues = percorredorValues + 1;
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
        (this,R.layout.listitem_palavras_treinadas_historico, values){
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
;


        // Assign adapter to ListView
        ListView listView = (ListView) this.popupPalavrasTreinadas.findViewById(R.id.listViewPalavrasAcertadasErradasTreinadas);
        listView.setAdapter(adapter);
	}
	
	private void fazerListViewMostrarPalavrasAcertadas()
	{
		this.oQueAtualmenteListViewMostra = "palavrasacertadas";
		
		TextView textViewTituloListView = (TextView)this.popupPalavrasTreinadas.findViewById(R.id.tituloListView);
		String stringPalavrasAcertadas = getResources().getString(R.string.palavrasAcertadas);
		textViewTituloListView.setText(stringPalavrasAcertadas);
		
		LinkedList<KanjiTreinar> palavrasAcertadas = this.dadosUmaPartida.getPalavrasAcertadas();
		int tamanhoValues = palavrasAcertadas.size();
		String[] values = new String[tamanhoValues];
        int percorredorValues = 0;
        
        for(int i = 0; i < palavrasAcertadas.size(); i++)
        {
        	KanjiTreinar umaPalavraAcertada = palavrasAcertadas.get(i);
        	String oQueApareceraComoItemNaLista = umaPalavraAcertada.getKanji() + " - " +
        					umaPalavraAcertada.getHiraganaDoKanji() + " - " +
        					umaPalavraAcertada.getTraducao();
        	values[percorredorValues] = oQueApareceraComoItemNaLista;
        	percorredorValues = percorredorValues + 1;
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
        (this,R.layout.listitem_palavras_treinadas_historico, values){
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
        ListView listView = (ListView) this.popupPalavrasTreinadas.findViewById(R.id.listViewPalavrasAcertadasErradasTreinadas);
        listView.setAdapter(adapter);


        
	}
	
	private void fazerListViewMostrarPalavrasErradas()
	{
		this.oQueAtualmenteListViewMostra = "palavraserradas";
		
		TextView textViewTituloListView = (TextView)this.popupPalavrasTreinadas.findViewById(R.id.tituloListView);
		String stringPalavrasErradas = getResources().getString(R.string.palavrasErradas);
		textViewTituloListView.setText(stringPalavrasErradas);
		
		LinkedList<KanjiTreinar> palavrasErradas = this.dadosUmaPartida.getPalavrasErradas();
		int tamanhoValues = palavrasErradas.size();
		String[] values = new String[tamanhoValues];
        int percorredorValues = 0;
        
        for(int i = 0; i < palavrasErradas.size(); i++)
        {
        	KanjiTreinar umaPalavraErrada = palavrasErradas.get(i);
        	String oQueApareceraComoItemNaLista = umaPalavraErrada.getKanji() + " - " +
        					umaPalavraErrada.getHiraganaDoKanji() + " - " +
        					umaPalavraErrada.getTraducao();
        	values[percorredorValues] = oQueApareceraComoItemNaLista;
        	percorredorValues = percorredorValues + 1;
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
        (this,R.layout.listitem_palavras_treinadas_historico, values){
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };
;


        // Assign adapter to ListView
        ListView listView = (ListView) this.popupPalavrasTreinadas.findViewById(R.id.listViewPalavrasAcertadasErradasTreinadas);
        listView.setAdapter(adapter);
        
        
	}
	
	private void usuarioClicouSetaEsquerda()
	{
		if(this.oQueAtualmenteListViewMostra.compareTo("palavrastreinadas") == 0)
		{
			this.fazerListViewMostrarPalavrasErradas();
		}
		else if(this.oQueAtualmenteListViewMostra.compareTo("palavrasacertadas") == 0)
		{
			this.fazerListViewMostrarPalavrasTreinadas();
		}
		else
		{
			this.fazerListViewMostrarPalavrasAcertadas();
		}
	}
	
	private void usuarioClicouSetaDireita()
	{
		if(this.oQueAtualmenteListViewMostra.compareTo("palavrastreinadas") == 0)
		{
			this.fazerListViewMostrarPalavrasAcertadas();
		}
		else if(this.oQueAtualmenteListViewMostra.compareTo("palavrasacertadas") == 0)
		{
			this.fazerListViewMostrarPalavrasErradas();
		}
		else
		{
			this.fazerListViewMostrarPalavrasTreinadas();
		}
	}

	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) {
	    case R.id.setaEsquerda:
	    	usuarioClicouSetaEsquerda();
	    	break;
	    case R.id.setaDireita:
	    	usuarioClicouSetaDireita();
	    	break;
		}
		
	}

	
}
