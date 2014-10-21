package com.phiworks.sumosenseinew;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Configuracoes extends Activity
{
	private boolean mostrarRegrasTreinamento;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracoes);
		
		
		ArmazenaMostrarRegrasTreinamento armazenaMostrarRegras = 
							ArmazenaMostrarRegrasTreinamento.getInstance();
		this.mostrarRegrasTreinamento = 
				armazenaMostrarRegras.getMostrarRegrasDoTreinamento(this);
		
		Button botaoCheckbox = (Button) findViewById(R.id.checkbox_mostrar_regras_treinamento);
		
		if(this.mostrarRegrasTreinamento == true)
		{
			botaoCheckbox.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
		}
		else
		{
			botaoCheckbox.setBackgroundResource(R.drawable.checkbox_desmarcada_regras_treinamento);
		}
	}

	
	
	public void salvarConfiguracoes(View v)
	{
		//armazenar o valor da checkbox mostrar regras modo treinamento
		ArmazenaMostrarRegrasTreinamento armazenaMostrarRegras = 
				ArmazenaMostrarRegrasTreinamento.getInstance();
		armazenaMostrarRegras.alterarMostrarRegrasDoTreinamento(this, this.mostrarRegrasTreinamento);
		
		Intent voltaAoMenuPrincipal =
				new Intent(this, MainActivity.class);
		voltaAoMenuPrincipal.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(voltaAoMenuPrincipal);
	}
	
	public void usarConfiguracoesPadrao(View v)
	{
		//armazenar valor padrao de mostrar regras modo treinamento
		ArmazenaMostrarRegrasTreinamento armazenaMostrarRegras = 
				ArmazenaMostrarRegrasTreinamento.getInstance();
		armazenaMostrarRegras.alterarMostrarRegrasDoTreinamento(this, true);
		
		mostrarRegrasTreinamento = true;
		Button botaoCheckbox = (Button) findViewById(R.id.checkbox_mostrar_regras_treinamento);
		botaoCheckbox.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
	}
	
	public void mudarValorMostrarRegrasTreinamento(View v)
	{
		if(this.mostrarRegrasTreinamento == true)
		{
			this.mostrarRegrasTreinamento = false;
		}
		else
		{
			this.mostrarRegrasTreinamento = true;
		}
		
		Button botaoCheckbox = (Button) findViewById(R.id.checkbox_mostrar_regras_treinamento);
		
		if(this.mostrarRegrasTreinamento == true)
		{
			botaoCheckbox.setBackgroundResource(R.drawable.checkbox_marcada_regras_treinamento);
		}
		else
		{
			botaoCheckbox.setBackgroundResource(R.drawable.checkbox_desmarcada_regras_treinamento);
		}
	}

}
