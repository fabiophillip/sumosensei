package docompeticao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bancodedados.SingletonArmazenaCategoriasDoJogo;
import br.ufrn.dimap.pairg.sumosensei.TelaModoCompeticao;

import com.phiworks.dapartida.ActivityPartidaMultiplayer;
import com.phiworks.domodocasual.DadosDaSalaModoCasual;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class CriarSalaCompeticaoTask extends AsyncTask<DadosDaSalaModoCasual, String, String> {
	
	private TelaModoCompeticao activityDoMultiplayer;
	private ProgressDialog popupDeProgresso;
	private String result = "";
	private InputStream inputStream = null;

	public CriarSalaCompeticaoTask(ProgressDialog loadingDaTela, TelaModoCompeticao telaDoMultiplayer)
	{
		this.activityDoMultiplayer = telaDoMultiplayer;
		this.popupDeProgresso = loadingDaTela;
	}

	@Override
	protected String doInBackground(DadosDaSalaModoCasual... params) {
		try
		{
			DadosDaSalaModoCasual dadosDaSala = params[0];
			String emailQuemCriouSala = dadosDaSala.getUsernameQuemCriouSala();
			String tituloDoJogador = dadosDaSala.getTituloDoJogador();
			
			//antes,setar novos dados da sala...
			LinkedList<String> categoriasSelecionadas = new LinkedList<String>();
			categoriasSelecionadas.add("Viagem");
			categoriasSelecionadas.add("Tempo");
			categoriasSelecionadas.add("Números");
			categoriasSelecionadas.add("Japão");
			categoriasSelecionadas.add("Cotidiano");
			categoriasSelecionadas.add("Contagem");
			categoriasSelecionadas.add("Calendário");
			categoriasSelecionadas.add("Adjetivos");
			dadosDaSala.setCategoriasSelecionadas(categoriasSelecionadas);
			this.activityDoMultiplayer.inserirDadosNovaSalaCasual(dadosDaSala);
			
			
			//primeiro, pegar o user id
			
			HttpClient httpClient = new DefaultHttpClient();

			String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuariopornome.php";//android nao aceita localhost, tem de ser seu IP
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        HttpPost httpPost = new HttpPost(url_select);
	        nameValuePairs.add(new BasicNameValuePair("nome_usuario", emailQuemCriouSala));
	        	
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
	        HttpResponse httpResponse = httpClient.execute(httpPost);
	        HttpEntity httpEntity = httpResponse.getEntity();
	        //
	        // Read the contents of an entity and return it as a String.
	        //
	        String content = httpEntity.toString();
	        System.out.println("oi");

	        // Read content & Log
	        inputStream = httpEntity.getContent();
	        BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	        StringBuilder sBuilder = new StringBuilder();

	        String line = null;
	        while ((line = bReader.readLine()) != null) {
	        	if(line.startsWith("<meta") == false)//pula linha de metadados
	        	{
	        		 sBuilder.append(line + "\n");
	        	}
	           
	        }

	        inputStream.close();
	        result = sBuilder.toString();
	        
	        //agora, pegar o id dele...
	        
	        JSONArray jArray = new JSONArray(result);
	        System.out.println("para testes");
	        if(jArray.length() > 0)
	        {
	        	String  idUsuario = "0";
	        	for(int i=0; i < jArray.length(); i++) 
	        	{

	                JSONObject jObject = jArray.getJSONObject(i);
	                
	                idUsuario = String.valueOf(jObject.getInt("id_usuario"));
	        	}
	        	
	        	String urlInserirNovaSala = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserir_nova_sala_competicao.php";//android nao aceita localhost, tem de ser seu IP
	    		ArrayList<NameValuePair> nameValuePairsInserirNovaSala = new ArrayList<NameValuePair>();
	    		
	    		
	    			HttpClient httpClientInserirNovaSala = new DefaultHttpClient();

	                HttpPost httpPostInserirNovaSala = new HttpPost(urlInserirNovaSala);
	                nameValuePairsInserirNovaSala.add(new BasicNameValuePair("idsalasmodocasual", null));
	                nameValuePairsInserirNovaSala.add(new BasicNameValuePair("id_usuario", idUsuario));
	                nameValuePairsInserirNovaSala.add(new BasicNameValuePair("titulodojogador", tituloDoJogador));
	                	
	                httpPostInserirNovaSala.setEntity(new UrlEncodedFormEntity(nameValuePairsInserirNovaSala,"UTF-8"));
	                HttpResponse httpResponseInserirNovaSala = httpClientInserirNovaSala.execute(httpPostInserirNovaSala); 
	                
	                
	                //em seguida, pegar o id da nova partida que acabou de ser criada.
	                String url_pegar_id = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegar_id_nova_sala_criada_competicao.php";
	                ArrayList<NameValuePair> nomeEEmailQuemCriouSala = new ArrayList<NameValuePair>();
	                HttpClient httpClientPegarIdSala = new DefaultHttpClient();
	                HttpPost httpPostPegarIdSala = new HttpPost(url_pegar_id);
	                nomeEEmailQuemCriouSala.add(new BasicNameValuePair("id_usuario", idUsuario));
	                httpPostPegarIdSala.setEntity(new UrlEncodedFormEntity(nameValuePairsInserirNovaSala,"UTF-8"));
	                HttpResponse httpRespostaIdSala = httpClientPegarIdSala.execute(httpPostPegarIdSala);
	                
	                HttpEntity httpEntityInserirNovaSala = httpRespostaIdSala.getEntity();

	                // Read content & Log
	                inputStream = httpEntityInserirNovaSala.getContent();
	                
	                try {
	                    BufferedReader bReaderInserirNovaSala = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	                    StringBuilder sBuilderInserirNovaSala = new StringBuilder();

	                    String lineInserirNovaSala = null;
	                    while ((lineInserirNovaSala = bReaderInserirNovaSala.readLine()) != null) {
	                    	if(lineInserirNovaSala.startsWith("<meta") == false)//pula linha de metadados
	                    	{
	                    		 sBuilderInserirNovaSala.append(lineInserirNovaSala + "\n");
	                    	}
	                       
	                    }

	                    inputStream.close();
	                    result = sBuilderInserirNovaSala.toString();

	                } catch (Exception e) {
	                    Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
	                }
	                
	                
	        
			
			
	      }
			return "";
		}
		catch(IOException exc)
		{
			exc.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}
	
	@Override
	protected void onPostExecute(String v) {
		this.popupDeProgresso.dismiss();
		try
		{
			JSONArray jArray = new JSONArray(result);
			int idSalaModoCasual = -1;
	        for(int i=0; i < jArray.length(); i++) {

	            JSONObject jObject = jArray.getJSONObject(i);
	            
	            String idSalaModoCasualEmString = jObject.getString("id_da_sala");
	            idSalaModoCasual = Integer.valueOf(idSalaModoCasualEmString);
	        }
	        if(idSalaModoCasual != -1)
	        {
	        	this.activityDoMultiplayer.setarIdDaSala(idSalaModoCasual);
	        	String tituloAtualJogador = SingletonGuardaDadosUsuarioNoRanking.getInstance().getTituloDoJogadorCalculadoRecentemente();
	   		 	int idSalaParaJogadorConectar = CalculaPosicaoDoJogadorNoRanking.getIdSalaDeAcordoComStringJogador(tituloAtualJogador, this.activityDoMultiplayer.getApplicationContext());
	        	this.activityDoMultiplayer.criarSalaQuickMatch(idSalaParaJogadorConectar);
	        }
	        
		}
		catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
        }
		
		
	}

}
