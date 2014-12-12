package com.phiworks.domodocasual;



import br.ufrn.dimap.pairg.sumosensei.app.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*classe para fazer uma listview com icones a esquerda e texto a direita*/
public class AdapterListViewIconeETexto extends ArrayAdapter<String>{
private final Activity context;
private final String[] web;
private final Integer[] imageId;
private final Typeface typeFaceFonteTexto;
private int layoutUsadoParaTextoEImagem;//possível valor = R.layout.list_item_icone_e_texto
private final boolean iconesDevemEstarMeioTransparentesNoComeco;

public AdapterListViewIconeETexto(Activity context,String[] web, Integer[] imageId, Typeface typeFaceFonteTexto, boolean iconesDevemEstarMeioTransparentesNoComeco) 
{
	super(context, R.layout.list_item_icone_e_texto, web);
	this.context = context;
	this.web = web;
	this.imageId = imageId;
	this.typeFaceFonteTexto = typeFaceFonteTexto;
	this.iconesDevemEstarMeioTransparentesNoComeco = iconesDevemEstarMeioTransparentesNoComeco;
	this.layoutUsadoParaTextoEImagem = R.layout.list_item_icone_e_texto;
}

public int getLayoutUsadoParaTextoEImagem() {
	return layoutUsadoParaTextoEImagem;
}

public void setLayoutUsadoParaTextoEImagem(int layoutUsadoParaTextoEImagem) {
	this.layoutUsadoParaTextoEImagem = layoutUsadoParaTextoEImagem;
}

@Override
public View getView(int position, View view, ViewGroup parent) 
{
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(layoutUsadoParaTextoEImagem, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	
	//mudar fonte do texto
	txtTitle.setTypeface(typeFaceFonteTexto);
	
	ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
	String stringASetColocadaNoTextView = web[position];
	
	Spannable span = new SpannableString(stringASetColocadaNoTextView);
	if(stringASetColocadaNoTextView.contains("(") == true)
	{
		//tudo que esta dentro do parenteses deveria ficar menor
		int indiceParentesesQueAbre = stringASetColocadaNoTextView.indexOf("(");
		int indiceParentesesQueFecha = stringASetColocadaNoTextView.indexOf(")");
		span.setSpan(new RelativeSizeSpan(0.7f), indiceParentesesQueAbre, indiceParentesesQueFecha + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	}
	
	txtTitle.setText(span);

	//txtTitle.setText(web[position]);
	imageView.setImageResource(imageId[position]);
	
	
	
	if(this.iconesDevemEstarMeioTransparentesNoComeco == true)
	{
		txtTitle.setTextColor(Color.argb(130, 0, 0, 0));
		imageView.setAlpha(130);
	}
	return rowView;
}

}
