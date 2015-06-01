package doteppo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import bancodedados.KanjiTreinar;
import bancodedados.SingletonArmazenaCategoriasDoJogo;
import br.ufrn.dimap.pairg.sumosensei.android.FimDeTreino;

import com.phiworks.dapartida.ActivityPartidaMultiplayer;
import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.domodocasual.DadosDaSalaModoCasual;

public class RegistraDadosDoTreinoTask extends AsyncTask<String, String, String>{
	private ProgressDialog popupDeProgresso;
	private String result = "";
	private InputStream inputStream = null;

	public RegistraDadosDoTreinoTask(ProgressDialog loadingDaTela)
	{
		this.popupDeProgresso = loadingDaTela;
	}

	@Override
	protected String doInBackground(String... params) {
		String nomeDeUsuario = params[0];
		
		String dataPartida = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String horaAtualPartida = new SimpleDateFormat("HH:mm").format(new Date());
		
		
		
		try
		{
			//primeiro, pegar o user id
			
			HttpClient httpClient = new DefaultHttpClient();

			String url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegarusuariopornome.php";//android nao aceita localhost, tem de ser seu IP
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        HttpPost httpPost = new HttpPost(url_select);
	        nameValuePairs.add(new BasicNameValuePair("nome_usuario", nomeDeUsuario));
	        	
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
            	for(int i=0; i < jArray.length(); i++) {

	                JSONObject jObject = jArray.getJSONObject(i);
	                
	                String  idUsuario = String.valueOf(jObject.getInt("id_usuario"));
	                
	              //depois, adicionar no log de partidas
	    			url_select = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirpartidatreinamentonolog.php";//android nao aceita localhost, tem de ser seu IP
	    			nameValuePairs = new ArrayList<NameValuePair>();
	    			httpClient = new DefaultHttpClient();

	                httpPost = new HttpPost(url_select);
	                nameValuePairs.add(new BasicNameValuePair("id_usuario", idUsuario));
	                nameValuePairs.add(new BasicNameValuePair("data_partida", dataPartida));
	                nameValuePairs.add(new BasicNameValuePair("hora_partida", horaAtualPartida));
	                	
	                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
	                httpResponse = httpClient.execute(httpPost); 
	                
	                
	                //em seguida, pegar o id da nova partida que acabou de ser criada.
	                String url_pegar_id = "http://server.sumosensei.pairg.dimap.ufrn.br/app/pegaridpartidatreinamentocriadapelousuario.php";
	                ArrayList<NameValuePair> nomeEEmailQuemCriouSala = new ArrayList<NameValuePair>();
	                HttpClient httpClientPegarIdSala = new DefaultHttpClient();
	                HttpPost httpPostPegarIdSala = new HttpPost(url_pegar_id);
	                nomeEEmailQuemCriouSala.add(new BasicNameValuePair("id_usuario", idUsuario));
	                httpPostPegarIdSala.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
	                HttpResponse httpRespostaIdSala = httpClientPegarIdSala.execute(httpPostPegarIdSala);
	                
	                httpEntity = httpRespostaIdSala.getEntity();

	                // Read content & Log
	                inputStream = httpEntity.getContent();
	                
	                try {
	                    bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
	                    sBuilder = new StringBuilder();

	                    line = null;
	                    while ((line = bReader.readLine()) != null) {
	                    	if(line.startsWith("<meta") == false)//pula linha de metadados
	                    	{
	                    		 sBuilder.append(line + "\n");
	                    	}
	                       
	                    }

	                    inputStream.close();
	                    result = sBuilder.toString();

	                } catch (Exception e) {
	                    Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
	                }
	                
	                //por fim, inserir as categorias da sala numa tabela diferente
	                String url_inserir_categorias = "http://server.sumosensei.pairg.dimap.ufrn.br/app/inserirpartidatreinamentonologpalavraserradas.php";
	                ArrayList<NameValuePair> idSalaECategorias = new ArrayList<NameValuePair>();
	                HttpClient httpClientinserirCategorias = new DefaultHttpClient();
	                HttpPost httpPostInserirCategorias = new HttpPost(url_inserir_categorias);
	                
	                GuardaDadosDaPartida sabeKanjisQueUsuarioErrou = GuardaDadosDaPartida.getInstance();
	                
	                String idsKanjisAcertadosEErradosSeparadosVirgula = "";
	                String idsCategoriasDosKanjisSeparadosPorVirgula = "";
	                String acertouOuErrouKanjisSeparadosVirgula = "";
	                
	                //primeiro, vamos pegar os kanjis que o usuário acertou...
	                LinkedList<KanjiTreinar> kanjisAcertados = sabeKanjisQueUsuarioErrou.getKanjisAcertadosNaPartida();
	                for(i = 0; i < kanjisAcertados.size(); i++)
	                {
	                	KanjiTreinar umKanjiAcertado = kanjisAcertados.get(i);
	                	idsKanjisAcertadosEErradosSeparadosVirgula = idsKanjisAcertadosEErradosSeparadosVirgula + umKanjiAcertado.getIdKanji() + ",";
	                	String categoriaKanjiAcertado = umKanjiAcertado.getCategoriaAssociada();
	                	int idCategoriaKanjiAcertado = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdDaCategoria(categoriaKanjiAcertado);
	                	idsCategoriasDosKanjisSeparadosPorVirgula = idsCategoriasDosKanjisSeparadosPorVirgula + idCategoriaKanjiAcertado + ",";
	                	acertouOuErrouKanjisSeparadosVirgula = acertouOuErrouKanjisSeparadosVirgula + "sim,";
	                }
	                
	                //agora, vamos pegar os kanjis que o usuário errou...
	                LinkedList<KanjiTreinar> kanjisErrados = sabeKanjisQueUsuarioErrou.getKanjisErradosNaPartida();
	                if(kanjisErrados.size() <= 0)
	                {
	                	//ele não errou nada? então tem de apagar as vírgulas do final
	                	if(idsKanjisAcertadosEErradosSeparadosVirgula != null && idsKanjisAcertadosEErradosSeparadosVirgula.length() > 0)
	                	{
	                		idsKanjisAcertadosEErradosSeparadosVirgula = idsKanjisAcertadosEErradosSeparadosVirgula.substring(0, idsKanjisAcertadosEErradosSeparadosVirgula.length()-1);
		                	idsCategoriasDosKanjisSeparadosPorVirgula = idsCategoriasDosKanjisSeparadosPorVirgula.substring(0, idsCategoriasDosKanjisSeparadosPorVirgula.length()-1);
		                	acertouOuErrouKanjisSeparadosVirgula = acertouOuErrouKanjisSeparadosVirgula.substring(0, acertouOuErrouKanjisSeparadosVirgula.length()-1);
	                	}
	                	
	                }
	                else
	                {

	                    for(int j = 0; j < kanjisErrados.size(); j++)
	                    {
	                    	KanjiTreinar kanjiErrado = kanjisErrados.get(j);
	                    	idsKanjisAcertadosEErradosSeparadosVirgula = idsKanjisAcertadosEErradosSeparadosVirgula + kanjiErrado.getIdKanji();
	                    	String categoriaKanjiAcertado = kanjiErrado.getCategoriaAssociada();
	                    	int idCategoriaKanjiAcertado = SingletonArmazenaCategoriasDoJogo.getInstance().pegarIdDaCategoria(categoriaKanjiAcertado);
	                    	idsCategoriasDosKanjisSeparadosPorVirgula = idsCategoriasDosKanjisSeparadosPorVirgula + idCategoriaKanjiAcertado;
	                    	acertouOuErrouKanjisSeparadosVirgula = acertouOuErrouKanjisSeparadosVirgula + "não";
	                    	if(j < kanjisErrados.size() - 1)
	                    	{
	                    		idsKanjisAcertadosEErradosSeparadosVirgula = idsKanjisAcertadosEErradosSeparadosVirgula + ",";
	                    		idsCategoriasDosKanjisSeparadosPorVirgula = idsCategoriasDosKanjisSeparadosPorVirgula + ",";
	                    		acertouOuErrouKanjisSeparadosVirgula = acertouOuErrouKanjisSeparadosVirgula + ",";
	                    	}
	                    	
	                    }
	                }
	                
	                
	                try
	        		{
	        			jArray = new JSONArray(result);
	        			String idPartidaDoTreinamento = "-1";
	        	        for(i=0; i < jArray.length(); i++) {

	        	            jObject = jArray.getJSONObject(i);
	        	            
	        	            idPartidaDoTreinamento = jObject.getString("id_partida_treinamento");
	        	           
	        	        }
	        	        if(idPartidaDoTreinamento.compareTo("-1") != 0 && (kanjisAcertados.size() + kanjisErrados.size() > 0))
	        	        {
	        	        	//não vale a pena chamar o php se ele não acertou nem errou kanjis
	        	        	 idSalaECategorias.add(new BasicNameValuePair("ids_kanjis", idsKanjisAcertadosEErradosSeparadosVirgula));
	        	        	 idSalaECategorias.add(new BasicNameValuePair("ids_categorias_kanjis", idsCategoriasDosKanjisSeparadosPorVirgula));
	        	        	 idSalaECategorias.add(new BasicNameValuePair("se_acertou_ou_errou", acertouOuErrouKanjisSeparadosVirgula));
	        	             idSalaECategorias.add(new BasicNameValuePair("id_partidas_treinamento", idPartidaDoTreinamento));
	        	             
	        	             httpPostInserirCategorias.setEntity(new UrlEncodedFormEntity(idSalaECategorias,"UTF-8"));
	        	             HttpResponse httpRespostaInserirLogTeppo = httpClientinserirCategorias.execute(httpPostInserirCategorias);
	        	             HttpEntity entity = httpRespostaInserirLogTeppo.getEntity();
	        	             
	        	             //
	        	             // Read the contents of an entity and return it as a String.
	        	             //
	        	             content = EntityUtils.toString(entity);
	        	             System.out.println("oi");
	        	        }
	        	        
	        		}
	        		catch (JSONException e) {
	                    Log.e("JSONException", "Error: " + e.toString());
	                }
	               
	               
	            } // End Loop
            }
            else
            {
            	//erro não achou usuário
            }
			
			
            
           
            
		}
		catch (UnsupportedEncodingException e1) {
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
        } catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
	}
	
	@Override
	protected void onPostExecute(String v) {
		this.popupDeProgresso.dismiss();
	}

}
