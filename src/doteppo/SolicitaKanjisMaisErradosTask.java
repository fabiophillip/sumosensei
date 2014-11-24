package doteppo;

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
import br.ufrn.dimap.pairg.sumosensei.ActivityQueEsperaAtePegarOsKanjis;
import br.ufrn.dimap.pairg.sumosensei.TreinoTeppo;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class SolicitaKanjisMaisErradosTask extends AsyncTask<DadosParametroPegarKanjisMenosTreinados, String, Void> {
	
	private InputStream inputStream = null;
	private String result = ""; 
	private ProgressDialog loadingDaTelaEmEspera;//eh o dialog da tela em espera pelo resultado do web service
	private TreinoTeppo activityEsperandoPorListaDeKanjisMenosTreinados;//pode ser a activity principal do seu treinamento
	
	public SolicitaKanjisMaisErradosTask(ProgressDialog loadingDaTela, TreinoTeppo activityQueEsperaAteRequestTerminar)
	{
		this.loadingDaTelaEmEspera = loadingDaTela;
		this.activityEsperandoPorListaDeKanjisMenosTreinados = activityQueEsperaAteRequestTerminar;
	}
	
	@Override
    protected Void doInBackground(DadosParametroPegarKanjisMenosTreinados... params) {
		//antigo:"http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarjlptjson.php";
       String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarkanjismaiserrados.php";//android nao aceita localhost, tem de ser seu IP
       
       
       ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
       DadosParametroPegarKanjisMenosTreinados dadosPegarKanjisMenosTreinados = params [0];
       
       

        try {
            // Set up HTTP post

            // HttpClient is more then less deprecated. Need to change to URLConnection
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url_select);
            httpPost = new HttpPost(url_select);
            String nomeDeUsuario = dadosPegarKanjisMenosTreinados.getNomeDeUsuario();
            nameValuePairs.add(new BasicNameValuePair("nome_usuario", nomeDeUsuario ));
            String categoriasSeparadasPorVirgula = dadosPegarKanjisMenosTreinados.getCategoriasSelecionadasSeparadasPorVirgula();
            nameValuePairs.add(new BasicNameValuePair("categorias", categoriasSeparadasPorVirgula));
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
            String resultadoComMetadados = sBuilder.toString();
            //tirar os metadados
            String [] stringSeparadoColchete = resultadoComMetadados.split("\\[");
            result = "[" + stringSeparadoColchete[1];
            
            System.out.println("oi");

        } catch (Exception e) {
            Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
        }
        
        if(this.activityEsperandoPorListaDeKanjisMenosTreinados instanceof ActivityMultiplayerQueEsperaAtePegarOsKanjis)
        {
        	//se trata da activity do multiplayer
        	ActivityMultiplayerQueEsperaAtePegarOsKanjis activityMultiplayer = (ActivityMultiplayerQueEsperaAtePegarOsKanjis) this.activityEsperandoPorListaDeKanjisMenosTreinados;
        	if(activityMultiplayer.jogadorEhHost() == true)
        	{
        		 while(activityMultiplayer.oGuestTerminouDeCarregarListaDeCategorias() == false)
        	        {
        	        	//espera...
        	        	try {
        					Thread.sleep(1000);
        				} catch (InterruptedException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        	        }
        	}
        }
       
        
		return null;
    } // protected Void doInBackground(String... params)
	
	 protected void onPostExecute(Void v) {
	        //parse JSON data
	        try {
	            JSONArray jArray = new JSONArray(result);
	            System.out.println("para testes");
	            LinkedList<KanjiTreinar> kanjisParaTreinarEmOrdem = new LinkedList<KanjiTreinar>();
	            for(int i=0; i < jArray.length(); i++) {

	                JSONObject jObject = jArray.getJSONObject(i);
	                
	                String categoriaAssociada = jObject.getString("nome_categoria");
	                String kanji = jObject.getString("kanji");
	              
	                ArmazenaKanjisPorCategoria sabeKanjiDaCategoria = ArmazenaKanjisPorCategoria.pegarInstancia();
	                
	                KanjiTreinar novoKanjiTreinar = sabeKanjiDaCategoria.acharKanji(categoriaAssociada, kanji);
	                //e, por fim, armazenar esses kanjis na lista de ArmazenaKanjisPorCategoria
	                kanjisParaTreinarEmOrdem.add(novoKanjiTreinar);
	                
	                

	            } // End Loop
	            ArmazenaListaDeKanjisTreinarEmOrdem.getInstance().setListaDeKanjisPraTreinarEmOrdem(kanjisParaTreinarEmOrdem);
	            System.out.println("oi");
	            
	        } catch (JSONException e) {
	            Log.e("JSONException", "Error: " + e.toString());
	        }
	        
	        this.activityEsperandoPorListaDeKanjisMenosTreinados.aposCarregarListaComKanjisParaTreinarEmOrdem();
	        this.loadingDaTelaEmEspera.dismiss();
	    } // protected void onPostExecute(Void v)

}
