package br.ufrn.dimap.pairg.sumosensei.android;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.Telephony.Sms.Conversations;
import android.text.AndroidCharacter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import armazenamentointerno.ConcreteDAOArmazenaInternamenteDadosDePartidasRealizadas;
import armazenamentointerno.DAOAcessaHistoricoDePartidas;
import armazenamentointerno.DadosDePartida;
import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.Categoria;
import bancodedados.DadosPartidaParaOLog;
import bancodedados.EnviarDadosDaPartidaParaLogTask;
import bancodedados.KanjiTreinar;
import bancodedados.MyCustomAdapter;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import bancodedados.SolicitaKanjisParaTreinoTask;



import cenario.ImageAdapter;
import cenario.MultiSelectionSpinner;
import cenario.SpinnerAdapterEstilizar;
import cenario.SpinnerFiltroSalasAbertasListener;
import cenario.SpinnerSelecionaMesmoQuandoVoltaAoMesmoItem;

import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.BlindAnimation;
import com.easyandroidanimations.library.BlinkAnimation;
import com.easyandroidanimations.library.BounceAnimation;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.multiplayer.realtime.*;
//import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.realtime.*;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.phiworks.dapartida.ActivityPartidaMultiplayer;
import com.phiworks.dapartida.ConcreteDAOGuardaConfiguracoesDoJogador;
import com.phiworks.dapartida.DAOGuardaConfiguracoesDoJogador;
import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.dapartida.EmbaralharAlternativasTask;
import com.phiworks.dapartida.TaskAnimaSetinhasSumo;
import com.phiworks.dapartida.TerminaPartidaTask;
import com.phiworks.dapartida.ThreadAnimaSetinhasSumo;
import com.phiworks.domodocasual.AdapterListViewChatCasual;
import com.phiworks.domodocasual.AdapterListViewIconeETexto;
import com.phiworks.domodocasual.AdapterListViewRankingDoUsuario;
import com.phiworks.domodocasual.AdapterListViewSalasCriadas;
import com.phiworks.domodocasual.AssociaCategoriaComIcone;
import com.phiworks.domodocasual.BuscaSalasModoCasualComArgumentoTask;
import com.phiworks.domodocasual.BuscaSalasModoCasualTask;
import com.phiworks.domodocasual.DesativarSalaEscolhidaDoBdTask;
import com.phiworks.domodocasual.DesativarSalaPorIdUsuarioTask;
import com.phiworks.domodocasual.RankingEImagem;
import com.phiworks.domodocasual.SolicitaCategoriasAbreSelecaoCategoriasTask;
import com.phiworks.domodocasual.SolicitaCategoriasDoJogoTask;
import com.phiworks.domodocasual.CriarSalaDoModoCasualTask;
import com.phiworks.domodocasual.DadosDaSalaModoCasual;
import com.phiworks.domodocasual.SalaAbertaModoCasual;
import com.phiworks.domodocasual.TaskChecaSeSalaFoiDesativada;
import com.phiworks.domodocasual.TaskPegaDadosJogadorNoRankingParaCasual;

import docompeticao.AdapterListViewRanking;
import docompeticao.CalculaPosicaoDoJogadorNoRanking;
import docompeticao.SingletonGuardaDadosUsuarioNoRanking;
import dousuario.SingletonDeveMostrarTelaDeLogin;
import dousuario.SingletonGuardaUsernameUsadoNoLogin;
import br.ufrn.dimap.pairg.sumosensei.android.R;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TelaModoCasual extends ActivityPartidaMultiplayer implements OnScrollListener
{
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
private static final int MOSTRAR_LOGIN_PLAY = 10006;

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

private boolean finalizouDecisaoEscolheCategoria = false;
private volatile boolean guestTerminouDeCarregarListaDeCategorias = false;


private String nomeUsuario;
private String nomeAdversario;
private boolean jogoJahTerminou = false;
private boolean estahComAnimacaoTegata = false;
private boolean jahDeixouASala;



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
	setContentView(R.layout.activity_tela_modo_casual);
	super.onCreate(savedInstanceState);
	// set up a click listener for everything we care about
	for (int id : CLICKABLES) 
	{
		findViewById(id).setOnClickListener(this);
	}
	
	
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
	
	beginUserInitiatedSignIn();
	
	euEscolhoACategoria = false;
	
	//falta pegar o título do jogador, para o caso dele querer criar uma sala...
	
	ProgressDialog loadingRankingUsuario = new ProgressDialog(getApplicationContext());
	loadingRankingUsuario = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.carregando_posicao_ranking), getResources().getString(R.string.por_favor_aguarde));
	TaskPegaDadosJogadorNoRankingParaCasual pegaPosicaoRanking =
			new TaskPegaDadosJogadorNoRankingParaCasual(loadingRankingUsuario, this);
	SingletonGuardaUsernameUsadoNoLogin sabeNomeUsuario = SingletonGuardaUsernameUsadoNoLogin.getInstance();
	String username = sabeNomeUsuario.getNomeJogador(getApplicationContext());
	pegaPosicaoRanking.execute(username);
	
	String fontPath = "fonts/gilles_comic_br.ttf";
    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	TextView textoLabelFiltrar = (TextView) findViewById(R.id.label_filtrar);
	textoLabelFiltrar.setTypeface(tf);
	
	SingletonDeveMostrarTelaDeLogin deveMostrarTelaLogin = SingletonDeveMostrarTelaDeLogin.getInstance();
	boolean mostrarTelaLoginPlayTemporario = deveMostrarTelaLogin.getMostrarTelaLoginPlayTemporario();
	boolean mostrarTelaLogin = deveMostrarTelaLogin.getDeveMostrarTelaDeLogin(getApplicationContext());
    if(mostrarTelaLoginPlayTemporario == true && mostrarTelaLogin == true)
    {
    	/*String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, true, null, null, null, null);
    	try
    	{
    		startActivityForResult(intent, MOSTRAR_LOGIN_PLAY);
    	}
    	catch (ActivityNotFoundException e)
        {

                Toast.makeText(this, "erro: sem activity da play pra isso", Toast.LENGTH_LONG).show();

                return;
        }*/
    	
    }
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
Intent intentVoltaMenuPrincipal = new Intent(TelaModoCasual.this, MainActivity.class);
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
    case R.id.button_create_room:
        // show list of invitable players
    	this.decidirCategoria();
        break;
    case R.id.botao_buscar_salas:
        // show list of pending invitations
    	this.switchToScreen(R.id.tela_buscar_salas);
    	solicitarBuscarSalasAbertas();
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
    	Intent intentVoltaMenuPrincipal = new Intent(TelaModoCasual.this, MainActivity.class);
    	intentVoltaMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intentVoltaMenuPrincipal);
    	break;
    case R.id.sendBtn:
    	if(this.popupDoChat != null)
    	{
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




/**
 * Referente a encontrar salas abertas e ATUALIZACAO COM NOVAS SALAS ABERTAS
 */

/*quando o usuario clica no iconezinho "C" em uma das salas, ele deve ver as categorias daquela sala*/
public void abrirPopupMostrarCategoriasDeUmaSala(LinkedList<Integer> categorias)
{
	 final Dialog dialog = new Dialog(TelaModoCasual.this);
	 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	  // Include dialog.xml file
	 dialog.setContentView(R.layout.popup_categorias_de_uma_sala_casual_da_lista);
	 
	 TextView tituloDoPopupCatNaSala = 
			 (TextView)dialog.findViewById(R.id.titulo_popup_cat_na_sala);
	 
	 String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	 Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
	 tituloDoPopupCatNaSala.setTypeface(tfBrPraTexto);
	 for(int i = 0; i < categorias.size(); i++)
	 {
		 RelativeLayout imageViewQuadradoBase; //quadrado que fica por baixo dos icones das categorias
		 ImageView imageViewQuadradoCategoria;
		 TextView textoLabelQuadradoCategoria;
		 if(i == 0)
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado1);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado1categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado1categoria);
		 }
		 else if(i == 1)
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado2);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado2categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado2categoria);
		 }
		 else if(i == 2)
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado3);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado3categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado3categoria);
		 }
		 else if(i == 3)
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado4);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado4categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado4categoria);
		 }
		 else if(i == 4)
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado5);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado5categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado5categoria);
		 }
		 else if(i == 5)
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado6);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado6categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado6categoria);
		 }
		 else if(i == 6)
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado7);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado7categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado7categoria);
		 }
		 else if(i == 7)
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado8);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado8categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado8categoria);
		 }
		 else
		 {
			 imageViewQuadradoBase = (RelativeLayout) dialog.findViewById(R.id.quadrado9);
			 imageViewQuadradoCategoria = (ImageView) dialog.findViewById(R.id.quadrado9categoria);
			 textoLabelQuadradoCategoria = (TextView) dialog.findViewById(R.id.textolabelparaquadrado9categoria);
		 }
		
		 
		 //vamos tornar o quadrado de fundo e o icone da categoriavisiveis
		 imageViewQuadradoBase.setBackgroundResource(R.drawable.quadrado_popup_categorias_sala_casual_invisivel);
		 imageViewQuadradoCategoria.setVisibility(View.VISIBLE);
		 textoLabelQuadradoCategoria.setVisibility(View.VISIBLE);
		 
		 //agora falta mudar o icone de acordo com a categoria
		 int umaCategoria = categorias.get(i);
		 int imageResourceIconeCategoria = AssociaCategoriaComIcone.pegarIdImagemDaCategoriaMaior(getApplicationContext(),umaCategoria);
		 String nomeDaCategoria = SingletonArmazenaCategoriasDoJogo.getInstance().pegarNomeCategoria(umaCategoria);
		 textoLabelQuadradoCategoria.setText(nomeDaCategoria);
		 textoLabelQuadradoCategoria.setTypeface(tfBrPraTexto);
		 //funcao acima eh so para pegar o icone da categoria com base no nome dela,tipo R.id.icone_cotidiano
		 
		 imageViewQuadradoCategoria.setImageResource(imageResourceIconeCategoria); 
	 }
	 
	 
	 dialog.show();
}

/*quando o usuario clica no iconezinho da porta, ele deve entrar numa sala*/
public void entrarNaSala(SalaAbertaModoCasual salaVaiEntrar)
{
	 salaAtual = salaVaiEntrar;
	 //DesativarSalaEscolhidaDoBdTask taskDesativaSala = new DesativarSalaEscolhidaDoBdTask();
	 //String idSala = String.valueOf(salaAtual.getIdDaSala());
	 //taskDesativaSala.execute(idSala);
	 //startQuickGame(salaAtual.getIdDaSala());
	 this.loadingKanjisDoBd = new ProgressDialog(getApplicationContext());
	 this.loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.verificando_se_sala_aberta), getResources().getString(R.string.por_favor_aguarde));
	 TaskChecaSeSalaFoiDesativada taskVerificarSalaEstahAberta = new TaskChecaSeSalaFoiDesativada(this, this.loadingKanjisDoBd); 
	 taskVerificarSalaEstahAberta.execute(salaVaiEntrar);
}

public void aposVerificarQueSalaEstahAtiva(SalaAbertaModoCasual salaVaiEntrar)
{
	String idSala = String.valueOf(salaAtual.getIdDaSala());
	DesativarSalaEscolhidaDoBdTask taskDesativaSala = new DesativarSalaEscolhidaDoBdTask();
	taskDesativaSala.execute(idSala);
	startQuickGame(salaAtual.getIdDaSala());
}

public void mostrarMensagemErroSalaInativa()
{
	String avisoSemOponentesDoNivelConectados = getResources().getString(R.string.erro_sala_desativada);
	Toast.makeText(getApplicationContext(), avisoSemOponentesDoNivelConectados, Toast.LENGTH_LONG).show();
	
}


Thread threadAtualizaComNovasSalasAbertas;
public void solicitarBuscarSalasAbertas() {
	this.loadingKanjisDoBd = new ProgressDialog(getApplicationContext());
	this.loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.buscando_salas_abertas), getResources().getString(R.string.por_favor_aguarde));
	//ele está buscando por salas abertas, então melhor tirar as salas que ele criou se ainda estiverem ativas
	DesativarSalaPorIdUsuarioTask desativaSalasDoUsuario = new DesativarSalaPorIdUsuarioTask();
	String nomeUsuario = SingletonGuardaUsernameUsadoNoLogin.getInstance().getNomeJogador(getApplicationContext()); 
	desativaSalasDoUsuario.execute(nomeUsuario);
	BuscaSalasModoCasualTask taskBuscaSalasAbertas = new BuscaSalasModoCasualTask(loadingKanjisDoBd, this);
	taskBuscaSalasAbertas.execute("");
	//vamos aqui criar uma nova thread que vai esperar um tempo(de cinco em 5 segundos) para invocar a Task de buscar por novas salas abertas
	threadAtualizaComNovasSalasAbertas = new Thread()
	{
		public void run()
		{
			while(true)
			{
				try {
					
					Thread.sleep(10000);
					BuscaSalasModoCasualTask taskBuscaSalasAbertas = new BuscaSalasModoCasualTask(loadingKanjisDoBd, TelaModoCasual.this);
					taskBuscaSalasAbertas.execute("");
				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					return;
				}
				
				
			}
		}
	};
	threadAtualizaComNovasSalasAbertas.start();
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


private AdapterListViewSalasCriadas adapterSalasAtivas = null;
private ArrayList<SalaAbertaModoCasual> salasCarregadasModoCasual;
private SalaAbertaModoCasual salaAtual;

public void mostrarListaComSalasAposCarregar(ArrayList<SalaAbertaModoCasual> salasModoCasual, boolean mostrarAlertaNovasSalasCriadas)
{
	if(this.salasCarregadasModoCasual != null && mostrarAlertaNovasSalasCriadas == true)
	{
		
		//novas salas foram carregadas e não foi de quando o jogador entrou no listView!
		Animation animacaoPiscar = AnimationUtils.loadAnimation(this, R.anim.anim_piscar_alerta_salas);
		final TextView textoAlertaNovasSalas = (TextView) findViewById(R.id.alerta_novas_salas);
		textoAlertaNovasSalas.setVisibility(View.VISIBLE);
		textoAlertaNovasSalas.startAnimation(animacaoPiscar);
		
		new Timer().schedule(new TimerTask() {
		    @Override
		    public void run() {
		        
		        //If you want to operate UI modifications, you must run ui stuff on UiThread.
		        TelaModoCasual.this.runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		            	textoAlertaNovasSalas.setVisibility(View.INVISIBLE);
		            }
		        });
		    }
		}, 3000);	
	}
	
	
	ArmazenaKanjisPorCategoria armazenaKanjiCategoria = ArmazenaKanjisPorCategoria.pegarInstancia();
	  
	/*if(armazenaKanjiCategoria.getCategoriasDeKanjiArmazenadas().size() == 0)
	{
		//guest tb tem de carregar os kanjis
		SolicitaCategoriasDoJogoTask solicitaCategoriasPraFiltro = new SolicitaCategoriasDoJogoTask(loadingKanjisDoBd, this);
		solicitaCategoriasPraFiltro.execute("");
	}*/
	SolicitaCategoriasDoJogoTask solicitaCategoriasPraFiltro = new SolicitaCategoriasDoJogoTask(loadingKanjisDoBd, this);
	solicitaCategoriasPraFiltro.execute("");
	final ListView listViewSalas = (ListView) findViewById(R.id.lista_salas_abertas);
	listViewSalas.setOnScrollListener(this);
	//PARTE REFERENTE AO SCROLL DA LISTA POR TOQUE
	ImageView imagemSetaLista = (ImageView) findViewById(R.id.imagem_seta_continua_listview);
	imagemSetaLista.setOnTouchListener(new OnTouchListener() {
		boolean setaDescendo = true;//pro scroll da lista no toque
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), "clicou na seta de menu" + listaKanjisMemorizar.isInTouchMode(), Toast.LENGTH_SHORT).show();
			boolean usuarioEstahNoComecoDaLista = false;
			int firstVisibleItem = listViewSalas.getFirstVisiblePosition();
			int visibleItemCount = 0;
			int totalItemCount = listViewSalas.getAdapter().getCount();
			for (int i = 0; i <= listViewSalas.getLastVisiblePosition(); i++)
			{
			    if (listViewSalas.getChildAt(i) != null)
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
	        	listViewSalas.smoothScrollBy(20, 20); // For increment. 
	        	setaDescendo = true;
	        	
	        }
	        else if(usuarioEstahNoComecoDaLista == false && usuarioEstahNoFimDaLista == false)
	        {
	        	//setaApontaTemItem.setImageAlpha(1);
	        	if(setaDescendo == true)
	        	{
	        		listViewSalas.smoothScrollBy(20, 20); // For increment. 
	        	}
	        	else
	        	{
	        		listViewSalas.smoothScrollBy(-20, 20); // For increment.
	        	}
	        	
	        }
	        else if(usuarioEstahNoComecoDaLista == false && usuarioEstahNoFimDaLista == true)
	        {
	        	//setaApontaTemItem.setImageAlpha(1);
	        	listViewSalas.smoothScrollBy(-20, 20); // For increment.
	        	setaDescendo = false;
	        }
	        else
	        {
	        	if(setaDescendo == true)
	        	{
	        		listViewSalas.smoothScrollBy(20, 20); // For increment. 
	        	}
	        	else
	        	{
	        		listViewSalas.smoothScrollBy(-20, 20); // For increment.
	        	}
	        }
	        
           return true;
		}
	});
		
	
	
	int indiceObjetoDeCima = listViewSalas.getFirstVisiblePosition();
	int visiblePercent = getVisiblePercent(listViewSalas.getChildAt(0)); //pega o primeiro item VISIVEL da listview
	if(visiblePercent != 100)
	{
		//se o primeiro item visivel da listview nao estah 100% visivel, o primeiro item visivel deveria ser o proximo
		//antes tava dando bug por exemplo: O item mais acima atualmente visivel era soh 50% visivel.
		indiceObjetoDeCima = indiceObjetoDeCima + 1;
	}
	int novoIndiceObjetoDeCima = 0; 
	if(salasCarregadasModoCasual != null && salasCarregadasModoCasual.size() > 0)
	{
		SalaAbertaModoCasual salaVistaPorUltimo = salasCarregadasModoCasual.get(indiceObjetoDeCima);
		this.salasCarregadasModoCasual = new ArrayList<SalaAbertaModoCasual>();
		for(int y = 0; y < salasModoCasual.size(); y++)
		{
			SalaAbertaModoCasual umaSala = salasModoCasual.get(y);
			this.salasCarregadasModoCasual.add(umaSala);
		}
		
		for(int y = 0; y < salasCarregadasModoCasual.size(); y++)
		{
			SalaAbertaModoCasual umaSalaAberta = salasCarregadasModoCasual.get(y);
			if(umaSalaAberta.getIdDaSala() == salaVistaPorUltimo.getIdDaSala())
			{
				novoIndiceObjetoDeCima = y;
				break;
			}
		}
	}
	else
	{
		this.salasCarregadasModoCasual = new ArrayList<SalaAbertaModoCasual>();
		for(int y = 0; y < salasModoCasual.size(); y++)
		{
			SalaAbertaModoCasual umaSala = salasModoCasual.get(y);
			this.salasCarregadasModoCasual.add(umaSala);
		}
	}
	
	
	//comecar adapter para listview
	adapterSalasAtivas = new AdapterListViewSalasCriadas
			(this, R.layout.item_lista_sala, salasCarregadasModoCasual, this);

	 // Assign adapter to ListView
	 //Parcelable state = listViewSalas.onSaveInstanceState();//pegar estado atual da listView
	 listViewSalas.setAdapter(adapterSalasAtivas); 
	 if(indiceObjetoDeCima != 0)
	 {
		 //quer dizer que o usuário não estava no começo da lista.
		 listViewSalas.setSelection(novoIndiceObjetoDeCima);
	 }
	
	
	 
	 //em seguida, setar o conteudo do spinner de filtragem
	 SpinnerSelecionaMesmoQuandoVoltaAoMesmoItem spinnerFiltragem = (SpinnerSelecionaMesmoQuandoVoltaAoMesmoItem) findViewById(R.id.spinner_escolha_filtro);
	
	 SpinnerAdapter adapterDoSpinner = spinnerFiltragem.getAdapter();
	 if(adapterDoSpinner == null)
	 {
		 //primeira vez que o spinner está sendo apresentado. vamos montar o adapter dele.
		 String labelFiltroNenhum = getResources().getString(R.string.filtro_nenhum);
		 String labelFiltroCategoria = getResources().getString(R.string.filtro_categoria);
		 String labelFiltroRanking = getResources().getString(R.string.filtro_ranking);
		 String labelFiltroUsername = getResources().getString(R.string.filtro_username);
		 List<String> filtrosDeSala = new ArrayList<String>();
		 filtrosDeSala.add(labelFiltroNenhum);
		 filtrosDeSala.add(labelFiltroCategoria);
		 filtrosDeSala.add(labelFiltroRanking);
		 filtrosDeSala.add(labelFiltroUsername);
		 
		 /*ArrayAdapter<String> adapterSpinnerFiltrarSala = new ArrayAdapter<String>(this,
					R.layout.spinner_custom_style, filtrosDeSala);
				adapterSpinnerFiltrarSala.setDropDownViewResource(R.layout.spinner_vaixo_customizado);
				spinnerFiltragem.setAdapter(adapterSpinnerFiltrarSala);*/
		
		 SpinnerAdapterEstilizar spinnerAdapter = new SpinnerAdapterEstilizar(getApplicationContext(), R.layout.spinner_custom_style, filtrosDeSala);
		 spinnerAdapter.setDropDownViewResource(R.layout.spinner_vaixo_customizado);
		 spinnerFiltragem.setAdapter(spinnerAdapter);
		 SpinnerFiltroSalasAbertasListener listenerDoSpinnerFiltrarCategorias = new SpinnerFiltroSalasAbertasListener(this);
		 spinnerFiltragem.setOnItemSelectedListener(listenerDoSpinnerFiltrarCategorias);
	 }
	 
	 //e mudar o estilo do spinner
	 
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

public ArrayList<SalaAbertaModoCasual> getSalasCarregadasModoCasual() {
	return salasCarregadasModoCasual;
}
public void mostrarPopupPesquisarPorRanking()
{
	// Include dialog.xml file
    RelativeLayout layoutTelaFiltragemPrincipal = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
    layoutTelaFiltragemPrincipal.setVisibility(View.INVISIBLE);
    RelativeLayout layoutTelaFiltragemRanking = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_titulo_usuario);
    layoutTelaFiltragemRanking.setVisibility(View.VISIBLE);
    
    TextView titulo = (TextView)findViewById(R.id.labeltitulofiltrartitulo);
    String fontpath = "fonts/Wonton.ttf";
    Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
    titulo.setTypeface(tf);
    ImageView botaoCancelar = (ImageView)findViewById(R.id.botao_cancelar_popup_filtrar_ranking);
    botaoCancelar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			RelativeLayout layoutTelaFiltragemPrincipal = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
		    layoutTelaFiltragemPrincipal.setVisibility(View.VISIBLE);
		    RelativeLayout layoutTelaFiltragemRanking = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_titulo_usuario);
		    layoutTelaFiltragemRanking.setVisibility(View.INVISIBLE);
			
		}
	});
    
    final String [] arrayTitulosSumoSensei = new String [9];
    arrayTitulosSumoSensei[0] = getResources().getString(R.string.sumo_ranking_1);
    arrayTitulosSumoSensei[1] = getResources().getString(R.string.sumo_ranking_2);
    arrayTitulosSumoSensei[2] = getResources().getString(R.string.sumo_ranking_3);
    arrayTitulosSumoSensei[3] = getResources().getString(R.string.sumo_ranking_4);
    arrayTitulosSumoSensei[4] = getResources().getString(R.string.sumo_ranking_5);
    arrayTitulosSumoSensei[5] = getResources().getString(R.string.sumo_ranking_6);
    arrayTitulosSumoSensei[6] = getResources().getString(R.string.sumo_ranking_7);
    arrayTitulosSumoSensei[7] = getResources().getString(R.string.sumo_ranking_8);
    arrayTitulosSumoSensei[8] = getResources().getString(R.string.sumo_ranking_9);
    
    final Integer [] idsImagensTitulosSumoSensei = new Integer [9];
    idsImagensTitulosSumoSensei[0] = getResources().getIdentifier("titulo_sumo_sensei_grande", "drawable", getPackageName());
    idsImagensTitulosSumoSensei[1] = getResources().getIdentifier("titulo_sumo_treme_terra_grande", "drawable", getPackageName());
    idsImagensTitulosSumoSensei[2] = getResources().getIdentifier("titulo_sumo_muralha_grande", "drawable", getPackageName());
    idsImagensTitulosSumoSensei[3] = getResources().getIdentifier("titulo_sumo_parede_grande", "drawable", getPackageName());
    idsImagensTitulosSumoSensei[4] = getResources().getIdentifier("titulo_sumo_eu_sei_grande", "drawable", getPackageName());
    idsImagensTitulosSumoSensei[5] = getResources().getIdentifier("titulo_sumo_cimento_grande", "drawable", getPackageName());
    idsImagensTitulosSumoSensei[6] = getResources().getIdentifier("titulo_sumo_acho_que_sei_grande", "drawable", getPackageName());
    idsImagensTitulosSumoSensei[7] = getResources().getIdentifier("titulo_sumo_fraquinho_grande", "drawable", getPackageName());
    idsImagensTitulosSumoSensei[8] = getResources().getIdentifier("titulo_sumo_nao_sei_grande", "drawable", getPackageName());
    
    String fontPath = "fonts/Wonton.ttf";
    Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath);
    
  //comecar adapter para listview
  	/*adapterSalasAtivas = new AdapterListViewSalasCriadas
  			(this, R.layout.item_lista_sala, salasCarregadasModoCasual, this);

  	 // Assign adapter to ListView
  	 //Parcelable state = listViewSalas.onSaveInstanceState();//pegar estado atual da listView
  	ListView listViewEscolhaRanking =(ListView)this.findViewById(R.id.listViewEscolhaRanking);
  	 listViewEscolhaRanking.setAdapter(adapterSalasAtivas); */
    ArrayList<RankingEImagem> conjuntoTextoEImagensDoRankingPraFiltro = new ArrayList<RankingEImagem>();
    for(int i = 0; i < idsImagensTitulosSumoSensei.length; i++ )
    {
    	RankingEImagem umRankingComImagem = new RankingEImagem();
    	umRankingComImagem.setIdImagemPosicaoNoRanking(idsImagensTitulosSumoSensei[i]);
    	umRankingComImagem.setNomeDaPosicaoNoRanking(arrayTitulosSumoSensei[i]);
    	conjuntoTextoEImagensDoRankingPraFiltro.add(umRankingComImagem);
    }
    
    final AdapterListViewRankingDoUsuario adapter = new AdapterListViewRankingDoUsuario(this,R.layout.item_lista_ranking_usuario , conjuntoTextoEImagensDoRankingPraFiltro,tf2,true);
    adapter.setLayoutUsadoParaTextoEImagem(R.layout.item_lista_ranking_usuario);
	final ListView listViewEscolhaRanking =(ListView)this.findViewById(R.id.listViewEscolhaRanking);
	listViewEscolhaRanking.setVisibility(View.VISIBLE);
	listViewEscolhaRanking.setAdapter(adapter);
	adapter.notifyDataSetChanged();
	//listViewEscolhaRanking.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	
	listViewEscolhaRanking.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) 
        {

            for (int j = 0; j < parent.getChildCount(); j++)
            {
            	
            	parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);
            }
            
            //antes era 5db1ff
        	view.setBackgroundColor(Color.parseColor("#003461"));
        	
        	String rankingAtualmenteSelecionado = arrayTitulosSumoSensei[position];
            adapter.setRankingAtualmenteSelecionado(rankingAtualmenteSelecionado);
            int idRankingSelecionado = position + 1;
            adapter.setIdRankingAtualmenteSelecionado(idRankingSelecionado);
        }
    });
	listViewEscolhaRanking.setOnScrollListener(new OnScrollListener() {
		
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
	        
	        ImageView setaApontaTemItem = (ImageView) findViewById(R.id.imagem_seta_continua_listview_filtro_ranking);
	        
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
	ImageView imagemSetaLista = (ImageView) findViewById(R.id.imagem_seta_continua_listview_filtro_ranking);
	imagemSetaLista.setOnTouchListener(new OnTouchListener() {
		boolean setaDescendo = true;//pro scroll da lista no toque
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			//Toast.makeText(getApplicationContext(), "clicou na seta de menu" + listaKanjisMemorizar.isInTouchMode(), Toast.LENGTH_SHORT).show();
			boolean usuarioEstahNoComecoDaLista = false;
			int firstVisibleItem = listViewEscolhaRanking.getFirstVisiblePosition();
			int visibleItemCount = 0;
			int totalItemCount = listViewEscolhaRanking.getAdapter().getCount();
			for (int i = 0; i <= listViewEscolhaRanking.getLastVisiblePosition(); i++)
			{
			    if (listViewEscolhaRanking.getChildAt(i) != null)
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
	        	listViewEscolhaRanking.smoothScrollBy(10, 20); // For increment. 
	        	setaDescendo = true;
	        	
	        }
	        else if(usuarioEstahNoComecoDaLista == false && usuarioEstahNoFimDaLista == false)
	        {
	        	//setaApontaTemItem.setImageAlpha(1);
	        	if(setaDescendo == true)
	        	{
	        		listViewEscolhaRanking.smoothScrollBy(10, 20); // For increment. 
	        	}
	        	else
	        	{
	        		listViewEscolhaRanking.smoothScrollBy(-10, 20); // For increment.
	        	}
	        	
	        }
	        else if(usuarioEstahNoComecoDaLista == false && usuarioEstahNoFimDaLista == true)
	        {
	        	//setaApontaTemItem.setImageAlpha(1);
	        	listViewEscolhaRanking.smoothScrollBy(-10, 20); // For increment.
	        	setaDescendo = false;
	        }
	        else
	        {
	        	if(setaDescendo == true)
	        	{
	        		listViewEscolhaRanking.smoothScrollBy(10, 20); // For increment. 
	        	}
	        	else
	        	{
	        		listViewEscolhaRanking.smoothScrollBy(-10, 20); // For increment.
	        	}
	        }
	        
           return true;
		}
	});
	
	Button botaoFiltrarRanking = (Button) findViewById(R.id.botao_filtrar_por_ranking);
	botaoFiltrarRanking.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int idRankingAtualmenteSelecionado = adapter.getIdRankingAtualmenteSelecionado();
			if(idRankingAtualmenteSelecionado >= 0)
			{
				usuarioEscolheuNivelOponenteFiltrarSala(idRankingAtualmenteSelecionado);
			}
			else
			{
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.erro_faltou_escolher_nivel), Toast.LENGTH_SHORT).show();
			}
			
		}
	});
	
}

private void usuarioEscolheuNivelOponenteFiltrarSala(int nivelSelecionado) {
	Toast.makeText(getApplicationContext(), "clicou titulo " + nivelSelecionado, Toast.LENGTH_SHORT).show();
	RelativeLayout layoutTelaFiltragemPrincipal = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
    layoutTelaFiltragemPrincipal.setVisibility(View.VISIBLE);
    RelativeLayout layoutTelaFiltragemRanking = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_titulo_usuario);
    layoutTelaFiltragemRanking.setVisibility(View.INVISIBLE);
	
	loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.buscando_salas_abertas), getResources().getString(R.string.por_favor_aguarde));
	BuscaSalasModoCasualComArgumentoTask buscaSalas = new BuscaSalasModoCasualComArgumentoTask(loadingKanjisDoBd, TelaModoCasual.this);
	
	String nomeDoNivelDoOponenteNoBD = "Ranking_" + nivelSelecionado;
	buscaSalas.execute("nivel_adversario", nomeDoNivelDoOponenteNoBD);
}

public void mostrarPopupPesquisarPorCategorias()
{
	TextView titulo = (TextView)findViewById(R.id.labeltitulofiltrarcategoria);
    String fontpath = "fonts/Wonton.ttf";
    Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
    titulo.setTypeface(tf);
	
	LinkedList<String> categorias = 
			  ArmazenaKanjisPorCategoria.pegarInstancia().getCategoriasDeKanjiArmazenadas("5");
	LinkedList<Integer> idsCategorias = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdsCategorias(categorias);
	int tamanhoLista1 = categorias.size()/2;
	final String[] arrayCategorias = new String[tamanhoLista1];
	final int [] arrayIdCategorias = new int [tamanhoLista1];
	final String[] arrayCategorias2 = new String[categorias.size() - tamanhoLista1];
	final int [] arrayIdCategorias2 = new int [categorias.size() - tamanhoLista1];
	final String[] arrayCategoriasParaListview = new String[tamanhoLista1]; // esses daqui tem quantos kanjis e a categoria em kanji tb
	final String[] arrayCategorias2ParaListView = new String[categorias.size() - tamanhoLista1];
	int iteradorCategorias1 = 0;
	int iteradorCategorias2 = 0;
	
	
	
	for(int i = 0; i < categorias.size(); i++)
	{
		String umaCategoria = categorias.get(i);
		int umIdCategoria = idsCategorias.get(i);
		if(iteradorCategorias1 < arrayCategorias.length)
		{
			int quantasPalavrasTemACategoria = 
					ArmazenaKanjisPorCategoria.pegarInstancia().quantasPalavrasTemACategoria(umaCategoria);
			String categoriaEscritaEmKanji = RetornaNomeCategoriaEscritaEmKanji.retornarNomeCategoriaEscritaEmKanji(umaCategoria);
			String textoDaCategoria = categoriaEscritaEmKanji;
			//String categoriaEscritaEmKanji = RetornaNomeCategoriaEscritaEmKanji.retornarNomeCategoriaEscritaEmKanji(umaCategoria);
			textoDaCategoria = textoDaCategoria + "\n" + umaCategoria + " (" + String.valueOf(quantasPalavrasTemACategoria) + ")";
			arrayCategorias[iteradorCategorias1] = umaCategoria;
			arrayCategoriasParaListview[iteradorCategorias1] = textoDaCategoria;
			arrayIdCategorias[iteradorCategorias1] = umIdCategoria;
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
			arrayCategorias2[iteradorCategorias2] = umaCategoria;
			arrayCategorias2ParaListView[iteradorCategorias2] = textoDaCategoria;
			arrayIdCategorias2[iteradorCategorias2] = umIdCategoria;
			iteradorCategorias2 = iteradorCategorias2 + 1;
		}
	}
	Integer[] imageId = new Integer[arrayCategorias.length];
	Integer[] imageId2 = new Integer[arrayCategorias2.length];
	
	for(int j = 0; j < arrayCategorias.length; j++)
	{
		int umIdCategoria = arrayIdCategorias[j];
		int idImagem = AssociaCategoriaComIcone.pegarIdImagemDaCategoria(getApplicationContext(),umIdCategoria);
		imageId[j] = idImagem;
	}
	for(int k = 0; k < arrayCategorias2.length; k++)
	{
		int umIdCategoria = arrayIdCategorias2[k];
		int idImagem = AssociaCategoriaComIcone.pegarIdImagemDaCategoria(getApplicationContext(),umIdCategoria);
		imageId2[k] = idImagem;
	}

	
    // arraylist to keep the selected items
   
	RelativeLayout layoutPrincipalFiltragem = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
	RelativeLayout layoutFiltragemCategorias = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_categoria);
	
	layoutPrincipalFiltragem.setVisibility(View.INVISIBLE);
	layoutFiltragemCategorias.setVisibility(View.VISIBLE);
    
	ImageView botaoCancelar = (ImageView)findViewById(R.id.botao_cancelar_popup_filtrar_categorias);
    botaoCancelar.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			RelativeLayout layoutPrincipalFiltragem = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
			RelativeLayout layoutFiltragemCategorias = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_categoria);
			
			layoutPrincipalFiltragem.setVisibility(View.VISIBLE);
			layoutFiltragemCategorias.setVisibility(View.INVISIBLE);
		}
	});
    
    final boolean[] categoriaEstahSelecionada = new boolean[arrayCategorias.length];
    final boolean[] categoriaEstahSelecionada2 = new boolean[arrayCategorias2.length];
	for(int l = 0; l < arrayCategorias.length; l++)
	{
		categoriaEstahSelecionada[l] = false;
	}
	for(int m = 0; m < arrayCategorias2.length; m++)
	{
		categoriaEstahSelecionada2[m] = false;
	}
	Typeface typeFaceFonteTextoListViewIconeETexto = this.escolherFonteDoTextoListViewIconeETexto();
	AdapterListViewIconeETexto adapter = new AdapterListViewIconeETexto(TelaModoCasual.this, arrayCategoriasParaListview, imageId,typeFaceFonteTextoListViewIconeETexto,true, R.layout.list_item_icone_e_texto_casual, true);
    ListView list=(ListView)findViewById(R.id.listaCategoriasPesquisaSalas1filtro);
    
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
                    	texto.setTextColor(Color.argb(255, 255, 255, 255));
                    }
                    else
                    {
                    	categoriaEstahSelecionada[position] = false;
                    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
                    	TextView texto = (TextView) view.findViewById(R.id.txt);
                    	texto.setTextColor(Color.argb(130, 255, 255, 255));
                    	imageView.setAlpha(130);
                    }
                }
            });
        
        
        AdapterListViewIconeETexto adapter2 = new AdapterListViewIconeETexto(TelaModoCasual.this, arrayCategorias2ParaListView, imageId2,typeFaceFonteTextoListViewIconeETexto,true, R.layout.list_item_icone_e_texto_casual, true);
	    ListView list2=(ListView)findViewById(R.id.listaCategoriasPesquisaSalas2filtro);
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
	                    	texto.setTextColor(Color.argb(255, 255, 255, 255));
	                    }
	                    else
	                    {
	                    	categoriaEstahSelecionada2[position] = false;
	                    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
	                    	TextView texto = (TextView) view.findViewById(R.id.txt);
	                    	texto.setTextColor(Color.argb(130, 255, 255, 255));
	                    	imageView.setAlpha(130);
	                    }
	                }
	            });

	        
	        
	      //falta definir a ação para o botão ok desse popup das categorias
	  	  Button botaoOk = (Button) findViewById(R.id.botao_filtrar_por_categoria);
	  	  final TelaModoCasual telaModoCasual = this;
	  	  botaoOk.setOnClickListener(new Button.OnClickListener() 
	  	  {
	  		  public void onClick(View v) 
	  	      {
	  			RelativeLayout layoutPrincipalFiltragem = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
				RelativeLayout layoutFiltragemCategorias = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_categoria);
				
				layoutPrincipalFiltragem.setVisibility(View.VISIBLE);
				layoutFiltragemCategorias.setVisibility(View.INVISIBLE);
				
	  			  loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.buscando_salas_abertas), getResources().getString(R.string.por_favor_aguarde));
	  		    	BuscaSalasModoCasualComArgumentoTask taskBuscaSalasModoCasual12 =
	  		    				new BuscaSalasModoCasualComArgumentoTask(loadingKanjisDoBd, telaModoCasual);
	  		    	String categoriasSeparadasPorVirgula = "";
	  		    	for(int n = 0; n < categoriaEstahSelecionada.length; n++)
	  		    	{
	  		    		if(categoriaEstahSelecionada[n] == true)
	  		    		{
	  		    			//o usuario quer procurar com essa categoria
	  		    			String umaCategoria = arrayCategorias[n];
	  		    			
	  		    			categoriasSeparadasPorVirgula = categoriasSeparadasPorVirgula + umaCategoria + ",";
	  		    			
	  		    		}
	  		    	}
	  		    	for(int o = 0; o < categoriaEstahSelecionada2.length; o++)
	  		    	{
	  		    		if(categoriaEstahSelecionada2[o] == true)
	  		    		{
	  		    			//o usuario quer procurar com essa categoria
	  		    			String umaCategoria = arrayCategorias2[o];
	  		    			
	  		    			categoriasSeparadasPorVirgula = categoriasSeparadasPorVirgula + umaCategoria + ",";
	  		    			
	  		    		}
	  		    	}
	  		    	
	  		    	
	  		    	if(categoriasSeparadasPorVirgula.length() > 1)
	  		    	{
	  		    		categoriasSeparadasPorVirgula = categoriasSeparadasPorVirgula.substring(0,categoriasSeparadasPorVirgula.length()-1);
	  		    	}
	  		    	taskBuscaSalasModoCasual12.execute("categorias",categoriasSeparadasPorVirgula);    
	  		    	
	  		    	//A STRING SCIMA ESTAH NORMAL COM AS CATEGORIAS. POR ALGUM MOTIVO O LISTVIEW NAO ESTAH SENDO ATUALIZADO COM O RESULTADO DA BUSCA
	  	      }
	  	  });	
	
}

private Typeface escolherFonteDoTextoListViewIconeETexto()
{
	String fontPath = "fonts/Wonton.ttf";
    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
    return tf;
}

public void mostrarPopupPesquisarPorUsername()
{
	TextView titulo = (TextView)findViewById(R.id.labeltitulofiltrarusername);
    String fontpath = "fonts/Wonton.ttf";
    Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
    titulo.setTypeface(tf);
    
	RelativeLayout layoutCampoPrincipalFiltreagem = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
	layoutCampoPrincipalFiltreagem.setVisibility(View.INVISIBLE);
	RelativeLayout layoutCampoFiltrarUsername = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_username);
	layoutCampoFiltrarUsername.setVisibility(View.VISIBLE);
	
	ImageView botaoCancelar = (ImageView)findViewById(R.id.botao_cancelar_popup_filtrar_username);
    botaoCancelar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			RelativeLayout layoutCampoPrincipalFiltreagem = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
			layoutCampoPrincipalFiltreagem.setVisibility(View.VISIBLE);
			RelativeLayout layoutCampoFiltrarUsername = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_username);
			layoutCampoFiltrarUsername.setVisibility(View.INVISIBLE);
			
		}
	});
    Button botaoFiltrarSalasPorUsuario = (Button)findViewById(R.id.botao_filtrar_por_username);
    botaoFiltrarSalasPorUsuario.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			EditText textfieldEspecificouUsername = (EditText) findViewById(R.id.campo_preencher_username_filtro);
			String nomeDoOponente = textfieldEspecificouUsername.getText().toString();
			textfieldEspecificouUsername.setText("");//limpar o editText
			//FAZER DESAPARECER TECLADO VIRTUAL
			InputMethodManager inputManager = (InputMethodManager)
	                getSystemService(Context.INPUT_METHOD_SERVICE); 
			View currentFocus = getCurrentFocus();
			if(currentFocus != null)
			{
				inputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(),
		                   InputMethodManager.HIDE_NOT_ALWAYS);
				//FIM DO FAZER DESAPARECER TECLADO VIRTUAL
			}
			
			if(nomeDoOponente != null && nomeDoOponente.length() > 0)
			{
				RelativeLayout layoutCampoPrincipalFiltreagem = (RelativeLayout) findViewById(R.id.id_componentes_filtragem);
				layoutCampoPrincipalFiltreagem.setVisibility(View.VISIBLE);
				RelativeLayout layoutCampoFiltrarUsername = (RelativeLayout) findViewById(R.id.id_componentes_filtrar_username);
				layoutCampoFiltrarUsername.setVisibility(View.INVISIBLE);
				loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.buscando_salas_abertas), getResources().getString(R.string.por_favor_aguarde));
				BuscaSalasModoCasualComArgumentoTask buscaSalas = new BuscaSalasModoCasualComArgumentoTask(loadingKanjisDoBd, TelaModoCasual.this);
				buscaSalas.execute("username", nomeDoOponente);
			}
		}
	});
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
			Toast toastAvisoChikaraMizu = 
					Toast.makeText(getApplicationContext(), avisoChikaramizu, Toast.LENGTH_SHORT);
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
			Toast toastAvisoTegata = Toast.makeText(getApplicationContext(), mensagemTegata , Toast.LENGTH_SHORT);
			toastAvisoTegata.setGravity(Gravity.CENTER, 0, 0);
			toastAvisoTegata.show();
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
	Toast.makeText(getApplicationContext(), "startedQuickGame=" + idDaPartida, Toast.LENGTH_SHORT).show();
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
    case MOSTRAR_LOGIN_PLAY:
    	if (responseCode == Activity.RESULT_OK) {
    		SingletonDeveMostrarTelaDeLogin deveMostrarTelaLogin = SingletonDeveMostrarTelaDeLogin.getInstance();
    		deveMostrarTelaLogin.setMostrarTelaLoginPlayTemporario(false);
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
	Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " pressionou back;" );
	boolean mudarParaTelaInicialSumoSensei = false;
	if(mCurScreen == R.id.screen_main)
	{
		mudarParaTelaInicialSumoSensei = true;
	}
    leaveRoom();
    if(mudarParaTelaInicialSumoSensei == true)
    {
    	Intent intentVoltaMenuPrincipal = new Intent(TelaModoCasual.this, MainActivity.class);
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
	 /*if(threadAnimaSetinhaSumoEsquerda != null)
	 {
		 threadAnimaSetinhaSumoEsquerda.interrupt();
	 }
	 if(threadAnimaSetinhaSumoDireita != null)
	 {
		 threadAnimaSetinhaSumoDireita.interrupt();
	 }*/
	if(salaAtual != null)
	{
		DesativarSalaEscolhidaDoBdTask taskDesativaSala = new DesativarSalaEscolhidaDoBdTask();
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
int musicaDeFundoHeadstart = R.raw.headstart;
if(musicaDeFundoAtual == musicaDeFundoHeadstart)
{
	this.mudarMusicaDeFundo(R.raw.lazy_susan);
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
		Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " recebeu mensagem oponenteacertou;" );
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
	    Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " atualizou animação dos sumozinhos na tela" );
	    Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " terminou de responder à mensagem ponenteAcertou;" );
	   
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
			        TelaModoCasual.this.runOnUiThread(new Runnable() {
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
			        TelaModoCasual.this.runOnUiThread(new Runnable() {
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
	    	Toast toastAvisoNaoSelecionouCategorias = Toast.makeText(getApplicationContext(), getResources().getString(R.string.aviso_nao_selecionou_categorias), Toast.LENGTH_SHORT);
			toastAvisoNaoSelecionouCategorias.show();
	    }
	    
	}
	else if(mensagem.contains("terminou escolher nova partida::") == true)
	{
		Log.i("TelaModoCasual", "jogador terminou de receber mensagem terminou escolher nova partida::");
		
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
	        
	    Log.i("TelaModoCasual", "jogador terminou de se preparar para nova partida::");    
	   
	}
	else if(mensagem.contains("oponenteganhou;"))
	{
		Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " recebeu mensagem oponenteGanhou;" );
		//o jogo acabou e o oponente ganhou... a menos que tenha teppo tree
		GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		
		boolean usuarioSeDefendeu = guardaDadosDaPartida.usuarioTemItemIncorporado("teppotree");
		if(usuarioSeDefendeu == false)
		{
			guardaDadosDaPartida.setPosicaoSumozinhoDoJogadorNaTela(-6);
			Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " não se defendeu e morreu;" );
		}
	   
	    if(usuarioSeDefendeu == true)
	    {
	    	Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " se defendeu e não morreu;" );
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
	    	Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " está terminando jogo..." );
	    	this.terminarJogoMultiplayer();
	    }
	}
	else if(mensagem.contains("terminouJogo;"))
	{
		ProgressDialog barraProgressoFinalTerminouJogo =  ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.aviso_tempo_acaboou), getResources().getString(R.string.por_favor_aguarde));
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
    	Toast toastAvisoRuimTegata = Toast.makeText(getApplicationContext(), avisoTegata , Toast.LENGTH_SHORT);
		toastAvisoRuimTegata.setGravity(Gravity.CENTER, 0, 0);
		toastAvisoRuimTegata.show();
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
			Toast toastAvisoRuimTeppoTree = Toast.makeText(getApplicationContext(), avisoJogadorDefendeu , Toast.LENGTH_SHORT);
			toastAvisoRuimTeppoTree.setGravity(Gravity.CENTER, 0, 0);
			toastAvisoRuimTeppoTree.show();
		}
		this.aposDizerProOponenteQueAcertouKanji(jogadorDefendeu);
	}
	else if(mensagem.contains("euDefendiDoOponenteGanhou;"))
	{
		String [] mensagemSplitada = mensagem.split(";");
		String stringJogadorDefendeu = mensagemSplitada[1];
		if(stringJogadorDefendeu.compareTo("true") == 0)
		{
			Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " se defendeu do ultimo golpe;" );
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
			Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " está terminando o jogo..." );
			this.terminarJogoMultiplayer();
		}
	}
	else if(mensagem.contains("username=") == true)
	{
		//vai começar uma nova partida, seta o boolean de jogoJahTerminou pra false...
		this.jogoJahTerminou = false;
		Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " recebeu username do oponente..." );
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
    R.id.button_create_room,R.id.botao_buscar_salas
};

// This array lists all the individual screens our game has.
final static int[] SCREENS = {
    R.id.screen_game, R.id.screen_main, R.id.tela_buscar_salas,
    R.id.screen_wait,R.id.decidindoQuemEscolheACategoria, R.id.tela_escolha_categoria, R.id.screen_final_partida
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
TextView textoExplicacaoCriarSala = (TextView) findViewById(R.id.labelexplicacaoCriarSala);
textoExplicacaoCriarSala.setTypeface(tfBrPraTexto);
TextView textoExplicacaoBuscarSalas = (TextView) findViewById(R.id.labelExplicacaoBuscarSalas);
textoExplicacaoBuscarSalas.setTypeface(tfBrPraTexto);

euEscolhoACategoria = false;//não sabemos se ele será host ou não



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

private void jogadorClicouNaAlternativa(int idDoBotaoQueUsuarioClicou)
{
	String nomeUsuario = this.nomeUsuario;
	Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " clicou na alternativa" );
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
				Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " acertou" );
				guardaDadosDaPartida.adicionarKanjiAcertadoNaPartida(ultimoKanjiTreinado);
				boolean jogadorTemChikaramizu = guardaDadosDaPartida.usuarioTemItemIncorporado("chikaramizu");
				if((jogadorTemChikaramizu == false && guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela() < 5) ||
						(jogadorTemChikaramizu == true && guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela() < 4))
				{
					this.reproduzirSfx("noJogo-jogadorAcertouAlternativa");
					
					Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " mandou mensagem de acertou para oponente" );
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
					
					Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " ocultou botão após acertar" );
					//manda Mensagem Pro Oponente... vamos precisar ver se o usuario tem itensIncorporados
					String mensagemParaOponente = "oponenteacertou;";
					mensagemParaOponente = mensagemParaOponente + "chikaramizu=" + jogadorTemChikaramizu + ";";
					
					this.mandarMensagemMultiplayer(mensagemParaOponente);
					
				}
				else
				{
					//jogador ganhou a partida, a menos que adversario tenha usado teppo tree 
					Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " mandou mensagem para oponente oponenteganhou;" );
					this.mandarMensagemMultiplayer("oponenteganhou;");
				}
				
				
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
				if(this.estahComAnimacaoTegata == false)
				{
					Animation animacaoTransparente = AnimationUtils.loadAnimation(this, R.anim.anim_transparente_botao);
					botaoAnswer1.startAnimation(animacaoTransparente);
					botaoAnswer2.startAnimation(animacaoTransparente);
					botaoAnswer3.startAnimation(animacaoTransparente);
					botaoAnswer4.startAnimation(animacaoTransparente);
				}
				
				/*botaoAnswer1.getBackground().setAlpha(128);
				botaoAnswer2.getBackground().setAlpha(128);
				botaoAnswer3.getBackground().setAlpha(128);
				botaoAnswer4.getBackground().setAlpha(128);*/
				Toast.makeText(this, getResources().getString(R.string.errou_traducao_kanji) , Toast.LENGTH_SHORT).show();
				new Timer().schedule(new TimerTask() {
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
				            	/*botaoAnswer1.getBackground().setAlpha(255);
								botaoAnswer2.getBackground().setAlpha(255);
								botaoAnswer3.getBackground().setAlpha(255);
								botaoAnswer4.getBackground().setAlpha(255);*/
				            }
				        });
				    }
				}, 3000);
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
	 if(threadAnimaSetinhaSumoEsquerda != null)
	 {
		 threadAnimaSetinhaSumoEsquerda.interrupt();
	 }
	 if(threadAnimaSetinhaSumoDireita != null)
	 {
		 threadAnimaSetinhaSumoDireita.interrupt();
	 }
	Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " está chamando método terminarJogoMultiplayer" );
	if(jogoJahTerminou == false)
	{
		Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " está terminando o jogo pq jogoJahTerminou == false" );
		if(this.timerFimDeJogo != null)
		{
			timerFimDeJogo.cancel();
		}
		this.mudarMusicaDeFundo(R.raw.lazy_susan);
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
		
		this.popupDoChat = new Dialog(TelaModoCasual.this);
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
		GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		int posicaoAntigaSumozinho = guardaDadosDaPartida.getPosicaoSumozinhoDoJogadorNaTela();
		
		
		String nomeUsuarioGanhou = "";
		RelativeLayout backgroundFinalPartida = (RelativeLayout) findViewById(R.id.screen_final_partida);
		
		if(posicaoAntigaSumozinho > 0)
		{
			//quem ganhou foi o usuario , nao o oponente!
			nomeUsuarioGanhou = nomeJogadorEncurtado;
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
			 
			nomeUsuarioGanhou = nomeAdversarioEncurtado;
			
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
		Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " jogoJahTerminou = true" );
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
	Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " atualizou animacao sumozinhos na arena" );
	this.prepararNovaPartida(false);
	Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " terminou preparação para nova partida" );
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
	
	solicitarPorKanjisPraTreino(true);
	switchToScreen(R.id.tela_escolha_categoria);
	String fontpath = "fonts/Wonton.ttf";
    Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
    TextView tituloEscolhaCategorias = (TextView) findViewById(R.id.textoTituloEscolhaCategorias);
    tituloEscolhaCategorias.setTypeface(tf);
	//adicionarListenerBotao();
}

/*NOVO DA ACTIVITY REFERENTE A SELECIONAR CATEGORIAS */
private MyCustomAdapter dataAdapter = null;
private ProgressDialog loadingKanjisDoBd;
private static String jlptEnsinarNaFerramenta = "5";

public void solicitarPorKanjisPraTreino(boolean abrirTelaSelecaoCategoriaApos) {
	this.loadingKanjisDoBd = new ProgressDialog(getApplicationContext());
	this.loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.carregando_kanjis_remotamente), getResources().getString(R.string.por_favor_aguarde));
	if(abrirTelaSelecaoCategoriaApos == true)
	{
		SolicitaKanjisParaTreinoTask solicitaKanjisPraTreino = new SolicitaKanjisParaTreinoTask(this.loadingKanjisDoBd, this, this);
		solicitaKanjisPraTreino.execute("");
	}
	else
	{
		SolicitaCategoriasDoJogoTask solicitaCategoriasPraFiltro = new SolicitaCategoriasAbreSelecaoCategoriasTask(loadingKanjisDoBd, this);
		solicitaCategoriasPraFiltro.execute("");
	}
	 
}
  
public void mostrarListaComKanjisAposCarregar() {
	 
	 LinkedList<String> categorias = 
			  ArmazenaKanjisPorCategoria.pegarInstancia().getCategoriasDeKanjiArmazenadas("5");
	 LinkedList<Integer> idsDasCategorias = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdsCategorias(categorias);
	for(int p = 0; p < categorias.size(); p++)
	{
		String umaCategoria = categorias.get(p);
		if(umaCategoria.compareToIgnoreCase("Números") == 0 || umaCategoria.compareToIgnoreCase("Numbers") == 0  )
		{
			categorias.remove(p);
			categorias.addLast(umaCategoria);
			int idUmaCategoria = idsDasCategorias.get(p);
			idsDasCategorias.remove(p);
			idsDasCategorias.addLast(idUmaCategoria);
			
		}
	}
	int tamanhoLista1 = 4;
	final String[] arrayCategorias = new String[tamanhoLista1];
	final int [] arrayIdsCategorias = new int [tamanhoLista1];
	final String[] arrayCategorias2 = new String[categorias.size() - tamanhoLista1];
	final int [] arrayIdsCategorias2 = new int[categorias.size() - tamanhoLista1];
	final String[] arrayCategoriasParaListview = new String[tamanhoLista1]; // esses daqui tem quantos kanjis e a categoria em kanji tb
	final String[] arrayCategorias2ParaListView = new String[categorias.size() - tamanhoLista1];
	
	int iteradorCategorias1 = 0;
	int iteradorCategorias2 = 0;
	
	for(int i = 0; i < categorias.size(); i++)
	{
		String umaCategoria = categorias.get(i);
		int umIdCategoria = idsDasCategorias.get(i);
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
			arrayIdsCategorias[iteradorCategorias1] = umIdCategoria;
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
			arrayIdsCategorias2[iteradorCategorias2] = umIdCategoria;
			iteradorCategorias2 = iteradorCategorias2 + 1;
		}
	}
	Integer[] imageId = new Integer[arrayCategoriasParaListview.length];
	Integer[] imageId2 = new Integer[arrayCategorias2ParaListView.length];
	
	for(int j = 0; j < arrayCategorias.length; j++)
	{
		int umIdCategoria = arrayIdsCategorias[j];
		int idImagem = AssociaCategoriaComIcone.pegarIdImagemDaCategoriaMaior(getApplicationContext(),umIdCategoria);
		imageId[j] = idImagem;
	}
	for(int k = 0; k < arrayCategorias2.length; k++)
	{
		int umIdCategoria = arrayIdsCategorias2[k];
		int idImagem = AssociaCategoriaComIcone.pegarIdImagemDaCategoriaMaior(getApplicationContext(),umIdCategoria);
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
	AdapterListViewIconeETexto adapter = new AdapterListViewIconeETexto(TelaModoCasual.this, arrayCategoriasParaListview, imageId,typeFaceFonteTextoListViewIconeETexto,true, R.layout.list_item_icone_e_texto_casual, true);
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
                   	TextView texto = (TextView) view.findViewById(R.id.txt);
                   	texto.setTextColor(Color.argb(255, 255, 255, 255));
                   }
                   else
                   {
                   	categoriaEstahSelecionada[position] = false;
                   	ImageView imageView = (ImageView) view.findViewById(R.id.img);
                   	TextView texto = (TextView) view.findViewById(R.id.txt);
                   	imageView.setAlpha(130);
                   	texto.setTextColor(Color.argb(130, 255, 255, 255));
                   	
                   }
               }
           });
       
       
       AdapterListViewIconeETexto adapter2 = new AdapterListViewIconeETexto(TelaModoCasual.this, arrayCategorias2ParaListView, imageId2,typeFaceFonteTextoListViewIconeETexto,true, R.layout.list_item_icone_e_texto_casual, true);
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
	                    	TextView texto = (TextView) view.findViewById(R.id.txt);
	                    	texto.setTextColor(Color.argb(255, 255, 255, 255));
	                    }
	                    else
	                    {
	                    	categoriaEstahSelecionada2[position] = false;
	                    	ImageView imageView = (ImageView) view.findViewById(R.id.img);
	                    	imageView.setAlpha(130);
	                    	TextView texto = (TextView) view.findViewById(R.id.txt);
	                    	texto.setTextColor(Color.argb(130, 255, 255, 255));
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
							  TelaModoCasual.this.criarSalaModoCasual(categoriasDeKanjiSelecionadas);
								
							  
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
 	String tituloDoJogadorParaOBD = CalculaPosicaoDoJogadorNoRanking.definirTituloDoJogadorParaBDCriacaoDeSala(tituloDoJogador, this.getApplicationContext());
 	dadosDeUmaPartidaCasual.setTituloDoJogador(tituloDoJogadorParaOBD);
 	loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.criando_sala), getResources().getString(R.string.por_favor_aguarde));
 	CriarSalaDoModoCasualTask criaSalaModoCasual = new CriarSalaDoModoCasualTask(loadingKanjisDoBd, TelaModoCasual.this);
 	salaAtual = new SalaAbertaModoCasual();
 	salaAtual.setCategoriasSelecionadas(categoriasDeKanjiSelecionadas);
 	salaAtual.setNivelDoUsuario(tituloDoJogador);
 	salaAtual.setNomeDeUsuario(nomeDoUsuarioUsado);
 	criaSalaModoCasual.execute(dadosDeUmaPartidaCasual);
 }
 
 public void setarIdDaSala(int idSalaModoCasual)
 {
	 this.salaAtual.setIdDaSala(idSalaModoCasual);
 }
 
 private void comecarNovaPartidaCasual(
			LinkedList<String> categoriasDeKanjiSelecionadas) 
 {
	 	Log.i("TelaModoCasual", "jogador " + nomeUsuario+ " jogoJahTerminou = false" );
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
		
		this.setinhaEmCimaSumoEsquerda = (ImageView) findViewById(R.id.seta_cima_sumo_esquerda);
		this.setinhaEmCimaSumoDireita = (ImageView) findViewById(R.id.seta_cima_sumo_direita);
		
		
		
		
		
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
 private ImageView viewImagemJogador;//imagem do jogador
 private ImageView viewImagemOponente;//imagem do oponente
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
 	//estilizar a label item
 	String fontPath = "fonts/gilles_comic_br.ttf";
    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	TextView textoLabelItem = (TextView) findViewById(R.id.label_item_casual);
	textoLabelItem.setTypeface(tf);
 	
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
        	--mSecondsLeft;
        	if(mSecondsLeft == 10)
        	{
        		//pouco tempo para acabar? add animação no timer!
        		final Animation animScale = AnimationUtils.loadAnimation(TelaModoCasual.this, R.anim.anim_scale_clock);
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
            	ProgressDialog barraProgressoFinalTerminouJogo =  ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.aviso_tempo_acaboou), getResources().getString(R.string.por_favor_aguarde));
            	TerminaPartidaTask taskTerminaPartida = new TerminaPartidaTask(barraProgressoFinalTerminouJogo, TelaModoCasual.this);
            	taskTerminaPartida.execute("");
        	}
        	
        }
     }.start();
     
     
     //por fim, mudar a musiquinha de background...
     this.mudarMusicaDeFundo(R.raw.headstart);
     
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
				TelaModoCasual.this.runOnUiThread(new Runnable() {
					
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
				TelaModoCasual.this.runOnUiThread(new Runnable() {
					
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

         this.viewImagemJogador = (ImageView) findViewById(R.id.imagem_background_host);
         this.viewImagemOponente = (ImageView) findViewById(R.id.imagem_background_guest);
     }
     else
     {
    	 PAREI AQUI PEGAR IMAGENS PARA QUANDO JOGADOR ACERTA/ERRA, VOCÊ CHACOALHAR
     }
    
 }
 
 
 private ThreadAnimaSetinhasSumo threadAnimaSetinhaSumoEsquerda;
 private ThreadAnimaSetinhasSumo threadAnimaSetinhaSumoDireita;
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
	 
	 /*if(threadAnimaSetinhaSumoEsquerda != null)
	 {
		 threadAnimaSetinhaSumoEsquerda.interrupt();
	 }
	 if(threadAnimaSetinhaSumoDireita != null)
	 {
		 threadAnimaSetinhaSumoDireita.interrupt();
	 }
	 boolean setaEmCimaSumo1ComecaAnimadaPraDireita = false;
	 boolean setaEmCimaSumo2ComecaAnimadaPraDireita = false;
	 if(nomeImagemSumozinhoAnimacao1 == "sumo_0_0" || nomeImagemSumozinhoAnimacao1 == "sumo_1_menos1" || nomeImagemSumozinhoAnimacao1 == "sumo_2_menos2" ||
			 nomeImagemSumozinhoAnimacao1 == "sumo_6_menos6" || nomeImagemSumozinhoAnimacao1 == "sumo_menos3_3" ||
			 nomeImagemSumozinhoAnimacao1 == "sumo_menos4_4" || nomeImagemSumozinhoAnimacao1 == "sumo_menos5_5")
	 {
		 setaEmCimaSumo1ComecaAnimadaPraDireita =  true;
		 setaEmCimaSumo2ComecaAnimadaPraDireita = true;
	 }
	 else if( nomeImagemSumozinhoAnimacao1 == "sumo_3_menos3" || nomeImagemSumozinhoAnimacao1 == "sumo_4_menos4" || nomeImagemSumozinhoAnimacao1 == "sumo_5_menos5" ||
			 nomeImagemSumozinhoAnimacao1 == "sumo_menos1_1" || nomeImagemSumozinhoAnimacao1 == "sumo_menos2_2" || nomeImagemSumozinhoAnimacao1 == "sumo_menos6_6" )
	 {
		 setaEmCimaSumo1ComecaAnimadaPraDireita =  false;
		 setaEmCimaSumo2ComecaAnimadaPraDireita = false;
	 }*/
	 
	 
	 
	 
	 //taskAnimaSetinhaSumoEsquerda = new TaskAnimaSetinhasSumo(setinhaEmCimaSumoEsquerda, this, setaEmCimaSumo1ComecaAnimadaPraDireita);
	 //taskAnimaSetinhaSumoDireita = new TaskAnimaSetinhasSumo(setinhaEmCimaSumoDireita, this, setaEmCimaSumo2ComecaAnimadaPraDireita);
	 
	 //taskAnimaSetinhaSumoEsquerda.execute("");
	 //taskAnimaSetinhaSumoDireita.execute("");
	 
	 //threadAnimaSetinhaSumoEsquerda = new ThreadAnimaSetinhasSumo(setinhaEmCimaSumoEsquerda, this, setaEmCimaSumo1ComecaAnimadaPraDireita);
	 //threadAnimaSetinhaSumoDireita = new ThreadAnimaSetinhasSumo(setinhaEmCimaSumoDireita, this, setaEmCimaSumo2ComecaAnimadaPraDireita);
	 //threadAnimaSetinhaSumoDireita.start();
	 //threadAnimaSetinhaSumoEsquerda.start();
	 
	//this.viewSumosNaArena.setImageResource(idImagemSumozinhoAnimacao1);
	 
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
        	 
        	 //e agora, fazer o item shimmer pra indicar que usuário ganhou item!
        	 /*String stringIdShimmerBotaoItem = "shimmer_pro_item" + indiceBotaoItem ;
        	 int idShimmerUltimoItem = getResources().getIdentifier(stringIdShimmerBotaoItem, "id", getPackageName());
        	ShimmerFrameLayout containerShimerUltimoItem = 
        			(ShimmerFrameLayout) findViewById(idShimmerUltimoItem);
        	containerShimerUltimoItem.setRepeatCount(3);
        	//containerShimerUltimoItem.startShimmerAnimation();*/
        	new BounceAnimation(botaoItem).animate();
        	 //botaoItem.setBackground(bitmapDrawableImagemItem);
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
			 	String ganhou = getResources().getString(R.string.ganhou);
			 	dadosPartida.setVoceGanhouOuPerdeu(ganhou);
				
			}
			else if(posicaoAntigaSumozinho < 0)
			{
				//quem ganhou foi o oponente, entao precisamos saber o nome dele
				String perdeu = getResources().getString(R.string.perdeu);
				dadosPartida.setVoceGanhouOuPerdeu(perdeu);
				
			}
			else
			{
				//empatou
				 String empatou = getResources().getString(R.string.empatou);
				 dadosPartida.setVoceGanhouOuPerdeu(empatou);
			}
		 
		 
		 
		 
		 
		 dadosPartida.setUsernameAdversario(this.nomeAdversario);
		 
		 EnviarDadosDaPartidaParaLogTask armazenaNoLog = new EnviarDadosDaPartidaParaLogTask();
		 armazenaNoLog.execute(dadosPartida);
		 
	 }
	 
	 @Override
	 public void onRestart()
	 {
		 super.onRestart();
		 Intent intentCriaTelaInicial = new Intent(TelaModoCasual.this, MainActivity.class);
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
		 View decorView = getWindow().getDecorView();
		 decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
		                               | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
		                               | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		                               | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
		                               | View.SYSTEM_UI_FLAG_FULLSCREEN
		                               | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	 }

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) 
	{
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
