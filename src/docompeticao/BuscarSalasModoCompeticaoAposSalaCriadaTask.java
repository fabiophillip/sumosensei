package docompeticao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;
import br.ufrn.dimap.pairg.sumosensei.TelaModoCompeticao;

public class BuscarSalasModoCompeticaoAposSalaCriadaTask extends BuscarSalasModoCompeticaoTask 
{

	public BuscarSalasModoCompeticaoAposSalaCriadaTask(
			ProgressDialog loadingDaTela,
			TelaModoCompeticao activityQueEsperaAteRequestTerminar) {
		super(loadingDaTela, activityQueEsperaAteRequestTerminar);
		// TODO Auto-generated constructor stub
	}
	
	 protected void onPostExecute(Void v) {
	        //parse JSON data
	        try {
	            JSONArray jArray = new JSONArray(result); 
	            ArrayList<SalaAbertaModoCompeticao> salasModoCompeticao = new ArrayList<SalaAbertaModoCompeticao>();
	            if(jArray.length() > 0)
	            {
		            this.activityQueEsperaAtePegarAsSalas.mostrarMensagemPessoaDoMesmoNivelEntrou();
	            }
	            else
	            {
	            	//ainda não tem salas pra jogar, tem de mostrar ao menos um popup de esperando...
	            	this.activityQueEsperaAtePegarAsSalas.mostrarPopupNaoHaSalasAbertasDeJogadoresComSeuNivelProCriadorSala();
	            }
	            
	          
	            if(loadingDaTelaEmEspera!= null && loadingDaTelaEmEspera.isShowing())
			 	{
				 	this.loadingDaTelaEmEspera.dismiss();	 
			 	}
	            //this.loadingDaTelaEmEspera.dismiss();
	           
	        } catch (JSONException e) {
	            Log.e("JSONException", "Error: " + e.toString());
	            if(loadingDaTelaEmEspera!= null && loadingDaTelaEmEspera.isShowing())
			 	{
				 	this.loadingDaTelaEmEspera.dismiss();	 
			 	}
	        }

	    }

}
