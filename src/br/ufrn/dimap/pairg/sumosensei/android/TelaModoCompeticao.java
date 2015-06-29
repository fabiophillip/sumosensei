package br.ufrn.dimap.pairg.sumosensei.android;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.DadosPartidaParaOLog;
import bancodedados.EnviarDadosDaPartidaParaLogTask;
import bancodedados.KanjiTreinar;
import bancodedados.MyCustomAdapter;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import bancodedados.SolicitaKanjisParaTreinoTask;
import br.ufrn.dimap.pairg.sumosensei.android.R;
import br.ufrn.dimap.pairg.sumosensei.android.R.anim;
import br.ufrn.dimap.pairg.sumosensei.android.R.drawable;
import br.ufrn.dimap.pairg.sumosensei.android.R.id;
import br.ufrn.dimap.pairg.sumosensei.android.R.layout;
import br.ufrn.dimap.pairg.sumosensei.android.R.raw;
import br.ufrn.dimap.pairg.sumosensei.android.R.string;
import cenario.SpinnerFiltroSalasAbertasListener;
import cenario.SpinnerSelecionaMesmoQuandoVoltaAoMesmoItem;

import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.BlinkAnimation;
import com.easyandroidanimations.library.BounceAnimation;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.phiworks.dapartida.ActivityPartidaMultiplayer;
import com.phiworks.dapartida.ConcreteDAOGuardaConfiguracoesDoJogador;
import com.phiworks.dapartida.DAOGuardaConfiguracoesDoJogador;
import com.phiworks.dapartida.EmbaralharAlternativasTask;
import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.dapartida.TerminaPartidaTask;
import com.phiworks.domodocasual.AdapterListViewChatCasual;
import com.phiworks.domodocasual.AdapterListViewIconeETexto;
import com.phiworks.domodocasual.AdapterListViewSalasCriadas;
import com.phiworks.domodocasual.AssociaCategoriaComIcone;
import com.phiworks.domodocasual.BuscaSalasModoCasualComArgumentoTask;
import com.phiworks.domodocasual.BuscaSalasModoCasualTask;
import com.phiworks.domodocasual.CriarSalaDoModoCasualTask;
import com.phiworks.domodocasual.DadosDaSalaModoCasual;
import com.phiworks.domodocasual.SalaAbertaModoCasual;
import com.phiworks.domodocasual.SolicitaCategoriasAbreSelecaoCategoriasTask;
import com.phiworks.domodocasual.SolicitaCategoriasDoJogoTask;

import docompeticao.BuscarSalasModoCompeticaoAposSalaCriadaTask;
import docompeticao.BuscarSalasModoCompeticaoTask;
import docompeticao.CalculaPosicaoDoJogadorNoRanking;
import docompeticao.CriarSalaCompeticaoTask;
import docompeticao.DadosUsuarioNoRanking;
import docompeticao.DesativarSalaEscolhidaCompeticaoTask;
import docompeticao.EnviarDadosDaPartidaParaLogCompeticaoTask;
import docompeticao.SalaAbertaModoCompeticao;
import docompeticao.SingletonGuardaDadosUsuarioNoRanking;
import docompeticao.TaskMudaDadosDoRanking;
import docompeticao.TaskPegaDadosDoJogadorNoRanking;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

public class TelaModoCompeticao extends ActivityPartidaMultiplayer {
	/*
	* API INTEGRATION SECTION. This section contains the code that integrates
	* the game with the Google Play game services API.
	*/

	// Debug tag
	final static boolean ENABLE_DEBUG = true;
	final static String TAG = "ButtonClicker2000";

	// Request codes for the UIs that we show with startActivityForResult:
	final static int RC_SELECT_PLAYERS = 10000;
	final static int RC_INVITATION_INBOX = 10001;
	final static int RC_WAITING_ROOM = 10002;

	// Room ID where the currently active game is taking place; null if we're
	// not playing.
	String mRoomId = null;
	private boolean euEscolhoACategoria = false;//quem escolhe a categoria esse boolean vira true

	// Are we playing in multiplayer mode?
	boolean mMultiplayer = false;

	// The participants in the currently active game
	ArrayList<Participant> mParticipants = null;

	// My participant ID in the currently active game
	String mMyId = null;

	// If non-null, this is the id of the invitation we received via the
	// invitation listener
	String mIncomingInvitationId = null;

	// Message buffer for sending messages
	byte[] mMsgBuf; //1 e 2 byte eu nao uso, mas o 3 diz se o adversario acertou ou nao clicou na carta1('N','A')

	private Room room;

	private volatile boolean guestTerminouDeCarregarListaDeCategorias = false;


	private String nomeUsuario;
	private String nomeAdversario;
	private boolean jogoJahTerminou = false;
	private boolean partidaComecou = false;
	private boolean estahComAnimacaoTegata = false;
	private boolean jahDeixouASala;
	private ProgressDialog popupCarregandoDadosDoJogadorNoRanking; 


	public void setEstahComAnimacaoTegata(boolean estahComAnimacaoTegata) {
		this.estahComAnimacaoTegata = estahComAnimacaoTegata;
	}

	/*referente a animacao de botoes */
	private Animation animAlpha;

	public boolean oGuestTerminouDeCarregarListaDeCategorias() {
		return guestTerminouDeCarregarListaDeCategorias;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		
		enableDebugLog(ENABLE_DEBUG, TAG);
		setContentView(R.layout.activity_tela_modo_competicao);
		super.onCreate(savedInstanceState);
		// set up a click listener for everything we care about
		for (int id : CLICKABLES) 
		{
			findViewById(id).setOnClickListener(this);
		}
		
		View botaoVerHistoricoPartida = findViewById(R.id.texto_botao_ver_historico_partida);
		botaoVerHistoricoPartida.setOnClickListener(this);
		View botaoResposta1 = findViewById (R.id.answer1);
		View botaoResposta2 = findViewById(R.id.answer2);
		View botaoResposta3 = findViewById(R.id.answer3);
		View botaoResposta4 = findViewById(R.id.answer4);
		View botaoVoltarAoMenuPrincipal = findViewById(R.id.botao_menu_principal);
		View botaoItem = findViewById(R.id.botaoItem1);
		View botaoItem2 = findViewById(R.id.botaoItem2);
		View botaoItem3 = findViewById(R.id.botaoItem3);
		View botaoItem4 = findViewById(R.id.botaoItem4);
		View botaoItem5 = findViewById(R.id.botaoItem5);
		View botaoAbrirChat = findViewById(R.id.botao_abrir_popup_chat);
		botaoResposta1.setOnClickListener(this);
		botaoResposta2.setOnClickListener(this);
		botaoResposta3.setOnClickListener(this);
		botaoResposta4.setOnClickListener(this);
		botaoVoltarAoMenuPrincipal.setOnClickListener(this);
		botaoItem.setOnClickListener(this);
		botaoItem2.setOnClickListener(this);
		botaoItem3.setOnClickListener(this);
		botaoItem4.setOnClickListener(this);
		botaoItem5.setOnClickListener(this);
		botaoAbrirChat.setOnClickListener(this);
		
		this.jahDeixouASala = false;
		this.jogoJahTerminou = false;
		this.partidaComecou = false;
		
		beginUserInitiatedSignIn();
		
		//mudar a label com o titulo atual do jogador...
		this.popupCarregandoDadosDoJogadorNoRanking = 
				ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.carregando_posicao_ranking), 
									getResources().getString(R.string.por_favor_aguarde));
		TaskPegaDadosDoJogadorNoRanking pegaDadosDoRanking = new TaskPegaDadosDoJogadorNoRanking(popupCarregandoDadosDoJogadorNoRanking, this);
		SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
		String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(getApplicationContext());
		pegaDadosDoRanking.execute(nomeJogadorArmazenado);
	}
	
	public void atualizarTituloJogador()
	{
		//TextView textoTituloJogadorRanking = (TextView)findViewById(R.id.tituloDoJogadorNoRanking);
		String textoRankingJogadorString = "";
		String tituloAtualDoJogador = CalculaPosicaoDoJogadorNoRanking.definirTituloDoJogadorNoRanking(getApplicationContext());
		SingletonGuardaDadosUsuarioNoRanking guardaTituloAtualJogador = SingletonGuardaDadosUsuarioNoRanking.getInstance();
		guardaTituloAtualJogador.setTituloDoJogadorCalculadoRecentemente(tituloAtualDoJogador);
		textoRankingJogadorString = textoRankingJogadorString + tituloAtualDoJogador;
		//textoTituloJogadorRanking.setText(textoRankingJogadorString);
		
		final ImageView faixaFundoRankingSumo = (ImageView) findViewById(R.id.imagem_insignia_sumo);
		String tituloJogador1 = getResources().getString(R.string.sumo_ranking_1);
		String tituloJogador2 = getResources().getString(R.string.sumo_ranking_2);
		String tituloJogador3 = getResources().getString(R.string.sumo_ranking_3);
		String tituloJogador4 = getResources().getString(R.string.sumo_ranking_4);
		String tituloJogador5 = getResources().getString(R.string.sumo_ranking_5);
		String tituloJogador6 = getResources().getString(R.string.sumo_ranking_6);
		String tituloJogador7 = getResources().getString(R.string.sumo_ranking_7);
		String tituloJogador8 = getResources().getString(R.string.sumo_ranking_8);
		if(tituloAtualDoJogador.compareToIgnoreCase(tituloJogador1) == 0)
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo1);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo1");
				}
			});
		}
		else if(tituloAtualDoJogador.compareToIgnoreCase(tituloJogador2) == 0)
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo2);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo2");
					
				}
			});
		}
		else if(tituloAtualDoJogador.compareToIgnoreCase(tituloJogador3) == 0)
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo3);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo3");
					
				}
			});
		}
		else if(tituloAtualDoJogador.compareToIgnoreCase(tituloJogador4) == 0)
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo4);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo4");
					
				}
			});
		}
		else if(tituloAtualDoJogador.compareToIgnoreCase(tituloJogador5) == 0)
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo5);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo5");
					
				}
			});
		}
		else if(tituloAtualDoJogador.compareToIgnoreCase(tituloJogador6) == 0)
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo6);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo6");
					
				}
			});
		}
		else if(tituloAtualDoJogador.compareToIgnoreCase(tituloJogador7) == 0)
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo7);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo7");
					
				}
			});
		}
		else if(tituloAtualDoJogador.compareToIgnoreCase(tituloJogador8) == 0)
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo8);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo8");
					
				}
			});
		}
		else 
		{
			faixaFundoRankingSumo.setImageResource(R.drawable.insignia_sumo9);
			faixaFundoRankingSumo.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					new com.easyandroidanimations.library.BounceAnimation(faixaFundoRankingSumo).setDuration(6000).animate();
					reproduzirSfx("competicao-rankingsumo9");
					
				}
			});
		}
		
		
		
		
		TextView textoBotaoVerRanking = (TextView) findViewById(R.id.botao_ver_ranking);
		TextView textoBotaoVerHistorico = (TextView) findViewById(R.id.texto_botao_ver_historico_partida);
		TextView textoBotaoJogarCompeticao = (TextView) findViewById(R.id.botao_jogar_competicao);
		
		 RotateAnimation rotate= (RotateAnimation)AnimationUtils.loadAnimation(this,R.anim.rotateanimation);
		 //textoTituloJogadorRanking.setAnimation(rotate);
		 textoBotaoVerRanking.setAnimation(rotate);
		 textoBotaoVerHistorico.setAnimation(rotate);
		 textoBotaoJogarCompeticao.setAnimation(rotate);
	}

	/**
	* Called by the base class (BaseGameActivity) when sign-in has failed. For
	* example, because the user hasn't authenticated yet. We react to this by
	* showing the sign-in button.
	*/
	@Override
	public void onSignInFailed() {
	Log.d(TAG, "Sign-in failed.");
	Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
	Intent intentVoltaMenuPrincipal = new Intent(TelaModoCompeticao.this, MainActivity.class);
	intentVoltaMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(intentVoltaMenuPrincipal);
	}

	/**
	* Called by the base class (BaseGameActivity) when sign-in succeeded. We
	* react by going to our main screen.
	*/
	@Override
	public void onSignInSucceeded() {
	Log.d(TAG, "Sign-in succeeded.");
	//pegar vocabulário do jogo
	SolicitaCategoriasDoJogoTask solicitaCategoriasPraFiltro = new SolicitaCategoriasDoJogoTask(null, this);
	solicitaCategoriasPraFiltro.execute("");

	// register listener so we are notified if we receive an invitation to play
	// while we are in the game
	Games.Invitations.registerInvitationListener(getApiClient(), this);

	// if we received an invite via notification, accept it; otherwise, go to main screen
	if (getInvitationId() != null) {
	    acceptInviteToRoom(getInvitationId());
	    return;
	}

	//salvarei o username do usuario para adicionar o log dele no banco de dados

	SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
	String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(getApplicationContext());
	this.nomeUsuario = nomeJogadorArmazenado;

	switchToMainScreen();
	}

	@Override
	public void onClick(View v) {
	Intent intent;

	switch (v.getId()) {
	    case R.id.button_sign_in:
	        // user wants to sign in
	        if (!verifyPlaceholderIdsReplaced()) {
	            showAlert("Error: sample not set up correctly. Please see README.");
	            return;
	        }
	        beginUserInitiatedSignIn();
	        break;
	    case R.id.botao_jogar_competicao:
	        // show list of invitable players
	    	this.loadingKanjisDoBd = ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.verificando_oponentes), getResources().getString(R.string.por_favor_aguarde));
	    	BuscarSalasModoCompeticaoTask buscaSalasCompetidores = new BuscarSalasModoCompeticaoTask(loadingKanjisDoBd, this);
	    	SingletonGuardaDadosUsuarioNoRanking sabeNivelDoUsuario = SingletonGuardaDadosUsuarioNoRanking.getInstance();
	    	String nivelAtualDoJogador = sabeNivelDoUsuario.getTituloDoJogadorCalculadoRecentemente();
	    	buscaSalasCompetidores.execute(nivelAtualDoJogador);
	        break;
	    case R.id.botao_ver_ranking:
	        // show list of pending invitations
	    	Intent chamarTelaRanking = new Intent(TelaModoCompeticao.this, TelaRankingActivity.class);
	    	startActivity(chamarTelaRanking);
	        break;
	    case R.id.texto_botao_ver_historico_partida:
	    	Intent iniciaTelaLog = new Intent(TelaModoCompeticao.this, DadosPartidasAnteriores.class);
			startActivity(iniciaTelaLog);
	    	break;
	    case R.id.button_accept_popup_invitation:
	        // user wants to accept the invitation shown on the invitation popup
	        // (the one we got through the OnInvitationReceivedListener).
	        acceptInviteToRoom(mIncomingInvitationId);
	        mIncomingInvitationId = null;
	        break;
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
	    case R.id.botao_menu_principal:
	    	leaveRoom();
	    	Intent intentVoltaMenuPrincipal = new Intent(TelaModoCompeticao.this, MainActivity.class);
	    	intentVoltaMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(intentVoltaMenuPrincipal);
	    	break;
	    case R.id.sendBtn:
	    	Log.i("TelaModoCompeticao", "jogador enviando msg" );
	    	if(this.popupDoChat != null)
	    	{
	    		Log.i("TelaModoCompeticao", "popupdochat nao eh null" );
	    		EditText textfieldMensagemDigitada = (EditText) popupDoChat.findViewById(R.id.chatET);
	        	String mensagemDigitada = textfieldMensagemDigitada.getText().toString();
	        	textfieldMensagemDigitada.setText("");
	        	String mensagemAdicionadaAoChat = this.adicionarMensagemNoChat(mensagemDigitada, true, this.nomeUsuario, false);
	        	this.avisarAoOponenteQueDigitouMensagem(mensagemAdicionadaAoChat);
	    	}
	    	break;
	    case R.id.botaoItem1:
	    	jogadorUsouItem(0);
	    	break;
	    case R.id.botaoItem2:
	    	jogadorUsouItem(1);
	    	break;
	    case R.id.botaoItem3:
	    	jogadorUsouItem(2);
	    	break;
	    case R.id.botaoItem4:
	    	jogadorUsouItem(3);
	    	break;
	    case R.id.botaoItem5:
	    	jogadorUsouItem(4);
	    	break;
	    case R.id.botao_abrir_popup_chat:
	    	mostrarPopupChat();
	    	break;
	    
	}
	}
	
	private void mostrarPopupChat()
	{
		if(this.popupDoChat != null)
		{
			popupDoChat.show();
			this.popupChatEstahAberto = true;
			
		}
	}
	
	/*
	 * Referente a entrar e criar ssalas modo competição
	 */
	
	public void entrarNoDueloComAlguemModoCompeticao(SalaAbertaModoCompeticao salaVaiEntrar)
	{
		salaAtual = salaVaiEntrar;
		 DesativarSalaEscolhidaCompeticaoTask taskDesativaSala = new DesativarSalaEscolhidaCompeticaoTask();
		 String idSala = String.valueOf(salaAtual.getIdDaSala());
		 taskDesativaSala.execute(idSala);
		 String tituloAtualJogador = SingletonGuardaDadosUsuarioNoRanking.getInstance().getTituloDoJogadorCalculadoRecentemente();
		 int idSalaParaJogadorConectar = CalculaPosicaoDoJogadorNoRanking.getIdSalaDeAcordoComStringJogador(tituloAtualJogador, getApplicationContext());
		 startQuickGame(idSalaParaJogadorConectar);
	}
	public void mostrarMensagemPessoaDoMesmoNivelEntrou()
	{
		String avisoSemOponentesDoNivelConectados = getResources().getString(R.string.chegou_oponente_mesmo_nivel);
		Toast.makeText(getApplicationContext(), avisoSemOponentesDoNivelConectados, Toast.LENGTH_SHORT).show();
	}
	
	public void mostrarPopupNaoHaSalasAbertasDeJogadoresComSeuNivel()
	{
		String avisoSemOponentesDoNivelConectados = getResources().getString(R.string.error_nenhum_oponente_do_nivel_conectado);
		Toast.makeText(getApplicationContext(), avisoSemOponentesDoNivelConectados, Toast.LENGTH_LONG).show();
		
		new Timer().schedule(new TimerTask() {
		    @Override
		    public void run() {
		        
		        //If you want to operate UI modifications, you must run ui stuff on UiThread.
		        TelaModoCompeticao.this.runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		            	if(partidaComecou == false && mCurScreen != R.id.screen_main && isMyServiceRunning() == true )
		            	{
		            		loadingKanjisDoBd = ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.verificando_oponentes), getResources().getString(R.string.por_favor_aguarde));
			    	    	BuscarSalasModoCompeticaoTask buscaSalasCompetidores = new BuscarSalasModoCompeticaoTask(loadingKanjisDoBd, TelaModoCompeticao.this);
			    	    	SingletonGuardaDadosUsuarioNoRanking sabeNivelDoUsuario = SingletonGuardaDadosUsuarioNoRanking.getInstance();
			    	    	String nivelAtualDoJogador = sabeNivelDoUsuario.getTituloDoJogadorCalculadoRecentemente();
			    	    	buscaSalasCompetidores.execute(nivelAtualDoJogador);
		            	}
		            }
		        });
		    }
		}, 5000);
	}
	
	public void mostrarPopupNaoHaSalasAbertasDeJogadoresComSeuNivelProCriadorSala()
	{
		String avisoSemOponentesDoNivelConectados = getResources().getString(R.string.error_nenhum_oponente_do_nivel_conectado);
		Toast.makeText(getApplicationContext(), avisoSemOponentesDoNivelConectados, Toast.LENGTH_LONG).show();
		
		new Timer().schedule(new TimerTask() {
		    @Override
		    public void run() {
		        
		        //If you want to operate UI modifications, you must run ui stuff on UiThread.
		        TelaModoCompeticao.this.runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		            	if(partidaComecou == false && mCurScreen != R.id.screen_main && isMyServiceRunning() == true )
		            	{
		            		loadingKanjisDoBd = ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.verificando_oponentes), getResources().getString(R.string.por_favor_aguarde));
			    	    	BuscarSalasModoCompeticaoAposSalaCriadaTask buscaSalasCompetidores = new BuscarSalasModoCompeticaoAposSalaCriadaTask(loadingKanjisDoBd, TelaModoCompeticao.this);
			    	    	SingletonGuardaDadosUsuarioNoRanking sabeNivelDoUsuario = SingletonGuardaDadosUsuarioNoRanking.getInstance();
			    	    	String nivelAtualDoJogador = sabeNivelDoUsuario.getTituloDoJogadorCalculadoRecentemente();
			    	    	buscaSalasCompetidores.execute(nivelAtualDoJogador);
		            	}
		            }
		        });
		    }
		}, 5000);
	}
	
	public void criarSalaEMostrarPopupNaoHaSalasAbertasDeJogadoresComSeuNivel()
	{
		String avisoSemOponentesDoNivelConectados = getResources().getString(R.string.error_nenhum_oponente_do_nivel_conectado);
		Toast.makeText(getApplicationContext(), avisoSemOponentesDoNivelConectados, Toast.LENGTH_SHORT).show();
		//criar uma sala...
		loadingKanjisDoBd = ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.verificando_oponentes), getResources().getString(R.string.por_favor_aguarde));
		CriarSalaCompeticaoTask criarSalaCompeticao = new CriarSalaCompeticaoTask(loadingKanjisDoBd, this);
		DadosDaSalaModoCasual dadosParaCriarSala = new DadosDaSalaModoCasual();
		String tituloDoJogador = SingletonGuardaDadosUsuarioNoRanking.getInstance().getTituloDoJogadorCalculadoRecentemente();
	 	String tituloDoJogadorParaOBD = CalculaPosicaoDoJogadorNoRanking.definirTituloDoJogadorParaBDCriacaoDeSala(tituloDoJogador, this.getApplicationContext());
	 	dadosParaCriarSala.setTituloDoJogador(tituloDoJogadorParaOBD);
		dadosParaCriarSala.setUsernameQuemCriouSala(this.nomeUsuario);
		criarSalaCompeticao.execute(dadosParaCriarSala);
		
		
	}
	
	@Override
	public void criarSalaQuickMatch(int idSalaQuickMatch)
	{
		this.euEscolhoACategoria = true;//quem cria a sala é o host e escolhe categoria...
		super.criarSalaQuickMatch(idSalaQuickMatch);
		this.aposCriarUmaSalaEEsperandoJogadoresEntrarem();
	}
	
	public void aposCriarUmaSalaEEsperandoJogadoresEntrarem()
	{
		new Timer().schedule(new TimerTask() {
		    @Override
		    public void run() {
		        
		        //If you want to operate UI modifications, you must run ui stuff on UiThread.
		        TelaModoCompeticao.this.runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		            	if(partidaComecou == false && mCurScreen != R.id.screen_main && isMyServiceRunning() == true )
		            	{
		            		loadingKanjisDoBd = ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.verificando_oponentes), getResources().getString(R.string.por_favor_aguarde));
			    	    	BuscarSalasModoCompeticaoAposSalaCriadaTask buscaSalasCompetidores = new BuscarSalasModoCompeticaoAposSalaCriadaTask(loadingKanjisDoBd, TelaModoCompeticao.this);
			    	    	SingletonGuardaDadosUsuarioNoRanking sabeNivelDoUsuario = SingletonGuardaDadosUsuarioNoRanking.getInstance();
			    	    	String nivelAtualDoJogador = sabeNivelDoUsuario.getTituloDoJogadorCalculadoRecentemente();
			    	    	buscaSalasCompetidores.execute(nivelAtualDoJogador);
		            	}
		            	
		            }
		        });
		    }
		}, 5000);
	}

	/**
	 * Referente a encontrar salas abertas e ATUALIZACAO COM NOVAS SALAS ABERTAS
	 */

	/*quando o usuario clica no iconezinho "C" em uma das salas, ele deve ver as categorias daquela sala*/
	public void abrirPopupMostrarCategoriasDeUmaSala(String[] categorias)
	{
		 final Dialog dialog = new Dialog(TelaModoCompeticao.this);
		 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			
		  // Include dialog.xml file
		 dialog.setContentView(R.layout.popup_categorias_de_uma_sala_casual_da_lista);
		 
		 
		 for(int i = 0; i < categorias.length; i++)
		 {
			 ImageView imageViewQuadradoBase; //quadrado que fica por baixo dos icones das categorias
			 ImageView imageViewQuadradoCategoria;
			 if(i == 0)
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado1base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado1categoria);
			 }
			 else if(i == 1)
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado2base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado2categoria);
			 }
			 else if(i == 2)
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado3base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado3categoria);
			 }
			 else if(i == 3)
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado4base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado4categoria);
			 }
			 else if(i == 4)
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado5base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado5categoria);
			 }
			 else if(i == 5)
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado6base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado6categoria);
			 }
			 else if(i == 6)
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado7base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado7categoria);
			 }
			 else if(i == 7)
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado8base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado8categoria);
			 }
			 else
			 {
				 imageViewQuadradoBase = (ImageView) dialog.findViewById(R.id.quadrado9base);
				 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado9categoria);
			 }
			
			 
			 //vamos tornar o quadrado de fundo e o icone da categoriavisiveis
			 imageViewQuadradoBase.setVisibility(View.VISIBLE);
			 imageViewQuadradoCategoria.setVisibility(View.VISIBLE);
			 
			 //agora falta mudar o icone de acordo com a categoria
			 String umaCategoria = categorias[i];
			 int imageResourceIconeCategoria = AssociaCategoriaComIcone.pegarIdImagemDaCategoria(getApplicationContext(),umaCategoria); 
			 //funcao acima eh so para pegar o icone da categoria com base no nome dela,tipo R.id.icone_cotidiano
			 
			 imageViewQuadradoCategoria.setImageResource(imageResourceIconeCategoria); 
		 }
		 
		 
		 dialog.show();
	}


	Thread threadAtualizaComNovasSalasAbertas;
	public void solicitarBuscarSalasAbertas() {
		this.loadingKanjisDoBd = new ProgressDialog(getApplicationContext());
		this.loadingKanjisDoBd = ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.buscando_salas_abertas), getResources().getString(R.string.por_favor_aguarde));
		BuscarSalasModoCompeticaoTask taskBuscaSalasAbertas = new BuscarSalasModoCompeticaoTask(loadingKanjisDoBd, this);
		taskBuscaSalasAbertas.execute("");
		//vamos aqui criar uma nova thread que vai esperar um tempo(de cinco em 5 segundos) para invocar a Task de buscar por novas salas abertas
		
	}

	public void pararThreadAtualizaComNovasSalas()
	{
		if(threadAtualizaComNovasSalasAbertas != null && threadAtualizaComNovasSalasAbertas.isAlive() == true)
		{
			threadAtualizaComNovasSalasAbertas.interrupt();
		}
	}

	@Override
	public void onPause()
	{
		super.onPause();
		if(threadAtualizaComNovasSalasAbertas != null && threadAtualizaComNovasSalasAbertas.isAlive() == true)
		{
			threadAtualizaComNovasSalasAbertas.interrupt();
		}
	}


	private ArrayList<SalaAbertaModoCompeticao> salasCarregadasModoCompeticao;
	private SalaAbertaModoCompeticao salaAtual;

	public void mostrarListaComSalasAposCarregar(ArrayList<SalaAbertaModoCompeticao> salasModoCasual, boolean mostrarAlertaNovasSalasCriadas)
	{
		
		 
		 if(loadingKanjisDoBd != null && loadingKanjisDoBd.isShowing())
		 {
			 this.loadingKanjisDoBd.dismiss();	 
		 }
		 
		 
	}

	public static int getVisiblePercent(View v) {
	    if (v != null && v.isShown()) {
	        Rect r = new Rect();
	        v.getGlobalVisibleRect(r);
	        double sVisible = r.width() * r.height();
	        double sTotal = v.getWidth() * v.getHeight();
	        return (int) (100 * sVisible / sTotal);
	    } else {
	        return -1;
	    }
	}

	public ArrayList<SalaAbertaModoCompeticao> getSalasCarregadasModoCasual() {
		return salasCarregadasModoCompeticao;
	}
	/* FIM PARTE REFERENTE A ATUALIZACAO COM NOVAS SALAS ABERTAS */ 


	private Typeface escolherFonteDoTextoListViewIconeETexto()
	{
		String fontPath = "fonts/Wonton.ttf";
	    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	    return tf;
	}

	

	/**
	 * Termina referente a encontrar salas abertas
	 */

	private void jogadorUsouItem(int indicePosicaoDoItemNoInventario) {
		GuardaDadosDaPartida guardaDadosDosItens = GuardaDadosDaPartida.getInstance();
		String itemSaiuInventario = guardaDadosDosItens.removerUmItemDoInventario(indicePosicaoDoItemNoInventario);
		if(itemSaiuInventario.compareTo("no_item") != 0)
		{
			
			if(itemSaiuInventario.compareTo("chikaramizu") == 0)
			{
				guardaDadosDosItens.adicionarItemIncorporado(itemSaiuInventario);
				this.reproduzirSfx("noJogo-chikaramizu");
				String avisoChikaramizu = getResources().getString(R.string.aviso_bom_chikaramizu);
				Toast toastAvisoChikaraMizu = Toast.makeText(getApplicationContext(), avisoChikaramizu , Toast.LENGTH_SHORT);
				toastAvisoChikaraMizu.setGravity(Gravity.CENTER, 0, 0);
				toastAvisoChikaraMizu.show();
			}
			else if(itemSaiuInventario.compareTo("shiko") == 0)
			{
				this.reproduzirSfx("noJogo-usouShiko");
				guardaDadosDosItens.setShikoFoiUsado(true);
				this.mandarMensagemMultiplayer("usouShiko;");
				Button botaoAnswer1 = (Button)findViewById(R.id.answer1);
				Button botaoAnswer2 = (Button)findViewById(R.id.answer2);
				Button botaoAnswer3 = (Button)findViewById(R.id.answer3);
				Button botaoAnswer4 = (Button)findViewById(R.id.answer4);
				botaoAnswer1.setClickable(false);
				botaoAnswer2.setClickable(false);
				botaoAnswer3.setClickable(false);
				botaoAnswer4.setClickable(false);
				final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
				animRotate.setRepeatCount(0);
				botaoAnswer1.startAnimation(animRotate);
				botaoAnswer2.startAnimation(animRotate);
				botaoAnswer3.startAnimation(animRotate);
				botaoAnswer4.startAnimation(animRotate);
				this.prepararNovaPartida(false);
			}
			else if(itemSaiuInventario.compareTo("tegata") == 0)
			{
				String mensagemTegata = getResources().getText(R.string.aviso_tegata) + "";
				Toast toastAvisoUsouTegata = Toast.makeText(getApplicationContext(), mensagemTegata , Toast.LENGTH_SHORT);
				toastAvisoUsouTegata.setGravity(Gravity.CENTER, 0, 0);
				toastAvisoUsouTegata.show();
				this.mandarMensagemMultiplayer("usouTegata;");
				this.reproduzirSfx("noJogo-usouTegata");
			}
			else if(itemSaiuInventario.compareTo("teppotree") == 0)
			{
				guardaDadosDosItens.adicionarItemIncorporado(itemSaiuInventario);
				String avisoTeppoTree = getResources().getString(R.string.aviso_bom_teppotree);
				Toast toastAvisoTeppoTree = Toast.makeText(getApplicationContext(), avisoTeppoTree , Toast.LENGTH_SHORT);
				toastAvisoTeppoTree.setGravity(Gravity.CENTER, 0, 0);
				toastAvisoTeppoTree.show();
				this.reproduzirSfx("noJogo-usouTeppotree");
				
			}
			//adicionar o item aos itens usados na partida
			guardaDadosDosItens.adicionarItemAListaDeItensUsados(itemSaiuInventario);
			
			//por fim, apagar o item da visualizacao
			ImageButton botaoItem = null;
			if(indicePosicaoDoItemNoInventario == 0)
			{
				botaoItem = (ImageButton)findViewById(R.id.botaoItem1);
			}
			else if(indicePosicaoDoItemNoInventario == 1)
			{
				botaoItem = (ImageButton)findViewById(R.id.botaoItem2);
			}
			else if(indicePosicaoDoItemNoInventario == 2)
			{
				botaoItem = (ImageButton)findViewById(R.id.botaoItem3);
			}
			else if(indicePosicaoDoItemNoInventario == 3)
			{
				botaoItem = (ImageButton)findViewById(R.id.botaoItem4);
			}
			else if(indicePosicaoDoItemNoInventario == 4)
			{
				botaoItem = (ImageButton)findViewById(R.id.botaoItem5);
			}
			if(botaoItem != null)
			{
				botaoItem.setImageResource(R.drawable.botaoitem);
			}
			
		}
		
		
	}

	void startQuickGame(int idDaPartida) {
		//parar a thread que ouve por novas salas
		if(this.threadAtualizaComNovasSalasAbertas != null && this.threadAtualizaComNovasSalasAbertas.isAlive())
		{
			this.threadAtualizaComNovasSalasAbertas.interrupt();
		}
	// quick-start a game with 1 randomly selected opponent
	final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
	Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
	        MAX_OPPONENTS, 0);
	RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
	rtmConfigBuilder.setMessageReceivedListener(this);
	rtmConfigBuilder.setRoomStatusUpdateListener(this);
	rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);

	rtmConfigBuilder.setVariant(idDaPartida); //somente dois usuarios com o mesmo variante podem jogar juntos no automatch. Usaremos o nivel do usuario como esse variante

	switchToScreen(R.id.screen_wait);
	keepScreenOn();
	resetGameVars();
	Games.RealTimeMultiplayer.create(getApiClient(), rtmConfigBuilder.build());
	}

	@Override
	public void onActivityResult(int requestCode, int responseCode,
	    Intent intent) {
	super.onActivityResult(requestCode, responseCode, intent);

	switch (requestCode) {
	    case RC_SELECT_PLAYERS:
	        // we got the result from the "select players" UI -- ready to create the room
	        handleSelectPlayersResult(responseCode, intent);
	        break;
	    case RC_INVITATION_INBOX:
	        // we got the result from the "select invitation" UI (invitation inbox). We're
	        // ready to accept the selected invitation:
	        handleInvitationInboxResult(responseCode, intent);
	        break;
	    case RC_WAITING_ROOM:
	        // we got the result from the "waiting room" UI.
	        if (responseCode == Activity.RESULT_OK) {
	            // ready to start playing
	            Log.d(TAG, "Starting game (waiting room returned OK).");
	            startGame(true);
	        } else if (responseCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
	            // player indicated that they want to leave the room
	            leaveRoom();
	        } else if (responseCode == Activity.RESULT_CANCELED) {
	            // Dialog was cancelled (user pressed back key, for instance). In our game,
	            // this means leaving the room too. In more elaborate games, this could mean
	            // something else (like minimizing the waiting room UI).
	            leaveRoom();
	        }
	        break;
	}
	}

	// Handle the result of the "Select players UI" we launched when the user clicked the
	// "Invite friends" button. We react by creating a room with those players.
	private void handleSelectPlayersResult(int response, Intent data) {
	if (response != Activity.RESULT_OK) {
	    Log.w(TAG, "*** select players UI cancelled, " + response);
	    switchToMainScreen();
	    return;
	}

	Log.d(TAG, "Select players UI succeeded.");

	// get the invitee list
	final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
	Log.d(TAG, "Invitee count: " + invitees.size());

	// get the automatch criteria
	Bundle autoMatchCriteria = null;
	int minAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
	int maxAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
	if (minAutoMatchPlayers > 0 || maxAutoMatchPlayers > 0) {
	    autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
	            minAutoMatchPlayers, maxAutoMatchPlayers, 0);
	    Log.d(TAG, "Automatch criteria: " + autoMatchCriteria);
	}

	// create the room
	Log.d(TAG, "Creating room...");
	RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
	rtmConfigBuilder.addPlayersToInvite(invitees);
	rtmConfigBuilder.setMessageReceivedListener(this);
	rtmConfigBuilder.setRoomStatusUpdateListener(this);
	if (autoMatchCriteria != null) {
	    rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
	}
	switchToScreen(R.id.screen_wait);
	keepScreenOn();
	resetGameVars();
	Games.RealTimeMultiplayer.create(getApiClient(), rtmConfigBuilder.build());
	Log.d(TAG, "Room created, waiting for it to be ready...");
	}

	// Handle the result of the invitation inbox UI, where the player can pick an invitation
	// to accept. We react by accepting the selected invitation, if any.
	private void handleInvitationInboxResult(int response, Intent data) {
	if (response != Activity.RESULT_OK) {
	    Log.w(TAG, "*** invitation inbox UI cancelled, " + response);
	    switchToMainScreen();
	    return;
	}

	Log.d(TAG, "Invitation inbox UI succeeded.");
	Invitation inv = data.getExtras().getParcelable(Multiplayer.EXTRA_INVITATION);

	// accept invitation
	acceptInviteToRoom(inv.getInvitationId());
	}

	// Accept the given invitation.
	void acceptInviteToRoom(String invId) {
	// accept the invitation
	Log.d(TAG, "Accepting invitation: " + invId);
	RoomConfig.Builder roomConfigBuilder = RoomConfig.builder(this);
	roomConfigBuilder.setInvitationIdToAccept(invId)
	        .setMessageReceivedListener(this)
	        .setRoomStatusUpdateListener(this);
	switchToScreen(R.id.screen_wait);
	keepScreenOn();
	resetGameVars();
	Games.RealTimeMultiplayer.join(getApiClient(), roomConfigBuilder.build());
	}

	// Activity is going to the background. We have to leave the current room.
	@Override
	public void onStop() {
	Log.d(TAG, "**** got onStop");

	// if we're in a room, leave it.
	leaveRoom();

	// stop trying to keep the screen on
	stopKeepingScreenOn();

	switchToScreen(R.id.screen_wait);
	super.onStop();
	}

	// Activity just got to the foreground. We switch to the wait screen because we will now
	// go through the sign-in flow (remember that, yes, every time the Activity comes back to the
	// foreground we go through the sign-in flow -- but if the user is already authenticated,
	// this flow simply succeeds and is imperceptible).
	@Override
	public void onStart() {
	switchToScreen(R.id.screen_wait);
	super.onStart();
	}

	// Handle back key to make sure we cleanly leave a game if we are in the middle of one
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent e) {
	if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
		Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " pressionou back;" );
		boolean mudarParaTelaInicialSumoSensei = false;
		if(mCurScreen == R.id.screen_main)
		{
			mudarParaTelaInicialSumoSensei = true;
		}
	    leaveRoom();
	    if(mudarParaTelaInicialSumoSensei == true)
	    {
	    	Intent intentVoltaMenuPrincipal = new Intent(TelaModoCompeticao.this, MainActivity.class);
	    	intentVoltaMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(intentVoltaMenuPrincipal);
	    	finish();
	    }
	    return true;
	}
	return super.onKeyDown(keyCode, e);
	}

	// Leave the room.
	void leaveRoom() {
	Log.d(TAG, "Leaving room.");
	mSecondsLeft = 0;
	if(this.jahDeixouASala == false)
	{
		if(salaAtual != null)
		{
			DesativarSalaEscolhidaCompeticaoTask taskDesativaSala = new DesativarSalaEscolhidaCompeticaoTask();
			String idSala = String.valueOf(salaAtual.getIdDaSala());
			taskDesativaSala.execute(idSala);
			
		}

		stopKeepingScreenOn();
		if (mRoomId != null) {
		    Games.RealTimeMultiplayer.leave(getApiClient(), this, mRoomId);
		    mRoomId = null;
		} 
		
		this.jahDeixouASala = true;
	}

	if(this.timerFimDeJogo != null)
	{
		this.timerFimDeJogo.cancel();
	}
	switchToMainScreen();

	}

	// Show the waiting room UI to track the progress of other players as they enter the
	// room and get connected.
	void showWaitingRoom(Room room) {
	// minimum number of players required for our game
	// For simplicity, we require everyone to join the game before we start it
	// (this is signaled by Integer.MAX_VALUE).
	//final int MIN_PLAYERS = Integer.MAX_VALUE;
	final int MIN_PLAYERS = 2;
	Intent i = Games.RealTimeMultiplayer.getWaitingRoomIntent(getApiClient(), room, MIN_PLAYERS);

	// show waiting room UI
	startActivityForResult(i, RC_WAITING_ROOM);
	}

	// Called when we get an invitation to play a game. We react by showing that to the user.
	@Override
	public void onInvitationReceived(Invitation invitation) {
	// We got an invitation to play a game! So, store it in
	// mIncomingInvitationId
	// and show the popup on the screen.
	mIncomingInvitationId = invitation.getInvitationId();
	((TextView) findViewById(R.id.incoming_invitation_text)).setText(
	        invitation.getInviter().getDisplayName() + " " +
	                getString(R.string.is_inviting_you));
	switchToScreen(mCurScreen); // This will show the invitation popup
	}

	@Override
	public void onInvitationRemoved(String invitationId) {
	if (mIncomingInvitationId.equals(invitationId)) {
	    mIncomingInvitationId = null;
	    switchToScreen(mCurScreen); // This will hide the invitation popup
	}
	}

	/*
	* CALLBACKS SECTION. This section shows how we implement the several games
	* API callbacks.
	*/

	// Called when we are connected to the room. We're not ready to play yet! (maybe not everybody
	// is connected yet).
	@Override
	public void onConnectedToRoom(Room room) {
	Log.d(TAG, "onConnectedToRoom.");

	// get room ID, participants and my ID:
	mRoomId = room.getRoomId();
	this.room = room;
	mParticipants = room.getParticipants();
	mMyId = room.getParticipantId(Games.Players.getCurrentPlayerId(getApiClient()));

	// print out the list of participants (for debug purposes)
	Log.d(TAG, "Room ID: " + mRoomId);
	Log.d(TAG, "My ID " + mMyId);
	Log.d(TAG, "<< CONNECTED TO ROOM>>");
	}

	// Called when we've successfully left the room (this happens a result of voluntarily leaving
	// via a call to leaveRoom(). If we get disconnected, we get onDisconnectedFromRoom()).
	@Override
	public void onLeftRoom(int statusCode, String roomId) {
	// we have left the room; return to main screen.
	Log.d(TAG, "onLeftRoom, code " + statusCode);
	switchToMainScreen();
	}

	// Called when we get disconnected from the room. We return to the main screen.
	@Override
	public void onDisconnectedFromRoom(Room room) {
		try
		{
			mRoomId = null;
			showGameError();
			this.leaveRoom();
		}
		catch(Exception exc)
		{
			mRoomId = null;
		}
		
	}

	// Show error message about game being cancelled and return to main screen.
	void showGameError() {
	showAlert(getString(R.string.game_problem));
	switchToMainScreen();
	SingletonArmazenaIdMusicaTocandoAtualmente sabeMusicaTocadaAtualmente = SingletonArmazenaIdMusicaTocandoAtualmente.getInstance();
	int musicaDeFundoAtual = sabeMusicaTocadaAtualmente.getIdMusicaTocandoAtualmente();
	int musicaDeFundoHeadstart = R.raw.ramblinglibrarian_nanyang_journey;
	if(musicaDeFundoAtual == musicaDeFundoHeadstart)
	{
		this.mudarMusicaDeFundo(R.raw.chineseinstrumentalmusic);
	}
	}

	// Called when room has been created
	@Override
	public void onRoomCreated(int statusCode, Room room) {
	Log.d(TAG, "onRoomCreated(" + statusCode + ", " + room + ")");
	if (statusCode != GamesStatusCodes.STATUS_OK) {
	    Log.e(TAG, "*** Error: onRoomCreated, status " + statusCode);
	    showGameError();
	    return;
	}

	// show the waiting room UI
	showWaitingRoom(room);
	}

	// Called when room is fully connected.
	@Override
	public void onRoomConnected(int statusCode, Room room) {
	Log.d(TAG, "onRoomConnected(" + statusCode + ", " + room + ")");
	if (statusCode != GamesStatusCodes.STATUS_OK) {
	    Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
	    showGameError();
	    return;
	}
	updateRoom(room);
	}

	@Override
	public void onJoinedRoom(int statusCode, Room room) {
	Log.d(TAG, "onJoinedRoom(" + statusCode + ", " + room + ")");
	if (statusCode != GamesStatusCodes.STATUS_OK) {
	    Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
	    showGameError();
	    return;
	}

	// show the waiting room UI
	showWaitingRoom(room);
	}

	// We treat most of the room update callbacks in the same way: we update our list of
	// participants and update the display. In a real game we would also have to check if that
	// change requires some action like removing the corresponding player avatar from the screen,
	// etc.
	@Override
	public void onPeerDeclined(Room room, List<String> arg1) {
	updateRoom(room);
	}

	@Override
	public void onPeerInvitedToRoom(Room room, List<String> arg1) {
	updateRoom(room);
	}

	@Override
	public void onP2PDisconnected(String participant) {
	}

	@Override
	public void onP2PConnected(String participant) {
	}

	@Override
	public void onPeerJoined(Room room, List<String> arg1) {
	updateRoom(room);
	}

	@Override
	public void onPeerLeft(Room room, List<String> peersWhoLeft) {
	updateRoom(room);
	}

	@Override
	public void onRoomAutoMatching(Room room) {
	updateRoom(room);
	}

	@Override
	public void onRoomConnecting(Room room) {
	updateRoom(room);
	}

	@Override
	public void onPeersConnected(Room room, List<String> peers) {
	updateRoom(room);
	}

	@Override
	public void onPeersDisconnected(Room room, List<String> peers) {
	updateRoom(room);
	}

	void updateRoom(Room room) {
	if (room != null) {
	    mParticipants = room.getParticipants();
	}
	if (mParticipants != null) {
	}
	}

	/*
	* GAME LOGIC SECTION. Methods that implement the game's rules.
	*/

	// Reset game variables in preparation for a new game.
	void resetGameVars() {
	mSecondsLeft = GAME_DURATION;
	mScore = 0;
	}

	// Start the gameplay phase of the game.
	void startGame(boolean multiplayer) 
	{
		mMultiplayer = multiplayer;
		this.enviarSeuUsernameParaOAdversario();
		//switchToScreen(R.id.decidindoQuemEscolheACategoria);
		/*LinkedList<String> categoriasSelecionadasNaSala = 
				salaAtual.getCategoriasSelecionadas();
		if(this.euEscolhoACategoria == true)
		{
			//vc é o host, começa uma partida com vc
			comecarNovaPartidaCasual(categoriasSelecionadasNaSala);
		}*/
		//this.decidirQuemEscolheACategoria();
		
		/*mMultiplayer = multiplayer;
		this.enviarSeuEmailParaOAdversario();
		switchToScreen(R.id.decidindoQuemEscolheACategoria);
		this.decidirQuemEscolheACategoria();*/
	}

	/*
	* COMMUNICATIONS SECTION. Methods that implement the game's network
	* protocol.
	*/

	// Called when we receive a real-time message from the network.
	// Messages in our game are made up of 2 bytes: the first one is 'F' or 'U'
	// indicating
	// whether it's a final or interim score. The second byte is the score.
	// There is also the
	// 'S' message, which indicates that the game should start.
	@Override
	public synchronized void onRealTimeMessageReceived(RealTimeMessage rtm) 
	{
		byte[] buf = rtm.getMessageData();
		String sender = rtm.getSenderParticipantId();

		String mensagem = "";
		try {
			mensagem = new String(buf, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Toast t = Toast.makeText(this, "mensagem recebida:" + mensagem, Toast.LENGTH_LONG);
	    t.show();*/
	    
		if(mensagem.contains("oponenteacertou;") == true)
		{
			String nomeUsuario = this.nomeUsuario;
			Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " recebeu mensagem oponenteacertou;" );
			if(this.viewImagemFundoOponente != null && this.textviewImagemOponente != null && this.viewImagemIconeOponente != null)
			{
				if(this.euEscolhoACategoria == true)
				{
					//jogador é o host e oponente acertou. mudar sumozinhos....
					viewImagemIconeJogador.setImageResource(R.drawable.bola_campo_jogador_left_errou);
					viewImagemIconeOponente.setImageResource(R.drawable.bola_campo_jogador_right_acertou);
				}
				else
				{
					//jogador é o guest e oponente acertou. mudar sumozinhos....
					viewImagemIconeJogador.setImageResource(R.drawable.bola_campo_jogador_right_errou);
					viewImagemIconeOponente.setImageResource(R.drawable.bola_campo_jogador_left_acertou);
				}
				new BounceAnimation(viewImagemIconeOponente).setBounceDistance(20).animate();
				new BounceAnimation(viewImagemFundoOponente).setBounceDistance(20).animate();
				new BounceAnimation(textviewImagemOponente).setBounceDistance(20).animate();
				
				//chamar uma thread pra normalizar as carinhas dos sumos deppois...
				new Timer().schedule(new TimerTask() {
				    @Override
				    public void run() {
				        
				        //If you want to operate UI modifications, you must run ui stuff on UiThread.
				        TelaModoCompeticao.this.runOnUiThread(new Runnable() {
				            @Override
				            public void run() {
				            	if(euEscolhoACategoria == true)
								{
									//jogador é o host
									viewImagemIconeJogador.setImageResource(R.drawable.bola_campo_jogador_left_final_casual);
									viewImagemIconeOponente.setImageResource(R.drawable.bola_campo_jogador_right_final_casual);
								}
								else
								{
									//jogador é o guest
									viewImagemIconeJogador.setImageResource(R.drawable.bola_campo_jogador_right_final_casual);
									viewImagemIconeOponente.setImageResource(R.drawable.bola_campo_jogador_left_final_casual);
								}
				            	
				            }
				        });
				    }
				}, 3000);
			}
			//reproduzir sfx opoenente acetou...
			this.reproduzirSfx("noJogo-oponenteAcertou");
			//o adversario acertou um dos kanjis
			//botaoAnswer1.setEnabled(false);
			botaoAnswer1.setClickable(false);
			botaoAnswer2.setClickable(false);
			botaoAnswer3.setClickable(false);
			botaoAnswer3.setClickable(false);
			/*botaoAnswer1.getBackground().setAlpha(128);
			botaoAnswer2.getBackground().setAlpha(128);
			botaoAnswer3.getBackground().setAlpha(128);
			botaoAnswer4.getBackground().setAlpha(128);*/
			if(this.estahComAnimacaoTegata == false)
			{
				Animation animacaoTransparente = AnimationUtils.loadAnimation(this, R.anim.anim_transparente_botao);
				botaoAnswer1.startAnimation(animacaoTransparente);
				botaoAnswer2.startAnimation(animacaoTransparente);
				botaoAnswer3.startAnimation(animacaoTransparente);
				botaoAnswer4.startAnimation(animacaoTransparente);
			}
			GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
			//atualizar a posicao do sumozinho na tela, pq o adversario te empurrou
			boolean usuarioSeDefendeu = guardaDadosDaPartida.usuarioTemItemIncorporado("teppotree");
			if(usuarioSeDefendeu == false)
			{
				int antigaPosicaoSumoNaTela = guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela();
				String [] mensagemSplitada = mensagem.split(";");
				String oponenteTemChikaraMizu = mensagemSplitada[1];
				if(oponenteTemChikaraMizu != null && oponenteTemChikaraMizu.contains("true"))
				{
					String avisoChikaramizu = getResources().getString(R.string.aviso_ruim_chikaramizu);
					Toast toastAvisoChikaraMizu = Toast.makeText(getApplicationContext(), avisoChikaramizu , Toast.LENGTH_SHORT);
					toastAvisoChikaraMizu.setGravity(Gravity.CENTER, 0, 0);
					toastAvisoChikaraMizu.show();
					guardaDadosDaPartida.setPosicaoSumozinhoDoJogadorNaTela(antigaPosicaoSumoNaTela - 2);
					this.reproduzirSfx("noJogo-levouGolpeChikaramizu");
				}
				else
				{
					guardaDadosDaPartida.setPosicaoSumozinhoDoJogadorNaTela(antigaPosicaoSumoNaTela - 1);
				}
				//e tem a animacao dos sumozinhos para fazer update...
			    atualizarAnimacaoSumosNaArena();
			}
		    Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " atualizou animação dos sumozinhos na tela" );
		    Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " terminou de responder à mensagem ponenteAcertou;" );
		   
		  //de qualquer forma remover chikara mizu do jogador, porque recebeu um golpe
			guardaDadosDaPartida.removerItemIncorporado("chikaramizu");
		    
		    if(usuarioSeDefendeu == true)
		    {
		    	String mensagemBoaSeDefendeu = getResources().getString(R.string.aviso_bom_teppotree2);
		    	Toast toastAvisoSeDefendeu = Toast.makeText(getApplicationContext(), mensagemBoaSeDefendeu , Toast.LENGTH_SHORT);
				toastAvisoSeDefendeu.setGravity(Gravity.CENTER, 0, 0);
				toastAvisoSeDefendeu.show();
		    	this.reproduzirSfx("noJogo-usouTeppotree");
		    	guardaDadosDaPartida.removerItemIncorporado("teppotree");
		    }
		    String mensagemProAdversario = "euDefendi;" + usuarioSeDefendeu + ";";
		    this.mandarMensagemMultiplayer(mensagemProAdversario);
		}
		else if(mensagem.contains("termineiDeCarregarListaDeCategoria;") == true)
		{
			//guset manda pro host que jah terminou de carregar lista de categorias
			this.guestTerminouDeCarregarListaDeCategorias = true;
		}
		else if(mensagem.contains("selecionouCategoria=") == true)
		{
			/*String mensagemSemSelecionouCategoria = mensagem.replaceFirst("selecionouCategoria=", "");
			String [] mensagemSplitada = mensagemSemSelecionouCategoria.split(";");
			while(dataAdapter == null)
			{
				new Timer().schedule(new TimerTask() {
				    @Override
				    public void run() {
				        
				        //If you want to operate UI modifications, you must run ui stuff on UiThread.
				        TelaModoCompeticao.this.runOnUiThread(new Runnable() {
				            @Override
				            public void run() {
				            	
				            }
				        });
				    }
				}, 1000);
			}
			ArrayList<CategoriaDeKanjiParaListviewSelecionavel> categoriasKanji = this.dataAdapter.getCategoriaDeKanjiList();
			while(categoriasKanji == null)
			{
				new Timer().schedule(new TimerTask() {
				    @Override
				    public void run() {
				        
				        //If you want to operate UI modifications, you must run ui stuff on UiThread.
				        TelaModoCompeticao.this.runOnUiThread(new Runnable() {
				            @Override
				            public void run() {
				            	
				            }
				        });
				    }
				}, 1000);	
			}
			for(int i = 0; i < categoriasKanji.size(); i++)
			{
				CategoriaDeKanjiParaListviewSelecionavel umaCategoriaKanji = categoriasKanji.get(i);
				if(umaCategoriaKanji.name.compareTo(mensagemSplitada[0]) == 0)
				{
							
					//eh a categoria que procuramos
					umaCategoriaKanji.selected = Boolean.valueOf(mensagemSplitada[1]);
					ListView listagemCategorias = (ListView) findViewById(R.id.listaCategorias);
					listagemCategorias.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
					listagemCategorias.setItemChecked(i, umaCategoriaKanji.selected);
				}
			}*/
			//Toast.makeText(this, "uma categoria de kanji tem de mudar:" + mensagemSemSelecionouCategoria, Toast.LENGTH_LONG).show();
			
			
		}
		else if(mensagem.contains("terminou selecionar categorias::") == true)
		{
			
			String mensagemTerminouDeSelecionarCategoria = mensagem.replaceFirst("terminou selecionar categorias::", "");
			String [] mensagemSplitada = mensagemTerminouDeSelecionarCategoria.split(";");
			
			LinkedList<String> categoriasDeKanjiSelecionadas = salaAtual.getCategoriasSelecionadas();
		    
		    //o que fazer depois de que o oponente terminou de selecionar categorias?
		    if(categoriasDeKanjiSelecionadas.size() > 0)
		    {
		    	
		        prepararTelaInicialDoJogo(categoriasDeKanjiSelecionadas);
		        int idVategoriaDoKanjiTreinadoInicialmente = Integer.valueOf(mensagemSplitada[0]);
		        
		        String categoriaDoKanjiTreinadoInicialmente = SingletonArmazenaCategoriasDoJogo.getInstance().pegarNomeCategoria(idVategoriaDoKanjiTreinadoInicialmente);
		        String textoKanjiTreinadoInicialmente = mensagemSplitada[1];
		        KanjiTreinar umKanjiAleatorioParaTreinar = GuardaDadosDaPartida.getInstance().encontrarKanjiTreinadoNaPartida(categoriaDoKanjiTreinadoInicialmente, textoKanjiTreinadoInicialmente);
		        
		        
		        LinkedList<String> hiraganasAlternativas = new LinkedList<String>();
		        hiraganasAlternativas.add(mensagemSplitada[2]);
		        hiraganasAlternativas.add(mensagemSplitada[3]);
		        hiraganasAlternativas.add(mensagemSplitada[4]);
		        hiraganasAlternativas.add(mensagemSplitada[5]);
		        
		        iniciarUmaPartida(umKanjiAleatorioParaTreinar, hiraganasAlternativas);
		        
		        
		    }
		    else
		    {
		    	Toast.makeText(getApplicationContext(), getResources().getString(R.string.aviso_nao_selecionou_categorias), Toast.LENGTH_SHORT).show();
		    }
		    
		}
		else if(mensagem.contains("terminou escolher nova partida::") == true)
		{
			Log.i("TelaModoCompeticao", "jogador terminou de receber mensagem terminou escolher nova partida::");
			
			String mensagemTerminouDeSelecionarCategoria = mensagem.replaceFirst("terminou escolher nova partida::", "");
			String [] mensagemSplitada = mensagemTerminouDeSelecionarCategoria.split(";");
			GuardaDadosDaPartida guardaDadosDeUmaPartida = GuardaDadosDaPartida.getInstance();
			int idVategoriaDoKanjiTreinadoInicialmente = Integer.valueOf(mensagemSplitada[0]);
	        
		    String categoriaDoKanjiTreinado = SingletonArmazenaCategoriasDoJogo.getInstance().pegarNomeCategoria(idVategoriaDoKanjiTreinadoInicialmente);
		    String textoKanjiTreinado = mensagemSplitada[1];
		    KanjiTreinar umKanjiAleatorioParaTreinar = guardaDadosDeUmaPartida.encontrarKanjiTreinadoNaPartida(categoriaDoKanjiTreinado, textoKanjiTreinado);
		    
		        
		    LinkedList<String> hiraganasAlternativas = new LinkedList<String>();
		    hiraganasAlternativas.add(mensagemSplitada[2]);
		    hiraganasAlternativas.add(mensagemSplitada[3]);
		    hiraganasAlternativas.add(mensagemSplitada[4]);
		    hiraganasAlternativas.add(mensagemSplitada[5]);
		    
		    
		    iniciarUmaPartida(umKanjiAleatorioParaTreinar, hiraganasAlternativas);
		        
		    Log.i("TelaModoCompeticao", "jogador terminou de se preparar para nova partida::");    
		   
		}
		else if(mensagem.contains("oponenteganhou;"))
		{
			Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " recebeu mensagem oponenteGanhou;" );
			//o jogo acabou e o oponente ganhou... a menos que tenha teppo tree
			GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
			
			boolean usuarioSeDefendeu = guardaDadosDaPartida.usuarioTemItemIncorporado("teppotree");
			if(usuarioSeDefendeu == false)
			{
				guardaDadosDaPartida.setPosicaoSumozinhoDoJogadorNaTela(-6);
				Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " não se defendeu e morreu;" );
			}
		   
		    if(usuarioSeDefendeu == true)
		    {
		    	Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " se defendeu e não morreu;" );
		    	String mensagemBoaSeDefendeu = getResources().getString(R.string.aviso_bom_teppotree2);
		    	Toast toastAvisoSeDefendeu = Toast.makeText(getApplicationContext(), mensagemBoaSeDefendeu , Toast.LENGTH_SHORT);
				toastAvisoSeDefendeu.setGravity(Gravity.CENTER, 0, 0);
				toastAvisoSeDefendeu.show();
		    	this.reproduzirSfx("noJogo-usouTeppotree");
		    	guardaDadosDaPartida.removerItemIncorporado("teppotree");
		    }
		    String mensagemProAdversario = "euDefendiDoOponenteGanhou;" + usuarioSeDefendeu + ";";
		    this.mandarMensagemMultiplayer(mensagemProAdversario);
		    if(usuarioSeDefendeu == false)
		    {
		    	Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " está terminando jogo..." );
		    	this.terminarJogoMultiplayer();
		    }
		}
		else if(mensagem.contains("terminouJogo;"))
		{
			ProgressDialog barraProgressoFinalTerminouJogo =  ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.aviso_tempo_acaboou), getResources().getString(R.string.por_favor_aguarde));
			TerminaPartidaTask taskTerminaPartida = new TerminaPartidaTask(barraProgressoFinalTerminouJogo, this);
			taskTerminaPartida.execute("");
		}
		else if(mensagem.contains("oponente falou no chat;"))
		{
			String mensagemAdicionarAoChat = mensagem.replaceFirst("oponente falou no chat;", "");
			this.adicionarMensagemNoChat(mensagemAdicionarAoChat, false, this.nomeAdversario, true);
			if(this.popupChatEstahAberto == false)
			{
				this.mostrarPopupChat();
			}
		}
		else if(mensagem.contains("usouShiko;"))
		{
			this.reproduzirSfx("noJogo-usouShiko");
			GuardaDadosDaPartida.getInstance().setShikoFoiUsado(true);
			Button botaoAnswer1 = (Button)findViewById(R.id.answer1);
			Button botaoAnswer2 = (Button)findViewById(R.id.answer2);
			Button botaoAnswer3 = (Button)findViewById(R.id.answer3);
			Button botaoAnswer4 = (Button)findViewById(R.id.answer4);
			botaoAnswer1.setClickable(false);
			botaoAnswer2.setClickable(false);
			botaoAnswer3.setClickable(false);
			botaoAnswer4.setClickable(false);
			final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
			animRotate.setRepeatCount(0);
			botaoAnswer1.startAnimation(animRotate);
			botaoAnswer2.startAnimation(animRotate);
			botaoAnswer3.startAnimation(animRotate);
			botaoAnswer4.startAnimation(animRotate);
		}
		else if(mensagem.contains("usouTegata;"))
		{
			this.estahComAnimacaoTegata = true;
			
			this.reproduzirSfx("noJogo-usouTegata");
			String avisoTegata = getResources().getString(R.string.aviso_tegata_ruim);
			Toast toastAvisoTegata = Toast.makeText(getApplicationContext(), avisoTegata , Toast.LENGTH_SHORT);
			toastAvisoTegata.setGravity(Gravity.CENTER, 0, 0);
			toastAvisoTegata.show();
			this.animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
			
			Button botaoAnswer1 = (Button)findViewById(R.id.answer1);
			Button botaoAnswer2 = (Button)findViewById(R.id.answer2);
			Button botaoAnswer3 = (Button)findViewById(R.id.answer3);
			Button botaoAnswer4 = (Button)findViewById(R.id.answer4);
			TextView kanjiAcertar = (TextView) findViewById(R.id.kanji_acertar);
			botaoAnswer1.startAnimation(animAlpha);
			botaoAnswer2.startAnimation(animAlpha);
			botaoAnswer3.startAnimation(animAlpha);
			botaoAnswer4.startAnimation(animAlpha);
			kanjiAcertar.startAnimation(animAlpha);
			
			EmbaralharAlternativasTask embaralhaAlternativasTask = new EmbaralharAlternativasTask(this);
			embaralhaAlternativasTask.execute("");
			
			
		}
		else if(mensagem.contains("euDefendi;"))
		{
			String [] mensagemSplitada = mensagem.split(";");
			String stringJogadorDefendeu = mensagemSplitada[1];
			boolean jogadorDefendeu = false;
			if(stringJogadorDefendeu.compareTo("true") == 0)
			{
				jogadorDefendeu = true;
				this.reproduzirSfx("noJogo-jogadorDefendeu");
				String avisoJogadorDefendeu = getResources().getString(R.string.aviso_ruim_teppotree);
				Toast toastAvisoSeDefendeu = Toast.makeText(getApplicationContext(), avisoJogadorDefendeu , Toast.LENGTH_SHORT);
				toastAvisoSeDefendeu.setGravity(Gravity.CENTER, 0, 0);
				toastAvisoSeDefendeu.show();
			}
			this.aposDizerProOponenteQueAcertouKanji(jogadorDefendeu);
		}
		else if(mensagem.contains("euDefendiDoOponenteGanhou;"))
		{
			String [] mensagemSplitada = mensagem.split(";");
			String stringJogadorDefendeu = mensagemSplitada[1];
			if(stringJogadorDefendeu.compareTo("true") == 0)
			{
				Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " se defendeu do ultimo golpe;" );
				this.reproduzirSfx("noJogo-jogadorDefendeu");
				String avisoJogadorDefendeu = getResources().getString(R.string.aviso_ruim_teppotree);
				Toast toastAvisoSeDefendeu = Toast.makeText(getApplicationContext(), avisoJogadorDefendeu , Toast.LENGTH_SHORT);
				toastAvisoSeDefendeu.setGravity(Gravity.CENTER, 0, 0);
				toastAvisoSeDefendeu.show();
			}
			else
			{
				//jogador ganhou o jogo. muda a tela para a tela de final de jogo...
				this.reproduzirSfx("noJogo-jogadorGanhou");
				GuardaDadosDaPartida.getInstance().setPosicaoSumozinhoDoJogadorNaTela(6);
				Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " está terminando o jogo..." );
				this.terminarJogoMultiplayer();
			}
		}
		else if(mensagem.contains("username=") == true)
		{
			//vai começar uma nova partida, seta o boolean de jogoJahTerminou pra false...
			this.jogoJahTerminou = false;
			this.partidaComecou = true;
			Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " recebeu username do oponente..." );
			this.nomeAdversario = mensagem.replace("username=", "");
			if(this.euEscolhoACategoria == true)
			{
				//vc é o host, começa uma partida com vc
				LinkedList<String> categoriasSelecionadasNaSala = 
						salaAtual.getCategoriasSelecionadas();
				comecarNovaPartidaCasual(categoriasSelecionadasNaSala);
			}
		}

	}



	/*
	* UI SECTION. Methods that implement the game's UI.
	*/

	// This array lists everything that's clickable, so we can install click
	// event handlers.
	final static int[] CLICKABLES = {
	    R.id.botao_jogar_competicao,R.id.botao_ver_ranking
	};

	// This array lists all the individual screens our game has.
	final static int[] SCREENS = {
	    R.id.screen_game, R.id.screen_main, R.id.tela_buscar_salas,
	    R.id.screen_wait,R.id.decidindoQuemEscolheACategoria, R.id.tela_escolha_categoria, R.id.screen_final_partida, R.id.tela_aguardando_oponentes
	};
	int mCurScreen = -1;

	public void switchToScreen(int screenId) {
	// make the requested screen visible; hide all others.
	for (int id : SCREENS) {
	    findViewById(id).setVisibility(screenId == id ? View.VISIBLE : View.GONE);
	}
	mCurScreen = screenId;

	// should we show the invitation popup?
	boolean showInvPopup;
	if (mIncomingInvitationId == null) {
	    // no invitation, so no popup
	    showInvPopup = false;
	} else if (mMultiplayer) {
	    // if in multiplayer, only show invitation on main screen
	    showInvPopup = (mCurScreen == R.id.screen_main);
	} else {
	    // single-player: show on main screen and gameplay screen
	    showInvPopup = (mCurScreen == R.id.screen_main || mCurScreen == R.id.screen_game);
	}
	findViewById(R.id.invitation_popup).setVisibility(showInvPopup ? View.VISIBLE : View.GONE);
	}

	void switchToMainScreen() {
	this.popupChatEstahAberto = false;
	if(this.popupDoChat != null)
	{
		this.popupDoChat.dismiss();
	}
	switchToScreen(R.id.screen_main);
	String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
	String fontpathBrPraTitulo = "fonts/Wonton.ttf";
	Typeface tfBrPraTitulo = Typeface.createFromAsset(getAssets(), fontpathBrPraTitulo);
	TextView textoTituloModoCasual = (TextView) findViewById(R.id.tituloModoCasualTelaPrincipal);
	textoTituloModoCasual.setTypeface(tfBrPraTitulo);
	

	}


	// formats a score as a three-digit number
	String formatScore(int i) {
	if (i < 0)
	    i = 0;
	String s = String.valueOf(i);
	return s.length() == 1 ? "00" + s : s.length() == 2 ? "0" + s : s;
	}

	// updates the screen with the scores from our peers


	/*
	* MISC SECTION. Miscellaneous methods.
	*/

	/**
	* Checks that the developer (that's you!) read the instructions. IMPORTANT:
	* a method like this SHOULD NOT EXIST in your production app! It merely
	* exists here to check that anyone running THIS PARTICULAR SAMPLE did what
	* they were supposed to in order for the sample to work.
	*/
	boolean verifyPlaceholderIdsReplaced() {
	final boolean CHECK_PKGNAME = true; // set to false to disable check
	                                    // (not recommended!)

	// Did the developer forget to change the package name?
	if (CHECK_PKGNAME && getPackageName().startsWith("com.google.example.")) {
	    Log.e(TAG, "*** Sample setup problem: " +
	        "package name cannot be com.google.example.*. Use your own " +
	        "package name.");
	    return false;
	}

	// Did the developer forget to replace a placeholder ID?
	int res_ids[] = new int[] {
	        R.string.app_id
	};
	for (int i : res_ids) {
	    if (getString(i).equalsIgnoreCase("ReplaceMe")) {
	        Log.e(TAG, "*** Sample setup problem: You must replace all " +
	            "placeholder IDs in the ids.xml file by your project's IDs.");
	        return false;
	    }
	}
	return true;
	}

	// Sets the flag to keep this screen on. It's recommended to do that during
	// the
	// handshake when setting up a game, because if the screen turns off, the
	// game will be
	// cancelled.
	public void keepScreenOn() {
	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	// Clears the flag that keeps the screen on.
	void stopKeepingScreenOn() {
	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	private int progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta;
	private void jogadorClicouNaAlternativa(int idDoBotaoQueUsuarioClicou)
	{
		String nomeUsuario = this.nomeUsuario;
		Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " clicou na alternativa" );
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
					Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " acertou" );
					guardaDadosDaPartida.adicionarKanjiAcertadoNaPartida(ultimoKanjiTreinado);
					boolean jogadorTemChikaramizu = guardaDadosDaPartida.usuarioTemItemIncorporado("chikaramizu");
					if((jogadorTemChikaramizu == false && guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela() < 5) ||
							(jogadorTemChikaramizu == true && guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela() < 4))
					{
						if(this.viewImagemFundoJogador != null && this.textviewImagemJogador != null && this.viewImagemIconeJogador != null)
						{
							if(this.euEscolhoACategoria == true)
							{
								//jogador é o host e ele acertou. mudar sumozinhos....
								viewImagemIconeJogador.setImageResource(R.drawable.bola_campo_jogador_left_acertou);
								viewImagemIconeOponente.setImageResource(R.drawable.bola_campo_jogador_right_errou);
							}
							else
							{
								//jogador é o guest e ele acertou. mudar sumozinhos....
								viewImagemIconeJogador.setImageResource(R.drawable.bola_campo_jogador_right_acertou);
								viewImagemIconeOponente.setImageResource(R.drawable.bola_campo_jogador_left_errou);
							}
							new BounceAnimation(viewImagemIconeJogador).setBounceDistance(20).animate();
							new BounceAnimation(viewImagemFundoJogador).setBounceDistance(20).animate();
							new BounceAnimation(textviewImagemJogador).setBounceDistance(20).animate();
							
							//chamar uma thread pra normalizar as carinhas dos sumos deppois...
							new Timer().schedule(new TimerTask() {
							    @Override
							    public void run() {
							        
							        //If you want to operate UI modifications, you must run ui stuff on UiThread.
							        TelaModoCompeticao.this.runOnUiThread(new Runnable() {
							            @Override
							            public void run() {
							            	if(euEscolhoACategoria == true)
											{
												//jogador é o host e
												viewImagemIconeJogador.setImageResource(R.drawable.bola_campo_jogador_left_final_casual);
												viewImagemIconeOponente.setImageResource(R.drawable.bola_campo_jogador_right_final_casual);
											}
											else
											{
												//jogador é o guest
												viewImagemIconeJogador.setImageResource(R.drawable.bola_campo_jogador_right_final_casual);
												viewImagemIconeOponente.setImageResource(R.drawable.bola_campo_jogador_left_final_casual);
											}
							            	
							            }
							        });
							    }
							}, 3000);
							
						}
						this.reproduzirSfx("noJogo-jogadorAcertouAlternativa");
						
						Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " mandou mensagem de acertou para oponente" );
						Button botaoAnswer1 = (Button)findViewById(R.id.answer1);
						Button botaoAnswer2 = (Button)findViewById(R.id.answer2);
						Button botaoAnswer3 = (Button)findViewById(R.id.answer3);
						Button botaoAnswer4 = (Button)findViewById(R.id.answer4);
						//botaoAnswer1.setEnabled(false);
						botaoAnswer1.setClickable(false);
						botaoAnswer2.setClickable(false);
						botaoAnswer3.setClickable(false);
						botaoAnswer4.setClickable(false);
						/*botaoAnswer1.getBackground().setAlpha(128);
						botaoAnswer2.getBackground().setAlpha(128);
						botaoAnswer3.getBackground().setAlpha(128);
						botaoAnswer4.getBackground().setAlpha(128);*/
						if(this.estahComAnimacaoTegata == false)
						{
							Animation animacaoTransparente = AnimationUtils.loadAnimation(this, R.anim.anim_transparente_botao);
							botaoAnswer1.startAnimation(animacaoTransparente);
							botaoAnswer2.startAnimation(animacaoTransparente);
							botaoAnswer3.startAnimation(animacaoTransparente);
							botaoAnswer4.startAnimation(animacaoTransparente);
						}
						
						Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " ocultou botão após acertar" );
						//manda Mensagem Pro Oponente... vamos precisar ver se o usuario tem itensIncorporados
						String mensagemParaOponente = "oponenteacertou;";
						mensagemParaOponente = mensagemParaOponente + "chikaramizu=" + jogadorTemChikaramizu + ";";
						
						this.mandarMensagemMultiplayer(mensagemParaOponente);
						
					}
					else
					{
						//jogador ganhou a partida, a menos que adversario tenha usado teppo tree 
						Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " mandou mensagem para oponente oponenteganhou;" );
						this.mandarMensagemMultiplayer("oponenteganhou;");
					}
					
					
				}
				else
				{
					//usuario errou uma alternativa
					this.reproduzirSfx("noJogo-jogadorErrouAlternativa");
					guardaDadosDaPartida.adicionarKanjiErradoNaPartida(ultimoKanjiTreinado);
					Toast.makeText(this, getResources().getString(R.string.errou_traducao_kanji) , Toast.LENGTH_SHORT).show();
					
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
					if(this.estahComAnimacaoTegata == false)
					{
						Animation animacaoTransparente = AnimationUtils.loadAnimation(this, R.anim.anim_transparente_botao);
						//botaoAnswer1.startAnimation(animacaoTransparente);
						//botaoAnswer2.startAnimation(animacaoTransparente);
						//botaoAnswer3.startAnimation(animacaoTransparente);
						//botaoAnswer4.startAnimation(animacaoTransparente);
						final CircleProgress circleprogress_answer1 = (CircleProgress) findViewById(R.id.circleprogress_resposta1);
						circleprogress_answer1.setVisibility(View.VISIBLE);
						circleprogress_answer1.setMax(100);
						circleprogress_answer1.setProgress(0); 
						final CircleProgress circleprogress_answer2 = (CircleProgress) findViewById(R.id.circleprogress_resposta2);
						circleprogress_answer2.setVisibility(View.VISIBLE);
						circleprogress_answer2.setMax(100);
						circleprogress_answer2.setProgress(0); 
						final CircleProgress circleprogress_answer3 = (CircleProgress) findViewById(R.id.circleprogress_resposta3);
						circleprogress_answer3.setVisibility(View.VISIBLE);
						circleprogress_answer3.setMax(100);
						circleprogress_answer3.setProgress(0);
						final CircleProgress circleprogress_answer4 = (CircleProgress) findViewById(R.id.circleprogress_resposta4);
						circleprogress_answer4.setVisibility(View.VISIBLE);
						circleprogress_answer4.setMax(100);
						circleprogress_answer4.setProgress(0); 
						//Depois de tudo isso, inicie uma thread para ficar atualizando os circleprogress(No meu exemplo, altera o percentual em 10% a mais da completude a cada 500 milisegundos porque quando chegar em 100%, vao ter passado 5 segundos(5000 milisegundos)):

						this.progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta = 0;
						 final Handler handler = new Handler();
						 Runnable runnableThread = new Runnable() {
					         @Override
					         public void run() 
					         {
					             
					            	TelaModoCompeticao.this.runOnUiThread(new Runnable() 
					 		        {
					 		            @Override
					 		            public void run() 
					 		            {
					 		            		progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta = progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta + 10;
					 		            		circleprogress_answer1.setProgress(progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta);
					 		            		circleprogress_answer2.setProgress(progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta);
					 		            		circleprogress_answer3.setProgress(progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta);
					 		            		circleprogress_answer4.setProgress(progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta);
					 		            		
					 		            		
					 	 		            	if(progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta == 100)
					 	 		            	{
					 	 		            		//acabou a espera!
					 	 		            		botaoAnswer1.setClickable(true);
									            	botaoAnswer2.setClickable(true);
									            	botaoAnswer3.setClickable(true);
									            	botaoAnswer4.setClickable(true);
									            	CircleProgress circleprogress_answer1 = (CircleProgress) findViewById(R.id.circleprogress_resposta1);
									       		 	circleprogress_answer1.setVisibility(View.GONE);
									       		 	CircleProgress circleprogress_answer2 = (CircleProgress) findViewById(R.id.circleprogress_resposta2);
									       		 	circleprogress_answer2.setVisibility(View.GONE);
									       		 	CircleProgress circleprogress_answer3 = (CircleProgress) findViewById(R.id.circleprogress_resposta3);
									       		 	circleprogress_answer3.setVisibility(View.GONE);
									       		 	CircleProgress circleprogress_answer4 = (CircleProgress) findViewById(R.id.circleprogress_resposta4);
									       		 	circleprogress_answer4.setVisibility(View.GONE);
					 	 		            	}
					 		            }
					 		        });
					             
					            	
					            	if(progressoTodosCircleProgressEsperaDoUsuarioAoErrarResposta < 100)
					            	{
					            		//ainda iremos realizar a tarefa da thread mais uma vez!!!
					            		 handler.postDelayed(this, 300); //faca as contas: 5000(5 segundos) -- 100%
											 //				   X                -- 10%
											 // no cruz-credo, isso dah 500. de 500 em 500 milisegundos, eu aumento em 10% a porcentagem de conclusao ateh chegar em 5000, que eh 5 segundos
					            	}
					            	 								
					         	}
					         };
					     
					     handler.postDelayed(runnableThread, 300); //faltou iniciar a thread atraves do handler!!!

					}
					
					//Toast.makeText(this, getResources().getString(R.string.errou_traducao_kanji) , Toast.LENGTH_SHORT).show();
					/*new Timer().schedule(new TimerTask() {
					    @Override
					    public void run() {
					        
					        //If you want to operate UI modifications, you must run ui stuff on UiThread.
					        TelaModoCasual.this.runOnUiThread(new Runnable() {
					            @Override
					            public void run() {
					            	//botaoAnswer1.setEnabled(true);
					            	botaoAnswer1.setClickable(true);
					            	botaoAnswer2.setClickable(true);
					            	botaoAnswer3.setClickable(true);
					            	botaoAnswer4.setClickable(true);
					            	CircleProgress circleprogress_answer1 = (CircleProgress) findViewById(R.id.circleprogress_answer1);
					       		 	circleprogress_answer1.setVisibility(View.GONE);
					            }
					        });
					    }
					}, 3000);*/
				}
			}
		}
		
		
		
	}

	private ListView listViewMensagensChat;
	private ArrayList<String> mensagensChat;
	private ArrayList<String> posicoesBaloesMensagensChat;//os valores deles são "direita" ou "esquerda"
	public Dialog popupDoChat;
	private boolean popupChatEstahAberto;
	public void terminarJogoMultiplayer()
	{
		Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " está chamando método terminarJogoMultiplayer" );
		if(jogoJahTerminou == false)
		{
			Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " está terminando o jogo pq jogoJahTerminou == false" );
			if(this.timerFimDeJogo != null)
			{
				timerFimDeJogo.cancel();
			}
			this.mudarMusicaDeFundo(R.raw.chineseinstrumentalmusic);
			this.switchToScreen(R.id.screen_final_partida);
			findViewById(R.id.textView2Final).setVisibility(View.VISIBLE);
			findViewById(R.id.nome_jogador_host_final).setVisibility(View.VISIBLE);
			findViewById(R.id.botao_menu_principal).setVisibility(View.VISIBLE);
			findViewById(R.id.nome_jogador_guest_final).setVisibility(View.VISIBLE);
			findViewById(R.id.quem_ganhou_final).setVisibility(View.VISIBLE);
			
			//alterar fonte...
			TextView quemGanhouFinal = (TextView) findViewById(R.id.quem_ganhou_final);
			String fontpath = "fonts/Wonton.ttf";
		    Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
		    quemGanhouFinal.setTypeface(tf);
		    TextView versus = (TextView) findViewById(R.id.textView2Final);
		    versus.setTypeface(tf);
		    TextView textviewHost = (TextView) findViewById(R.id.nome_jogador_host_final);
		    textviewHost.setTypeface(tf);
		    TextView textviewGuest = (TextView) findViewById(R.id.nome_jogador_guest_final);
		    textviewGuest.setTypeface(tf);
			
		  //fazer o popup chat...
			
			this.popupDoChat = new Dialog(TelaModoCompeticao.this);
			this.popupDoChat.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// Include dialog.xml file
			this.popupDoChat.setContentView(R.layout.popup_chat);
			
			Drawable d = new ColorDrawable(Color.parseColor("#800000c0"));
			this.popupDoChat.getWindow().setBackgroundDrawable(d);
			
			this.popupDoChat.hide();
			this.popupChatEstahAberto = false;
			Button botaoFecharChat = (Button) this.popupDoChat.findViewById(R.id.botao_fechar_chat);
			botaoFecharChat.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					popupDoChat.hide();
					popupChatEstahAberto = false;
					
				}
			});
			
			final Button botaoEnviarTextoChat = (Button) this.popupDoChat.findViewById(R.id.sendBtn);
			botaoEnviarTextoChat.setOnClickListener(this);
			final EditText textoChat = (EditText) this.popupDoChat.findViewById(R.id.chatET);
			
			//TextWatcher
	        TextWatcher textWatcher = new TextWatcher() {
	            @Override
	            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
	           {

	            }

	            @Override
	            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
	                checkFieldsForEmptyValues(textoChat, botaoEnviarTextoChat);
	            }

	            @Override
	            public void afterTextChanged(Editable editable) {
	            }
	        };
	        
	        textoChat.addTextChangedListener(textWatcher);
			this.listViewMensagensChat = (ListView) this.popupDoChat.findViewById(R.id.mensagens_chat);
			
			this.mensagensChat = new ArrayList<String>();
			this.posicoesBaloesMensagensChat = new ArrayList<String>();
			
			TextView textviewNomeJogadorHost = (TextView) findViewById(R.id.nome_jogador_host_final);
			TextView textviewNomeJogadorGuest = (TextView) findViewById(R.id.nome_jogador_guest_final);
			
			String nomeJogador = this.nomeUsuario;
		 	String nomeAdversario = this.nomeAdversario;
		 	String nomeJogadorEncurtado = nomeJogador;
		 	if(nomeJogador.length() > 13)
		 	{
		 		nomeJogadorEncurtado = nomeJogadorEncurtado.substring(0, 12);
		 	}
		 	String nomeAdversarioEncurtado = nomeAdversario;
		 	if(nomeAdversario.length() > 13)
		 	{
		 		nomeAdversarioEncurtado = nomeAdversarioEncurtado.substring(0, 12);
		 	}
		 	if(this.euEscolhoACategoria == true)
		 	{
		 		textviewNomeJogadorHost.setText(nomeJogadorEncurtado);
				textviewNomeJogadorGuest.setText(nomeAdversarioEncurtado);
		 	}
		 	else
		 	{
		 		textviewNomeJogadorHost.setText(nomeAdversarioEncurtado);
				textviewNomeJogadorGuest.setText(nomeJogadorEncurtado);
		 	}
		 	
		 	
		 	int posicaoAntigaSumozinho = GuardaDadosDaPartida.getInstance().getPosicaoSumozinhoDoJogadorNaTela();
		 	String nomeUsuarioGanhou = "";
			RelativeLayout backgroundFinalPartida = (RelativeLayout) findViewById(R.id.screen_final_partida);
			
			if(posicaoAntigaSumozinho > 0)
			{
				//quem ganhou foi o usuario , nao o oponente!
				nomeUsuarioGanhou = nomeJogador;
				if(this.euEscolhoACategoria == true)
				{
					backgroundFinalPartida.setBackgroundResource(R.drawable.background_tela_fim_host_ganha);
				}
				else
				{
					backgroundFinalPartida.setBackgroundResource(R.drawable.background_tela_fim_guest_ganha);
				}
			}
			else if(posicaoAntigaSumozinho < 0)
			{
				//quem ganhou foi o oponente, entao precisamos saber o nome dele
				 
				nomeUsuarioGanhou = nomeAdversario;
				
				if(this.euEscolhoACategoria == true)
				{
					backgroundFinalPartida.setBackgroundResource(R.drawable.background_tela_fim_guest_ganha);
				}
				else
				{
					backgroundFinalPartida.setBackgroundResource(R.drawable.background_tela_fim_host_ganha);
				}
				 	    
			}
			else
			{
				nomeUsuarioGanhou = "empatou";
				
				backgroundFinalPartida.setBackgroundResource(R.drawable.background_tela_fim_empatou);
			}
		 	
			TextView textViewQuemGanhouFinal = (TextView)findViewById(R.id.quem_ganhou_final);
			String textoQuemGanhouFinal = nomeUsuarioGanhou +  " " + getResources().getString(R.string.texto_venceu_a_partida);
			textViewQuemGanhouFinal.setText(textoQuemGanhouFinal);
			if(nomeUsuarioGanhou.compareTo("empatou") == 0)
			{
				textViewQuemGanhouFinal.setText(getResources().getString(R.string.empatou));
			}
			
			
			
			//guardar esse historico no armazenamento BD
			this.enviarDadosDaPartidaParaOLogDoUsuarioNoBancoDeDados();
			
			this.jogoJahTerminou = true;
			Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " jogoJahTerminou = true" );
		}
		
	}

	private void aposDizerProOponenteQueAcertouKanji(boolean adversarioDefendeuDoGolpe) {
		String nomeUsuario = this.nomeUsuario;
		GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		
		//e muda a posicao do sumozinho do jogador...
		if(adversarioDefendeuDoGolpe != true )
		{
			int posicaoAntigaSumozinho = guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela();
			boolean usuarioTemChikaramizu = guardaDadosDaPartida.usuarioTemItemIncorporado("chikaramizu");
			if(usuarioTemChikaramizu == false)
			{
				guardaDadosDaPartida.setPosicaoSumozinhoDoJogadorNaTela(posicaoAntigaSumozinho + 1);
			}
			else
			{
				guardaDadosDaPartida.setPosicaoSumozinhoDoJogadorNaTela(posicaoAntigaSumozinho + 2);
				guardaDadosDaPartida.removerItemIncorporado("chikaramizu");
				
			}
		}
		//de qualquer forma remover chikara mizu do jogador porque o efeito se perdeu.
		guardaDadosDaPartida.removerItemIncorporado("chikaramizu");

		//primeiro, atualizar os pontos que ganhou na ultima partida
		
		KanjiTreinar ultimoKanjiTreinado = guardaDadosDaPartida.getKanjisTreinadosNaPartida().getLast();
		//usuario acertou o hiragana do kanji. ganha pontos
		int dificuldadeDoKanjiAcertado = ultimoKanjiTreinado.getDificuldadeDoKanji();
		int pontuacaoParaAdicionarAoJogador = 50 * dificuldadeDoKanjiAcertado;
		guardaDadosDaPartida.adicionarPontosPlacarDoJogadorNaPartida(pontuacaoParaAdicionarAoJogador);
		TextView textviewScoreDoJogador = (TextView)findViewById(R.id.score_partida);
		//o placar atual tem 5 dígitos? se não, tem de adicionar uns zeros ao lado...
		int quantosDigitosTemPontuacaoAtual = String.valueOf(guardaDadosDaPartida.getPlacarDoJogadorNaPartida()).length();
		String novoTextoScore = "Score:";
		for(int i = quantosDigitosTemPontuacaoAtual; i < 5; i++)
		{
			novoTextoScore = novoTextoScore + "0";
		}
		novoTextoScore = novoTextoScore + guardaDadosDaPartida.getPlacarDoJogadorNaPartida();
		textviewScoreDoJogador.setText(novoTextoScore);
		
		
		
		
		//e tem a animacao dos sumozinhos para fazer update...
		atualizarAnimacaoSumosNaArena();
		Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " atualizou animacao sumozinhos na arena" );
		this.prepararNovaPartida(false);
		Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " terminou preparação para nova partida" );
	}

	private void prepararNovaPartida(boolean perdeuPartidaAnterior)
	{
		
		
		if(perdeuPartidaAnterior == false)
		{
			
			GuardaDadosDaPartida guardaDadosDeUmaPartida = GuardaDadosDaPartida.getInstance();
			KanjiTreinar umKanjiAleatorioParaTreinar = guardaDadosDeUmaPartida.getUmKanjiAleatorioParaTreinar();
	       
	        
	        LinkedList<String> hiraganasAlternativas = new LinkedList<String>();
	        hiraganasAlternativas.add(umKanjiAleatorioParaTreinar.getHiraganaDoKanji());
	        LinkedList<String> possiveisCiladasHiragana = umKanjiAleatorioParaTreinar.getPossiveisCiladasKanji();
	        for(int j = 0; j < possiveisCiladasHiragana.size(); j++)
	        {
	        	String umaPossivelCilada = possiveisCiladasHiragana.get(j);
	        	hiraganasAlternativas.add(umaPossivelCilada);
	        }
	        LinkedList<String> outrasCiladasMesmaCategoria = guardaDadosDeUmaPartida.getHiraganasAleatoriosSimilares(umKanjiAleatorioParaTreinar);
	        for(int k = 0; k < outrasCiladasMesmaCategoria.size(); k++)
	        {
	        	String possivelCiladaMesmaCategoria = outrasCiladasMesmaCategoria.get(k);
	        	hiraganasAlternativas.add(possivelCiladaMesmaCategoria);
	        }
	        //vamos embaralhar e associar aos botoes
	        Collections.shuffle(hiraganasAlternativas);
	        Collections.shuffle(hiraganasAlternativas);
	        
	        int idCategoriaKanjiAleatorioPraTreinar = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdDaCategoria(umKanjiAleatorioParaTreinar.getCategoriaAssociada());
	        String mensagemParaOponente = "terminou escolher nova partida::" + idCategoriaKanjiAleatorioPraTreinar + ";" +
					umKanjiAleatorioParaTreinar.getKanji() + ";" + hiraganasAlternativas.get(0) + ";" +hiraganasAlternativas.get(1) + ";" +
					hiraganasAlternativas.get(2) + ";" + hiraganasAlternativas.get(3);
	        
	        this.mandarMensagemMultiplayer(mensagemParaOponente);
	        
	        this.iniciarUmaPartida(umKanjiAleatorioParaTreinar, hiraganasAlternativas);
		}
		
		
	}



	private String getNomeImagemSumozinho(int posicaoDoSumozinho)
	{
		boolean usuarioEhOHost = false;
		if(this.euEscolhoACategoria == true)
		{
			usuarioEhOHost = true;
		}
		String stringId = "sumo_";
		if(posicaoDoSumozinho > 0 && usuarioEhOHost == true)
		{
			stringId = stringId + posicaoDoSumozinho + "_menos" + posicaoDoSumozinho; 
		}
		else if(posicaoDoSumozinho < 0 && usuarioEhOHost == true)
		{
			int pontuacaoSumozinhoSemNegativo = posicaoDoSumozinho * -1;
			stringId = stringId + "menos" + pontuacaoSumozinhoSemNegativo + "_" + pontuacaoSumozinhoSemNegativo;
		}
		else if(posicaoDoSumozinho > 0 && usuarioEhOHost == false)
		{
			stringId = stringId + "menos" + posicaoDoSumozinho + "_" + posicaoDoSumozinho;
		}
		else if(posicaoDoSumozinho < 0 && usuarioEhOHost == false)
		{
			int pontuacaoSumozinhoSemNegativo = posicaoDoSumozinho * -1;
			stringId = stringId  + pontuacaoSumozinhoSemNegativo + "_menos" + pontuacaoSumozinhoSemNegativo;
		}
		else
		{
			//pontuacao eh zero
			stringId = stringId + "0_0";
		}
		return stringId;
	}




	private void decidirCategoria()
	{
		this.euEscolhoACategoria = true;
		
		solicitarPorKanjisPraTreino();
		
		switchToScreen(R.id.tela_escolha_categoria);
		String fontpath = "fonts/Wonton.ttf";
	    Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
	    TextView tituloEscolhaCategorias = (TextView) findViewById(R.id.textoTituloEscolhaCategorias);
	    tituloEscolhaCategorias.setTypeface(tf);
	}

	/*NOVO DA ACTIVITY REFERENTE A SELECIONAR CATEGORIAS */
	private MyCustomAdapter dataAdapter = null;
	private ProgressDialog loadingKanjisDoBd;

	public void solicitarPorKanjisPraTreino() {
		this.loadingKanjisDoBd = new ProgressDialog(getApplicationContext());
		this.loadingKanjisDoBd = ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.carregando_kanjis_remotamente), getResources().getString(R.string.por_favor_aguarde));
		SolicitaKanjisParaTreinoTask solicitaKanjisPraTreino = new SolicitaKanjisParaTreinoTask(this.loadingKanjisDoBd, this, this);
		solicitaKanjisPraTreino.execute("");
		
		 
	}
	  
	public void mostrarListaComKanjisAposCarregar() {
		 LinkedList<String> categorias = 
				  ArmazenaKanjisPorCategoria.pegarInstancia().getCategoriasDeKanjiArmazenadas("5");
		for(int p = 0; p < categorias.size(); p++)
		{
			String umaCategoria = categorias.get(p);
			if(umaCategoria.compareToIgnoreCase("Números") == 0)
			{
				categorias.remove(p);
				categorias.addLast(umaCategoria);
			}
		}
		int tamanhoLista1 = 4;
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
				String categoriaEscritaEmKanji = RetornaNomeCategoriaEscritaEmKanji.retornarNomeCategoriaEscritaEmKanji(umaCategoria);
				String textoDaCategoria = categoriaEscritaEmKanji;
				//String categoriaEscritaEmKanji = RetornaNomeCategoriaEscritaEmKanji.retornarNomeCategoriaEscritaEmKanji(umaCategoria);
				textoDaCategoria = textoDaCategoria + "\n" + umaCategoria + " (" + String.valueOf(quantasPalavrasTemACategoria) + ")";
				arrayCategoriasParaListview[iteradorCategorias1] = textoDaCategoria;
				arrayCategorias[iteradorCategorias1] = umaCategoria;
				iteradorCategorias1 = iteradorCategorias1 + 1;
			}
			else
			{
				int quantasPalavrasTemACategoria = 
						ArmazenaKanjisPorCategoria.pegarInstancia().quantasPalavrasTemACategoria(umaCategoria);
				String categoriaEscritaEmKanji = RetornaNomeCategoriaEscritaEmKanji.retornarNomeCategoriaEscritaEmKanji(umaCategoria);
				String textoDaCategoria = categoriaEscritaEmKanji;
				//String categoriaEscritaEmKanji = RetornaNomeCategoriaEscritaEmKanji.retornarNomeCategoriaEscritaEmKanji(umaCategoria);
				textoDaCategoria = textoDaCategoria + "\n" + umaCategoria + " (" + String.valueOf(quantasPalavrasTemACategoria) + ")";
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
			int idImagem = AssociaCategoriaComIcone.pegarIdImagemDaCategoria(getApplicationContext(),umaCategoria);
			imageId[j] = idImagem;
		}
		for(int k = 0; k < arrayCategorias2.length; k++)
		{
			String umaCategoria = arrayCategorias2[k];
			int idImagem = AssociaCategoriaComIcone.pegarIdImagemDaCategoria(getApplicationContext(),umaCategoria);
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
		AdapterListViewIconeETexto adapter = new AdapterListViewIconeETexto(TelaModoCompeticao.this, arrayCategoriasParaListview, imageId,typeFaceFonteTextoListViewIconeETexto,true, R.layout.list_item_icone_e_texto, false);
	   adapter.setLayoutUsadoParaTextoEImagem(R.layout.list_item_icone_e_texto);
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
	                   	TextView texto = (TextView) view.findViewById(R.id.txt);
	                   	texto.setTextColor(Color.argb(255, 0, 0, 0));
	                   }
	                   else
	                   {
	                   	categoriaEstahSelecionada[position] = false;
	                   	ImageView imageView = (ImageView) view.findViewById(R.id.img);
	                   	TextView texto = (TextView) view.findViewById(R.id.txt);
	                   	imageView.setAlpha(130);
	                   	texto.setTextColor(Color.argb(130, 0, 0, 0));
	                   	
	                   }
	               }
	           });
	       
	       
	       AdapterListViewIconeETexto adapter2 = new AdapterListViewIconeETexto(TelaModoCompeticao.this, arrayCategorias2ParaListView, imageId2,typeFaceFonteTextoListViewIconeETexto,true, R.layout.list_item_icone_e_texto, false);
	       adapter2.setLayoutUsadoParaTextoEImagem(R.layout.list_item_icone_e_texto);
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
		                    	TextView texto = (TextView) view.findViewById(R.id.txt);
		                    	texto.setTextColor(Color.argb(255, 0, 0, 0));
		                    }
		                    else
		                    {
		                    	categoriaEstahSelecionada2[position] = false;
		                    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
		                    	imageView.setAlpha(130);
		                    	TextView texto = (TextView) view.findViewById(R.id.txt);
		                    	texto.setTextColor(Color.argb(130, 0, 0, 0));
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
								  TelaModoCompeticao.this.criarSalaModoCasual(categoriasDeKanjiSelecionadas);
									
								  
							  }
							  else
							  {
								  Toast.makeText(getApplicationContext(), getResources().getString(R.string.aviso_nao_selecionou_categorias), Toast.LENGTH_SHORT).show();
							  }
						
					}
			  		  
			  	  });
		
		  
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
	 
	  
	 
	 
	 public void criarSalaModoCasual(LinkedList<String> categoriasDeKanjiSelecionadas) 
	 {
	 	
	 	DadosDaSalaModoCasual dadosDeUmaPartidaCasual = new DadosDaSalaModoCasual();
	 	dadosDeUmaPartidaCasual.setCategoriasSelecionadas(categoriasDeKanjiSelecionadas);
	 	SingletonGuardaUsernameUsadoNoLogin caraConheceNomeDeUsuarioCriado = SingletonGuardaUsernameUsadoNoLogin.getInstance();
	 	String nomeDoUsuarioUsado = caraConheceNomeDeUsuarioCriado.getNomeJogador(getApplicationContext());
	 	dadosDeUmaPartidaCasual.setUsernameQuemCriouSala(nomeDoUsuarioUsado);
	 	String tituloDoJogador = SingletonGuardaDadosUsuarioNoRanking.getInstance().getTituloDoJogadorCalculadoRecentemente();
	 	dadosDeUmaPartidaCasual.setTituloDoJogador(tituloDoJogador);
	 	loadingKanjisDoBd = ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.criando_sala), getResources().getString(R.string.por_favor_aguarde));
	 	CriarSalaDoModoCasualTask criaSalaModoCasual = new CriarSalaDoModoCasualTask(loadingKanjisDoBd, TelaModoCompeticao.this);
	 	salaAtual = new SalaAbertaModoCompeticao();
	 	salaAtual.setCategoriasSelecionadas(categoriasDeKanjiSelecionadas);
	 	salaAtual.setNivelDoUsuario(tituloDoJogador);
	 	salaAtual.setNomeDeUsuario(nomeDoUsuarioUsado);
	 	criaSalaModoCasual.execute(dadosDeUmaPartidaCasual);
	 }
	 
	 public void inserirDadosNovaSalaCasual(DadosDaSalaModoCasual dadosNovaSala)
	 {
		 LinkedList<String> categporiasSelecionadas = dadosNovaSala.getCategoriasSelecionadas();
		 String tituloJogador = dadosNovaSala.getTituloDoJogador();
		 String nomeUsuarioUsado = dadosNovaSala.getUsernameQuemCriouSala();
		 salaAtual = new SalaAbertaModoCompeticao();
		 salaAtual.setCategoriasSelecionadas(categporiasSelecionadas);
		 salaAtual.setNivelDoUsuario(tituloJogador);
		 salaAtual.setNomeDeUsuario(nomeUsuarioUsado);
	 }
	 
	 public void setarIdDaSala(int idSalaModoCasual)
	 {
		 this.salaAtual.setIdDaSala(idSalaModoCasual);
	 }
	 
	 private void comecarNovaPartidaCasual(
				LinkedList<String> categoriasDeKanjiSelecionadas) 
	 {
		 	Log.i("TelaModoCompeticao", "jogador " + nomeUsuario+ " jogoJahTerminou = false" );
			GuardaDadosDaPartida guardaDadosDeUmaPartida = GuardaDadosDaPartida.getInstance();
			prepararTelaInicialDoJogo(categoriasDeKanjiSelecionadas);
			KanjiTreinar umKanjiAleatorioParaTreinar = GuardaDadosDaPartida.getInstance().getUmKanjiAleatorioParaTreinar();
			
			
			LinkedList<String> hiraganasAlternativas = new LinkedList<String>();
			hiraganasAlternativas.add(umKanjiAleatorioParaTreinar.getHiraganaDoKanji());
			LinkedList<String> possiveisCiladasHiragana = umKanjiAleatorioParaTreinar.getPossiveisCiladasKanji();
			for(int j = 0; j < possiveisCiladasHiragana.size(); j++)
			{
				String umaPossivelCilada = possiveisCiladasHiragana.get(j);
				hiraganasAlternativas.add(umaPossivelCilada);
			}
			LinkedList<String> outrasCiladasMesmaCategoria = guardaDadosDeUmaPartida.getHiraganasAleatoriosSimilares(umKanjiAleatorioParaTreinar);
			for(int k = 0; k < outrasCiladasMesmaCategoria.size(); k++)
			{
				String possivelCiladaMesmaCategoria = outrasCiladasMesmaCategoria.get(k);
				hiraganasAlternativas.add(possivelCiladaMesmaCategoria);
			}
			//vamos embaralhar e associar aos botoes
			Collections.shuffle(hiraganasAlternativas);
			Collections.shuffle(hiraganasAlternativas);
			
			iniciarUmaPartida(umKanjiAleatorioParaTreinar, hiraganasAlternativas);
			
			String mensagemParaOponente = "terminou selecionar categorias::" + umKanjiAleatorioParaTreinar.getIdCategoriaAssociada() + ";" +
											umKanjiAleatorioParaTreinar.getKanji() + ";" + hiraganasAlternativas.get(0) + ";" +hiraganasAlternativas.get(1) + ";" +
											hiraganasAlternativas.get(2) + ";" + hiraganasAlternativas.get(3);
			mandarMensagemMultiplayer(mensagemParaOponente);
		}
	 
	 private LinkedList<String> pegarCategoriasDeKanjiSelecionadas() 
	 {
	 	StringBuffer responseText = new StringBuffer();
	 	    responseText.append("The following were selected...\n");
	 	  
	 	    ArrayList<CategoriaDeKanjiParaListviewSelecionavel> categoriaDeKanjiList = dataAdapter.getCategoriaDeKanjiList();
	 	    LinkedList<String> categoriasDeKanjiSelecionadas = new LinkedList<String>();
	 	    for(int j = 0; j < categoriaDeKanjiList.size(); j++)
	 	    {
	 	    	CategoriaDeKanjiParaListviewSelecionavel categoriaDekanji = categoriaDeKanjiList.get(j);
	 	    	if(categoriaDekanji.isSelected()){
	 	    		responseText.append("\n" + categoriaDekanji.getName());
	 	    		String categoriaDeKanjiSemParenteses = categoriaDekanji.getName().split("\\(")[0]; 
	 	    		categoriasDeKanjiSelecionadas.add(categoriaDeKanjiSemParenteses);
	 	    	}
	 	    }
	 	  
	 	    /*Toast.makeText(getApplicationContext(),
	 	      responseText, Toast.LENGTH_LONG).show();*/
	 	return categoriasDeKanjiSelecionadas;
	 }
	 
	 private ImageView viewSumosNaArena;
	 private ImageView viewImagemFundoJogador;//imagem do jogador
	 private ImageView viewImagemIconeJogador;
	 private TextView textviewImagemJogador;//texto em cima do background imagem jogador
	 private ImageView viewImagemFundoOponente;//imagem do oponente
	 private TextView textviewImagemOponente;//texto em cima do background imagem jogador
	 private ImageView viewImagemIconeOponente;
	 private AnimationDrawable animacaoSumosNaArena;
	 private Button botaoAnswer1;
	 private Button botaoAnswer2;
	 private Button botaoAnswer3;
	 private Button botaoAnswer4;
	//Current state of the game:
	 private CountDownTimer timerFimDeJogo;
	 
	 private void prepararTelaInicialDoJogo(LinkedList<String> categoriasDeKanjiSelecionadas) 
	 {
	 	GuardaDadosDaPartida guardaDadosDeUmaPartida = GuardaDadosDaPartida.getInstance();
	 	guardaDadosDeUmaPartida.limparDadosPartida();//nova partida...
	 	guardaDadosDeUmaPartida.setCategoriasSelecionadasPraPartida(categoriasDeKanjiSelecionadas);
	 	switchToScreen(R.id.screen_game);
	 	
	 	
	 	LinkedList<Integer> idsCategoriasSelecionadas = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdsCategorias(categoriasDeKanjiSelecionadas);
	 	Integer [] indicesIconesCategoriasDoJogo = PegaIdsIconesDasCategoriasSelecionadas.pegarIndicesIconesDasCategoriasSelecionadasPraPratida(idsCategoriasSelecionadas, this.getApplicationContext());
	 	//botar as categorias do jogo em uma ordem específica
	 	LinkedList<Integer> idsCategoriasNaTelaEmOrdem = new LinkedList<Integer>();
	 	idsCategoriasNaTelaEmOrdem.add(R.id.categoria4);
	 	idsCategoriasNaTelaEmOrdem.add(R.id.categoria5);
	 	idsCategoriasNaTelaEmOrdem.add(R.id.categoria3);
	 	idsCategoriasNaTelaEmOrdem.add(R.id.categoria6);
	 	idsCategoriasNaTelaEmOrdem.add(R.id.categoria2);
	 	idsCategoriasNaTelaEmOrdem.add(R.id.categoria7);
	 	idsCategoriasNaTelaEmOrdem.add(R.id.categoria1);
	 	idsCategoriasNaTelaEmOrdem.add(R.id.categoria8);
	 	
	 	for(int i = 0; i < indicesIconesCategoriasDoJogo.length; i++)
	 	{
	 		Integer umIconeCategoriaDoJogo = indicesIconesCategoriasDoJogo[i];
	 		Integer umIdCategoriaNaTela = idsCategoriasNaTelaEmOrdem.get(i);
	 		int umIdCategoriaNaTelaEmInt = umIdCategoriaNaTela.intValue();
	 		ImageView umaCategoriaImageView = (ImageView) findViewById(umIdCategoriaNaTelaEmInt);
	 		umaCategoriaImageView.setImageResource(umIconeCategoriaDoJogo);
	 	}
	 	for(int j = indicesIconesCategoriasDoJogo.length; j < idsCategoriasNaTelaEmOrdem.size(); j++)
	 	{
	 		Integer umIdCategoriaNaTela = idsCategoriasNaTelaEmOrdem.get(j);
	 		int umIdCategoriaNaTelaEmInt = umIdCategoriaNaTela.intValue();
	 		ImageView umaCategoriaImageView = (ImageView) findViewById(umIdCategoriaNaTelaEmInt);
	 		umaCategoriaImageView.setImageResource(R.drawable.iconcat_nada);
	 		umaCategoriaImageView.setAlpha(new Float(0.2));
	 	}
	 	
	 	
	 	
	    
	    ImageButton botaoItem1 = (ImageButton) findViewById(R.id.botaoItem1);
	    ImageButton botaoItem2 = (ImageButton) findViewById(R.id.botaoItem2);
	    ImageButton botaoItem3 = (ImageButton) findViewById(R.id.botaoItem3);
	    ImageButton botaoItem4 = (ImageButton) findViewById(R.id.botaoItem4);
	    ImageButton botaoItem5 = (ImageButton) findViewById(R.id.botaoItem5);
	    int idPngDoItemSemNada = getResources().getIdentifier("botaoitem", "drawable", getPackageName());
	    botaoItem1.setImageResource(idPngDoItemSemNada);
	    botaoItem2.setImageResource(idPngDoItemSemNada);
	    botaoItem3.setImageResource(idPngDoItemSemNada);
	    botaoItem4.setImageResource(idPngDoItemSemNada);
	    botaoItem5.setImageResource(idPngDoItemSemNada);
	    
	 	
	 	TextView textviewNomeJogadorGuest = (TextView)findViewById(R.id.nome_jogador_guest);
	 	textviewNomeJogadorGuest.setVisibility(View.VISIBLE);
	 	TextView textviewNomeJogadorHost = (TextView)findViewById(R.id.nome_jogador_host);
	 	textviewNomeJogadorHost.setVisibility(View.VISIBLE);
	 	//bug aki
	 	String nomeJogador = this.nomeUsuario;
	 	String nomeAdversario = this.nomeAdversario;
	 	String nomeJogadorEncurtado = nomeJogador;
	 	if(nomeJogador.length() > 13)
	 	{
	 		nomeJogadorEncurtado = nomeJogadorEncurtado.substring(0,12);
	 	}
	 	String nomeAdversarioEncurtado = nomeAdversario;
	 	if(nomeAdversario.length() > 13)
	 	{
	 		nomeAdversarioEncurtado = nomeAdversarioEncurtado.substring(0, 12);
	 	}
	 	
	 	if(this.euEscolhoACategoria == true)
	 	{
	 		textviewNomeJogadorHost.setText(nomeJogadorEncurtado);
			textviewNomeJogadorGuest.setText(nomeAdversarioEncurtado);
	 	}
	 	else
	 	{
	 		textviewNomeJogadorHost.setText(nomeAdversarioEncurtado);
			textviewNomeJogadorGuest.setText(nomeJogadorEncurtado);
	 	}
	 	
	 	
	 	TextView textoTempoDaPartida = (TextView) findViewById(R.id.countdown);
	 	
	 	
	 	textoTempoDaPartida.setText("01:30");
	 	
	 	this.viewSumosNaArena = (ImageView)findViewById(R.id.ringue_luta);
	 	this.botaoAnswer1 = (Button)findViewById(R.id.answer1);
	 	this.botaoAnswer1.setVisibility(View.VISIBLE);
		this.botaoAnswer2 = (Button)findViewById(R.id.answer2);
		this.botaoAnswer2.setVisibility(View.VISIBLE);
		this.botaoAnswer3 = (Button)findViewById(R.id.answer3);
		this.botaoAnswer3.setVisibility(View.VISIBLE);
		this.botaoAnswer4 = (Button)findViewById(R.id.answer4);
		this.botaoAnswer4.setVisibility(View.VISIBLE);
		atualizarAnimacaoSumosNaArena();
	 	
	 	this.mSecondsLeft = GAME_DURATION;
	 	
	 	this.timerFimDeJogo = new CountDownTimer(mSecondsLeft * 1000, 1000) {

	        public void onTick(long millisUntilFinished) {
	        	Log.i("TelaModoCompeticao", "mSecondsLeft== " + mSecondsLeft );
	        	--mSecondsLeft;
	        	if(mSecondsLeft == 10)
	        	{
	        		//pouco tempo para acabar? add animação no timer!
	        		final Animation animScale = AnimationUtils.loadAnimation(TelaModoCompeticao.this, R.anim.anim_scale_clock);
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
	        	if(jogoJahTerminou == false)
	        	{
	        		// finish game
	            	mandarMensagemMultiplayer("terminouJogo;");
	            	ProgressDialog barraProgressoFinalTerminouJogo =  ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.aviso_tempo_acaboou), getResources().getString(R.string.por_favor_aguarde));
	            	TerminaPartidaTask taskTerminaPartida = new TerminaPartidaTask(barraProgressoFinalTerminouJogo, TelaModoCompeticao.this);
	            	taskTerminaPartida.execute("");
	        	}
	        	
	        }
	     }.start();
	     
	     
	     //por fim, mudar a musiquinha de background...
	     this.mudarMusicaDeFundo(R.raw.ramblinglibrarian_nanyang_journey);
	     
	     //e setar textos dessas mesmas setinhas...
	     final TextView textoCimaSetaSumoEsquerda = (TextView) findViewById(R.id.texto_label_sumo_esquerda);
	     final TextView textoCimaSetaSumoDireita = (TextView) findViewById(R.id.texto_label_sumo_direita);
	     String textSumoRepresentaVoce = (String) getResources().getText(R.string.label_cima_sumo_voce);
	     String textoSumoRepresentaAdversario = (String) getResources().getText(R.string.label_cima_sumo_rival);
	     if(this.euEscolhoACategoria == true)
	  	{
	    	 textoCimaSetaSumoEsquerda.setText(textSumoRepresentaVoce);
	    	 textoCimaSetaSumoDireita.setText(textoSumoRepresentaAdversario);
	  	}
	  	else
	  	{
	  		textoCimaSetaSumoEsquerda.setText(textoSumoRepresentaAdversario);
	  		textoCimaSetaSumoDireita.setText(textSumoRepresentaVoce);
	  	}
	     
	     //e piscar setinhas...
	     if(this.setinhaEmCimaSumoDireita == null)
		 {
			 ImageView setaDireita = (ImageView) findViewById(R.id.seta_cima_sumo_direita);
			 this.setinhaEmCimaSumoDireita = setaDireita;
		 }
		 if(this.setinhaEmCimaSumoEsquerda == null)
		 {
			 ImageView setaEsquerda = (ImageView) findViewById(R.id.seta_cima_sumo_esquerda);
			 this.setinhaEmCimaSumoEsquerda= setaEsquerda;
		 }
		 setinhaEmCimaSumoDireita.setVisibility(View.VISIBLE);
		 textoCimaSetaSumoDireita.setVisibility(View.VISIBLE);
		 setinhaEmCimaSumoEsquerda.setVisibility(View.VISIBLE);
		 textoCimaSetaSumoEsquerda.setVisibility(View.VISIBLE);
	     new BlinkAnimation(this.setinhaEmCimaSumoEsquerda).setDuration(5000).setListener(new AnimationListener() {
				
				@Override
				public void onAnimationEnd(com.easyandroidanimations.library.Animation arg0) 
				{
					TelaModoCompeticao.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							setinhaEmCimaSumoEsquerda.setVisibility(View.INVISIBLE);
							textoCimaSetaSumoEsquerda.setVisibility(View.INVISIBLE);
							
						}
					});
					
				}
			}).animate();
	     new BlinkAnimation(this.setinhaEmCimaSumoDireita).setDuration(5000).setListener(new AnimationListener() {
				
				@Override
				public void onAnimationEnd(com.easyandroidanimations.library.Animation arg0) 
				{
					TelaModoCompeticao.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							setinhaEmCimaSumoDireita.setVisibility(View.INVISIBLE);
							textoCimaSetaSumoDireita.setVisibility(View.INVISIBLE);
							
						}
					});
					
				}
			}).animate();
	     
	     //e pegar as imagens do host e guest
	     if(this.euEscolhoACategoria == true)
	     {

	         this.viewImagemFundoJogador = (ImageView) findViewById(R.id.imagem_background_host);
	         this.textviewImagemJogador = (TextView) findViewById(R.id.nome_jogador_host);
	         this.viewImagemFundoOponente = (ImageView) findViewById(R.id.imagem_background_guest);
	         this.textviewImagemOponente = (TextView) findViewById(R.id.nome_jogador_guest);
	         this.viewImagemIconeJogador = (ImageView) findViewById(R.id.figura_jogador_host);
	         this.viewImagemIconeOponente = (ImageView) findViewById(R.id.figura_jogador_guest);
	     }
	     else
	     {
	    	 this.viewImagemFundoJogador = (ImageView) findViewById(R.id.imagem_background_guest);
	    	 this.textviewImagemJogador = (TextView) findViewById(R.id.nome_jogador_guest);
	         this.viewImagemFundoOponente = (ImageView) findViewById(R.id.imagem_background_host);
	         this.textviewImagemOponente = (TextView) findViewById(R.id.nome_jogador_host);
	         this.viewImagemIconeJogador = (ImageView) findViewById(R.id.figura_jogador_guest);
	         this.viewImagemIconeOponente = (ImageView) findViewById(R.id.figura_jogador_host);
	     }
	     
	 }
	 
	 private ImageView setinhaEmCimaSumoEsquerda;
	 private ImageView setinhaEmCimaSumoDireita;
	 private void atualizarAnimacaoSumosNaArena()
	 {
		 if(this.setinhaEmCimaSumoDireita == null)
		 {
			 ImageView setaDireita = (ImageView) findViewById(R.id.seta_cima_sumo_direita);
			 this.setinhaEmCimaSumoDireita = setaDireita;
		 }
		 if(this.setinhaEmCimaSumoEsquerda == null)
		 {
			 ImageView setaEsquerda = (ImageView) findViewById(R.id.seta_cima_sumo_esquerda);
			 this.setinhaEmCimaSumoEsquerda= setaEsquerda;
		 }
		 this.animacaoSumosNaArena = new AnimationDrawable();
		 GuardaDadosDaPartida guardaDadosPartida = GuardaDadosDaPartida.getInstance();
		 int posicaoDoSumozinhoAtual = guardaDadosPartida.getPosicaoSumozinhoDoJogadorNaTela(); 
		 String nomeImagemSumozinhoAnimacao1 = this.getNomeImagemSumozinho(posicaoDoSumozinhoAtual);
		 String nomeImagemSumozinhoAnimacao2 = nomeImagemSumozinhoAnimacao1 + "_alt";
		 int idImagemSumozinhoAnimacao1 = getResources().getIdentifier(nomeImagemSumozinhoAnimacao1, "drawable", getPackageName());
		 int idImagemSumozinhoAnimacao2 = getResources().getIdentifier(nomeImagemSumozinhoAnimacao2, "drawable", getPackageName());
		 animacaoSumosNaArena.addFrame(getResources().getDrawable(idImagemSumozinhoAnimacao1), 200);
		 animacaoSumosNaArena.addFrame(getResources().getDrawable(idImagemSumozinhoAnimacao2), 200);
		 animacaoSumosNaArena.setOneShot(false);
		 this.viewSumosNaArena.setImageDrawable(animacaoSumosNaArena);
		 this.viewSumosNaArena.post(new Runnable() {
			@Override
			public void run() {
				animacaoSumosNaArena.start();
			}
		});
		 if(nomeImagemSumozinhoAnimacao1 == "sumo_0_0")
		 {
			 RelativeLayout.LayoutParams paramsSetaEsquerda = (android.widget.RelativeLayout.LayoutParams) setinhaEmCimaSumoEsquerda.getLayoutParams();
			        paramsSetaEsquerda.addRule(RelativeLayout.RIGHT_OF, R.id.categoria4);
			        paramsSetaEsquerda.addRule(RelativeLayout.LEFT_OF, 0);
			        paramsSetaEsquerda.setMargins(10, 0, 0, 0);
			 setinhaEmCimaSumoEsquerda.setLayoutParams(paramsSetaEsquerda);
			 RelativeLayout.LayoutParams paramsSetaDireita = (android.widget.RelativeLayout.LayoutParams) setinhaEmCimaSumoDireita.getLayoutParams();
			 paramsSetaDireita.addRule(RelativeLayout.LEFT_OF, 0);
			 paramsSetaDireita.addRule(RelativeLayout.RIGHT_OF, R.id.seta_cima_sumo_esquerda);
			 paramsSetaEsquerda.setMargins(35, 0, 0, 0);
			 setinhaEmCimaSumoDireita.setLayoutParams(paramsSetaDireita);
		 }
		 
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
	     
	     botaoAlternativa1.setClickable(true);
	     botaoAlternativa2.setClickable(true);
	     botaoAlternativa3.setClickable(true);
	     botaoAlternativa4.setClickable(true);
	     if(estahComAnimacaoTegata == false && guardaDadosDeUmaPartida.oShikoFoiUsado() == false)
	     {
	    	 botaoAlternativa1.clearAnimation();
	    	 botaoAlternativa2.clearAnimation();
	    	 botaoAlternativa3.clearAnimation();
	    	 botaoAlternativa4.clearAnimation();
	     }
	     
	     if(guardaDadosDeUmaPartida.oShikoFoiUsado() == false)
	     {
	    	 //golpe shiko não foi usado, é novo round.
	    	 guardaDadosDeUmaPartida.incrementarRoundDaPartida();
	         
	         //vamos dar um item ao jogador se o round for par e ele não tem item...
	         int roundDaPartida = guardaDadosDeUmaPartida.getRoundDaPartida();
	         int posicaoSemItemNoInventario = guardaDadosDeUmaPartida.getPrimeiraPosicaoSemItemDoInventario();// é -1 se ele já tem todos os itens
	         if((roundDaPartida & 1) == 0 && posicaoSemItemNoInventario != -1)
	         {
	        	 //round par e sem itens? vamos dar um item ao jogador!
	        	 String nomeItemAdicionado = guardaDadosDeUmaPartida.adicionarItemAleatorioAoInventario();
	        	 int idPngDoItem = getResources().getIdentifier(nomeItemAdicionado, "drawable", getPackageName());
	        	 //Bitmap imagemDoItem = BitmapFactory.decodeResource(getResources(), idPngDoItem);
	        	 int indiceBotaoItem = posicaoSemItemNoInventario + 1;
	        	 String stringIdUltimoBotaoItem = "botaoItem" + indiceBotaoItem ;
	        	 int idBotaoUltimoItem = getResources().getIdentifier(stringIdUltimoBotaoItem, "id", getPackageName());
	        	 ImageButton botaoItem = (ImageButton)findViewById(idBotaoUltimoItem);
	        	
	        	 //BitmapDrawable bitmapDrawableImagemItem = new BitmapDrawable(imagemDoItem);
	        	 botaoItem.setImageResource(idPngDoItem);
	        	 //botaoItem.setBackground(bitmapDrawableImagemItem);
	        	 new BounceAnimation(botaoItem).animate();
	         }
	     }
	     else
	     {
	    	 //shiko perde efeito.
	    	 guardaDadosDeUmaPartida.setShikoFoiUsado(false);
	    	 
	     }
	     
	     
	     
	     
	    	
	     
	 }
	 
	 public synchronized void mandarMensagemMultiplayer(String mensagem)
	 {
		 byte [] mensagemProAdversarioEmBytes = mensagem.getBytes();
		 if(mParticipants != null)
		 {
			 for (Participant p : mParticipants) 
			 	{
			 		if (p.getParticipantId().equals(mMyId))
			 		{
			 			continue;
			 		}
			 	    else
			 	    {
			 	    	Games.RealTimeMultiplayer.sendReliableMessage(getApiClient(), null, mensagemProAdversarioEmBytes, mRoomId,
			 		            p.getParticipantId());
			 	    }
			 	}
		 }
		
	 }

	public boolean jogadorEhHost() {
		if(euEscolhoACategoria == true)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}


	/*
	 * PARTE REFERENTE AO CHAT
	 */

	private Handler mHandler = new Handler();

	private void setListAdapter() {
		AdapterListViewChatCasual adapter = new AdapterListViewChatCasual(this, R.layout.listitem, this.mensagensChat, this.posicoesBaloesMensagensChat);
		    this.listViewMensagensChat.setAdapter(adapter);
	  }

	/**
	 * 
	 * @param mensagem
	 * @param adicionarNomeDoRemetente precisa complementar a mensagem com o nome do remetente ou nao...
	 * @return a mensagem adicionada no chat.
	 */
	private String adicionarMensagemNoChat(String mensagem, boolean adicionarNomeDoRemetente, String nomeRemetente, boolean foiMensagemDoAdversario)
	{
		String mensagemAdicionarNoChat = mensagem;
		if(adicionarNomeDoRemetente == true)
		{
			//append na mensagem o nome do remetente
			String nomeUsuarioEncurtado = this.nomeUsuario;//talvez precise encurtar com .substring(0, 11)
			mensagemAdicionarNoChat = nomeUsuarioEncurtado + ":" + mensagem;
		}
		
		this.mensagensChat.add(mensagemAdicionarNoChat);
		if(foiMensagemDoAdversario == true)
		{
			this.posicoesBaloesMensagensChat.add("direita");
		}
		else
		{
			this.posicoesBaloesMensagensChat.add("esquerda");
		}
		setListAdapter();
		return mensagemAdicionarNoChat;
	}

	private void avisarAoOponenteQueDigitouMensagem(String mensagemAdicionarNoChat)
	{
		//mandar mensagem para oponente...
		this.mandarMensagemMultiplayer("oponente falou no chat;" + mensagemAdicionarNoChat);
		
	}


	/*
	 * PARTE REFERENTE AO CONTADOR DA PARTIDA
	 */



	/**
		 * PARTE REFERENTE A INSERÇÃO DE LOG DA PARTIDA NO BD
		 */
		
		private void enviarSeuUsernameParaOAdversario()
		 {
			SingletonGuardaUsernameUsadoNoLogin pegarUsernameUsadoPeloJogador = SingletonGuardaUsernameUsadoNoLogin.getInstance();
			String nomeJogadorArmazenado = pegarUsernameUsadoPeloJogador.getNomeJogador(getApplicationContext());
			this.mandarMensagemMultiplayer("username=" + nomeJogadorArmazenado);
		 }
		 
		 private void enviarDadosDaPartidaParaOLogDoUsuarioNoBancoDeDados()
		 {
			 //enviaremos as informacoes da partida num log que escreveremos para o usuário e salvaremos num servidor remoto
			 DadosPartidaParaOLog dadosPartida = new DadosPartidaParaOLog();
			 LinkedList<String> categoriasTreinadas = GuardaDadosDaPartida.getInstance().getCategoriasTreinadasNaPartida();
			 String idsCategoriasEmString = "";
			 for(int i = 0; i < categoriasTreinadas.size(); i++)
			 {
				String umaCategoria = categoriasTreinadas.get(i);
				int idCategoriaCorrespondente = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdDaCategoria(umaCategoria);
				idsCategoriasEmString = idsCategoriasEmString + idCategoriaCorrespondente;
				if(i < categoriasTreinadas.size() - 1)
				{
					idsCategoriasEmString = idsCategoriasEmString + ",";
				}
			 }
			 
			 dadosPartida.setCategoria(idsCategoriasEmString);
			 
			 Calendar c = Calendar.getInstance();
			 SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
			 String formattedDate = df.format(c.getTime());
			 dadosPartida.setData(formattedDate);
			 
			 dadosPartida.setUsernameJogador(this.nomeUsuario);
			 
			 GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
			 dadosPartida.setPalavrasAcertadas(guardaDadosDaPartida.getKanjisAcertadosNaPartida());
			 dadosPartida.setPalavrasErradas(guardaDadosDaPartida.getKanjisErradosNaPartida());
			 dadosPartida.setPalavrasJogadas(guardaDadosDaPartida.getKanjisTreinadosNaPartida());
			 dadosPartida.setPontuacao(guardaDadosDaPartida.getPlacarDoJogadorNaPartida());
			 
			 
			 int posicaoAntigaSumozinho = guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela();
			 
			 if(posicaoAntigaSumozinho > 0)
				{
					//quem ganhou foi o usuario , nao o oponente!
				 	String ganhou = "ganhou";
				 	dadosPartida.setVoceGanhouOuPerdeu(ganhou);
					
				}
				else if(posicaoAntigaSumozinho < 0)
				{
					//quem ganhou foi o oponente, entao precisamos saber o nome dele
					String perdeu = "perdeu";
					dadosPartida.setVoceGanhouOuPerdeu(perdeu);
					
				}
				else
				{
					//empatou
					 String empatou = "empatou";
					 dadosPartida.setVoceGanhouOuPerdeu(empatou);
				}
			 
			 
			 
			 
			 
			 dadosPartida.setUsernameAdversario(this.nomeAdversario);
			 this.popupCarregandoDadosDoJogadorNoRanking = 
						ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.salvando_partida), 
											getResources().getString(R.string.por_favor_aguarde));
			 EnviarDadosDaPartidaParaLogCompeticaoTask armazenaNoLog = new EnviarDadosDaPartidaParaLogCompeticaoTask(this, this.popupCarregandoDadosDoJogadorNoRanking);
			 armazenaNoLog.execute(dadosPartida);
			 
		 }
		 
		 private DadosUsuarioNoRanking dadosAntigosUsuarioRanking;
		 public void atualizarVitoriasDerrotasDoUsuario()
		 {
			 
			 GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
			 SingletonGuardaDadosUsuarioNoRanking guardaDadosRanking = SingletonGuardaDadosUsuarioNoRanking.getInstance();
			 DadosUsuarioNoRanking antigosDadosRanking = guardaDadosRanking.getDadosSalvosUsuarioNoRanking();
			 this.dadosAntigosUsuarioRanking = antigosDadosRanking;
			 int posicaoAntigaSumozinho = guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela();
			 if(posicaoAntigaSumozinho > 0)
				{
					//quem ganhou foi o usuario , nao o oponente!
					int antigoVitorias = antigosDadosRanking.getVitoriasCompeticao();
					antigosDadosRanking.setVitoriasCompeticao(antigoVitorias + 1);
				}
				else if(posicaoAntigaSumozinho < 0)
				{
					//quem ganhou foi o oponente, entao precisamos saber o nome dele
					int antigoDerrotas = antigosDadosRanking.getDerrotasCompeticao();
					antigosDadosRanking.setDerrotasCompeticao(antigoDerrotas + 1); 
					
				}
			 int pontuacaoDoJogadorNaPartida = guardaDadosDaPartida.getPlacarDoJogadorNaPartida();
			 int antigaPontuacaoJogador = antigosDadosRanking.getPontuacaoTotal();
			 antigosDadosRanking.setPontuacaoTotal(antigaPontuacaoJogador + pontuacaoDoJogadorNaPartida);
			 guardaDadosRanking.setDadosSalvosUsuarioNoRanking(antigosDadosRanking);
			 this.popupCarregandoDadosDoJogadorNoRanking = 
						ProgressDialog.show(TelaModoCompeticao.this, getResources().getString(R.string.carregando_posicao_ranking), 
											getResources().getString(R.string.por_favor_aguarde));
			 TaskMudaDadosDoRanking taskMudarDadosRanking = new TaskMudaDadosDoRanking(this, this.popupCarregandoDadosDoJogadorNoRanking);
			 
			 taskMudarDadosRanking.execute(antigosDadosRanking);
		 }
		 
		 public void mudarParaTelaFimDeJogoComRanking()
		 {
			SingletonGuardaDadosUsuarioNoRanking guardaDadosNovosRanking = SingletonGuardaDadosUsuarioNoRanking.getInstance();
			DadosUsuarioNoRanking novosDadosUsuarioRanking = guardaDadosNovosRanking.getDadosSalvosUsuarioNoRanking();
			int posicaoNovaUsuario = novosDadosUsuarioRanking.getPosicaoJogadorNoRanking();
			int posicaoAntigaUsuario = dadosAntigosUsuarioRanking.getPosicaoJogadorNoRanking();
			String textoFinalJogo = "";
			if(posicaoNovaUsuario > posicaoAntigaUsuario)
			{
				//usuário foi rebaixado...
				textoFinalJogo = getResources().getString(R.string.sua_posicao_decrementou);
			}
			else if(posicaoNovaUsuario < posicaoAntigaUsuario)
			{
				//usuário foi promovido...
				textoFinalJogo = getResources().getString(R.string.sua_posicao_incrementou);
			}
			else
			{
				textoFinalJogo = getResources().getString(R.string.sua_posicao_permaneceu);
			}
			
			textoFinalJogo = textoFinalJogo + " " + posicaoNovaUsuario + "(";
			String tituloNovoJogador = CalculaPosicaoDoJogadorNoRanking.definirTituloDoJogadorNoRanking(getApplicationContext());
			textoFinalJogo = textoFinalJogo + tituloNovoJogador + ")";
			
			TextView textoPosicaoNovaRanking = (TextView) findViewById(R.id.quem_ganhou_final);
			String textoAntigoDoTextview = textoPosicaoNovaRanking.getText().toString() + "\n";
			
			SpannableStringBuilder builder = new SpannableStringBuilder();

			SpannableString redSpannable= new SpannableString(textoAntigoDoTextview);
			redSpannable.setSpan(new RelativeSizeSpan(1.0f), 0, textoAntigoDoTextview.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			builder.append(redSpannable);

			SpannableString whiteSpannable= new SpannableString(textoFinalJogo);
			whiteSpannable.setSpan(new RelativeSizeSpan(0.8f), 0, textoFinalJogo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			builder.append(whiteSpannable);

			

			textoPosicaoNovaRanking.setText(builder, BufferType.SPANNABLE);
			
			
			
		 }
		 
		 @Override
		 public void onRestart()
		 {
			 super.onRestart();
			 Intent intentCriaTelaInicial = new Intent(TelaModoCompeticao.this, MainActivity.class);
			 intentCriaTelaInicial.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 startActivity(intentCriaTelaInicial);
		 }
		 
		 @Override
		 public void onResume()
		 {
			 super.onResume();
			/* View decorView = getWindow().getDecorView();
			// Hide both the navigation bar and the status bar.
			// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
			// a general rule, you should design your app to hide the status bar whenever you
			// hide the navigation bar.
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
			              | View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);*/
		 }

		 /* NOVO referente a habiilitar/desabilitar botao send em chat */
	        
	        
	        private  void checkFieldsForEmptyValues(EditText editText1, Button b){

	            
	            String s1 = editText1.getText().toString();

	            if(s1.equals(""))
	            {
	                b.setEnabled(false);
	            }
	            else
	            {
	                b.setEnabled(true);
	            }
	        }
	
	
}
