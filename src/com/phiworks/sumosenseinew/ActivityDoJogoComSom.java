package com.phiworks.sumosenseinew;

import java.util.LinkedList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import bancodedados.KanjiTreinar;


import com.google.example.games.basegameutils.BaseGameActivity;
import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.sumosenseinew.BackgroundSoundService.BackgroundSoundServiceBinder;

public abstract class ActivityDoJogoComSom extends BaseGameActivity{

	private SoundPool soundPool;
	private int soundIds[] = new int[10];//array com todos os SFXs da tela, limitado a 10 SFXs
	private boolean soundPoolLoaded;
	private boolean activityEstahAmarradaComServiceMusicaDeFundo = false;//a activity estah conectada com o service que roda musica de fundo ou nao
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		try
		{
			super.onCreate(savedInstanceState);
			this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
			
			soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
			soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
				@Override
				public void onLoadComplete(SoundPool soundPool, int sampleId,
						int status) {
					soundPoolLoaded = true;
				}
			});
			soundIds = new int[20];
			//adicionar os sfxs do seu jogo
			soundIds[0] = soundPool.load(getApplicationContext(), R.raw.correct_sound, 1);
			soundIds[1] = soundPool.load(getApplicationContext(), R.raw.cartoon_hop, 1);
			soundIds[2] = soundPool.load(getApplicationContext(), R.raw.small_group_of_american_children_shout_hooray_, 1);
			soundIds[3] = soundPool.load(getApplicationContext(), R.raw.drink_slurp_from_cup_001, 1);
			soundIds[4] = soundPool.load(getApplicationContext(), R.raw.grunt_as_if_been_hit_or_winded_male, 1);
			soundIds[5] = soundPool.load(getApplicationContext(), R.raw.human_face_punch, 1);
			soundIds[6] = soundPool.load(getApplicationContext(), R.raw.blood_splat, 1);
			soundIds[7] = soundPool.load(getApplicationContext(), R.raw.voice_clip_martial_arts_karate_or_kung_fu_style_huh_, 1);
			soundIds[8] = soundPool.load(getApplicationContext(), R.raw.cartoon_dazed_headshake_vocal_2, 1);
			soundIds[9] = soundPool.load(getApplicationContext(), R.raw.small_tree_fall_over, 1);
			soundIds[10] = soundPool.load(getApplicationContext(), R.raw.tree_fall_down, 1);
		}
		catch(Exception exc)
		{
			//Toast.makeText(getApplicationContext(), exc.getMessage(), Toast.LENGTH_LONG).show();
		}
		
	}
	
	
	
	/**
	 * reproduz um sfx de acordo com a situacao do usuario no jogo
	 * @param situacaoDoUsuario "jogadorGanhou", "jogadorAcertouAlternativa" ou "jogadorErrouAlternativa"
	 */
	protected void reproduzirSfx(String situacaoDoUsuario)
	{
		if(situacaoDoUsuario.compareTo("noJogo-jogadorGanhou") == 0)
		{
			this.tocarSom(soundIds[2]);
		}
		else if(situacaoDoUsuario.compareTo("noJogo-jogadorAcertouAlternativa") == 0)
		{
			this.tocarSom(soundIds[0]);
		}
		else if(situacaoDoUsuario.compareTo("noJogo-jogadorErrouAlternativa") == 0)
		{
			this.tocarSom(soundIds[1]);
		}
		else if(situacaoDoUsuario.compareTo("noJogo-chikaramizu") == 0)
		{
			this.tocarSom(soundIds[3]);
		}
		else if(situacaoDoUsuario.compareTo("noJogo-levouGolpeChikaramizu") == 0)
		{
			this.tocarSom(soundIds[4]);
		}
		else if(situacaoDoUsuario.compareTo("noJogo-usouShiko") == 0)
		{
			this.tocarSom(soundIds[5]);
		}
		else if(situacaoDoUsuario.compareTo("noJogo-usouTegata") == 0)
		{
			this.tocarSom(soundIds[6]);
		}
		else if(situacaoDoUsuario.compareTo("noJogo-usouTeppotree") == 0)
		{
			this.tocarSom(soundIds[7]);
		}
		else if(situacaoDoUsuario.compareTo("noJogo-jogadorDefendeu") == 0)
		{
			this.tocarSom(soundIds[8]);
		}
		else if(situacaoDoUsuario.compareTo("teppo-empurrouArvore") == 0)
		{
			this.tocarSom(soundIds[9]);
		}
		else if(situacaoDoUsuario.compareTo("teppo-derrubouArvore") == 0)
		{
			this.tocarSom(soundIds[10]);
		}
		
	}
	
	private void tocarSom(int idSom)
	{
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		float actualVolume = (float) audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float maxVolume = (float) audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = actualVolume / maxVolume;
		// Is the sound loaded already?
		if (this.soundPoolLoaded == true) {
			
			soundPool.play(idSom, 1, 1, 9, 0, 1f);
			Log.e("Test", "Played sound");
		}
	}
	
	
	@Override
	public abstract void onSignInFailed();

	@Override
	public abstract void onSignInSucceeded();
	
	
	/**
	 * REFERENTE A BACKGROUND MUSIC
	 */
	
	private BackgroundSoundService servicoFazMusicaDeFundo;//servico que faz a musica de fundo do jogo
	
	
	public void mudarMusicaDeFundo(int idMusicaDeFundo)
	{
		 if (activityEstahAmarradaComServiceMusicaDeFundo == true && servicoFazMusicaDeFundo != null) {
			 
			 this.servicoFazMusicaDeFundo.mudarMusica(idMusicaDeFundo);
		 }
	}
	
	@Override
	protected void onPause()
	{
		//TocadorMusicaBackground.getInstance().pausarTocadorMusica();
		
		
		Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
          ComponentName topActivity = taskInfo.get(0).topActivity; 
          if (!topActivity.getPackageName().equals(context.getPackageName())) {
        	
        	Intent iniciaMusicaFundo = new Intent(ActivityDoJogoComSom.this, BackgroundSoundService.class);
  			//stopService(iniciaMusicaFundo);
            Toast.makeText(ActivityDoJogoComSom.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            // Unbind from the service
	       
          }
        }
        
        super.onPause();
		
	}
	
	@Override
	protected void onResume()
	{
		if(isMyServiceRunning() == false)
		{
			Intent iniciaMusicaFundo = new Intent(ActivityDoJogoComSom.this, BackgroundSoundService.class);
			//startService(iniciaMusicaFundo);
			
		}
		super.onResume();
		//TocadorMusicaBackground.getInstance().resumirTocadorMusica();
		
	}
	
	 @Override
	    protected void onStart() {
	        super.onStart();
	        // Bind to the service
	        bindService(new Intent(this, BackgroundSoundService.class), mConnection,
	            Context.BIND_AUTO_CREATE);
	    }

	    @Override
	    protected void onStop() {
	        super.onStop();
	        // Unbind from the service
	        if (activityEstahAmarradaComServiceMusicaDeFundo) {
	            unbindService(mConnection);
	            activityEstahAmarradaComServiceMusicaDeFundo = false;
	        }
	    }
	
	
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (BackgroundSoundService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	//REFERENTE AO BINDIND DA ACTIVITY AO SERVICO DE MUSICA DE FUNDO PARA MUDAR MUSICA
	  /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BackgroundSoundServiceBinder binder = (BackgroundSoundServiceBinder) service;
            servicoFazMusicaDeFundo = binder.getService();
            activityEstahAmarradaComServiceMusicaDeFundo = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            activityEstahAmarradaComServiceMusicaDeFundo = false;
        }
    };
    
    
    

}
