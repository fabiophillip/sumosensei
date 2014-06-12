package cenario;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class AdicionaAnimacaoAoImageView {
	
	public static void adicionarAnimacaoAoImageview(ImageView imageViewTerahAnimacao, 
			LinkedList<String> nomesDosPngsDaAnimacao, Context contextoActivityAtual)
	{
		final AnimationDrawable novaAnimacao = new AnimationDrawable();
		for(int i = 0; i < nomesDosPngsDaAnimacao.size(); i++)
		{
			String umNomePngAnimacao = nomesDosPngsDaAnimacao.get(i);
			int idPngAnimacao = contextoActivityAtual.getResources().getIdentifier(umNomePngAnimacao, "drawable", contextoActivityAtual.getPackageName());
			novaAnimacao.addFrame(contextoActivityAtual.getResources().getDrawable(idPngAnimacao), 200);
		} 
		novaAnimacao.setOneShot(false);
		imageViewTerahAnimacao.setImageDrawable(novaAnimacao);
		imageViewTerahAnimacao.post(new Runnable() {
			@Override
			public void run() {
				novaAnimacao.start();
			}
		});
	}
	
	public static void adicionarAnimacaoAoImageview(ImageView imageViewTerahAnimacao, 
			LinkedList<String> nomesDosPngsDaAnimacao, Context contextoActivityAtual, 
			boolean animacaoEhSemLoop)
	{
		final AnimationDrawable novaAnimacao = new AnimationDrawable();
		for(int i = 0; i < nomesDosPngsDaAnimacao.size(); i++)
		{
			String umNomePngAnimacao = nomesDosPngsDaAnimacao.get(i);
			int idPngAnimacao = contextoActivityAtual.getResources().getIdentifier(umNomePngAnimacao, "drawable", contextoActivityAtual.getPackageName());
			novaAnimacao.addFrame(contextoActivityAtual.getResources().getDrawable(idPngAnimacao), 200);
		} 
		novaAnimacao.setOneShot(animacaoEhSemLoop);
		imageViewTerahAnimacao.setImageDrawable(novaAnimacao);
		imageViewTerahAnimacao.post(new Runnable() {
			@Override
			public void run() {
				novaAnimacao.start();
			}
		});
	}
	
	
	public static void adicionarAnimacaoAoImageview(ImageView imageViewTerahAnimacao, 
			LinkedList<String> nomesDosPngsDaAnimacao, Context contextoActivityAtual, 
			boolean animacaoEhSemLoop, int milisegundosQueCadaCenaDura )
	{
		final AnimationDrawable novaAnimacao = new AnimationDrawable();
		for(int i = 0; i < nomesDosPngsDaAnimacao.size(); i++)
		{
			String umNomePngAnimacao = nomesDosPngsDaAnimacao.get(i);
			int idPngAnimacao = contextoActivityAtual.getResources().getIdentifier(umNomePngAnimacao, "drawable", contextoActivityAtual.getPackageName());
			novaAnimacao.addFrame(contextoActivityAtual.getResources().getDrawable(idPngAnimacao), milisegundosQueCadaCenaDura);
		} 
		novaAnimacao.setOneShot(animacaoEhSemLoop);
		imageViewTerahAnimacao.setImageDrawable(novaAnimacao);
		imageViewTerahAnimacao.post(new Runnable() {
			@Override
			public void run() {
				novaAnimacao.start();
			}
		});
	}

}
