package com.phiworks.sumosenseinew;


import com.phiworks.dapartida.GuardaDadosDaPartida;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class FimDeTreino extends ActivityDoJogoComSom {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fim_de_treino);
		
		this.atualizarAnimacaoSumozinhoAcertandoArvore();
		
		TextView textviewTextoFinal = (TextView)findViewById(R.id.texto_vitoria_treino);
		int numeroAcertos = GuardaDadosDaPartida.getInstance().getRoundDaPartida() - 1;
		
		
		String fraseFinal = "" + numeroAcertos;
		if(numeroAcertos < 10)
		{
			fraseFinal = fraseFinal + " " + getResources().getString(R.string.vitoriaTeppo0);
			
		}
		else if(numeroAcertos < 20)
		{
			fraseFinal = fraseFinal + " " + getResources().getString(R.string.vitoriaTeppo1);
		}
		else if(numeroAcertos < 30)
		{
			fraseFinal = fraseFinal + " " +  getResources().getString(R.string.vitoriaTeppo2);
		}
		else if(numeroAcertos < 40)
		{
			fraseFinal = fraseFinal + " " +  getResources().getString(R.string.vitoriaTeppo3);
		}
		else
		{
			fraseFinal = fraseFinal + " " +  getResources().getString(R.string.vitoriaTeppo4);
		}
		textviewTextoFinal.setText(fraseFinal);

		final Animation animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
		animScale.setRepeatCount(Animation.INFINITE);
		View viewTextoFinalEFundo = findViewById(R.id.nihonball_e_text_final);
		viewTextoFinalEFundo.startAnimation(animScale);
		
	}

	

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	private void atualizarAnimacaoSumozinhoAcertandoArvore()
	{
		final AnimationDrawable animacaoSumozinhoEArvore = new AnimationDrawable();
		GuardaDadosDaPartida guardaDadosPartida = GuardaDadosDaPartida.getInstance();
		int numeroDeAcertos = guardaDadosPartida.getRoundDaPartida() - 1; 
		String nomeImagemSumozinhoAnimacao1 = this.getNomeImagemSumozinho(numeroDeAcertos);
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
	
	private String getNomeImagemSumozinho(int numeroDeAcertos)
	{
		String nomeImagemSumozinho = "sumoarenasingle";
		if(numeroDeAcertos < 10)
		{
			//0..5
			nomeImagemSumozinho = nomeImagemSumozinho + 0;
		}
		else if(numeroDeAcertos < 20)
		{
			//0..9
			nomeImagemSumozinho = nomeImagemSumozinho + 10;
		}
		else if(numeroDeAcertos < 30)
		{
			//10..14
			nomeImagemSumozinho = nomeImagemSumozinho + 20;
		}
		else if(numeroDeAcertos < 40)
		{
			//15..20
			nomeImagemSumozinho = nomeImagemSumozinho + 30;
		}
		else
		{
			nomeImagemSumozinho = nomeImagemSumozinho + 40;
		}
		return nomeImagemSumozinho;
	}
	
	public void voltarAoMenuPrincipal(View v)
	{
		Intent intentVoltaMenuPrincipal = new Intent(FimDeTreino.this, MainActivity.class);
    	intentVoltaMenuPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intentVoltaMenuPrincipal);
	}

}
