package doteppo;

public class GuardaFormaComoKanjisSeraoTreinados {
	private static GuardaFormaComoKanjisSeraoTreinados instancia;
	private String modoComoOsKanjisSeraoJogados;
	
	public static GuardaFormaComoKanjisSeraoTreinados getInstance()
	{
		if(instancia == null)
		{
			instancia = new GuardaFormaComoKanjisSeraoTreinados();
		}
		
		return instancia;
	}

	public String getModoDeJogo() {
		return modoComoOsKanjisSeraoJogados;
	}

	public void setModoDeJogo(String modoDeJogo) {
		this.modoComoOsKanjisSeraoJogados = modoDeJogo;
	}
	
	

}
