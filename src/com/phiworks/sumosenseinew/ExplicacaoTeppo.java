package com.phiworks.sumosenseinew;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ExplicacaoTeppo extends ActivityDoJogoComSom {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explicacao_teppo);
		final AnimationDrawable animacaoTeppo = new AnimationDrawable();
		animacaoTeppo.addFrame(getResources().getDrawable(R.drawable.sumoarenasingle0), 200);
		animacaoTeppo.addFrame(getResources().getDrawable(R.drawable.sumoarenasingle0_alt), 200);
		animacaoTeppo.setOneShot(false);
		ImageView viewSumosNaArena = (ImageView)findViewById(R.id.imagem_treino_teppo);
		viewSumosNaArena.setImageDrawable(animacaoTeppo);
		viewSumosNaArena.post(new Runnable() {
			@Override
			public void run() {
				animacaoTeppo.start();
			}
		});

		
	}
	
	public void irParaSelecionarCategoriasTeppo(View v)
	{
		Intent iniciaTelaTreinoIndividual = new Intent(ExplicacaoTeppo.this, EscolhaNivelActivity.class);
		startActivity(iniciaTelaTreinoIndividual);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

	

	

	

}
