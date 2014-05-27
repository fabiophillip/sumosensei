package bancodedados;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.games.Games;
import com.phiworks.sumosenseinew.ActivityQueEsperaAtePegarOsKanjis;
import com.phiworks.sumosenseinew.CategoriaDeKanjiParaListviewSelecionavel;
import com.phiworks.sumosenseinew.R;
import com.phiworks.sumosenseinew.TelaInicialMultiplayer;

public class MyCustomAdapter extends ArrayAdapter<CategoriaDeKanjiParaListviewSelecionavel> {
	  
	  private ArrayList<CategoriaDeKanjiParaListviewSelecionavel> categoriaDeKanjiList;
	  private Context contexto;
	  private boolean podeMudarCategoria;
	  private ActivityQueEsperaAtePegarOsKanjis telaEsperaPegarKanjis;
	  
	  public MyCustomAdapter(Context context, int textViewResourceId,
	    ArrayList<CategoriaDeKanjiParaListviewSelecionavel> categoriaDeKanjiList, boolean podeMudarCategoria, ActivityQueEsperaAtePegarOsKanjis telaEsperaPegarKanjis) {
	   super(context, textViewResourceId, categoriaDeKanjiList);
	   this.categoriaDeKanjiList = new ArrayList<CategoriaDeKanjiParaListviewSelecionavel>();
	   this.categoriaDeKanjiList.addAll(categoriaDeKanjiList);
	   this.contexto = context;
	   this.podeMudarCategoria = podeMudarCategoria;
	   this.telaEsperaPegarKanjis = telaEsperaPegarKanjis;
	  }
	  
	  
	  
	  public ArrayList<CategoriaDeKanjiParaListviewSelecionavel> getCategoriaDeKanjiList() {
		return categoriaDeKanjiList;
	}
	  
	  



	public void setCategoriaDeKanjiList(
			ArrayList<CategoriaDeKanjiParaListviewSelecionavel> categoriaDeKanjiList) {
		this.categoriaDeKanjiList = categoriaDeKanjiList;
	}





	private class ViewHolder {
	   CheckBox name;
	  }
	  
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	  
	   ViewHolder holder = null;
	   Log.v("ConvertView", String.valueOf(position));
	  
	   if (convertView == null) {
	   LayoutInflater vi = (LayoutInflater)contexto.getSystemService(
	     Context.LAYOUT_INFLATER_SERVICE);
	   convertView = vi.inflate(R.layout.categoria_de_kanji_na_lista, null);
	  
	   holder = new ViewHolder();
	   holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
	   if(podeMudarCategoria == false)
	   {
		   convertView.findViewById(R.id.checkBox1).setEnabled(false);
		   holder.name.setEnabled(false);
	   }
	   convertView.setTag(holder);
	  
	    holder.name.setOnClickListener( new View.OnClickListener() { 
	     public void onClick(View v) { 
	      CheckBox cb = (CheckBox) v ; 
	      CategoriaDeKanjiParaListviewSelecionavel categoriaDeKanji = (CategoriaDeKanjiParaListviewSelecionavel) cb.getTag(); 
	      
	      if(podeMudarCategoria == true)
	      {
	    	  categoriaDeKanji.setSelected(cb.isChecked());
	    	  telaEsperaPegarKanjis.mandarMensagemMultiplayer("selecionouCategoria=" + categoriaDeKanji.getName() + ";" + categoriaDeKanji.isSelected());
	      }
	      else
	      {
	    	 String mensagemDeAviso = contexto.getResources().getString(R.string.espere_adversario_selecionar_categoria);
	      	 Toast.makeText(contexto.getApplicationContext(), mensagemDeAviso, Toast.LENGTH_SHORT).show();
	      	 
	      }
	    	  
	     } 
	    }); 
	   }
	   else {
	    holder = (ViewHolder) convertView.getTag();
	   }
	  
	   CategoriaDeKanjiParaListviewSelecionavel categoriaDeKanji = categoriaDeKanjiList.get(position);
	   holder.name.setText(categoriaDeKanji.getName());
	   holder.name.setChecked(categoriaDeKanji.isSelected());
	   holder.name.setTag(categoriaDeKanji);
	  
	   return convertView;
	  
	  }
	  
	 }