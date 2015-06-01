package doteppo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import bancodedados.KanjiTreinar;

import cenario.SetaAlturaListViewComQuantidade;

import com.phiworks.dapartida.GuardaDadosDaPartida;

import br.ufrn.dimap.pairg.sumosensei.android.FimDeTreino;
import br.ufrn.dimap.pairg.sumosensei.android.R;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TaskCalculaPalavrasErradas extends AsyncTask<String, Integer, Void> {

	private FimDeTreino telaDeFimDeTreino;
	private ProgressDialog loadingDaTelaEmEspera;
	public TaskCalculaPalavrasErradas(ProgressDialog loadingDaTela, FimDeTreino telaDeFimDeTreino)
	{
		this.telaDeFimDeTreino = telaDeFimDeTreino;
		this.loadingDaTelaEmEspera = loadingDaTela;
	}
	@Override
	protected Void doInBackground(String... params) {
		//agrupar palavras erradas para se ter uma contagem de quantas vezes a palavra foi errada
	    HashMap<String, Integer> kanjiEQuantasVezesErrou = new HashMap<String, Integer>();
	    //guardar tambem uma referencia rapida para o kanji errado
	    HashMap<String, KanjiTreinar> textoKanjiEObjetoKanji = new HashMap<String, KanjiTreinar>();
	    GuardaDadosDaPartida guardaPalavrasErradasNaPartida = GuardaDadosDaPartida.getInstance();
	    LinkedList<KanjiTreinar> kanjisErradosNaPartida = guardaPalavrasErradasNaPartida.getKanjisErradosNaPartida();
	    for(int i = 0; i < kanjisErradosNaPartida.size(); i++)
	    {
	    	KanjiTreinar umKanjiErrado = kanjisErradosNaPartida.get(i);
	    	if(kanjiEQuantasVezesErrou.containsKey(umKanjiErrado.getKanji()) == true)
	    	{
	    		Integer quantasVezesKanjiFoiErrado = kanjiEQuantasVezesErrou.get(umKanjiErrado.getKanji());
	    		quantasVezesKanjiFoiErrado = quantasVezesKanjiFoiErrado + 1;
	    		kanjiEQuantasVezesErrou.put(umKanjiErrado.getKanji(), quantasVezesKanjiFoiErrado);
	    	}
	    	else
	    	{
	    		kanjiEQuantasVezesErrou.put(umKanjiErrado.getKanji(), 1);//errou uma vez ainda
	    		textoKanjiEObjetoKanji.put(umKanjiErrado.getKanji(), umKanjiErrado);
	    	}
	    }
	    
	    //criar agora uma lista com os dados dos kanjis errados
	    final ArrayList<DadosDeKanjiErrado> arrayListKanjisErrados = new ArrayList<DadosDeKanjiErrado>();
	    Iterator<String> iteraSobreTextosKanjisErrados = textoKanjiEObjetoKanji.keySet().iterator();
	    while(iteraSobreTextosKanjisErrados.hasNext())
	    {
	    	String textoUmKanjiErrado = iteraSobreTextosKanjisErrados.next();
	    	
	    	DadosDeKanjiErrado novoDadoKanjiErrado = new DadosDeKanjiErrado();
	    	KanjiTreinar umKanjiErrado = textoKanjiEObjetoKanji.get(textoUmKanjiErrado);
	    	novoDadoKanjiErrado.setKanjiErrado(umKanjiErrado.getKanji());
	    	novoDadoKanjiErrado.setHiraganaErrado(umKanjiErrado.getHiraganaDoKanji());
	    	novoDadoKanjiErrado.setTraducaoErrada(umKanjiErrado.getTraducao());
	    	String textoKanjiErrado = umKanjiErrado.getKanji();
	    	Integer quantasVezesKanjiFoiErrado = kanjiEQuantasVezesErrou.get(textoKanjiErrado);
	    	if(quantasVezesKanjiFoiErrado != null)
	    	{
	    		novoDadoKanjiErrado.setQuantasVezesKanjiFoiErrado(quantasVezesKanjiFoiErrado);
	    	}
	    	
	    	arrayListKanjisErrados.add(novoDadoKanjiErrado);
	    	
	    }
	    
	    
	    //e, enfim, criar o adapter para a listView das palavras erradas
	    ArrayList<DadosDeKanjiErrado> listaVaziaKanjisErrados = new ArrayList<DadosDeKanjiErrado>();
	    final AdapterListViewKanjisErrados adapterKanjisErrados = new AdapterListViewKanjisErrados
				(this.telaDeFimDeTreino, R.layout.item_lista_palavras_erradas, listaVaziaKanjisErrados);

	    this.telaDeFimDeTreino.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 // Assign adapter to ListView
			    ListView listviewKanjisErrados = (ListView) telaDeFimDeTreino.findViewById(R.id.listagem_de_palavras_erradas);
				listviewKanjisErrados.setAdapter(adapterKanjisErrados);
				RelativeLayout campoKanjisErrados = (RelativeLayout) telaDeFimDeTreino.findViewById(R.id.campo_palavras_erradas);
				TextView textoNaoErrouNada = (TextView) telaDeFimDeTreino.findViewById(R.id.texto_errou_nada);
				if(arrayListKanjisErrados.size() == 0)
				{
					//usuario nao errou nenhum kanji
					
					campoKanjisErrados.setVisibility(View.INVISIBLE);
					textoNaoErrouNada.setVisibility(View.VISIBLE);
					RelativeLayout layoutBalao = (RelativeLayout) telaDeFimDeTreino.findViewById(R.id.campo_componentes_balao_teppo);
					ViewGroup.LayoutParams params = layoutBalao.getLayoutParams();
					int alturaApropriadaBalao = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, telaDeFimDeTreino.getResources().getDisplayMetrics());
			        params.height = alturaApropriadaBalao;
			        layoutBalao.requestLayout();
				}
				else
				{
					//usuario errou algo
					campoKanjisErrados.setVisibility(View.VISIBLE);
					textoNaoErrouNada.setVisibility(View.INVISIBLE);
				}
				
				//
				//SetaAlturaListViewComQuantidade.setListViewHeightBasedOnChildren(listviewKanjisErrados);
			    
				
				// TODO Auto-generated method stub
				loadingDaTelaEmEspera.dismiss();
			    adapterKanjisErrados.setListItems(arrayListKanjisErrados);
			}
		});
	    

	    
		return null;
	}

}
