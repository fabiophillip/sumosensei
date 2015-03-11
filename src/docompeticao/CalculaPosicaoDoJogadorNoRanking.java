package docompeticao;

import br.ufrn.dimap.pairg.sumosensei.app.R;
import android.content.Context;

public class CalculaPosicaoDoJogadorNoRanking {
	
	public static String definirTituloDoJogadorNoRanking(Context contextoAplicacao)
	{
		SingletonGuardaDadosUsuarioNoRanking guardaDadosRanking = SingletonGuardaDadosUsuarioNoRanking.getInstance();
		DadosUsuarioNoRanking dadosRanking = guardaDadosRanking.getDadosSalvosUsuarioNoRanking();
		int posicaoAtualJogador = dadosRanking.getPosicaoJogadorNoRanking();
		int vitoriasganhas = dadosRanking.getVitoriasCompeticao();
		if(vitoriasganhas >= 5000)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_1);
		}
		else if(posicaoAtualJogador >= 0 && posicaoAtualJogador <= 8)
		{
			/*if(vitoriasganhas < 7)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_2);
		}
		else if(posicaoAtualJogador >= 9 && posicaoAtualJogador <= 18)
		{
			/*if(vitoriasganhas < 6)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_3);
			
		}
		else if(posicaoAtualJogador >= 19 && posicaoAtualJogador <= 30)
		{
			/*if(vitoriasganhas < 5)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_4);
		}
		else if(posicaoAtualJogador >= 31 && posicaoAtualJogador <= 58)
		{
			/*if(vitoriasganhas < 4)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_5);
		}
		else if(posicaoAtualJogador >= 59 && posicaoAtualJogador <= 178)
		{
			/*if(vitoriasganhas < 3)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_6);
		}
		else if(posicaoAtualJogador >= 179 && posicaoAtualJogador <= 378)
		{
			/*if(vitoriasganhas < 2)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_7);
		}
		else if(posicaoAtualJogador >= 379 && posicaoAtualJogador <= 638)
		{
			/*if(vitoriasganhas < 1)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_8);
		}
		else 
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_9);
		}
		
		
	}
	
	public static String definirTituloJogador(Context contextoAplicacao, int posicaoAtualJogador, int vitoriasGanhas)
	{
		if(vitoriasGanhas >= 5000)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_1);
		}
		else if(posicaoAtualJogador >= 0 && posicaoAtualJogador <= 8)
		{
			/*if(vitoriasganhas < 7)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_2);
		}
		else if(posicaoAtualJogador >= 9 && posicaoAtualJogador <= 18)
		{
			/*if(vitoriasganhas < 6)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_3);
			
		}
		else if(posicaoAtualJogador >= 19 && posicaoAtualJogador <= 30)
		{
			/*if(vitoriasganhas < 5)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_4);
		}
		else if(posicaoAtualJogador >= 31 && posicaoAtualJogador <= 58)
		{
			/*if(vitoriasganhas < 4)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_5);
		}
		else if(posicaoAtualJogador >= 59 && posicaoAtualJogador <= 178)
		{
			/*if(vitoriasganhas < 3)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_6);
		}
		else if(posicaoAtualJogador >= 179 && posicaoAtualJogador <= 378)
		{
			/*if(vitoriasganhas < 2)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_7);
		}
		else if(posicaoAtualJogador >= 379 && posicaoAtualJogador <= 638)
		{
			/*if(vitoriasganhas < 1)
			{
				return definirTituloJogadorPoucosUsuarios(contextoAplicacao);
			}*/
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_8);
		}
		else 
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_9);
		}
	}
	
	/*public static String definirTituloJogadorPoucosUsuarios(Context contextoAplicacao)
	{
		SingletonGuardaDadosUsuarioNoRanking guardaDadosRanking = SingletonGuardaDadosUsuarioNoRanking.getInstance();
		DadosUsuarioNoRanking dadosRanking = guardaDadosRanking.getDadosSalvosUsuarioNoRanking();
		int posicaoAtualJogador = dadosRanking.getPosicaoJogadorNoRanking();
		int vitoriasganhas = dadosRanking.getVitoriasCompeticao();
		if(vitoriasganhas >= 7)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_2);
		}
		if(vitoriasganhas >= 6)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_3);
		}
		else if(vitoriasganhas >= 5)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_4);
		}
		else if(vitoriasganhas >= 4)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_5);
		}
		else if(vitoriasganhas >= 3)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_6);
		}
		else if(vitoriasganhas >= 2)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_7);
		}
		else if(vitoriasganhas >= 1)
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_8);
		}
		else
		{
			return contextoAplicacao.getResources().getString(R.string.sumo_ranking_9);
		}
	}*/
	
	public static int getIdSalaDeAcordoComStringJogador(String tituloJogador, Context contextoAplicacao)
	{
		String [] titulos =  new String [9];
		titulos[0] = contextoAplicacao.getString(R.string.sumo_ranking_1);
		titulos[1] = contextoAplicacao.getString(R.string.sumo_ranking_2);
		titulos[2] = contextoAplicacao.getString(R.string.sumo_ranking_3);
		titulos[3] = contextoAplicacao.getString(R.string.sumo_ranking_4);
		titulos[4] = contextoAplicacao.getString(R.string.sumo_ranking_5);
		titulos[5] = contextoAplicacao.getString(R.string.sumo_ranking_6);
		titulos[6] = contextoAplicacao.getString(R.string.sumo_ranking_7);
		titulos[7] = contextoAplicacao.getString(R.string.sumo_ranking_8);
		titulos[8] = contextoAplicacao.getString(R.string.sumo_ranking_9);
		
		int idSalaDoJOgador = 0;
		
		for(idSalaDoJOgador = 0; idSalaDoJOgador < titulos.length; idSalaDoJOgador++)
		{
			String umTitulo = titulos[idSalaDoJOgador];
			if(umTitulo.compareToIgnoreCase(tituloJogador) == 0)
			{
				break;
			}
		}
		
		idSalaDoJOgador = idSalaDoJOgador + 1;
		
		return idSalaDoJOgador;
		
		
	}
	
	public static String definirTituloDoJogadorParaBDCriacaoDeSala(String tituloAtualJogador, Context contextoAplicacao)
	{
		String [] titulos =  new String [9];
		titulos[0] = contextoAplicacao.getString(R.string.sumo_ranking_1);
		titulos[1] = contextoAplicacao.getString(R.string.sumo_ranking_2);
		titulos[2] = contextoAplicacao.getString(R.string.sumo_ranking_3);
		titulos[3] = contextoAplicacao.getString(R.string.sumo_ranking_4);
		titulos[4] = contextoAplicacao.getString(R.string.sumo_ranking_5);
		titulos[5] = contextoAplicacao.getString(R.string.sumo_ranking_6);
		titulos[6] = contextoAplicacao.getString(R.string.sumo_ranking_7);
		titulos[7] = contextoAplicacao.getString(R.string.sumo_ranking_8);
		titulos[8] = contextoAplicacao.getString(R.string.sumo_ranking_9);
		
		int idTituloJOgador = 0;
		
		for(idTituloJOgador = 0; idTituloJOgador < titulos.length; idTituloJOgador++)
		{
			String umTitulo = titulos[idTituloJOgador];
			if(umTitulo.compareToIgnoreCase(tituloAtualJogador) == 0)
			{
				break;
			}
		}
		
		idTituloJOgador = idTituloJOgador + 1;
		
		return "Ranking_" + idTituloJOgador;
		
	}
	
	public static String decodificarTituloDoJogadorBDCriacaoDeSala(String tituloAtualJogadorNoBD, Context contextoAplicacao)
	{
		String [] titulos =  new String [9];
		titulos[0] = contextoAplicacao.getString(R.string.sumo_ranking_1);
		titulos[1] = contextoAplicacao.getString(R.string.sumo_ranking_2);
		titulos[2] = contextoAplicacao.getString(R.string.sumo_ranking_3);
		titulos[3] = contextoAplicacao.getString(R.string.sumo_ranking_4);
		titulos[4] = contextoAplicacao.getString(R.string.sumo_ranking_5);
		titulos[5] = contextoAplicacao.getString(R.string.sumo_ranking_6);
		titulos[6] = contextoAplicacao.getString(R.string.sumo_ranking_7);
		titulos[7] = contextoAplicacao.getString(R.string.sumo_ranking_8);
		titulos[8] = contextoAplicacao.getString(R.string.sumo_ranking_9);
		
		int idTituloJOgador = 0;
		
		for(idTituloJOgador = 0; idTituloJOgador < titulos.length; idTituloJOgador++)
		{
			int idParaTitulosNoBD = idTituloJOgador + 1;
			String umTituloEstiloBD = "Ranking_" + idParaTitulosNoBD;
			if(umTituloEstiloBD.compareTo(tituloAtualJogadorNoBD) == 0)
			{
				break;
			}
		}
		
		
		return titulos[idTituloJOgador];
		
	}

}
