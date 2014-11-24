package br.ufrn.dimap.pairg.sumosensei;


import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Text;

import com.google.android.gms.internal.gu;
import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.dapartida.TerminaPartidaTask;
import br.ufrn.dimap.pairg.sumosensei.app.R;

import bancodedados.KanjiTreinar;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import cenario.ImageAdapter;
import doteppo.ArmazenaListaDeKanjisTreinarEmOrdem;
import doteppo.DadosParametroPegarKanjisMenosTreinados;
import doteppo.GuardaFormaComoKanjisSeraoTreinados;
import doteppo.SolicitaKanjisMaisErradosTask;
import doteppo.SolicitaKanjisMenosTreinadosTask;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class TreinoTeppo extends ActivityDoJogoComSom implements View.OnClickListener {
	
	private static int GAME_DURATION = 90;
	private int mSecondsLeft;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
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
		
		//ver qual o modo escolhido para exibir os kanjis, pra ver se precisamos extrair algo no BD
		GuardaFormaComoKanjisSeraoTreinados guardaModoComoKanjisSeraoExibidos =
				GuardaFormaComoKanjisSeraoTreinados.getInstance();
		String formaComoKanjisSeraoTreinados = guardaModoComoKanjisSeraoExibidos.getModoDeJogo();
		if(formaComoKanjisSeraoTreinados.compareTo("menos_jogados") == 0)
		{
			//temos de carregar a lista dos menos jogados primeiro, via task
			ProgressDialog loadingPegandoKanjisEmOrdem =  ProgressDialog.show(this, getResources().getString(R.string.carregando_palavras_em_ordem), getResources().getString(R.string.por_favor_aguarde));
			SolicitaKanjisMenosTreinadosTask taskPegaKanjisMenosTreinadosEmOrdem =
					new SolicitaKanjisMenosTreinadosTask(loadingPegandoKanjisEmOrdem, this);
			DadosParametroPegarKanjisMenosTreinados dadosParaPegarKanjisMenosTreinados = new DadosParametroPegarKanjisMenosTreinados();
			//precisamos do nome do jogador...
			SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
			String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(getApplicationContext());
			dadosParaPegarKanjisMenosTreinados.setNomeDeUsuario(nomeJogadorArmazenado);
			//e das categorias sendo jogadas...
			GuardaDadosDaPartida guardaCategoriasJogadasNoJogo = GuardaDadosDaPartida.getInstance();
			LinkedList<String> categoriasTreinadas = guardaCategoriasJogadasNoJogo.getCategoriasTreinadasNaPartida();
			dadosParaPegarKanjisMenosTreinados.setCategoriasSelecionadas(categoriasTreinadas);
			taskPegaKanjisMenosTreinadosEmOrdem.execute(dadosParaPegarKanjisMenosTreinados);
		}
		else if(formaComoKanjisSeraoTreinados.compareTo("mais_errados") == 0)
		{
			//temos de carregar a lista dos mmais errados primeiro, via task
			ProgressDialog loadingPegandoKanjisEmOrdem =  ProgressDialog.show(this, getResources().getString(R.string.carregando_palavras_em_ordem), getResources().getString(R.string.por_favor_aguarde));
			SolicitaKanjisMaisErradosTask taskPegaKanjisMenosTreinadosEmOrdem =
					new SolicitaKanjisMaisErradosTask(loadingPegandoKanjisEmOrdem, this);
			DadosParametroPegarKanjisMenosTreinados dadosParaPegarKanjisMenosTreinados = new DadosParametroPegarKanjisMenosTreinados();
			//precisamos do nome do jogador...
			SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
			String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(getApplicationContext());
			dadosParaPegarKanjisMenosTreinados.setNomeDeUsuario(nomeJogadorArmazenado);
			//e das categorias sendo jogadas...
			GuardaDadosDaPartida guardaCategoriasJogadasNoJogo = GuardaDadosDaPartida.getInstance();
			LinkedList<String> categoriasTreinadas = guardaCategoriasJogadasNoJogo.getCategoriasTreinadasNaPartida();
			dadosParaPegarKanjisMenosTreinados.setCategoriasSelecionadas(categoriasTreinadas);
			taskPegaKanjisMenosTreinadosEmOrdem.execute(dadosParaPegarKanjisMenosTreinados);
		}

		else
		{
			//já carregados oa kanjis aleatoriamente antes,então não precisamos carregar novamente
			aposCarregarListaComKanjisParaTreinarEmOrdem();
		}
			
	}
	
	public void aposCarregarListaComKanjisParaTreinarEmOrdem()
	{
		prepararTelaInicialJogo();
		escolherUmNovoKanjiParaTreinar();
	}
	
	private void escolherUmNovoKanjiParaTreinar()
	{
		GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		KanjiTreinar umKanjiAleatorioParaTreinar;
		//NOVO ANDREWS
		String formaComoOsKanjisSeraoApresentados = GuardaFormaComoKanjisSeraoTreinados.getInstance().getModoDeJogo();
		if(formaComoOsKanjisSeraoApresentados.compareTo("menos_jogados") == 0)
		{
			ArmazenaListaDeKanjisTreinarEmOrdem guardaOsKanjisEmOrdem = ArmazenaListaDeKanjisTreinarEmOrdem.getInstance();
			umKanjiAleatorioParaTreinar = guardaOsKanjisEmOrdem.pegarProximoKanjiPraTreinar();
		}
		else if(formaComoOsKanjisSeraoApresentados.compareTo("mais_errados") == 0)
		{
			//tem de ver se tem kanji errado
			ArmazenaListaDeKanjisTreinarEmOrdem guardaOsKanjisEmOrdem = ArmazenaListaDeKanjisTreinarEmOrdem.getInstance();
			LinkedList<KanjiTreinar> listaKanjisTreinarEmOrdem = guardaOsKanjisEmOrdem.getListaDeKanjisPraTreinarEmOrdem();
			if(listaKanjisTreinarEmOrdem != null && listaKanjisTreinarEmOrdem.size() > 0)
			{
				umKanjiAleatorioParaTreinar = guardaOsKanjisEmOrdem.pegarProximoKanjiPraTreinar();
			}
			else
			{
				//escolhe aleatoriamente um kanji pra treinar...
				umKanjiAleatorioParaTreinar = guardaDadosDaPartida.getUmKanjiAleatorioParaTreinar();
			}
		}
		else
		{
			//escolhe aleatoriamente um kanji pra treinar...
			umKanjiAleatorioParaTreinar = guardaDadosDaPartida.getUmKanjiAleatorioParaTreinar();
		}
		//FIM DO NOVO
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
	    TextView traducaoKanjiAcertar = (TextView) findViewById(R.id.traducao_kanji_acertar);
	    String textoKanjiTreinar = kanjiParaTreinarNaPartida.getKanji();
	    String textoTraducaoKanjiAcertar = kanjiParaTreinarNaPartida.getTraducaoEmPortugues();
	    textViewKanjiAcertar.setText(textoKanjiTreinar);
	    traducaoKanjiAcertar.setText(textoTraducaoKanjiAcertar);
	    guardaDadosDeUmaPartida.incrementarRoundDaPartida();
	 }


	
	private CountDownTimer timerFimDeJogo;
	private void prepararTelaInicialJogo() 
	{	
		//primeiro, setar a fonte do balão do sensei
		String fontpath2 = "fonts/chifont.ttf";
	    Typeface tf2 = Typeface.createFromAsset(this.getAssets(), fontpath2);
	    TextView textviewFalaSensei = (TextView) findViewById(R.id.kanji_acertar);
	    textviewFalaSensei.setTypeface(tf2);
		
		final AnimationDrawable animacaoTeppo = new AnimationDrawable();
		animacaoTeppo.addFrame(getResources().getDrawable(R.drawable.sumoarenasingle0), 200);
		animacaoTeppo.addFrame(getResources().getDrawable(R.drawable.sumoarenasingle0_alt), 200);
		animacaoTeppo.setOneShot(false);
		RelativeLayout viewSumoAcertandoArvore = (RelativeLayout) findViewById(R.id.parte_emcima_dos_botoes);
		viewSumoAcertandoArvore.setBackgroundDrawable(animacaoTeppo);
		viewSumoAcertandoArvore.post(new Runnable() {
			@Override
			public void run() {
				animacaoTeppo.start();
			}
		});
		
		final LinkedList<String> categoriasSelecionadas = GuardaDadosDaPartida.getInstance().getCategoriasTreinadasNaPartida();
		
		Integer [] indicesIconesCategoriasDoJogo = PegaIdsIconesDasCategoriasSelecionadas.pegarIndicesIconesDasCategoriasSelecionadas(categoriasSelecionadas);
	 	final Gallery gallery = (Gallery) findViewById(R.id.listagem_categorias);
	    gallery.setAdapter(new ImageAdapter(indicesIconesCategoriasDoJogo, this));
	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id) {
	        	String textoCategoria = categoriasSelecionadas.get(position);
	            Toast mensagemAvisoCategoria = Toast.makeText(TreinoTeppo.this, textoCategoria, Toast.LENGTH_SHORT);
	            mensagemAvisoCategoria.setGravity(Gravity.RIGHT | Gravity.TOP, gallery.getLeft(), gallery.getTop()+(gallery.getBottom()-gallery.getTop())/2);
	            mensagemAvisoCategoria.show();
	        }
	    });
	    this.mSecondsLeft = GAME_DURATION;
	    
	    timerFimDeJogo = new CountDownTimer(mSecondsLeft * 1000, 1000) {

	        public void onTick(long millisUntilFinished) {
	        	--mSecondsLeft;
	        	if(mSecondsLeft == 10)
	        	{
	        		//pouco tempo para acabar? add animação no timer!
	        		final Animation animScale = AnimationUtils.loadAnimation(TreinoTeppo.this, R.anim.anim_scale_clock);
	        		TextView viewTimer = (TextView) findViewById(R.id.countdown);
	        		viewTimer.setTextColor(Color.RED);
	        		viewTimer.startAnimation(animScale);
	        		
	        	}
	        	String tempoAtual = String.format("%02d:%02d", 
	        		    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
	        		    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - 
	        		    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
	        		);

	    		// update countdown
	    		((TextView) findViewById(R.id.countdown)).setText(tempoAtual);
	        }

	        public void onFinish() {
	        	mudarMusicaDeFundo(R.raw.lazy_susan);
	        	Intent intentTerminarJogo = new Intent(TreinoTeppo.this, FimDeTreino.class);
		    	startActivity(intentTerminarJogo);
		    	finish();
	        }
	     }.start();
	}
	
	
	
	@Override
	public void onBackPressed()  {
		mudarMusicaDeFundo(R.raw.lazy_susan);
		if(this.timerFimDeJogo != null)
		{
			timerFimDeJogo.cancel();
		}
		super.onBackPressed();
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
	
	private boolean estahrodandoAnimacaoJogadorErrou = false;//estah rodando a animacao do mestre kin de resposta errada?
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
					/*botaoAnswer1.getBackground().setAlpha(128);
					botaoAnswer2.getBackground().setAlpha(128);
					botaoAnswer3.getBackground().setAlpha(128);
					botaoAnswer4.getBackground().setAlpha(128);*/
					Animation animacaoTransparente = AnimationUtils.loadAnimation(this, R.anim.anim_transparente_botao);
					botaoAnswer1.startAnimation(animacaoTransparente);
					botaoAnswer2.startAnimation(animacaoTransparente);
					botaoAnswer3.startAnimation(animacaoTransparente);
					botaoAnswer4.startAnimation(animacaoTransparente);
					
					//mudar carinha do mestre...
					final ImageView imagemMestre = (ImageView)findViewById(R.id.mestrekin);
					final Resources res = getResources();
					imagemMestre.setImageDrawable(res.getDrawable(R.drawable.mestrezangado));
					this.estahrodandoAnimacaoJogadorErrou = true;
					Toast.makeText(this, getResources().getString(R.string.errou_traducao_kanji) , Toast.LENGTH_SHORT).show();
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
									imagemMestre.setImageDrawable(res.getDrawable(R.drawable.senseiteppocortado));
									estahrodandoAnimacaoJogadorErrou = false;//acabou animacao
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
		//mudar a carinha do sensei...
		final ImageView imagemMestre = (ImageView)findViewById(R.id.mestrekin);
		final Resources res = getResources();
		imagemMestre.setImageDrawable(res.getDrawable(R.drawable.mestrefeliz));
		new Timer().schedule(new TimerTask() {
		    @Override
		    public void run() {
		        
		        //If you want to operate UI modifications, you must run ui stuff on UiThread.
		        TreinoTeppo.this.runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		            	if(estahrodandoAnimacaoJogadorErrou == false)
		            	{
		            		imagemMestre.setImageDrawable(res.getDrawable(R.drawable.senseiteppocortado));
		            	}
		            	
		            }
		        });
		    }
		}, 1000);
		
		
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
		String novoTextoScore = getResources().getString(R.string.label_acertos_sem_zero);
		/*for(int i = quantosDigitosTemPontuacaoAtual; i < 5; i++)
		{
			novoTextoScore = novoTextoScore + "0";
		}*/
		novoTextoScore = novoTextoScore + guardaDadosDaPartida.getRoundDaPartida();
		textviewScoreDoJogador.setText(novoTextoScore);
		
		//atualizar a animacao do sumo socando árvore, se necessário...
		int roundAtual = guardaDadosDaPartida.getRoundDaPartida();
		if(roundAtual > 5 && (roundAtual % 10) == 0)
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
		RelativeLayout viewSumoAcertandoArvore = (RelativeLayout) findViewById(R.id.parte_emcima_dos_botoes);
		viewSumoAcertandoArvore.setBackgroundDrawable(animacaoSumozinhoEArvore);
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
			this.reproduzirSfx("teppo-empurrouArvore");
		}
		else if(roundAtual < 20)
		{
			//10..19
			nomeImagemSumozinho = nomeImagemSumozinho + 10;
			this.reproduzirSfx("teppo-empurrouArvore");
		}
		else if(roundAtual < 30)
		{
			//20..29
			nomeImagemSumozinho = nomeImagemSumozinho + 20;
			this.reproduzirSfx("teppo-empurrouArvore");
		}
		else if(roundAtual < 40)
		{
			//30..39
			nomeImagemSumozinho = nomeImagemSumozinho + 30;
			this.reproduzirSfx("teppo-empurrouArvore");
		}
		else
		{
			nomeImagemSumozinho = nomeImagemSumozinho + 40;
			this.reproduzirSfx("teppo-derrubouArvore");
		}
		
		return nomeImagemSumozinho;
	}
	
	

}
