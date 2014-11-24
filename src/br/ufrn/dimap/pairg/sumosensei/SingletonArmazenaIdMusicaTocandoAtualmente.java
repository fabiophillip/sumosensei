package br.ufrn.dimap.pairg.sumosensei;

import br.ufrn.dimap.pairg.sumosensei.app.R;

/*classe que armazena a musica que estah atualmente tocando como background do jogo. Foi o unico jeito
 * de fazer ela tocar mesmo apos onstop() de uma tela. O BackgroundSoundService usa ela no onCreate*/
public class SingletonArmazenaIdMusicaTocandoAtualmente 
{
	private static SingletonArmazenaIdMusicaTocandoAtualmente instancia;
	private int idMusicaTocandoAtualmente;
	
	private SingletonArmazenaIdMusicaTocandoAtualmente()
	{
		idMusicaTocandoAtualmente = R.raw.lazy_susan;
	}
	
	public static SingletonArmazenaIdMusicaTocandoAtualmente getInstance()
	{
		if(instancia == null)
		{
			instancia = new SingletonArmazenaIdMusicaTocandoAtualmente();
		}
		
		return instancia;
	}

	public int getIdMusicaTocandoAtualmente() {
		return idMusicaTocandoAtualmente;
	}

	public void setIdMusicaTocandoAtualmente(int idMusicaTocandoAtualmente) {
		this.idMusicaTocandoAtualmente = idMusicaTocandoAtualmente;
	}

}
