package br.ufrn.dimap.pairg.sumosensei;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import lojinha.DAOAcessaDinheiroDoJogador;
import lojinha.TransformaPontosEmCredito;

import bancodedados.KanjiTreinar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.internal.gu;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.example.games.basegameutils.BaseGameUtils;
import com.google.example.games.basegameutils.GameHelper;
import com.phiworks.dapartida.ActivityPartidaMultiplayer;
import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.domodocasual.AdapterListViewSalasCriadas;

import doteppo.RegistraDadosDoTreinoTask;
import doteppo.TaskCalculaPalavrasErradas;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;
import br.ufrn.dimap.pairg.sumosensei.app.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FimDeTreino extends ActivityDoJogoComSom implements ConnectionCallbacks, OnConnectionFailedListener {
	
	//private GoogleApiClient mGoogleApiClient;
	private GameHelper gameHelper;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		 // Create the Google Api Client with access to Plus and Games
	    /*mGoogleApiClient = new GoogleApiClient.Builder(this)
	            .addConnectionCallbacks(this)
	            .addOnConnectionFailedListener(this)
	            .addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN)
	            .addApi(Games.API).addScope(Games.SCOPE_GAMES)
	            .build();*/
		setContentView(R.layout.activity_fim_de_treino);
		
		
		
		TextView textviewTextoFinal = (TextView)findViewById(R.id.texto_de_jogador_ganhou);
		RelativeLayout fundoFimDeTreino = (RelativeLayout) findViewById(R.id.fundo_fim_de_treino);
		ImageView sensei = (ImageView) findViewById(R.id.sensei);
		final Resources res = getResources();
		int numeroAcertos = GuardaDadosDaPartida.getInstance().getRoundDaPartida() - 1;
		
		
		String fraseFinal = "" + numeroAcertos;
		if(numeroAcertos == 0)
		{
			fraseFinal = getResources().getString(R.string.vitoriaTeppo0_acertos);
			fundoFimDeTreino.setBackground(getResources().getDrawable(R.drawable.teppofinal0));
			sensei.setImageDrawable(res.getDrawable(R.drawable.mestrezangado));
		}
		else if(numeroAcertos == 1)
		{
			fraseFinal = fraseFinal + " " + getResources().getString(R.string.vitoriaTeppo0singular);
			fundoFimDeTreino.setBackground(getResources().getDrawable(R.drawable.teppofinal0));
			sensei.setImageDrawable(res.getDrawable(R.drawable.mestrezangado));
		}
		else if(numeroAcertos < 10)
		{
			fraseFinal = fraseFinal + " " + getResources().getString(R.string.vitoriaTeppo0);
			fundoFimDeTreino.setBackground(getResources().getDrawable(R.drawable.teppofinal0));
			sensei.setImageDrawable(res.getDrawable(R.drawable.mestrezangado));
			
			
		}
		else if(numeroAcertos < 20)
		{
			fraseFinal = fraseFinal + " " + getResources().getString(R.string.vitoriaTeppo1);
			fundoFimDeTreino.setBackground(getResources().getDrawable(R.drawable.teppofinal10));
			sensei.setImageDrawable(res.getDrawable(R.drawable.mestrezangado));
		}
		else if(numeroAcertos < 30)
		{
			fraseFinal = fraseFinal + " " +  getResources().getString(R.string.vitoriaTeppo2);
			fundoFimDeTreino.setBackground(getResources().getDrawable(R.drawable.teppofinal20));
			sensei.setImageDrawable(res.getDrawable(R.drawable.senseiteppocortado));
		}
		else if(numeroAcertos < 40)
		{
			fraseFinal = fraseFinal + " " +  getResources().getString(R.string.vitoriaTeppo3);
			fundoFimDeTreino.setBackground(getResources().getDrawable(R.drawable.teppofinal30));
			sensei.setImageDrawable(res.getDrawable(R.drawable.mestrefeliz));
		}
		else
		{
			fraseFinal = fraseFinal + " " +  getResources().getString(R.string.vitoriaTeppo4);
			fundoFimDeTreino.setBackground(getResources().getDrawable(R.drawable.teppofinal40));
			sensei.setImageDrawable(res.getDrawable(R.drawable.mestrefeliz));
		
			System.out.println("teste");
			
		}
		textviewTextoFinal.setText(fraseFinal);

		final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
		animScale.setRepeatCount(Animation.INFINITE);
		View viewTextoFinalEFundo = findViewById(R.id.nihonball_e_text_final);
		//agora não tem mais a animação...
		//viewTextoFinalEFundo.startAnimation(animScale);
		
		//mudar fontes para fonte mais estilizada
		TextView tituloPalavrasErradas = (TextView)findViewById(R.id.label_palavras_erradas);
		TextView labelKanjiErrado = (TextView) findViewById(R.id.label_kanji_errado);
		TextView labelHiraganaErrado = (TextView) findViewById(R.id.label_hiragana_errado);
		TextView labelTraducaoErrada = (TextView) findViewById(R.id.label_traducao_errada);
		TextView labelQuantosErros = (TextView) findViewById(R.id.label_quantos_erros);
		TextView labelTextoVitoria = (TextView) findViewById(R.id.texto_de_jogador_ganhou);
		TextView labelTextoErrouNada = (TextView) findViewById(R.id.texto_errou_nada);
	    String fontpath = "fonts/gilles_comic_br.ttf";
	    Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
	    tituloPalavrasErradas.setTypeface(tf);
	    labelKanjiErrado.setTypeface(tf);
	    labelHiraganaErrado.setTypeface(tf);
	    labelTraducaoErrada.setTypeface(tf);
	    labelQuantosErros.setTypeface(tf);
	    labelTextoVitoria.setTypeface(tf);
	    labelTextoErrouNada.setTypeface(tf);
	    
	   //falta registrar os dados da partida no banco de dados usando uma task
		ProgressDialog loadingGuardandoDadosTreino =  ProgressDialog.show(this, getResources().getString(R.string.mensagem_enviando_dados_treino_para_bd), getResources().getString(R.string.por_favor_aguarde));
	  	RegistraDadosDoTreinoTask taskParaMostrarKanjisErrados = 
	  				new RegistraDadosDoTreinoTask(loadingGuardandoDadosTreino);
	  	
	  	SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(getApplicationContext());
	  	taskParaMostrarKanjisErrados.execute(nomeJogadorArmazenado);
	    
	    
	    calcularEMostrarKanjisErrados();
		
		//adicionar dinheirinho da partida no final
		int creditosAdicionarAoJogador = TransformaPontosEmCredito.converterPontosEmCredito(numeroAcertos);
		DAOAcessaDinheiroDoJogador daoDinheiroJogador = ConcreteDAOAcessaDinheiroDoJogador.getInstance();
		daoDinheiroJogador.adicionarCredito(creditosAdicionarAoJogador, this);
		String textoGanhouCreditoNaPartida = creditosAdicionarAoJogador + "x ";
		TextView textviewCreditoGanhoNaPartida = (TextView)findViewById(R.id.texto_moeda_ganha_no_jogo);
		textviewCreditoGanhoNaPartida.setText(textoGanhouCreditoNaPartida);
		
		//dar consuista, se ele conseguiu...
		 /*gameHelper = new GameHelper(this, GameHelper.CLIENT_ALL);
	        gameHelper.setup(this);
	        
		beginUserInitiatedSignIn();
		darConquistaDerrubouArvore(numeroAcertos);*/
	}
	
	@Override
	protected void onStart() {
	    super.onStart();
	    
	}
	
	@Override
	protected void onStop() {
	    super.onStop();
	    
	}



	private void darConquistaDerrubouArvore(int numeroDeAcertos) {
		 // start the asynchronous sign in flow
        /*mSignInClicked = true;
        mGoogleApiClient.connect();
		if(numeroDeAcertos <= 40)
		{
			try
			{
					Games.Achievements.unlock(gameHelper.getApiClient(), "CgkIs_27xcoSEAIQAQ");
					Log.i("TelaModoCasual", "usuário não está logado");
					this.onSignInFailed();
				
			}
			catch(Exception exc)
			{
				exc.printStackTrace();
				this.onSignInFailed();
			}
		}*/
		
	}

	ProgressDialog barraProgressoFinalTerminouJogo;

	private void calcularEMostrarKanjisErrados() {
		
		//agora essa tarefa é de uma task para não ocupar tempo de processamento
		
		barraProgressoFinalTerminouJogo =  ProgressDialog.show(this, getResources().getString(R.string.aviso_tempo_acaboou), getResources().getString(R.string.por_favor_aguarde));
		TaskCalculaPalavrasErradas taskParaMostrarKanjisErrados = 
				new TaskCalculaPalavrasErradas(barraProgressoFinalTerminouJogo, this);
		taskParaMostrarKanjisErrados.execute("");
		/*//agrupar palavras erradas para se ter uma contagem de quantas vezes a palavra foi errada
	    HashMap<String, Integer> kanjiEQuantasVezesErrou = new HashMap<String, Integer>();
	    //guardar tambem uma referencia rapida para o kanji errado
	    HashMap<String, KanjiTreinar> textoKanjiEObjetoKanji = new HashMap<String, KanjiTreinar>();
	    GuardaDadosDaPartida guardaPalavrasErradasNaPartida = GuardaDadosDaPartida.getInstance();
	    LinkedList<KanjiTreinar> kanjisErradosNaPartida = guardaPalavrasErradasNaPartida.getKanjisErradosNaPartida();
	    for(int i = 0; i < kanjisErradosNaPartida.size(); i++)
	    {
	    	KanjiTreinar umKanjiErrado = kanjisErradosNaPartida.get(i);
	    	if(kanjiEQuantasVezesErrou.containsKey(umKanjiErrado.getKanji()) == true)
	    	{
	    		Integer quantasVezesKanjiFoiErrado = kanjiEQuantasVezesErrou.get(umKanjiErrado.getKanji());
	    		quantasVezesKanjiFoiErrado = quantasVezesKanjiFoiErrado + 1;
	    		kanjiEQuantasVezesErrou.put(umKanjiErrado.getKanji(), quantasVezesKanjiFoiErrado);
	    	}
	    	else
	    	{
	    		kanjiEQuantasVezesErrou.put(umKanjiErrado.getKanji(), 1);//errou uma vez ainda
	    		textoKanjiEObjetoKanji.put(umKanjiErrado.getKanji(), umKanjiErrado);
	    	}
	    }
	    
	    //criar agora uma lista com os dados dos kanjis errados
	    ArrayList<DadosDeKanjiErrado> arrayListKanjisErrados = new ArrayList<DadosDeKanjiErrado>();
	    Iterator<String> iteraSobreTextosKanjisErrados = textoKanjiEObjetoKanji.keySet().iterator();
	    while(iteraSobreTextosKanjisErrados.hasNext())
	    {
	    	String textoUmKanjiErrado = iteraSobreTextosKanjisErrados.next();
	    	
	    	DadosDeKanjiErrado novoDadoKanjiErrado = new DadosDeKanjiErrado();
	    	KanjiTreinar umKanjiErrado = textoKanjiEObjetoKanji.get(textoUmKanjiErrado);
	    	novoDadoKanjiErrado.setKanjiErrado(umKanjiErrado.getKanji());
	    	novoDadoKanjiErrado.setHiraganaErrado(umKanjiErrado.getHiraganaDoKanji());
	    	novoDadoKanjiErrado.setTraducaoErrada(umKanjiErrado.getTraducaoEmPortugues());
	    	String textoKanjiErrado = umKanjiErrado.getKanji();
	    	Integer quantasVezesKanjiFoiErrado = kanjiEQuantasVezesErrou.get(textoKanjiErrado);
	    	if(quantasVezesKanjiFoiErrado != null)
	    	{
	    		novoDadoKanjiErrado.setQuantasVezesKanjiFoiErrado(quantasVezesKanjiFoiErrado);
	    	}
	    	arrayListKanjisErrados.add(novoDadoKanjiErrado);
	    }
	    //e, enfim, criar o adapter para a listView das palavras erradas
	    AdapterListViewKanjisErrados adapterKanjisErrados = new AdapterListViewKanjisErrados
				(this, R.layout.item_lista_palavras_erradas, arrayListKanjisErrados);

		 // Assign adapter to ListView
	    ListView listviewKanjisErrados = (ListView) findViewById(R.id.lista_palavras_erradas);
		listviewKanjisErrados.setAdapter(adapterKanjisErrados);*/
	}

	

	@Override
	public void onSignInFailed() {
		//Toast.makeText(getApplicationContext(), getResources().getText(R.string.login_failed_achievements), Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	/*public void encerrarFinalPartida(ArrayList<DadosDeKanjiErrado> arrayListKanjisErrados, ProgressDialog loadingDaTelaEmEspera)
	{
		// TODO Auto-generated method stub
		 // Assign adapter to ListView
		  //e, enfim, criar o adapter para a listView das palavras erradas
	    final AdapterListViewKanjisErrados adapterKanjisErrados = new AdapterListViewKanjisErrados
				(this, R.layout.item_lista_palavras_erradas, arrayListKanjisErrados);
	    ListView listviewKanjisErrados = (ListView) this.findViewById(R.id.lista_palavras_erradas);
		listviewKanjisErrados.setAdapter(adapterKanjisErrados);
		// TODO Auto-generated method stub
		loadingDaTelaEmEspera.dismiss();
	}*/
	
	
	
	
	public void voltarAoMenuPrincipal(View v)
	{
		Intent intentVoltaMenuPrincipal = new Intent(FimDeTreino.this, MainActivity.class);
    	intentVoltaMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intentVoltaMenuPrincipal);
    	finish();
	}
	
	public void jogarNovamente(View v)
	{
		Intent intentVoltaMenuPrincipal = new Intent(FimDeTreino.this, EscolhaNivelActivity.class);
    	intentVoltaMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intentVoltaMenuPrincipal);
    	finish();
	}

	

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private static int RC_SIGN_IN = 9001;

	private boolean mResolvingConnectionFailure = false;
	private boolean mAutoStartSignInflow = true;
	private boolean mSignInClicked = false;

	// ...

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	    /*if (mResolvingConnectionFailure) {
	        // Already resolving
	        return;
	    }

	    // If the sign in button was clicked or if auto sign-in is enabled,
	    // launch the sign-in flow
	    if (mSignInClicked || mAutoStartSignInflow) {
	        mAutoStartSignInflow = false;
	        mSignInClicked = false;
	        mResolvingConnectionFailure = true;

	        // Attempt to resolve the connection failure using BaseGameUtils.
	        // The R.string.signin_other_error value should reference a generic
	        // error string in your strings.xml file, such as "There was
	        // an issue with sign in, please try again later."
	        if (!BaseGameUtils.resolveConnectionFailure(this,
	                mGoogleApiClient, connectionResult,
	                RC_SIGN_IN, "sign in falhou!!!!!")) {
	            mResolvingConnectionFailure = false;
	        }
	    }*/

	    // Put code here to display the sign-in button
	}



	
	
	
	
	

}
