package lojinha;

import android.content.Context;

public interface DAOAcessaComprasMaceteKanji {
	public void inserirMaceteKanjiComprado(String labelMaceteKanjiComprado, Context contextoAplicacao);
	public ComprasDaLojinhaDeKanjis getDadosComprasMaceteKanji(Context contextoAplicacao);
	

}
