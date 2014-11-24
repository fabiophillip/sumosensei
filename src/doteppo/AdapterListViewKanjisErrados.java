package doteppo;

import java.util.ArrayList;

import br.ufrn.dimap.pairg.sumosensei.app.R;



import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterListViewKanjisErrados extends ArrayAdapter<DadosDeKanjiErrado> 
{
	private ArrayList<DadosDeKanjiErrado> arrayListKanjisErrados;
	private Context contextoAplicacao;
	private Typeface tf;
	private Typeface tf2;
	
	public AdapterListViewKanjisErrados(Context contextoAplicacao, int textViewResourceId,
		    ArrayList<DadosDeKanjiErrado> kanjisErrados) 
	{
		super(contextoAplicacao, textViewResourceId, kanjisErrados);
		this.arrayListKanjisErrados = kanjisErrados;
		this.contextoAplicacao = contextoAplicacao;
		
		String fontpath = "fonts/gilles_comic_br.ttf";
	    tf = Typeface.createFromAsset(contextoAplicacao.getAssets(), fontpath);
	    String fontpath2 = "fonts/chifont.ttf";
	    tf2 = Typeface.createFromAsset(contextoAplicacao.getAssets(), fontpath2);
	}

	public ArrayList<DadosDeKanjiErrado> getArrayListKanjisErrados() {
		return arrayListKanjisErrados;
	}

	public void setArrayListKanjisErrados(
			ArrayList<DadosDeKanjiErrado> arrayListKanjisErrados) {
		this.arrayListKanjisErrados = arrayListKanjisErrados;
	}
	
	
	private class ViewHolderKanjisErrados {
		   TextView kanjiErrado;
		   TextView hiraganaErrado;
		   TextView traducaoKanjiErrado;
		   TextView quantasVezesKanjiFoiErrado;
		  }
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderKanjisErrados viewholderKanjisErrados = null;
		if(convertView == null)
		{
			LayoutInflater vi = (LayoutInflater)contextoAplicacao.getSystemService(
				     Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.item_lista_palavras_erradas, null);
			
			viewholderKanjisErrados = new ViewHolderKanjisErrados();
			
		}
		else
		{
			viewholderKanjisErrados = (ViewHolderKanjisErrados) convertView.getTag();
		}
		viewholderKanjisErrados.kanjiErrado = (TextView) convertView.findViewById(R.id._kanji_errado);
		viewholderKanjisErrados.hiraganaErrado = (TextView) convertView.findViewById(R.id.hiragana_errado);
		viewholderKanjisErrados.traducaoKanjiErrado = (TextView) convertView.findViewById(R.id.traducao_errada);
		viewholderKanjisErrados.quantasVezesKanjiFoiErrado = (TextView) convertView.findViewById(R.id.quantos_erros);
		
		
		
		convertView.setTag(viewholderKanjisErrados);
		
		DadosDeKanjiErrado dadosDoKanjiErrado = arrayListKanjisErrados.get(position);
		viewholderKanjisErrados.kanjiErrado.setText(dadosDoKanjiErrado.getKanjiErrado());
		viewholderKanjisErrados.hiraganaErrado.setText(dadosDoKanjiErrado.getHiraganaErrado());
		viewholderKanjisErrados.traducaoKanjiErrado.setText(dadosDoKanjiErrado.getTraducaoErrada());
		viewholderKanjisErrados.quantasVezesKanjiFoiErrado.setText(String.valueOf(dadosDoKanjiErrado.getQuantasVezesKanjiFoiErrado()));
		
		
	    viewholderKanjisErrados.kanjiErrado.setTypeface(tf2);
	    viewholderKanjisErrados.hiraganaErrado.setTypeface(tf2);
	    viewholderKanjisErrados.traducaoKanjiErrado.setTypeface(tf);
	    viewholderKanjisErrados.quantasVezesKanjiFoiErrado.setTypeface(tf);
		
		
		
		
		return convertView;
		
	
	}
	
	public void setListItems(ArrayList<DadosDeKanjiErrado> newList) {
	     this.clear();
	     this.addAll(newList); 
	     notifyDataSetChanged();
	}
	

}
