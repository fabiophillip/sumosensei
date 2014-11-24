package doteppo;

import java.util.ArrayList;

import br.ufrn.dimap.pairg.sumosensei.app.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AdapterListaKanjisPraMemorizar extends ArrayAdapter<DadosDeKanjiMemorizar>
{
	private ArrayList<DadosDeKanjiMemorizar> arrayListKanjisMemorizar;
	private Context contextoAplicacao;
	private Typeface tf;
	private Typeface tf2;
	
	public AdapterListaKanjisPraMemorizar(Context contextoAplicacao, int textViewResourceId,
		    ArrayList<DadosDeKanjiMemorizar> kanjisPraMemorizar) 
	{
		super(contextoAplicacao, textViewResourceId, kanjisPraMemorizar);
		this.arrayListKanjisMemorizar = kanjisPraMemorizar;
		this.contextoAplicacao = contextoAplicacao;
		
		String fontpath = "fonts/gilles_comic_br.ttf";
	    tf = Typeface.createFromAsset(contextoAplicacao.getAssets(), fontpath);
	    String fontpath2 = "fonts/chifont.ttf";
	    tf2 = Typeface.createFromAsset(contextoAplicacao.getAssets(), fontpath2);
	}

	public ArrayList<DadosDeKanjiMemorizar> getArrayListKanjisMemorizar() {
		return arrayListKanjisMemorizar;
	}

	public void setArrayListKanjisMemorizar(
			ArrayList<DadosDeKanjiMemorizar> arrayListKanjisMemorizar) {
		this.arrayListKanjisMemorizar = arrayListKanjisMemorizar;
	}
	
	private class ViewHolderKanjisMemorizar {
		   TextView kanjiMemorizar;
		   TextView hiraganaMemorizar;
		   TextView traducaoMemorizar;
		  }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ViewHolderKanjisMemorizar viewholderKanjisMemorizar = null;
		if(convertView == null)
		{
			LayoutInflater vi = (LayoutInflater)contextoAplicacao.getSystemService(
				     Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.item_palavra_pra_treinar, null);
			
			viewholderKanjisMemorizar = new ViewHolderKanjisMemorizar();
			
		}
		else
		{
			viewholderKanjisMemorizar = (ViewHolderKanjisMemorizar) convertView.getTag();
		}
		viewholderKanjisMemorizar.kanjiMemorizar = (TextView) convertView.findViewById(R.id.kanji_palavra_treinada);
		viewholderKanjisMemorizar.hiraganaMemorizar = (TextView) convertView.findViewById(R.id.hiragana_palavra_treinada);
		viewholderKanjisMemorizar.traducaoMemorizar = (TextView) convertView.findViewById(R.id.traducao_palavra_treinada);
		
		
		
		convertView.setTag(viewholderKanjisMemorizar);
		
		DadosDeKanjiMemorizar dadosDoKanjiMemorizar = arrayListKanjisMemorizar.get(position);
		viewholderKanjisMemorizar.kanjiMemorizar.setText(dadosDoKanjiMemorizar.getKanjiMemorizar());
		viewholderKanjisMemorizar.hiraganaMemorizar.setText(dadosDoKanjiMemorizar.getHiraganaMemorizar());
		viewholderKanjisMemorizar.traducaoMemorizar.setText(dadosDoKanjiMemorizar.getTraducaoMemorizar());
		
		
	    viewholderKanjisMemorizar.kanjiMemorizar.setTypeface(tf2);
	    viewholderKanjisMemorizar.hiraganaMemorizar.setTypeface(tf2);
	    viewholderKanjisMemorizar.traducaoMemorizar.setTypeface(tf);
		
		
		
		
		return convertView;
		
	
	}
	
	
	
	public void setListItems(ArrayList<DadosDeKanjiMemorizar> newList) {
	     this.clear();
	     this.addAll(newList); 
	     notifyDataSetChanged();
	}
	

}
