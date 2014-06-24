package com.phiworks.sumosenseinew;


import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.internal.gu;
import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.dapartida.TerminaPartidaTask;

import bancodedados.KanjiTreinar;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import cenario.ImageAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class TreinoTeppo extends ActivityDoJogoComSom implements View.OnClickListener {
	
	private static int GAME_DURATION = 90;
	private int mSecondsLeft;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_treino_teppo);
		
		View botaoResposta1 = findViewById (R.id.answer1);
		View botaoResposta2 = findViewById(R.id.answer2);
		View botaoResposta3 = findViewById(R.id.answer3);
		View botaoResposta4 = findViewById(R.id.answer4);
		botaoResposta1.setOnClickListener(this);
		botaoResposta2.setOnClickListener(this);
		botaoResposta3.setOnClickListener(this);
		botaoResposta4.setOnClickListener(this);
		
		
		prepararTelaInicialJogo();
		escolherUmNovoKanjiParaTreinar();	
	}
	
	private void escolherUmNovoKanjiParaTreinar()
	{
		GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		KanjiTreinar umKanjiAleatorioParaTreinar = guardaDadosDaPartida.getUmKanjiAleatorioParaTreinar();
        guardaDadosDaPartida.adicionarKanjiAoTreinoDaPartida(umKanjiAleatorioParaTreinar);
        
        LinkedList<String> hiraganasAlternativas = new LinkedList<String>();
        hiraganasAlternativas.add(umKanjiAleatorioParaTreinar.getHiraganaDoKanji());
        LinkedList<String> possiveisCiladasHiragana = umKanjiAleatorioParaTreinar.getPossiveisCiladasKanji();
        for(int j = 0; j < possiveisCiladasHiragana.size(); j++)
        {
        	String umaPossivelCilada = possiveisCiladasHiragana.get(j);
        	hiraganasAlternativas.add(umaPossivelCilada);
        }
        LinkedList<String> outrasCiladasMesmaCategoria = guardaDadosDaPartida.getHiraganasAleatoriosSimilares(umKanjiAleatorioParaTreinar);
        for(int k = 0; k < outrasCiladasMesmaCategoria.size(); k++)
        {
        	String possivelCiladaMesmaCategoria = outrasCiladasMesmaCategoria.get(k);
        	hiraganasAlternativas.add(possivelCiladaMesmaCategoria);
        }
        //vamos embaralhar e associar aos botoes
        Collections.shuffle(hiraganasAlternativas);
        Collections.shuffle(hiraganasAlternativas);
        
        iniciarUmaPartida(umKanjiAleatorioParaTreinar, hiraganasAlternativas);
	}
	
	private void iniciarUmaPartida(KanjiTreinar kanjiParaTreinarNaPartida, LinkedList<String> hiraganasAlternativas)
	{
		Button botaoAlternativa1 = (Button) findViewById(R.id.answer1);
	    botaoAlternativa1.setText(hiraganasAlternativas.get(0));
	    Button botaoAlternativa2 = (Button) findViewById(R.id.answer2);
	    botaoAlternativa2.setText(hiraganasAlternativas.get(1));
	    Button botaoAlternativa3 = (Button) findViewById(R.id.answer3);
	    botaoAlternativa3.setText(hiraganasAlternativas.get(2));
	    Button botaoAlternativa4 = (Button) findViewById(R.id.answer4);
	    botaoAlternativa4.setText(hiraganasAlternativas.get(3));
	     
	    GuardaDadosDaPartida guardaDadosDeUmaPartida = GuardaDadosDaPartida.getInstance();
	    guardaDadosDeUmaPartida.adicionarKanjiAoTreinoDaPartida(kanjiParaTreinarNaPartida);
	    TextView textViewKanjiAcertar = (TextView) findViewById(R.id.kanji_acertar);
	    String textoKanjiTreinar = kanjiParaTreinarNaPartida.getKanji();
	    textViewKanjiAcertar.setText(textoKanjiTreinar);
	    guardaDadosDeUmaPartida.incrementarRoundDaPartida();
	 }


	
	private void prepararTelaInicialJogo() 
	{	
		final AnimationDrawable animacaoTeppo = new AnimationDrawable();
		animacaoTeppo.addFrame(getResources().getDrawable(R.drawable.sumoarenasingle0), 200);
		animacaoTeppo.addFrame(getResources().getDrawable(R.drawable.sumoarenasingle0_alt), 200);
		animacaoTeppo.setOneShot(false);
		ImageView viewSumosNaArena = (ImageView)findViewById(R.id.ringue_luta);
		viewSumosNaArena.setImageDrawable(animacaoTeppo);
		viewSumosNaArena.post(new Runnable() {
			@Override
			public void run() {
				animacaoTeppo.start();
			}
		});
		
		LinkedList<String> categoriasSelecionadas = GuardaDadosDaPartida.getInstance().getCategoriasTreinadasNaPartida();
		
		Integer [] indicesIconesCategoriasDoJogo = PegaIdsIconesDasCategoriasSelecionadas.pegarIndicesIconesDasCategoriasSelecionadas(categoriasSelecionadas);
	 	Gallery gallery = (Gallery) findViewById(R.id.listagem_categorias);
	    gallery.setAdapter(new ImageAdapter(indicesIconesCategoriasDoJogo, this));
	    
	    this.mSecondsLeft = GAME_DURATION;
	    
	  //botar um cara para fazer update do tempo restante do jogo.
	    final Handler handlerTiqueRelogio = new Handler();
	    //tiqueRelogio = new TiqueRelogio();
	    handlerTiqueRelogio.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				 if (mSecondsLeft <= 0)
	                 return;
	             gameTick();
	             handlerTiqueRelogio.postDelayed(this, 1000);
				
			}
		}, 1000);
	}
	
	
	
	
	

	private void gameTick() {
		if (mSecondsLeft > 0)
			--mSecondsLeft;

		// update countdown
		((TextView) findViewById(R.id.countdown)).setText("0:" +
            (mSecondsLeft < 10 ? "0" : "") + String.valueOf(mSecondsLeft));

		if (mSecondsLeft <= 0) {
			//terminar jogo
			Intent intentTerminarJogo = new Intent(TreinoTeppo.this, FimDeTreino.class);
	    	startActivity(intentTerminarJogo);
	    	finish();
	    	
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


	@Override
	public void onClick(View v) 
	{
		switch(v.getId()){
			case R.id.answer1:
				this.jogadorClicouNaAlternativa(R.id.answer1);
		    	break;
		    case R.id.answer2:
		    	this.jogadorClicouNaAlternativa(R.id.answer2);
		    	break;
		    case R.id.answer3:
		    	this.jogadorClicouNaAlternativa(R.id.answer3);
		    	break;
		    case R.id.answer4:
		    	this.jogadorClicouNaAlternativa(R.id.answer4);
		    	break;
		}
	}
	
	private void jogadorClicouNaAlternativa(int idDoBotaoQueUsuarioClicou)
	{
		if (mSecondsLeft > 0)
		{
			//ainda nao acabou o tempo...
			Button botaoAlternativaUsuarioClicou = (Button) findViewById(idDoBotaoQueUsuarioClicou);
			if(botaoAlternativaUsuarioClicou != null)
			{
				String hiraganaUsuarioSelecionou = botaoAlternativaUsuarioClicou.getText().toString();
				GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
				LinkedList<KanjiTreinar> kanjisTreinadosNaPartida = guardaDadosDaPartida.getKanjisTreinadosNaPartida();
				KanjiTreinar ultimoKanjiTreinado = kanjisTreinadosNaPartida.getLast();
				if(hiraganaUsuarioSelecionou.compareTo(ultimoKanjiTreinado.getHiraganaDoKanji()) == 0)
				{
					jogadorAcertouUmKanji();
				}
				else
				{
					//usuario errou uma alternativa
					this.reproduzirSfx("noJogo-jogadorErrouAlternativa");
					guardaDadosDaPartida.adicionarKanjiErradoNaPartida(ultimoKanjiTreinado);
					
					//desabilita os botoes de alternativa por um tempo...
					final Button botaoAnswer1 = (Button)findViewById(R.id.answer1);
					final Button botaoAnswer2 = (Button)findViewById(R.id.answer2);
					final Button botaoAnswer3 = (Button)findViewById(R.id.answer3);
					final Button botaoAnswer4 = (Button)findViewById(R.id.answer4);
					//botaoAnswer1.setEnabled(false);
					botaoAnswer1.setClickable(false);
					botaoAnswer2.setClickable(false);
					botaoAnswer3.setClickable(false);
					botaoAnswer4.setClickable(false);
					botaoAnswer1.getBackground().setAlpha(128);
					botaoAnswer2.getBackground().setAlpha(128);
					botaoAnswer3.getBackground().setAlpha(128);
					botaoAnswer4.getBackground().setAlpha(128);
					Toast.makeText(this, getResources().getString(R.string.errou_traducao_kanji) , Toast.LENGTH_LONG).show();
					new Timer().schedule(new TimerTask() {
					    @Override
					    public void run() {
					        
					        //If you want to operate UI modifications, you must run ui stuff on UiThread.
					        TreinoTeppo.this.runOnUiThread(new Runnable() {
					            @Override
					            public void run() {
					            	//botaoAnswer1.setEnabled(true);
					            	botaoAnswer1.setClickable(true);
					            	botaoAnswer2.setClickable(true);
					            	botaoAnswer3.setClickable(true);
					            	botaoAnswer4.setClickable(true);
					            	botaoAnswer1.getBackground().setAlpha(255);
									botaoAnswer2.getBackground().setAlpha(255);
									botaoAnswer3.getBackground().setAlpha(255);
									botaoAnswer4.getBackground().setAlpha(255);
					            }
					        });
					    }
					}, 3000);
				}
			}
		}
		
		
		
	}

	private void jogadorAcertouUmKanji() 
	{
		GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		KanjiTreinar ultimoKanjiTreinado = guardaDadosDaPartida.getKanjisTreinadosNaPartida().getLast();
		guardaDadosDaPartida.adicionarKanjiAcertadoNaPartida(ultimoKanjiTreinado);
		this.reproduzirSfx("noJogo-jogadorAcertouAlternativa");
		//primeiro, atualizar os pontos que ganhou na ultima partida
		//usuario acertou o hiragana do kanji. ganha pontos
		int dificuldadeDoKanjiAcertado = ultimoKanjiTreinado.getDificuldadeDoKanji();
		int pontuacaoParaAdicionarAoJogador = 10 * dificuldadeDoKanjiAcertado;
		guardaDadosDaPartida.adicionarPontosPlacarDoJogadorNaPartida(pontuacaoParaAdicionarAoJogador);
		TextView textviewScoreDoJogador = (TextView)findViewById(R.id.hitsPartida);
		//o placar atual tem 5 dígitos? se não, tem de adicionar uns zeros ao lado...
		int quantosDigitosTemPontuacaoAtual = String.valueOf(guardaDadosDaPartida.getRoundDaPartida()).length();
		String novoTextoScore = "Strikes:";
		for(int i = quantosDigitosTemPontuacaoAtual; i < 5; i++)
		{
			novoTextoScore = novoTextoScore + "0";
		}
		novoTextoScore = novoTextoScore + guardaDadosDaPartida.getRoundDaPartida();
		textviewScoreDoJogador.setText(novoTextoScore);
		
		//atualizar a animacao do sumo socando árvore, se necessário...
		int roundAtual = guardaDadosDaPartida.getRoundDaPartida();
		if(roundAtual > 5 && (roundAtual % 5) == 0)
		{
			this.atualizarAnimacaoSumozinhoAcertandoArvore();
		}
		
		this.escolherUmNovoKanjiParaTreinar();
	}
	
	private void atualizarAnimacaoSumozinhoAcertandoArvore()
	{
		final AnimationDrawable animacaoSumozinhoEArvore = new AnimationDrawable();
		GuardaDadosDaPartida guardaDadosPartida = GuardaDadosDaPartida.getInstance();
		int roundAtual = guardaDadosPartida.getRoundDaPartida(); 
		String nomeImagemSumozinhoAnimacao1 = this.getNomeImagemSumozinho(roundAtual);
		String nomeImagemSumozinhoAnimacao2 = nomeImagemSumozinhoAnimacao1 + "_alt";
		int idImagemSumozinhoAnimacao1 = getResources().getIdentifier(nomeImagemSumozinhoAnimacao1, "drawable", getPackageName());
		int idImagemSumozinhoAnimacao2 = getResources().getIdentifier(nomeImagemSumozinhoAnimacao2, "drawable", getPackageName());
		animacaoSumozinhoEArvore.addFrame(getResources().getDrawable(idImagemSumozinhoAnimacao1), 200);
		animacaoSumozinhoEArvore.addFrame(getResources().getDrawable(idImagemSumozinhoAnimacao2), 200);
		animacaoSumozinhoEArvore.setOneShot(false);
		ImageView viewSumoAcertandoArvore = (ImageView) findViewById(R.id.ringue_luta);
		viewSumoAcertandoArvore.setImageDrawable(animacaoSumozinhoEArvore);
		viewSumoAcertandoArvore.post(new Runnable() {
			@Override
			public void run() {
				animacaoSumozinhoEArvore.start();
			}
		});
	}
	
	private String getNomeImagemSumozinho(int roundAtual)
	{
		String nomeImagemSumozinho = "sumoarenasingle";
		if(roundAtual < 10)
		{
			//0..9
			nomeImagemSumozinho = nomeImagemSumozinho + 0;
		}
		else if(roundAtual < 20)
		{
			//10..19
			nomeImagemSumozinho = nomeImagemSumozinho + 10;
		}
		else if(roundAtual < 30)
		{
			//20..29
			nomeImagemSumozinho = nomeImagemSumozinho + 20;
		}
		else if(roundAtual < 40)
		{
			//30..39
			nomeImagemSumozinho = nomeImagemSumozinho + 30;
		}
		else
		{
			nomeImagemSumozinho = nomeImagemSumozinho + 40;
		}
		
		return nomeImagemSumozinho;
	}

}
