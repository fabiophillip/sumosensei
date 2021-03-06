package br.ufrn.dimap.pairg.sumosensei.android;

import br.ufrn.dimap.pairg.sumosensei.android.R;

/*classe que armazena a musica que estah atualmente tocando como background do jogo. Foi o unico jeito
 * de fazer ela tocar mesmo apos onstop() de uma tela. O BackgroundSoundService usa ela no onCreate*/
public class SingletonArmazenaIdMusicaTocandoAtualmente 
{
	private static SingletonArmazenaIdMusicaTocandoAtualmente instancia;
	private int idMusicaTocandoAtualmente;
	
	private SingletonArmazenaIdMusicaTocandoAtualmente()
	{
		idMusicaTocandoAtualmente = R.raw.chineseinstrumentalmusic;
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
