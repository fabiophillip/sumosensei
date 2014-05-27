package lojinha;

import android.content.Context;
import android.content.SharedPreferences;

public class ConcreteDAOAcessaDinheiroDoJogador implements DAOAcessaDinheiroDoJogador{
	
	private static volatile ConcreteDAOAcessaDinheiroDoJogador instanciaUnica;
	
	public static ConcreteDAOAcessaDinheiroDoJogador getInstance()
	{
		if(instanciaUnica == null)
		{
			instanciaUnica = new ConcreteDAOAcessaDinheiroDoJogador();
		}
		
		return instanciaUnica;
	}

	@Override
	public int getCreditoQuePossui(Context contextoAplicacao) {
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("dinheiro_jogador", Context.MODE_PRIVATE);
		int dinheiroAtual = configuracoesSalvar.getInt("dinheiro_jogador", 0);
		return dinheiroAtual;
	}

	@Override
	public void adicionarCredito(int creditoAdicionar, Context contextoAplicacao) {
		int creditoAtual = this.getCreditoQuePossui(contextoAplicacao);
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("dinheiro_jogador", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		int novoCredito = creditoAdicionar + creditoAtual;
		editorConfig.putInt("dinheiro_jogador", novoCredito);
		editorConfig.commit();
		
	}

	@Override
	public void reduzirCredito(int creditoReduzir, Context contextoAplicacao) {
		int creditoAtual = this.getCreditoQuePossui(contextoAplicacao);
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("dinheiro_jogador", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		int novoCredito = creditoAtual - creditoReduzir;
		if(novoCredito < 0)
		{
			novoCredito = 0;
		}
		editorConfig.putInt("dinheiro_jogador", novoCredito);
		editorConfig.commit();
		
	}

	
	

}
