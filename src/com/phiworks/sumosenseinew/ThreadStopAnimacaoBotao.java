package com.phiworks.sumosenseinew;

import java.util.LinkedList;

import android.widget.Button;

public class ThreadStopAnimacaoBotao extends Thread
{
	private TelaInicialMultiplayer telaDoJogo;
	private boolean jogadorPerdeuPartidaAnterior;
	public ThreadStopAnimacaoBotao(TelaInicialMultiplayer telaDoJogo, boolean jogadorPerdeuPartidaAnterior)
	{
		
		this.telaDoJogo = telaDoJogo;
		this.jogadorPerdeuPartidaAnterior = jogadorPerdeuPartidaAnterior;
	}
	public void run()
	{
		try {
			Thread.sleep(3000);
			this.telaDoJogo.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					telaDoJogo.prepararNovaPartida(jogadorPerdeuPartidaAnterior);
					
				}
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
