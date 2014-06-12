package bancodedados;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.phiworks.sumosenseinew.MainActivity;
import com.phiworks.sumosenseinew.R;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class ChecaVersaoAtualDoSistemaTask extends AsyncTask<String, Integer, String> {
	
	private static String versaoDoSistema = "0.1.1 - beta";//versão do sistema que o usuário possui
	private ProgressDialog loadingDaTelaEmEspera;//eh o dialog da tela em espera pelo resultado do web service
	private MainActivity activityEsperandoCarregarVersaoDoSistema;
	public ChecaVersaoAtualDoSistemaTask(MainActivity activityEsperandoCarregarVersao, ProgressDialog caixaDeProgressoDaActivity)
	{
		this.loadingDaTelaEmEspera = caixaDeProgressoDaActivity;
		this.activityEsperandoCarregarVersaoDoSistema = activityEsperandoCarregarVersao;
	}
	
	private String usuarioEstahJogandoNaVersaoAtual()
	{
		//antigo:http://server.sumosensei.pairg.dimap.ufrn.br/app/pegar_versao_do_sistema_atual.php
		String url_pega_versao = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegar_versao_do_sistema_atual.php";
		try {  
		      HttpClient httpclient = new DefaultHttpClient();  
		      HttpPost httppost = new HttpPost(url_pega_versao);  
		      HttpResponse response = httpclient.execute(httppost);  
		      InputStream inputStream = response.getEntity().getContent();
		      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		      String readData = "";
		      String line = "";

		      while((line = br.readLine()) != null){
		          readData += line;
		      }


		      if(readData.contains(versaoDoSistema) == true)
		      {
		    	  return "true";
		      }
		      else
		      {
		    	  return "false;versaoDesatualizada";
		      }
		}
		catch(Exception e) 
	      {	
	    	  Log.e("log_tag", "Error in http connection"+e.toString()); 
	    	  return "false;erroConexao";
	      }
		      
	}


	@Override
	protected String doInBackground(String... params) 
	{
		return this.usuarioEstahJogandoNaVersaoAtual();
	}
	
	@Override
	protected void onPostExecute(String usuarioEstahComVersaoAtualDoSistema)
	{
		this.activityEsperandoCarregarVersaoDoSistema.trocarTelaDeAcordoComVersaoDoSistema(usuarioEstahComVersaoAtualDoSistema);
		this.loadingDaTelaEmEspera.dismiss();
	}

}
