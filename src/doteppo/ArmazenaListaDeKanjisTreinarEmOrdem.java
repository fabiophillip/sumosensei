package doteppo;

import java.util.LinkedList;

import bancodedados.KanjiTreinar;

public class ArmazenaListaDeKanjisTreinarEmOrdem 
{
	private static ArmazenaListaDeKanjisTreinarEmOrdem instancia;
	private LinkedList<KanjiTreinar> listaDeKanjisPraTreinarEmOrdem;
	private int indiceProximoKanjiTreinar;
	
	
	private ArmazenaListaDeKanjisTreinarEmOrdem()
	{
		
	}
	
	public static ArmazenaListaDeKanjisTreinarEmOrdem getInstance()
	{
		if(instancia == null)
		{
			instancia = new ArmazenaListaDeKanjisTreinarEmOrdem();
		}
		
		return instancia;
	}

	public void setListaDeKanjisPraTreinarEmOrdem(
			LinkedList<KanjiTreinar> listaDeKanjisPraTreinarEmOrdem) {
		this.listaDeKanjisPraTreinarEmOrdem = listaDeKanjisPraTreinarEmOrdem;
		this.indiceProximoKanjiTreinar = 0;
	}
	
	
	
	public LinkedList<KanjiTreinar> getListaDeKanjisPraTreinarEmOrdem() {
		return listaDeKanjisPraTreinarEmOrdem;
	}

	public KanjiTreinar pegarProximoKanjiPraTreinar()
	{
		if(indiceProximoKanjiTreinar >= this.listaDeKanjisPraTreinarEmOrdem.size())
		{
			indiceProximoKanjiTreinar = 0;
		}
		KanjiTreinar proximoKanjiTreinar = this.listaDeKanjisPraTreinarEmOrdem.get(indiceProximoKanjiTreinar);
		
		indiceProximoKanjiTreinar = indiceProximoKanjiTreinar + 1;
		return proximoKanjiTreinar;
		
	}
	
	
	

}
