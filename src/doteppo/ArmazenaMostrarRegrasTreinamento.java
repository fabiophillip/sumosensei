package doteppo;


import android.content.Context;
import android.content.SharedPreferences;

public class ArmazenaMostrarRegrasTreinamento 
{
	private static ArmazenaMostrarRegrasTreinamento instancia;
	
	private ArmazenaMostrarRegrasTreinamento()
	{
		
	}
	
	public static ArmazenaMostrarRegrasTreinamento getInstance()
	{
		if(instancia == null)
		{
			instancia = new ArmazenaMostrarRegrasTreinamento();
		}
		
		return instancia;
	}
	
	public boolean getMostrarRegrasDoTreinamento(Context contextoAplicacao)
	{
		SharedPreferences configuracoesSalvas = contextoAplicacao.getSharedPreferences("mostrar_regras_do_treinamento", Context.MODE_PRIVATE);
		boolean mostrarRegrasDoTreinamento = configuracoesSalvas.getBoolean("mostrar_regras_do_treinamento", true);
		return mostrarRegrasDoTreinamento;
	}
	
	public void alterarMostrarRegrasDoTreinamento(Context contextoAplicacao, boolean novoValor) 
	{
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("mostrar_regras_do_treinamento", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putBoolean("mostrar_regras_do_treinamento", novoValor);
		editorConfig.commit();
	}
	
	public boolean getMostrarAvisoAprenderKanjisAntes(Context contextoAplicacao)
	{
		SharedPreferences configuracoesSalvas = contextoAplicacao.getSharedPreferences("mostrar_aviso_aprender_kanji_antes", Context.MODE_PRIVATE);
		boolean mostrarRegrasDoTreinamento = configuracoesSalvas.getBoolean("mostrar_aviso_aprender_kanji_antes", true);
		return mostrarRegrasDoTreinamento;
	}
	
	public void alterarMostrarAvisoAprenderKanjisAntes(Context contextoAplicacao, boolean novoValor)
	{
		SharedPreferences configuracoesSalvar = contextoAplicacao.getSharedPreferences("mostrar_aviso_aprender_kanji_antes", Context.MODE_PRIVATE);
		SharedPreferences.Editor editorConfig = configuracoesSalvar.edit();
		editorConfig.putBoolean("mostrar_aviso_aprender_kanji_antes", novoValor);
		editorConfig.commit();
	}
}
