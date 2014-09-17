package com.phiworks.dapartida;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.phiworks.sumosenseinew.ActivityDoJogoComSom;
import com.phiworks.sumosenseinew.ActivityMultiplayerQueEsperaAtePegarOsKanjis;
import com.phiworks.sumosenseinew.ActivityQueEsperaAtePegarOsKanjis;
import com.phiworks.sumosenseinew.R;

public abstract class ActivityPartidaMultiplayer extends ActivityDoJogoComSom implements View.OnClickListener, RealTimeMessageReceivedListener,RoomStatusUpdateListener, RoomUpdateListener, OnInvitationReceivedListener, ActivityQueEsperaAtePegarOsKanjis
{
	protected int mScore = 0; // user's current score
	protected int mSecondsLeft = -1; // how long until the game ends (seconds)
	protected final static int GAME_DURATION = 90; // game duration, seconds.
	protected boolean estahComAnimacaoTegata = false;
	
	
	public void setEstahComAnimacaoTegata(boolean novoEstado)
	{
		this.estahComAnimacaoTegata = novoEstado;
	}
	public abstract void terminarJogoMultiplayer();
	
	public void criarSalaQuickMatch(int idSalaQuickMatch)
	{
		
		// quick-start a game with 1 randomly selected opponent
		final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
		Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
		        MAX_OPPONENTS, 0);
		RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(this);
		rtmConfigBuilder.setMessageReceivedListener(this);
		rtmConfigBuilder.setRoomStatusUpdateListener(this);
		rtmConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);

		rtmConfigBuilder.setVariant(idSalaQuickMatch); //somente dois usuarios com o mesmo variante podem jogar juntos no automatch. Usaremos o nivel do usuario como esse variante

		switchToScreen(R.id.screen_wait);
		keepScreenOn();
		resetGameVars();
		Games.RealTimeMultiplayer.create(getApiClient(), rtmConfigBuilder.build());
		//Toast.makeText(getApplicationContext(), idSalaQuickMatch, Toast.LENGTH_SHORT).show();
	}
	
	public void keepScreenOn() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	
	public abstract void switchToScreen(int screenId);
	

	// Reset game variables in preparation for a new game.
	void resetGameVars() {
	mSecondsLeft = GAME_DURATION;
	mScore = 0;
	}
	public abstract void setarIdDaSala(int idSalaModoCasual);

}
