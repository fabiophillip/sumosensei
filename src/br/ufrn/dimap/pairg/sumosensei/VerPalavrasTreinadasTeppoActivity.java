package br.ufrn.dimap.pairg.sumosensei;

import java.util.ArrayList;
import java.util.LinkedList;

import com.phiworks.dapartida.GuardaDadosDaPartida;
import com.phiworks.domodocasual.AssociaCategoriaComIcone;

import doteppo.AdapterListViewKanjisErrados;
import doteppo.AdapterListaKanjisPraMemorizar;
import doteppo.ArmazenaMostrarDicaTreinamento;
import doteppo.ArmazenaMostrarRegrasTreinamento;
import doteppo.DadosDeKanjiMemorizar;

import bancodedados.ArmazenaKanjisPorCategoria;
import bancodedados.KanjiTreinar;
import bancodedados.PegaIdsIconesDasCategoriasSelecionadas;
import br.ufrn.dimap.pairg.sumosensei.app.R;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class VerPalavrasTreinadasTeppoActivity extends ActivityDoJogoComSom {
	
	private int indiceCategoriaAtual;//indice da categoria das palavras sendo apresentadas na lista atualmente
	private LinkedList<String> categoriasEscolhidasPraTreinar;
	private boolean mostrarDicasTeppoNovamente;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.getGameHelper().setMaxAutoSignInAttempts(0);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_palavras_treinadas_teppo);
		
		mostrarDicasTeppoNovamente = true;
		//deixar o seletor de categorias transparente...
		AlphaAnimation alpha = new AlphaAnimation(0.5F, 0.5F); // change values as you want
		alpha.setDuration(0); // Make animation instant
		alpha.setFillAfter(true); // Tell it to persist after the animation ends
		// And then on your imageview
		ImageView imageviewIconeCategoriaDaLista = (ImageView)findViewById(R.id.categoriaParaMostrarNaLista);
		imageviewIconeCategoriaDaLista.startAnimation(alpha);
		
		//mudar a fonte dos textos...
		
		String fontpathBrPraTexto = "fonts/gilles_comic_br.ttf";
	    Typeface tfBrPraTexto = Typeface.createFromAsset(getAssets(), fontpathBrPraTexto);
	    TextView textoMostrarKanjisMemorizar = (TextView)findViewById(R.id.texto_mostrar_kanjis_memorizar);
	    textoMostrarKanjisMemorizar.setTypeface(tfBrPraTexto);
	    String fontPath = "fonts/Wonton.ttf";
	    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	    TextView tituloDaTela = (TextView) findViewById(R.id.textoTituloPalavrasTreinadas);
	    tituloDaTela.setTypeface(tf);
		
		//pegar as categorias escolhidas para jogar...
		 GuardaDadosDaPartida guardaDadosDaPartida = GuardaDadosDaPartida.getInstance();
		 categoriasEscolhidasPraTreinar = guardaDadosDaPartida.getCategoriasTreinadasNaPartida();
		 //agora, pegar os kanjis da primeira categoria...
		 this.indiceCategoriaAtual = 0;//primeira categoria
		 String umaCategoria = categoriasEscolhidasPraTreinar.get(0);
		 //setar o icone da categoria atual para a tela...
		 Integer idImagemDaUmaCategoria = AssociaCategoriaComIcone.pegarIdImagemDaCategoriaTeppo(getApplicationContext(), umaCategoria);
		 imageviewIconeCategoriaDaLista.setImageResource(idImagemDaUmaCategoria);
					
		 
		 ArmazenaKanjisPorCategoria sabeKanjisDasCategorias = ArmazenaKanjisPorCategoria.pegarInstancia();
		 LinkedList<KanjiTreinar> kanjisDaCategoria = sabeKanjisDasCategorias.getListaKanjisTreinar(umaCategoria);
		 //agora, transformar essa lista de kanjis em array list de DadosKanjiMemorizar pra botar na ListView
		 ArrayList<DadosDeKanjiMemorizar> dadosDeKanjiMemorizar = new ArrayList<DadosDeKanjiMemorizar>();
		 for(int k = 0; k < kanjisDaCategoria.size(); k++)
		 {
			 KanjiTreinar umKanjiPraTreinar = kanjisDaCategoria.get(k);
			 String kanjiMemorizar = umKanjiPraTreinar.getKanji();
			 String hiraganaMemorizar = umKanjiPraTreinar.getHiraganaDoKanji();
			 String traducaoMemorizar = umKanjiPraTreinar.getTraducaoEmPortugues();
			 DadosDeKanjiMemorizar umKanjiMemorizar = new DadosDeKanjiMemorizar(kanjiMemorizar, hiraganaMemorizar, traducaoMemorizar);
			 dadosDeKanjiMemorizar.add(umKanjiMemorizar);
		 }
		 //falta agora associar essa lista ao adapter para o listview
		 AdapterListaKanjisPraMemorizar adapterKanjisMemorizar = new AdapterListaKanjisPraMemorizar
					(this, R.layout.item_palavra_pra_treinar, dadosDeKanjiMemorizar);
		 
		 ListView listaKanjisMemorizar = (ListView) findViewById(R.id.listaPalavrasTreinadas);
		 listaKanjisMemorizar.setAdapter(adapterKanjisMemorizar);
		 
		 
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}
	
	public void mudarKanjisDeAcordoComCategoriaSeguinte(View view)
	{
		this.indiceCategoriaAtual = indiceCategoriaAtual + 1;
		if(this.indiceCategoriaAtual >= this.categoriasEscolhidasPraTreinar.size())
		{
			this.indiceCategoriaAtual = 0;
		}
		String nomeCategoriaSeguinte = this.categoriasEscolhidasPraTreinar.get(indiceCategoriaAtual);
		
		//setar o icone da categoria atual para a tela...
		ImageView imageviewIconeCategoriaDaLista = (ImageView)findViewById(R.id.categoriaParaMostrarNaLista);
		Integer idImagemDaUmaCategoria = AssociaCategoriaComIcone.pegarIdImagemDaCategoriaTeppo(getApplicationContext(), nomeCategoriaSeguinte);
		imageviewIconeCategoriaDaLista.setImageResource(idImagemDaUmaCategoria);
		
		ArmazenaKanjisPorCategoria sabeKanjisDasCategorias = ArmazenaKanjisPorCategoria.pegarInstancia();
		 LinkedList<KanjiTreinar> kanjisDaCategoria = sabeKanjisDasCategorias.getListaKanjisTreinar(nomeCategoriaSeguinte);
		 //agora, transformar essa lista de kanjis em array list de DadosKanjiMemorizar pra botar na ListView
		 ArrayList<DadosDeKanjiMemorizar> dadosDeKanjiMemorizar = new ArrayList<DadosDeKanjiMemorizar>();
		 for(int k = 0; k < kanjisDaCategoria.size(); k++)
		 {
			 KanjiTreinar umKanjiPraTreinar = kanjisDaCategoria.get(k);
			 String kanjiMemorizar = umKanjiPraTreinar.getKanji();
			 String hiraganaMemorizar = umKanjiPraTreinar.getHiraganaDoKanji();
			 String traducaoMemorizar = umKanjiPraTreinar.getTraducaoEmPortugues();
			 DadosDeKanjiMemorizar umKanjiMemorizar = new DadosDeKanjiMemorizar(kanjiMemorizar, hiraganaMemorizar, traducaoMemorizar);
			 dadosDeKanjiMemorizar.add(umKanjiMemorizar);
		 }
		 //falta agora setar a nova lista para o existente adapter do listview...
		 
		 ListView listaKanjisMemorizar = (ListView) findViewById(R.id.listaPalavrasTreinadas);
		 ListAdapter adapterDaListView = listaKanjisMemorizar.getAdapter();
		 if(adapterDaListView instanceof AdapterListaKanjisPraMemorizar)
		 {
			 AdapterListaKanjisPraMemorizar adapterListaKanjisMemorizar = (AdapterListaKanjisPraMemorizar) adapterDaListView;
			 adapterListaKanjisMemorizar.setListItems(dadosDeKanjiMemorizar);
		 }
	}
	
	public void iniciarActivityJogo(View view)
	{
		mudarMusicaDeFundo(R.raw.headstart);
		 Intent iniciaTelaTeppo = new Intent(VerPalavrasTreinadasTeppoActivity.this, TreinoTeppo.class);
		  startActivity(iniciaTelaTeppo);
		  finish();
	}
	
	public void mudarValorMostrarKanjisMemorizar(View view)
	{
		Button checkboxMostrarRegrasNovamente = (Button)findViewById(R.id.checkbox_mostrar_kanjis_memorizar);
		if(this.mostrarDicasTeppoNovamente == false)
		{
			this.mostrarDicasTeppoNovamente = true;
			checkboxMostrarRegrasNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_desmarcada_regras_treinamento));
			
		}
		else
		{
			this.mostrarDicasTeppoNovamente = false;
			checkboxMostrarRegrasNovamente.setBackground(getResources().getDrawable(R.drawable.checkbox_marcada_regras_treinamento));
			
		}
		ArmazenaMostrarDicaTreinamento guardaConfiguracoes = ArmazenaMostrarDicaTreinamento.getInstance();
		guardaConfiguracoes.alterarMostrarDicaDoTreinamento(getApplicationContext(), mostrarDicasTeppoNovamente);
	}
}
