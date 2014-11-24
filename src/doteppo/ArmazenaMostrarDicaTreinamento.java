package doteppo;

import android.content.Context;
import android.content.SharedPreferences;

public class ArmazenaMostrarDicaTreinamento {
private static ArmazenaMostrarDicaTreinamento instancia;
	
	private ArmazenaMostrarDicaTreinamento()
	{
		
	}
	
	public static ArmazenaMostrarDicaTreinamento getInstance()
	{
		if(instancia == null)
		{
			instancia = new ArmazenaMostrarDicaTreinamento();
		}
		
		return instancia;
	}
	
	public boolean getMostrarDicaDoTreinamento(Context contextoAplicacao)
	{
		SharedPreferences configuracoesSalvas = contextoAplicacao.getSharedPreferences("mostrar_dicas_do_treinamento", Context.MODE_PRIVATE);
		boolean mostrarRegrasDoTreinamento = configuracoesSalvas.getBoolean("mostrar_dicas_do_treinamento", true);
		return mostrarRegrasDoTreinamento;
	}
	
	public void alterarMostrarDicaDoTreinamento(Context contextoAplicacao, boolean novoValor) 
	{
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("mostrar_dicas_do_treinamento", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putBoolean("mostrar_dicas_do_treinamento", novoValor);
		editorConfig.commit();
	}

}
