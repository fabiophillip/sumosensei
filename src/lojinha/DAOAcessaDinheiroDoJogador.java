package lojinha;

import android.content.Context;

public interface DAOAcessaDinheiroDoJogador {
	
	public int getCreditoQuePossui(Context contextoAplicacao);
	public void adicionarCredito(int creditoAdicionar, Context contextoAplicacao);
	public void reduzirCredito(int creditoReduzir, Context contextoAplicacao);

}
