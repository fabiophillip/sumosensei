package armazenamentointerno;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

import bancodedados.KanjiTreinar;


import android.content.Context;

public class ConcreteDAOArmazenaInternamenteDadosDePartidasRealizadas implements DAOAcessaHistoricoDePartidas {
	private LinkedList<DadosDePartida> dadosDePartidasAnteriores;
	private static int limiteDePartidasParaArmazenar = 5;//armazzena 5 ultimas partidas.
	private static ConcreteDAOArmazenaInternamenteDadosDePartidasRealizadas instanciaArmazenaDadosDePartidas;
	
	private ConcreteDAOArmazenaInternamenteDadosDePartidasRealizadas()
	{
		this.dadosDePartidasAnteriores = new LinkedList<DadosDePartida>();
	}
	
	public static ConcreteDAOArmazenaInternamenteDadosDePartidasRealizadas getInstance(Context context)
	{
		if(instanciaArmazenaDadosDePartidas == null)
		{
			instanciaArmazenaDadosDePartidas= new ConcreteDAOArmazenaInternamenteDadosDePartidasRealizadas();
			criarPastaDadosDePartidasAnteriores(context);
		}
		
		return instanciaArmazenaDadosDePartidas;
	}
	
	private static void criarPastaDadosDePartidasAnteriores(Context c)
	{
		//se já existe, não tem problema. Não será criado novamente
		File file = new File(c.getFilesDir(), "dadosDePartidasAnteriores");
	}
	
	//CHAMAR NA ACTIVITY QUE MOSTRA O LOG DAS JOGATINAS ANTERIORES
	public LinkedList<DadosDePartida> getDadosDePartidasAnteriores(Context context) 
	{
		try
		{
			File arquivoListasDeDicas =
					new File(context.getFilesDir() + File.separator + "dadosDePartidasAnteriores");
			if(arquivoListasDeDicas.exists() == false)
			{
				return new LinkedList<DadosDePartida>();
			}
			else
			{
				FileInputStream leitorArquivo = new FileInputStream(arquivoListasDeDicas);
				ObjectInputStream leitorObjeto = new ObjectInputStream(leitorArquivo);
				Object objetoLido = leitorObjeto.readObject();
				leitorObjeto.close();
				if(objetoLido != null)
				{
					this.dadosDePartidasAnteriores = (LinkedList<DadosDePartida>) objetoLido;
					return dadosDePartidasAnteriores;
				}
				else
				{
					return new LinkedList<DadosDePartida>();
				}
			}
			
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	/**
	 * CHAMAR QUANDO QUISER ARMAZENAR DADOS DE UMA PARTIDA
	 * @param categoriasTreinadasNaPartida
	 * @param kanjisTreinadosNaPartida
	 * @param kanjisErradosNaPartida
	 * @param pontuacaoNaPartida
	 * @param itensUsadosNaPartida
	 * @param context geralmente é getApplicationContext() se vc estah em uma Activity
	 */
	public void inserirDadosDeNovaPartida(LinkedList<String> categoriasTreinadasNaPartida, 
			LinkedList<KanjiTreinar> kanjisTreinadosNaPartida, LinkedList<KanjiTreinar> kanjisErradosNaPartida,
			int pontuacaoNaPartida, LinkedList<String> itensUsadosNaPartida, Context context)
	{
		DadosDePartida novoDadoDePartida = new DadosDePartida();
		novoDadoDePartida.setCategoriasTreinadasNaPartida(categoriasTreinadasNaPartida);
		novoDadoDePartida.setItensUsadosNaPartida(itensUsadosNaPartida);
		novoDadoDePartida.setKanjisErradosNaPartida(kanjisErradosNaPartida);
		novoDadoDePartida.setKanjisTreinadosNaPartida(kanjisTreinadosNaPartida);
		novoDadoDePartida.setPontuacaoNaPartida(pontuacaoNaPartida);
		this.dadosDePartidasAnteriores.add(novoDadoDePartida);
		//precisamos ver se precisa deletar dados de uma partida mais antiga...
		if(this.dadosDePartidasAnteriores.size() > this.limiteDePartidasParaArmazenar)
		{
			this.dadosDePartidasAnteriores.removeFirst();
		}
		
		escreverListasDeDicasQueUsuarioCriouNoArquivoInterno(context);
	}
	
	private void escreverListasDeDicasQueUsuarioCriouNoArquivoInterno(Context context)
	{
		FileOutputStream outputStream;

		try 
		{
		  outputStream = context.openFileOutput("dadosDePartidasAnteriores", Context.MODE_PRIVATE);
		  ObjectOutputStream outputStreamGravaObjetos = new ObjectOutputStream(outputStream);
		  outputStreamGravaObjetos.writeObject(dadosDePartidasAnteriores);
		  outputStreamGravaObjetos.close();
		  outputStream.close();
		} 
		catch (Exception e) 
		{
		  e.printStackTrace();
		}
	}
	
	
	
	

}
