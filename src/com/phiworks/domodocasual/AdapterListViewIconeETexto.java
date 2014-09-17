package com.phiworks.domodocasual;



import com.phiworks.sumosenseinew.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*classe para fazer uma listview com icones a esquerda e texto a direita*/
public class AdapterListViewIconeETexto extends ArrayAdapter<String>{
private final Activity context;
private final String[] web;
private final Integer[] imageId;
private final Typeface typeFaceFonteTexto;
private final boolean iconesDevemEstarMeioTransparentesNoComeco;

public AdapterListViewIconeETexto(Activity context,String[] web, Integer[] imageId, Typeface typeFaceFonteTexto, boolean iconesDevemEstarMeioTransparentesNoComeco) 
{
	super(context, R.layout.list_item_icone_e_texto, web);
	this.context = context;
	this.web = web;
	this.imageId = imageId;
	this.typeFaceFonteTexto = typeFaceFonteTexto;
	this.iconesDevemEstarMeioTransparentesNoComeco = iconesDevemEstarMeioTransparentesNoComeco;
}
@Override
public View getView(int position, View view, ViewGroup parent) 
{
	LayoutInflater inflater = context.getLayoutInflater();
	View rowView= inflater.inflate(R.layout.list_item_icone_e_texto, null, true);
	TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
	
	//mudar fonte do texto
	txtTitle.setTypeface(typeFaceFonteTexto);
	txtTitle.setTextColor(Color.parseColor("#6b0674"));
	
	ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
	txtTitle.setText(web[position]);
	imageView.setImageResource(imageId[position]);
	
	if(this.iconesDevemEstarMeioTransparentesNoComeco == true)
	{
		imageView.setAlpha(128);
	}
	return rowView;
}

}
