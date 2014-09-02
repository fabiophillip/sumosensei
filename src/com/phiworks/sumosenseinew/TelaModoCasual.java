package com.phiworks.sumosenseinew;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony.Sms.Conversations;
import android.text.AndroidCharacter;
import android.util.Log;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import armazenamentointerno.ConcreteDAOArmazenaInternamenteDadosDePartidasRealizadas;
import armazenamentointerno.DAOAcessaHistoricoDePartidas;
import armazenamentointerno.DadosDePartida;
import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.DadosPartidaParaOLog;
import bancodedados.EnviarDadosDaPartidaParaLogTask;
import bancodedados.KanjiTreinar;
import bancodedados.MyCustomAdapter;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import bancodedados.SolicitaKanjisParaTreinoTask;



import cenario.ImageAdapter;
import cenario.MultiSelectionSpinner;
import cenario.SpinnerFiltroSalasAbertasListener;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
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
import com.phiworks.dapartida.TerminaPartidaTask;
import com.phiworks.domodocasual.AdapterListViewIconeETexto;
import com.phiworks.domodocasual.AdapterListViewSalasCriadas;
import com.phiworks.domodocasual.BuscaSalasModoCasualComArgumentoTask;
import com.phiworks.domodocasual.BuscaSalasModoCasualTask;
import com.phiworks.domodocasual.SolicitaCategoriasDoJogoTask;
import com.phiworks.domodocasual.CriarSalaDoModoCasualTask;
import com.phiworks.domodocasual.DadosDaSalaModoCasual;
import com.phiworks.domodocasual.SalaAbertaModoCasual;

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
import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import lojinha.DAOAcessaDinheiroDoJogador;
import lojinha.TransformaPontosEmCredito;

public class TelaModoCasual extends ActivityPartidaMultiplayer 
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

private String quemEscolheACategoria;
private boolean finalizouDecisaoEscolheCategoria = false;
private volatile boolean guestTerminouDeCarregarListaDeCategorias = false;


private String emailUsuario;
private String emailAdversario;
private boolean jogoJahTerminou = false;
private boolean estahComAnimacaoTegata = false;


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
	View botaoAdicionarMensagemNoChat = findViewById(R.id.sendBtn);
	View botaoItem = findViewById(R.id.botaoItem1);
	View botaoItem2 = findViewById(R.id.botaoItem2);
	View botaoItem3 = findViewById(R.id.botaoItem3);
	View botaoItem4 = findViewById(R.id.botaoItem4);
	View botaoItem5 = findViewById(R.id.botaoItem5);
	botaoResposta1.setOnClickListener(this);
	botaoResposta2.setOnClickListener(this);
	botaoResposta3.setOnClickListener(this);
	botaoResposta4.setOnClickListener(this);
	botaoVoltarAoMenuPrincipal.setOnClickListener(this);
	botaoAdicionarMensagemNoChat.setOnClickListener(this);
	botaoItem.setOnClickListener(this);
	botaoItem2.setOnClickListener(this);
	botaoItem3.setOnClickListener(this);
	botaoItem4.setOnClickListener(this);
	botaoItem5.setOnClickListener(this);
	
	

}

/**
* Called by the base class (BaseGameActivity) when sign-in has failed. For
* example, because the user hasn't authenticated yet. We react to this by
* showing the sign-in button.
*/
@Override
public void onSignInFailed() {
Log.d(TAG, "Sign-in failed.");
switchToScreen(R.id.screen_sign_in);
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

//salvarei o email do usuario para adicionar o log dele no banco de dados
AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
Account[] list = manager.getAccounts();

for(Account account: list)
{
    if(account.type.equalsIgnoreCase("com.google"))
    {
        this.emailUsuario = account.name;
        break;
    }
}
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
    case R.id.button_sign_out:
        // user wants to sign out
        signOut();
        switchToScreen(R.id.screen_sign_in);
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
    case R.id.button_quick_game:
        // user wants to play against a random opponent right now
        startQuickGame(2);
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
    	Intent intentVoltaMenuPrincipal = new Intent(TelaModoCasual.this, MainActivity.class);
    	intentVoltaMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intentVoltaMenuPrincipal);
    	break;
    case R.id.sendBtn:
    	EditText textfieldMensagemDigitada = (EditText) findViewById(R.id.chatET);
    	String mensagemDigitada = textfieldMensagemDigitada.getText().toString();
    	textfieldMensagemDigitada.setText("");
    	String mensagemAdicionadaAoChat = this.adicionarMensagemNoChat(mensagemDigitada, true, this.emailUsuario.split("@")[0]);
    	this.avisarAoOponenteQueDigitouMensagem(mensagemAdicionadaAoChat);
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
    
}
}

public void solicitarBuscarSalasAbertas() {
	this.loadingKanjisDoBd = new ProgressDialog(getApplicationContext());
	this.loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.buscando_salas_abertas), getResources().getString(R.string.por_favor_aguarde));
	BuscaSalasModoCasualTask taskBuscaSalasAbertas = new BuscaSalasModoCasualTask(loadingKanjisDoBd, this);
	taskBuscaSalasAbertas.execute("");
}

/**
 * Referente a encontrar salas abertas
 */
private AdapterListViewSalasCriadas adapterSalasAtivas = null;
private ArrayList<SalaAbertaModoCasual> salasCarregadasModoCasual;
private SalaAbertaModoCasual salaAtual;
public void mostrarListaComSalasAposCarregar(ArrayList<SalaAbertaModoCasual> salasModoCasual)
{
	 
	this.salasCarregadasModoCasual = salasModoCasual;
 
	adapterSalasAtivas = new AdapterListViewSalasCriadas
			(this, R.layout.item_lista_sala, salasModoCasual, this);


	 // Assign adapter to ListView
	 ListView listViewSalas = (ListView) findViewById(R.id.lista_salas_abertas);
	 listViewSalas.setAdapter(adapterSalasAtivas); 
 
	 listViewSalas.setOnItemClickListener(new OnItemClickListener() {
		 public void onItemClick(AdapterView parent, View view,
				 int position, long id) 
		 {
			 salaAtual = salasCarregadasModoCasual.get(position);
			 startQuickGame(salaAtual.getIdDaSala());
		 }
	 });
	 
	 //em seguida, setar o conteudo do spinner de filtragem
	 Spinner spinnerFiltragem = (Spinner) findViewById(R.id.spinner_escolha_filtro);
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
		 
		 ArrayAdapter<String> adapterSpinnerFiltrarSala = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, filtrosDeSala);
				adapterSpinnerFiltrarSala.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinnerFiltragem.setAdapter(adapterSpinnerFiltrarSala);
		 SpinnerFiltroSalasAbertasListener listenerDoSpinnerFiltrarCategorias = new SpinnerFiltroSalasAbertasListener(this);
		 spinnerFiltragem.setOnItemSelectedListener(listenerDoSpinnerFiltrarCategorias);
	 }
	 
	 
	 
}

public void mostrarPopupPesquisarPorRanking()
{
	final Dialog dialog = new Dialog(TelaModoCasual.this);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    // Include dialog.xml file
    dialog.setContentView(R.layout.popup_escolha_nivel_piramide);
    dialog.show();
    TextView titulo = (TextView)dialog.findViewById(R.id.tituloEscolhaRanking);
    String fontpath = "fonts/edosz.ttf";
    Typeface tf = Typeface.createFromAsset(getAssets(), fontpath);
    titulo.setTypeface(tf);
    ImageView imagemRank1Sumo = (ImageView)dialog.findViewById(R.id.imagem1RankSumo);
    imagemRank1Sumo.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 1);
		}

	});
    ImageView imagemRank2Sumo = (ImageView)dialog.findViewById(R.id.imagem2RankSumo);
    imagemRank2Sumo.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 2);
		}
	});
    ImageView imagemRank3Sumo = (ImageView)dialog.findViewById(R.id.imagem3RankSumo);
    imagemRank3Sumo.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 3);
		}
	});
    ImageView imagemRank4Sumo = (ImageView)dialog.findViewById(R.id.imagem4RankSumo);
    imagemRank4Sumo.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 4);
		}
	});
    ImageView imagemRank5Sumo = (ImageView)dialog.findViewById(R.id.imagem5RankSumo);
    imagemRank5Sumo.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 5);
		}
	});
    ImageView imagemRank6Sumo = (ImageView)dialog.findViewById(R.id.imagem6RankSumo);
    imagemRank6Sumo.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 6);
		}
	});
    ImageView imagemRank7Sumo = (ImageView)dialog.findViewById(R.id.imagem7RankSumo);
    imagemRank7Sumo.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 7);
		}
	});
    ImageView imagemRank8Sumo = (ImageView)dialog.findViewById(R.id.imagem8RankSumo);
    imagemRank8Sumo.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 8);
		}
	});
    ImageView imagemRank9Sumo = (ImageView)dialog.findViewById(R.id.imagem9RankSumo);
    imagemRank9Sumo.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			usuarioEscolheuNivelOponenteFiltrarSala(dialog, 9);
		}
	});
    
    
}

private void usuarioEscolheuNivelOponenteFiltrarSala(final Dialog dialog, int nivelSelecionado) {
	Toast.makeText(getApplicationContext(), "clicou titulo " + nivelSelecionado, Toast.LENGTH_SHORT).show();
	dialog.dismiss();
	loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.buscando_salas_abertas), getResources().getString(R.string.por_favor_aguarde));
	BuscaSalasModoCasualComArgumentoTask buscaSalas = new BuscaSalasModoCasualComArgumentoTask(loadingKanjisDoBd, TelaModoCasual.this);
	String nomeIdStringNivelSelecionado = "sumo_ranking_" + nivelSelecionado;
	int idStringNivelSelecionado = getApplicationContext().getResources().getIdentifier(nomeIdStringNivelSelecionado, "string", getPackageName());
	String nomeDoNivelDoOponente = getResources().getString(idStringNivelSelecionado);
	buscaSalas.execute("nivel_adversario", nomeDoNivelDoOponente);
}

public void mostrarPopupPesquisarPorCategorias()
{
	LinkedList<String> categorias = 
			  ArmazenaKanjisPorCategoria.pegarInstancia().getCategoriasDeKanjiArmazenadas("5");
	final String[] arrayCategorias = new String[categorias.size()];
	for(int i = 0; i < categorias.size(); i++)
	{
		String umaCategoria = categorias.get(i);
		arrayCategorias[i] = umaCategoria;
	}
	
	Integer[] imageId = new Integer[arrayCategorias.length];
	for(int j = 0; j < arrayCategorias.length; j++)
	{
		imageId[j] = R.drawable.categoria_pequeno;
	}

	
    // arraylist to keep the selected items
   
	final Dialog dialog = new Dialog(TelaModoCasual.this);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//vou tirar a linha que divide o titulo e o conteudo do dialog
	/*int divierId = dialog.getContext().getResources()
            .getIdentifier("android:id/titleDivider", null, null);
	View divider = dialog.findViewById(divierId);
	divider.setBackgroundColor(Color.WHITE);*/
	
	
    // Include dialog.xml file
    dialog.setContentView(R.layout.popup_escolha_categorias);
    
    final boolean[] categoriaEstahSelecionada = new boolean[arrayCategorias.length];
	for(int k = 0; k < arrayCategorias.length; k++)
	{
		categoriaEstahSelecionada[k] = false;
	}
	
	AdapterListViewIconeETexto adapter = new AdapterListViewIconeETexto(TelaModoCasual.this, arrayCategorias, imageId);
	    ListView list=(ListView)dialog.findViewById(R.id.listaCategoriasPesquisaSalas);
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
	                    	imageView.setAlpha(128);
	                    }
	                }
	            });

    //this.popupPesquisarSalaPorCategoria = builder.create();//AlertDialog dialog; create like this outside onClick
	  dialog.show();
	        
	//falta definir a ação para o botão ok desse popup das categorias
	  Button botaoOk = (Button) dialog.findViewById(R.id.confirmar_escolha_categorias);
	  final TelaModoCasual telaModoCasual = this;
	  botaoOk.setOnClickListener(new Button.OnClickListener() 
	  {
		  public void onClick(View v) 
	      {
			  dialog.dismiss();
			  loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.buscando_salas_abertas), getResources().getString(R.string.por_favor_aguarde));
		    	BuscaSalasModoCasualComArgumentoTask taskBuscaSalasModoCasual12 =
		    				new BuscaSalasModoCasualComArgumentoTask(loadingKanjisDoBd, telaModoCasual);
		    	String categoriasSeparadasPorVirgula = "";
		    	for(int l = 0; l < categoriaEstahSelecionada.length; l++)
		    	{
		    		if(categoriaEstahSelecionada[l] == true)
		    		{
		    			//o usuario quer procurar com essa categoria
		    			String umaCategoria = arrayCategorias[l];
		    			if(l == categoriaEstahSelecionada.length - 1)
			    		{
			    			categoriasSeparadasPorVirgula = categoriasSeparadasPorVirgula + umaCategoria;
			    		}
			    		else
			    		{
			    			categoriasSeparadasPorVirgula = categoriasSeparadasPorVirgula + umaCategoria + ",";
			    		}
		    			
		    		}
		    	}
		    	
		    	taskBuscaSalasModoCasual12.execute("categorias",categoriasSeparadasPorVirgula);      
	      }
	  });
}

public void mostrarPopupPesquisarPorUsername()
{
	final Dialog dialog = new Dialog(TelaModoCasual.this);
	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    // Include dialog.xml file
    dialog.setContentView(R.layout.popup_escolha_username);
    dialog.show();
    Button botaoFiltrarSalasPorUsuario = (Button)dialog.findViewById(R.id.botao_filtra_username);
    botaoFiltrarSalasPorUsuario.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			EditText textfieldEspecificouUsername = (EditText) dialog.findViewById(R.id.textfield_filtrar_username);
			String nomeDoOponente = textfieldEspecificouUsername.getText().toString();
			if(nomeDoOponente != null && nomeDoOponente.length() > 0)
			{
				dialog.dismiss();
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
			Toast.makeText(getApplicationContext(), avisoChikaramizu, Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getApplicationContext(), mensagemTegata , Toast.LENGTH_SHORT).show();
			this.mandarMensagemMultiplayer("usouTegata;");
			this.reproduzirSfx("noJogo-usouTegata");
		}
		else if(itemSaiuInventario.compareTo("teppotree") == 0)
		{
			guardaDadosDosItens.adicionarItemIncorporado(itemSaiuInventario);
			String avisoTeppoTree = getResources().getString(R.string.aviso_bom_teppotree);
			Toast.makeText(getApplicationContext(), avisoTeppoTree, Toast.LENGTH_SHORT).show();
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
if (keyCode == KeyEvent.KEYCODE_BACK && mCurScreen == R.id.screen_game) {
    leaveRoom();
    return true;
}
return super.onKeyDown(keyCode, e);
}

// Leave the room.
void leaveRoom() {
Log.d(TAG, "Leaving room.");
mSecondsLeft = 0;
stopKeepingScreenOn();
if (mRoomId != null) {
    Games.RealTimeMultiplayer.leave(getApiClient(), this, mRoomId);
    mRoomId = null;
    switchToScreen(R.id.screen_wait);
} else {
    switchToMainScreen();
}
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
		ArrayList<String> participantesNoQuarto = room.getParticipantIds();
		if(participantesNoQuarto.size() >= 2)
		{
			mRoomId = null;
			showGameError();
		}
		else
		{
			
			showAlert(getString(R.string.adversario_saiu));
			switchToMainScreen();
		}
	}
	catch(Exception exc)
	{
		mRoomId = null;
		showGameError();
	}
}

// Show error message about game being cancelled and return to main screen.
void showGameError() {
showAlert(getString(R.string.game_problem));
switchToMainScreen();
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
	this.enviarSeuEmailParaOAdversario();
	switchToScreen(R.id.decidindoQuemEscolheACategoria);

	//this.decidirQuemEscolheACategoria();
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
		String nomeUsuario = emailUsuario.split("@")[0];
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
				Toast.makeText(getApplicationContext(), avisoChikaramizu, Toast.LENGTH_SHORT).show();
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
	   
	    if(usuarioSeDefendeu == true)
	    {
	    	String mensagemBoaSeDefendeu = getResources().getString(R.string.aviso_bom_teppotree2);
	    	Toast.makeText(getApplicationContext(), mensagemBoaSeDefendeu, Toast.LENGTH_SHORT).show();
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
	else if(mensagem.contains("escolheCategoria") == true && finalizouDecisaoEscolheCategoria == false)
	{
		//o adversario e o jogador atual irao escolher aleatoriamente quem ira decidir a lista e essa escolha so
		//irah parar quando os dois decidirem na mesma pessoa
		String jogadorEscolhidoNaTelaDoAdversario = mensagem.replaceFirst("escolheCategoria ", ""); 
		
		if(this.quemEscolheACategoria != null && jogadorEscolhidoNaTelaDoAdversario.compareTo(this.quemEscolheACategoria) == 0)
		{
			//chegou-se a um consenso, basta alertar ao adversario
			this.finalizouDecisaoEscolheCategoria = true;
			mensagem = "escolheCategoria " + this.quemEscolheACategoria;
			mMsgBuf = mensagem.getBytes();
			
			for (Participant p : mParticipants) 
			{
				if (p.getParticipantId().equals(mMyId))
				{
					continue;
				}
			    else
			    {
			    	Games.RealTimeMultiplayer.sendReliableMessage(getApiClient(), null, mMsgBuf, mRoomId,
				            p.getParticipantId());
			    }
			}
			
			
			
			//agora vamos passar para a outra tela do jogo, a de escolha da categoria
			switchToScreen(R.id.tela_escolha_categoria);
			this.decidirCategoria();
			
			
		}
		else
		{
			//mais uma vez gerar o numero aleatorio
			//this.decidirQuemEscolheACategoria();
		}
	}
	else if(mensagem.contains("selecionouCategoria=") == true)
	{
		String mensagemSemSelecionouCategoria = mensagem.replaceFirst("selecionouCategoria=", "");
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
		}
		//Toast.makeText(this, "uma categoria de kanji tem de mudar:" + mensagemSemSelecionouCategoria, Toast.LENGTH_LONG).show();
		
		
	}
	else if(mensagem.contains("terminou selecionar categorias::") == true)
	{
		String mensagemTerminouDeSelecionarCategoria = mensagem.replaceFirst("terminou selecionar categorias::", "");
		String [] mensagemSplitada = mensagemTerminouDeSelecionarCategoria.split(";");
		
		LinkedList<String> categoriasDeKanjiSelecionadas = pegarCategoriasDeKanjiSelecionadas();
	    
	    //o que fazer depois de que o oponente terminou de selecionar categorias?
	    if(categoriasDeKanjiSelecionadas.size() > 0)
	    {
	    	
	        GuardaDadosDaPartida guardaDadosDeUmaPartida = GuardaDadosDaPartida.getInstance();
	        prepararTelaInicialDoJogo(categoriasDeKanjiSelecionadas);
	        String categoriaDoKanjiTreinadoInicialmente = mensagemSplitada[0];
	        String textoKanjiTreinadoInicialmente = mensagemSplitada[1];
	        KanjiTreinar umKanjiAleatorioParaTreinar = GuardaDadosDaPartida.getInstance().encontrarKanjiTreinadoNaPartida(categoriaDoKanjiTreinadoInicialmente, textoKanjiTreinadoInicialmente);
	        guardaDadosDeUmaPartida.adicionarKanjiAoTreinoDaPartida(umKanjiAleatorioParaTreinar);
	        
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
		Log.i("TelaModoCasual", "jogador terminou de receber mensagem terminou escolher nova partida::");
		
		String mensagemTerminouDeSelecionarCategoria = mensagem.replaceFirst("terminou escolher nova partida::", "");
		String [] mensagemSplitada = mensagemTerminouDeSelecionarCategoria.split(";");
		GuardaDadosDaPartida guardaDadosDeUmaPartida = GuardaDadosDaPartida.getInstance();
	    String categoriaDoKanjiTreinado = mensagemSplitada[0];
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
		//o jogo acabou e o oponente ganhou... a menos que tenha teppo tree
		GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		
		boolean usuarioSeDefendeu = guardaDadosDaPartida.usuarioTemItemIncorporado("teppotree");
		if(usuarioSeDefendeu == false)
		{
			guardaDadosDaPartida.setPosicaoSumozinhoDoJogadorNaTela(-6);
			this.terminarJogoMultiplayer();
		}
	   
	    if(usuarioSeDefendeu == true)
	    {
	    	String mensagemBoaSeDefendeu = getResources().getString(R.string.aviso_bom_teppotree2);
	    	Toast.makeText(getApplicationContext(), mensagemBoaSeDefendeu, Toast.LENGTH_SHORT).show();
	    	this.reproduzirSfx("noJogo-usouTeppotree");
	    	guardaDadosDaPartida.removerItemIncorporado("teppotree");
	    }
	    String mensagemProAdversario = "euDefendiDoOponenteGanhou;" + usuarioSeDefendeu + ";";
	    this.mandarMensagemMultiplayer(mensagemProAdversario);
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
		this.adicionarMensagemNoChat(mensagemAdicionarAoChat, false, this.emailAdversario.split("@")[0]);
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
		Toast.makeText(getApplicationContext(), avisoTegata, Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getApplicationContext(), avisoJogadorDefendeu, Toast.LENGTH_SHORT).show();
		}
		this.aposDizerProOponenteQueAcertouKanji(jogadorDefendeu);
	}
	else if(mensagem.contains("euDefendiDoOponenteGanhou;"))
	{
		String [] mensagemSplitada = mensagem.split(";");
		String stringJogadorDefendeu = mensagemSplitada[1];
		if(stringJogadorDefendeu.compareTo("true") == 0)
		{
			this.reproduzirSfx("noJogo-jogadorDefendeu");
			String avisoJogadorDefendeu = getResources().getString(R.string.aviso_ruim_teppotree);
			Toast.makeText(getApplicationContext(), avisoJogadorDefendeu, Toast.LENGTH_SHORT).show();
		}
		else
		{
			//jogador ganhou o jogo. muda a tela para a tela de final de jogo...
			this.reproduzirSfx("noJogo-jogadorGanhou");
			GuardaDadosDaPartida.getInstance().setPosicaoSumozinhoDoJogadorNaTela(6);
			this.terminarJogoMultiplayer();
		}
	}
	else if(mensagem.contains("email=") == true)
	{
		this.emailAdversario = mensagem.replace("email=", "");
	}

}



/*
* UI SECTION. Methods that implement the game's UI.
*/

// This array lists everything that's clickable, so we can install click
// event handlers.
final static int[] CLICKABLES = {
    R.id.button_accept_popup_invitation, R.id.button_create_room,
    R.id.button_quick_game, R.id.botao_buscar_salas, R.id.button_sign_in,
    R.id.button_sign_out,
};

// This array lists all the individual screens our game has.
final static int[] SCREENS = {
    R.id.screen_game, R.id.screen_main, R.id.screen_sign_in, R.id.tela_buscar_salas,
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
switchToScreen(isSignedIn() ? R.id.screen_main : R.id.screen_sign_in);
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
	String nomeUsuario = emailUsuario.split("@")[0];
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
public void terminarJogoMultiplayer()
{
	if(jogoJahTerminou == false)
	{
		if(this.timerFimDeJogo != null)
		{
			timerFimDeJogo.cancel();
		}
		this.mudarMusicaDeFundo(R.raw.lazy_susan);
		this.switchToScreen(R.id.screen_final_partida);
		findViewById(R.id.textView2Final).setVisibility(View.VISIBLE);
		findViewById(R.id.nome_jogador_host_final).setVisibility(View.VISIBLE);
		findViewById(R.id.nihonball_final).setVisibility(View.VISIBLE);
		findViewById(R.id.botao_menu_principal).setVisibility(View.VISIBLE);
		findViewById(R.id.nome_jogador_guest_final).setVisibility(View.VISIBLE);
		findViewById(R.id.ringue_luta_final).setVisibility(View.VISIBLE);
		findViewById(R.id.quem_ganhou_final).setVisibility(View.VISIBLE);
		findViewById(R.id.sendBtn).setVisibility(View.VISIBLE);
		findViewById(R.id.chatET).setVisibility(View.VISIBLE);
		this.listViewMensagensChat = (ListView) findViewById(R.id.mensagens_chat);
		this.listViewMensagensChat.setVisibility(View.VISIBLE);
		this.mensagensChat = new ArrayList<String>();
		
		TextView textviewNomeJogadorHost = (TextView) findViewById(R.id.nome_jogador_host_final);
		TextView textviewNomeJogadorGuest = (TextView) findViewById(R.id.nome_jogador_guest_final);
		
		String nomeJogador = this.emailUsuario.split("@")[0];
	 	String nomeAdversario = this.emailAdversario.split("@")[0];
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
	 	
	 	if(quemEscolheACategoria.compareTo(mMyId) == 0)
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
		this.animacaoSumosNaArena = new AnimationDrawable();
		 String nomeImagemSumozinhoAnimacao1 = this.getNomeImagemSumozinho(posicaoAntigaSumozinho);
		 String nomeImagemSumozinhoAnimacao2 = nomeImagemSumozinhoAnimacao1 + "_alt";
		 int idImagemSumozinhoAnimacao1 = getResources().getIdentifier(nomeImagemSumozinhoAnimacao1, "drawable", getPackageName());
		 int idImagemSumozinhoAnimacao2 = getResources().getIdentifier(nomeImagemSumozinhoAnimacao2, "drawable", getPackageName());
		 animacaoSumosNaArena.addFrame(getResources().getDrawable(idImagemSumozinhoAnimacao1), 200);
		 animacaoSumosNaArena.addFrame(getResources().getDrawable(idImagemSumozinhoAnimacao2), 200);
		 animacaoSumosNaArena.setOneShot(false);
		 ImageView viewSumozinhosNaTela = (ImageView)findViewById(R.id.ringue_luta_final);
		 viewSumozinhosNaTela.setImageDrawable(animacaoSumosNaArena);
		 this.viewSumosNaArena.post(new Runnable() {
			@Override
			public void run() {
				animacaoSumosNaArena.start();
			}
		});
		
		
		String nomeUsuarioGanhou = "";
		if(posicaoAntigaSumozinho > 0)
		{
			//quem ganhou foi o usuario , nao o oponente!
			nomeUsuarioGanhou = nomeJogadorEncurtado;
		}
		else if(posicaoAntigaSumozinho < 0)
		{
			//quem ganhou foi o oponente, entao precisamos saber o nome dele
			 
			nomeUsuarioGanhou = nomeAdversarioEncurtado;
			 	    
		}
		else
		{
			nomeUsuarioGanhou = "empatou";
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
		int pontuacaoDoJogadorNaPartida = guardaDadosDaPartida.getPlacarDoJogadorNaPartida();
		int creditosAdicionarAoJogador = TransformaPontosEmCredito.converterPontosEmCredito(pontuacaoDoJogadorNaPartida);
		DAOAcessaDinheiroDoJogador daoDinheiroJogador = ConcreteDAOAcessaDinheiroDoJogador.getInstance();
		daoDinheiroJogador.adicionarCredito(creditosAdicionarAoJogador, this);
		String textoGanhouCreditoNaPartida = getResources().getString(R.string.texto_ganhou) + " " + 
											 creditosAdicionarAoJogador + " " + getResources().getString(R.string.moeda_do_jogo) + " " + 
											 getResources().getString(R.string.texto_na_partida); 
		Toast.makeText(this, textoGanhouCreditoNaPartida, Toast.LENGTH_SHORT).show();
		this.jogoJahTerminou = true;
	}
	
}

private void aposDizerProOponenteQueAcertouKanji(boolean adversarioDefendeuDoGolpe) {
	String nomeUsuario = emailUsuario.split("@")[0];
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
	/*Button botaoAnswer1 = (Button)findViewById(R.id.answer1);
	Button botaoAnswer2 = (Button)findViewById(R.id.answer2);
	Button botaoAnswer3 = (Button)findViewById(R.id.answer3);
	Button botaoAnswer4 = (Button)findViewById(R.id.answer4);
	
	botaoAnswer1.clearAnimation();
	botaoAnswer2.clearAnimation();
	botaoAnswer3.clearAnimation();
	botaoAnswer4.clearAnimation();*/
	
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
        
        String mensagemParaOponente = "terminou escolher nova partida::" + umKanjiAleatorioParaTreinar.getCategoriaAssociada() + ";" +
				umKanjiAleatorioParaTreinar.getKanji() + ";" + hiraganasAlternativas.get(0) + ";" +hiraganasAlternativas.get(1) + ";" +
				hiraganasAlternativas.get(2) + ";" + hiraganasAlternativas.get(3);
        
        this.mandarMensagemMultiplayer(mensagemParaOponente);
        
        this.iniciarUmaPartida(umKanjiAleatorioParaTreinar, hiraganasAlternativas);
	}
	
	
}



private String getNomeImagemSumozinho(int posicaoDoSumozinho)
{
	boolean usuarioEhOHost = false;
	if(mMyId.compareTo(quemEscolheACategoria) == 0)
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
	TextView tituloDecicindoCategoria = (TextView) findViewById (R.id.titulo_escolha_categoria);
	tituloDecicindoCategoria.setVisibility(View.VISIBLE);
	
	findViewById(R.id.listaCategorias).setVisibility(View.VISIBLE);
	findViewById(R.id.imageView1).setVisibility(View.VISIBLE);
	findViewById(R.id.listaCategorias).setVisibility(View.VISIBLE);
	findViewById(R.id.ok_button).setVisibility(View.VISIBLE);
	solicitarPorKanjisPraTreino(true);
	switchToScreen(R.id.tela_escolha_categoria);
	adicionarListenerBotao();
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
		SolicitaKanjisParaTreinoTask solicitaKanjisPraTreino = new SolicitaKanjisParaTreinoTask(this.loadingKanjisDoBd, this);
		solicitaKanjisPraTreino.execute("");
	}
	else
	{
		SolicitaCategoriasDoJogoTask solicitaCategoriasPraFiltro = new SolicitaCategoriasDoJogoTask(loadingKanjisDoBd, this);
		solicitaCategoriasPraFiltro.execute("");
	}
	 
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
	   }
	  });
	  
	 }
  
 
  
 private void adicionarListenerBotao() {
  
  
  Button myButton = (Button) findViewById(R.id.ok_button);
  myButton.setOnClickListener(new OnClickListener() {
  
   @Override
   public void onClick(View v) {
   LinkedList<String> categoriasDeKanjiSelecionadas = pegarCategoriasDeKanjiSelecionadas();
    
    //o que fazer depois de que o usuario terminou de selecionar categorias?
    if(categoriasDeKanjiSelecionadas.size() > 0)
    {
    	
        DadosDaSalaModoCasual dadosDeUmaPartidaCasual = new DadosDaSalaModoCasual();
        dadosDeUmaPartidaCasual.setCategoriasSelecionadas(categoriasDeKanjiSelecionadas);
        dadosDeUmaPartidaCasual.setUsernameQuemCriouSala(emailUsuario);
        DAOGuardaConfiguracoesDoJogador sabeNomeDoJogador = ConcreteDAOGuardaConfiguracoesDoJogador.getInstance();
        String tituloDoJogador = sabeNomeDoJogador.obterTituloDoJogador(getApplicationContext());
        dadosDeUmaPartidaCasual.setTituloDoJogador(tituloDoJogador);
        loadingKanjisDoBd = ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.criando_sala), getResources().getString(R.string.por_favor_aguarde));
        CriarSalaDoModoCasualTask criaSalaModoCasual = new CriarSalaDoModoCasualTask(loadingKanjisDoBd, TelaModoCasual.this);
        criaSalaModoCasual.execute(dadosDeUmaPartidaCasual);
    }
    else
    {
    	Toast.makeText(getApplicationContext(), getResources().getString(R.string.aviso_nao_selecionou_categorias), Toast.LENGTH_SHORT).show();
    }
    
   }

private void comecarNovaPartidaCasual(
		LinkedList<String> categoriasDeKanjiSelecionadas) {
	GuardaDadosDaPartida guardaDadosDeUmaPartida = GuardaDadosDaPartida.getInstance();
	prepararTelaInicialDoJogo(categoriasDeKanjiSelecionadas);
	KanjiTreinar umKanjiAleatorioParaTreinar = GuardaDadosDaPartida.getInstance().getUmKanjiAleatorioParaTreinar();
	guardaDadosDeUmaPartida.adicionarKanjiAoTreinoDaPartida(umKanjiAleatorioParaTreinar);
	
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
	
	String mensagemParaOponente = "terminou selecionar categorias::" + umKanjiAleatorioParaTreinar.getCategoriaAssociada() + ";" +
									umKanjiAleatorioParaTreinar.getKanji() + ";" + hiraganasAlternativas.get(0) + ";" +hiraganasAlternativas.get(1) + ";" +
									hiraganasAlternativas.get(2) + ";" + hiraganasAlternativas.get(3);
	mandarMensagemMultiplayer(mensagemParaOponente);
}

  });
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
 	
 	
 	
 	Integer [] indicesIconesCategoriasDoJogo = PegaIdsIconesDasCategoriasSelecionadas.pegarIndicesIconesDasCategoriasSelecionadas(categoriasDeKanjiSelecionadas);
 	Gallery gallery = (Gallery) findViewById(R.id.listagem_categorias);
    gallery.setAdapter(new ImageAdapter(indicesIconesCategoriasDoJogo, this));
    
    
 	
 	TextView textviewNomeJogadorGuest = (TextView)findViewById(R.id.nome_jogador_guest);
 	textviewNomeJogadorGuest.setVisibility(View.VISIBLE);
 	TextView textviewNomeJogadorHost = (TextView)findViewById(R.id.nome_jogador_host);
 	textviewNomeJogadorHost.setVisibility(View.VISIBLE);
 	
 	String nomeJogador = this.emailUsuario.split("@")[0];
 	String nomeAdversario = this.emailAdversario.split("@")[0];
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
 	
 	if(quemEscolheACategoria.compareTo(mMyId) == 0)
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
 	
 	//botar um cara para fazer update do tempo restante do jogo.
 	 /*final Handler h = new Handler();
     h.postDelayed(new Runnable() {
         @Override
         public void run() {
             if (mSecondsLeft <= 0)
                 return;
             gameTick();
             h.postDelayed(this, 1000);
         }
     }, 1000);*/
 	
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
        	// finish game
        	mandarMensagemMultiplayer("terminouJogo;");
        	ProgressDialog barraProgressoFinalTerminouJogo =  ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.aviso_tempo_acaboou), getResources().getString(R.string.por_favor_aguarde));
        	TerminaPartidaTask taskTerminaPartida = new TerminaPartidaTask(barraProgressoFinalTerminouJogo, TelaModoCasual.this);
        	taskTerminaPartida.execute("");
        }
     }.start();
     
     
     //por fim, mudar a musiquinha de background...
     this.mudarMusicaDeFundo(R.raw.headstart);
 }
 
 private void atualizarAnimacaoSumosNaArena()
 {
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
     /*String textoKanjiTreinarNaVertical = "";
     for(int i = 0; i < textoKanjiTreinar.length(); i++)
     {
     	char umCaractereKanji = textoKanjiTreinar.charAt(i);
     	textoKanjiTreinarNaVertical = textoKanjiTreinarNaVertical + umCaractereKanji;
     	if(i != textoKanjiTreinar.length() - 1)
     	{
     		textoKanjiTreinarNaVertical = textoKanjiTreinarNaVertical + "\n ";
     	}
     }*/
     textViewKanjiAcertar.setText(textoKanjiTreinar);
     
     //botaoAlternativa1.setEnabled(true);
     botaoAlternativa1.setClickable(true);
     botaoAlternativa2.setClickable(true);
     botaoAlternativa3.setClickable(true);
     botaoAlternativa4.setClickable(true);
     /*botaoAlternativa1.getBackground().setAlpha(255);
     botaoAlternativa2.getBackground().setAlpha(255);
     botaoAlternativa3.getBackground().setAlpha(255);
     botaoAlternativa4.getBackground().setAlpha(255);*/
     if(estahComAnimacaoTegata == false)
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
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listitem, this.mensagensChat);
    this.listViewMensagensChat.setAdapter(adapter);
  }

/**
 * 
 * @param mensagem
 * @param adicionarNomeDoRemetente precisa complementar a mensagem com o nome do remetente ou nao...
 * @return a mensagem adicionada no chat.
 */
private String adicionarMensagemNoChat(String mensagem, boolean adicionarNomeDoRemetente, String nomeRemetente)
{
	String mensagemAdicionarNoChat = mensagem;
	if(adicionarNomeDoRemetente == true)
	{
		//append na mensagem o nome do remetente
		String nomeUsuarioEncurtado = this.emailUsuario.split("@")[0];//talvez precise encurtar com .substring(0, 11)
		mensagemAdicionarNoChat = nomeUsuarioEncurtado + ":" + mensagem;
	}
	
	this.mensagensChat.add(mensagemAdicionarNoChat);
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



//Game tick -- update countdown, check if game ended.
	private void gameTick() {
		if (mSecondsLeft > 0)
			--mSecondsLeft;

		// update countdown
		((TextView) findViewById(R.id.countdown)).setText("0:" +
            (mSecondsLeft < 10 ? "0" : "") + String.valueOf(mSecondsLeft));

		if (mSecondsLeft <= 0) {
		// finish game
			this.mandarMensagemMultiplayer("terminouJogo;");
			ProgressDialog barraProgressoFinalTerminouJogo =  ProgressDialog.show(TelaModoCasual.this, getResources().getString(R.string.aviso_tempo_acaboou), getResources().getString(R.string.por_favor_aguarde));
			TerminaPartidaTask taskTerminaPartida = new TerminaPartidaTask(barraProgressoFinalTerminouJogo, this);
			taskTerminaPartida.execute("");
			//terminarJogo();
		}
	}
	
	/**
	 * PARTE REFERENTE A INSERÇÃO DE LOG DA PARTIDA NO BD
	 */
	
	private void enviarSeuEmailParaOAdversario()
	 {
		 this.mandarMensagemMultiplayer("email=" + this.emailUsuario);
	 }
	 
	 private void enviarDadosDaPartidaParaOLogDoUsuarioNoBancoDeDados()
	 {
		 //enviaremos as informacoes da partida num log que escreveremos para o usuário e salvaremos num servidor remoto
		 DadosPartidaParaOLog dadosPartida = new DadosPartidaParaOLog();
		 LinkedList<String> categoriasTreinadas = GuardaDadosDaPartida.getInstance().getCategoriasTreinadasNaPartida();
		 String categoriasEmString = "";
		 for(int i = 0; i < categoriasTreinadas.size(); i++)
		 {
			String umaCategoria = categoriasTreinadas.get(i);
			categoriasEmString = categoriasEmString + umaCategoria + ";";
		 }
		 
		 dadosPartida.setCategoria(categoriasEmString);
		 
		 Calendar c = Calendar.getInstance();
		 SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		 String formattedDate = df.format(c.getTime());
		 dadosPartida.setData(formattedDate);
		 
		 dadosPartida.setEmail(this.emailUsuario);
		 dadosPartida.setJogoAssociado("karuta kanji");
		 
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
		 
		 
		 
		 
		 
		 dadosPartida.seteMailAdversario(this.emailAdversario);
		 
		 EnviarDadosDaPartidaParaLogTask armazenaNoLog = new EnviarDadosDaPartidaParaLogTask();
		 armazenaNoLog.execute(dadosPartida);
	 }	


}
