package br.ufrn.dimap.pairg.sumosensei.android;

import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * interface de Activity que precisa esperar a task de pegar os kanjis terminar seu servi�o
 * @author F�bioPhillip
 *
 */
public interface ActivityQueEsperaAtePegarOsKanjis {
	public void mostrarListaComKanjisAposCarregar();
	public void mandarMensagemMultiplayer(String mensagem);
	

}
