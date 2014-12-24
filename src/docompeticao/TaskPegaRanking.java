package docompeticao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.Categoria;
import bancodedados.KanjiTreinar;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import br.ufrn.dimap.pairg.sumosensei.ActivityMultiplayerQueEsperaAtePegarOsKanjis;
import br.ufrn.dimap.pairg.sumosensei.TelaRankingActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import doteppo.DadosParametroPegarKanjisMenosTreinados;

public class TaskPegaRanking extends AsyncTask<String, String, Void>  {
	
	private InputStream inputStream = null;
	private String result = ""; 
	private ProgressDialog loadingDaTelaEmEspera;//eh o dialog da tela em espera pelo resultado do web service
	private TelaRankingActivity telaEsperandoRanking;
	
	public TaskPegaRanking(ProgressDialog loading, TelaRankingActivity telaRanking)
	{
		this.telaEsperandoRanking = telaRanking;
		this.loadingDaTelaEmEspera = loading;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		//antigo:"http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarjlptjson.php";
	       String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegatodooranking.php";//android nao aceita localhost, tem de ser seu IP
	       
	       
	       ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	       String pegarTodosDoRankingOuSohMelhores = params [0];
	       if(pegarTodosDoRankingOuSohMelhores.compareTo("sohMelhores") == 0)
	       {
	    	   url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegamelhoresvinteusuarios.php";//android nao aceita localhost, tem de ser seu IP
	       }
	       
	       

	        try {
	            // Set up HTTP post

	            // HttpClient is more then less deprecated. Need to change to URLConnection
	            HttpClient httpClient = new DefaultHttpClient();

	            HttpPost httpPost = new HttpPost(url_select);
	            httpPost = new HttpPost(url_select);
	            
	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
	            HttpResponse httpResponse = httpClient.execute(httpPost);
	            HttpEntity httpEntity = httpResponse.getEntity();

	            // Read content & Log
	            inputStream = httpEntity.getContent();
	        } catch (UnsupportedEncodingException e1) {
	            Log.e("UnsupportedEncodingException", e1.toString());
	            e1.printStackTrace();
	        } catch (ClientProtocolException e2) {
	            Log.e("ClientProtocolException", e2.toString());
	            e2.printStackTrace();
	        } catch (IllegalStateException e3) {
	            Log.e("IllegalStateException", e3.toString());
	            e3.printStackTrace();
	        } catch (IOException e4) {
	            Log.e("IOException", e4.toString());
	            e4.printStackTrace();
	        }
	        // Convert response to string using String Builder
	        try {
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
	            
	            System.out.println("oi");

	        } catch (Exception e) {
	            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
	        }
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void onPostExecute(Void v) {
        //parse JSON data
        try {
            JSONArray jArray = new JSONArray(result);
            System.out.println("para testes");
            ArrayList<DadosDeRanking> dadosObtidosNaConsulta = new ArrayList<DadosDeRanking>();
            for(int i=0; i < jArray.length(); i++) {

                JSONObject jObject = jArray.getJSONObject(i);
                
                String posicaoNoRanking = jObject.getString("position");
                String nomeUsuario = jObject.getString("nome_usuario");
                String pontuacao = jObject.getString("pontuacao_total");
                String vitoriasCompeticao = jObject.getString("vitorias_competicao");
                String derrotasCompeticao = jObject.getString("derrotas_competicao");
                String tituloJogador = CalculaPosicaoDoJogadorNoRanking.definirTituloJogador(this.telaEsperandoRanking.getApplicationContext(), Integer.valueOf(posicaoNoRanking), Integer.valueOf(vitoriasCompeticao));
                DadosDeRanking novoDadoDeRanking = new DadosDeRanking(posicaoNoRanking, nomeUsuario, tituloJogador, vitoriasCompeticao, derrotasCompeticao);
                if(novoDadoDeRanking != null)
                {
                	dadosObtidosNaConsulta.add(novoDadoDeRanking);
                }

            } // End Loop
            this.telaEsperandoRanking.mostrarRankingAposCarregar(dadosObtidosNaConsulta);
            this.loadingDaTelaEmEspera.dismiss();
            
           
        } catch (JSONException e) {
            Log.e("JSONException", "Error: " + e.toString());
            this.loadingDaTelaEmEspera.dismiss();
        }
    } // protected void onPostExecute(Void v)

}
