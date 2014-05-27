package lojinha;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

public class ConcreteDAOAcessaComprasMaceteKanji implements DAOAcessaComprasMaceteKanji 
{
	private static ConcreteDAOAcessaComprasMaceteKanji instanciaUnica;//eh singleton
	private ComprasDaLojinhaDeKanjis dadosDeComprasAnterioresDaLojinhaDeKanjis;
	
	private ConcreteDAOAcessaComprasMaceteKanji()
	{
		
	}
	
	public static ConcreteDAOAcessaComprasMaceteKanji getInstance()
	{
		if(instanciaUnica == null)
		{
			instanciaUnica = new ConcreteDAOAcessaComprasMaceteKanji();
		}
		
		return instanciaUnica;
	}

	@Override
	public void inserirMaceteKanjiComprado(String labelMaceteKanjiComprado,
			Context contextoAplicacao) 
	{
		String [] labelMaceteKanjiSplitado = labelMaceteKanjiComprado.split("-");
		String romajiDoMacete = labelMaceteKanjiSplitado[0];
		String romajiDoMaceteSemEspacos = romajiDoMacete.replaceAll("\\s+","");
		String traducaoDoMacete = labelMaceteKanjiSplitado[1];
		String traducaoDoMaceteSemEspacos = traducaoDoMacete.replaceAll("\\s+","");
		String romajiETraducao = romajiDoMaceteSemEspacos + traducaoDoMaceteSemEspacos;
		String romajiETraducaoSemAcento = CorrigeNomesDosMacetesComAcento.corrigirNomesMaceteRemoverAcento(romajiETraducao, contextoAplicacao);
		String nomeUrlDoMaceteKanji = "macete_" + romajiETraducaoSemAcento;
		MaceteKanjiParaListviewSelecionavel maceteKanji = new 
				MaceteKanjiParaListviewSelecionavel(labelMaceteKanjiComprado, true, nomeUrlDoMaceteKanji );
		if(this.dadosDeComprasAnterioresDaLojinhaDeKanjis == null)
		{
			getDadosComprasMaceteKanji(contextoAplicacao);
			if(this.dadosDeComprasAnterioresDaLojinhaDeKanjis != null)
			{
				this.dadosDeComprasAnterioresDaLojinhaDeKanjis.adicionarMaceteDeKanjiComprado(maceteKanji);
			}
		}
		else
		{
			this.dadosDeComprasAnterioresDaLojinhaDeKanjis.adicionarMaceteDeKanjiComprado(maceteKanji);
		}
		
		this.escreverComprasDeMacetesNoArquivoInterno(contextoAplicacao);
		
		
	}
	
	private void escreverComprasDeMacetesNoArquivoInterno(Context context)
	{
		FileOutputStream outputStream;

		try 
		{
		  outputStream = context.openFileOutput("dadosComprasMaceteKanji", Context.MODE_PRIVATE);
		  ObjectOutputStream outputStreamGravaObjetos = new ObjectOutputStream(outputStream);
		  outputStreamGravaObjetos.writeObject(dadosDeComprasAnterioresDaLojinhaDeKanjis);
		  outputStreamGravaObjetos.close();
		  outputStream.close();
		} 
		catch (Exception e) 
		{
		  e.printStackTrace();
		}
	}

	@Override
	public ComprasDaLojinhaDeKanjis getDadosComprasMaceteKanji(Context contextoAplicacao) {
		try
		{
			File arquivoDadosDeCompraMaceteKanji =
					new File(contextoAplicacao.getFilesDir() + File.separator + "dadosComprasMaceteKanji");
			if(arquivoDadosDeCompraMaceteKanji.exists() == false)
			{
				this.dadosDeComprasAnterioresDaLojinhaDeKanjis = new ComprasDaLojinhaDeKanjis();
				return this.dadosDeComprasAnterioresDaLojinhaDeKanjis;
			}
			else
			{
				FileInputStream leitorArquivo = new FileInputStream(arquivoDadosDeCompraMaceteKanji);
				ObjectInputStream leitorObjeto = new ObjectInputStream(leitorArquivo);
				Object objetoLido = leitorObjeto.readObject();
				leitorObjeto.close();
				if(objetoLido != null)
				{
					this.dadosDeComprasAnterioresDaLojinhaDeKanjis = (ComprasDaLojinhaDeKanjis) objetoLido;
					
					return dadosDeComprasAnterioresDaLojinhaDeKanjis;
				}
				else
				{
					this.dadosDeComprasAnterioresDaLojinhaDeKanjis = new ComprasDaLojinhaDeKanjis();
					return this.dadosDeComprasAnterioresDaLojinhaDeKanjis;
				}
			}
		}
		catch(Exception e)
		{
			Exception exc = e;
			this.dadosDeComprasAnterioresDaLojinhaDeKanjis = new ComprasDaLojinhaDeKanjis();
			return this.dadosDeComprasAnterioresDaLojinhaDeKanjis;
		}
	}


	

}
