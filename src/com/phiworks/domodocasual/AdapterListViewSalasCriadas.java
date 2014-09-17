package com.phiworks.domodocasual;

import java.util.ArrayList;
import java.util.LinkedList;

import com.phiworks.sumosenseinew.CategoriaDeKanjiParaListviewSelecionavel;
import com.phiworks.sumosenseinew.LojinhaMaceteKanjiActivity;
import com.phiworks.sumosenseinew.R;
import com.phiworks.sumosenseinew.TelaModoCasual;
import com.phiworks.sumosenseinew.VerMaceteKanjiActivity;

import lojinha.ConcreteDAOAcessaComprasMaceteKanji;
import lojinha.ConcreteDAOAcessaDinheiroDoJogador;
import lojinha.DAOAcessaComprasMaceteKanji;
import lojinha.DAOAcessaDinheiroDoJogador;
import lojinha.MaceteKanjiParaListviewSelecionavel;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterListViewSalasCriadas extends ArrayAdapter<SalaAbertaModoCasual> 
{
	private ArrayList<SalaAbertaModoCasual> arrayListSalasAbertas;
	private Context contextoAplicacao;
	private TelaModoCasual telaDoModoCasual;
	
	public AdapterListViewSalasCriadas(Context contextoAplicacao, int textViewResourceId,
		    ArrayList<SalaAbertaModoCasual> salasAbertas, TelaModoCasual telaDoModoCasual) 
	{
		super(contextoAplicacao, textViewResourceId, salasAbertas);
		this.arrayListSalasAbertas = salasAbertas;
		this.contextoAplicacao = contextoAplicacao;
		this.telaDoModoCasual = telaDoModoCasual;
	}

	public ArrayList<SalaAbertaModoCasual> getArrayListSalasAbertas() {
		return arrayListSalasAbertas;
	}

	public void setArrayListSalasAbertas(
			ArrayList<SalaAbertaModoCasual> arrayListSalasAbertas) {
		this.arrayListSalasAbertas = arrayListSalasAbertas;
	}
	
	
	private class ViewHolderSalasCriadas {
		   TextView nomeDeUsuario;
		   TextView nivelDoUsuario;
		  }
	
	@Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	  
	   ViewHolderSalasCriadas holder = null;
	   Log.v("ConvertView", String.valueOf(position));
	  
	   if (convertView == null) {
		   
	   LayoutInflater vi = (LayoutInflater)contextoAplicacao.getSystemService(
	     Context.LAYOUT_INFLATER_SERVICE);
	   convertView = vi.inflate(R.layout.item_lista_sala, null);
	   
	   LinearLayout layoutDeUmaLinhaDoBuscarSalas = (LinearLayout) convertView.findViewById(R.id.uma_linha_buscar_salas);
	   TextView textoUsername = (TextView) convertView.findViewById(R.id.username);
	   TextView textoTituloDoJogador = (TextView) convertView.findViewById(R.id.titulo_do_jogador);
	   if((position & 1) != 0)
	   {
		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.red_header);
		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
		   textoTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	   }
	   else
	   {
		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.white_header);
		   textoUsername.setTextColor(Color.parseColor("#000000"));
		   textoTituloDoJogador.setTextColor(Color.parseColor("#000000"));
	   }
	  
	   holder = new ViewHolderSalasCriadas();
	   holder.nomeDeUsuario = (TextView) convertView.findViewById(R.id.username);
	   holder.nivelDoUsuario = (TextView) convertView.findViewById(R.id.titulo_do_jogador);
	   
	  
	   
	   convertView.setTag(holder);
	   SalaAbertaModoCasual salaEscolhidaPraJogar = arrayListSalasAbertas.get(position);
	   holder.nomeDeUsuario.setText(salaEscolhidaPraJogar.getNomeDeUsuario());
	   holder.nivelDoUsuario.setText(salaEscolhidaPraJogar.getNivelDoUsuario());
	   
	   LinkedList<String> categoriasPraJogar = salaEscolhidaPraJogar.getCategoriasSelecionadas();
	   
	   
	   LinkedList<String> categoriasTreinadasNaSala = categoriasPraJogar;
	   
	   new LinkedList<ImageView>();
	   LinearLayout linearLayoutAtualParaAdicionar = null;
	   for(int i = 0; i < categoriasTreinadasNaSala.size(); i++)
	   {
		   if(i == 0 || (i % 5) == 0)//5 categorias por linha
		   {
			   //adicionar novo LinearLayout no com uma linha nova para ícones de categorias PROGRAMATICAMENTE
			   LinearLayout novoLinearLayoutCategorias = new LinearLayout(contextoAplicacao);
			   RelativeLayout.LayoutParams parametrosNovoLinearLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			   parametrosNovoLinearLayout.addRule(RelativeLayout.CENTER_HORIZONTAL);
			   if(linearLayoutAtualParaAdicionar == null)
			   {
				   parametrosNovoLinearLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			   }
			   else
			   {
				   int idAntigoLinearLayoutAdicionarCategorias = linearLayoutAtualParaAdicionar.getId();
				   parametrosNovoLinearLayout.addRule(RelativeLayout.BELOW, idAntigoLinearLayoutAdicionarCategorias);
			   }
			   novoLinearLayoutCategorias.setLayoutParams(parametrosNovoLinearLayout);
			   novoLinearLayoutCategorias.setId(123456789 + i);
			   RelativeLayout layoutIconesCategorias = (RelativeLayout) convertView.findViewById(R.id.campo_categorias);
			   layoutIconesCategorias.addView(novoLinearLayoutCategorias);
			   linearLayoutAtualParaAdicionar = novoLinearLayoutCategorias;
			 //terminou de adicionar novo LinearLayout no com uma linha nova para ícones de categorias PROGRAMATICAMENTE
		   }
		   String nomeUmaCategoriaTreinada = categoriasTreinadasNaSala.get(i);
		   int idImagemCategoria = AssociaCategoriaComIcone.pegarIdImagemDaCategoria(contextoAplicacao, nomeUmaCategoriaTreinada);
		   if(idImagemCategoria != -1)
		   {
			   ImageView umImageViewCategoria = new ImageView(contextoAplicacao);
			   LinearLayout.LayoutParams parametrosNovaImageView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			   umImageViewCategoria.setLayoutParams(parametrosNovaImageView);
			   umImageViewCategoria.setImageResource(idImagemCategoria);
			   umImageViewCategoria.setPadding(1, 0, 1, 0);
			   linearLayoutAtualParaAdicionar.addView(umImageViewCategoria);
		   }
	   }
	   
	   }
	   else 
	   {
		   //ANDREWS ADICIONOU
	       holder = (ViewHolderSalasCriadas) convertView.getTag();
	       LinearLayout layoutDeUmaLinhaDoBuscarSalas = (LinearLayout) convertView.findViewById(R.id.uma_linha_buscar_salas);
	 	   TextView textoUsername = (TextView) convertView.findViewById(R.id.username);
	 	   TextView textoTituloDoJogador = (TextView) convertView.findViewById(R.id.titulo_do_jogador);
	 	   if((position & 1) != 0)
	 	   {
	 		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.red_header);
	 		   textoUsername.setTextColor(Color.parseColor("#FFFFFF"));
	 		   textoTituloDoJogador.setTextColor(Color.parseColor("#FFFFFF"));
	 	   }
	 	   else
	 	   {
	 		   layoutDeUmaLinhaDoBuscarSalas.setBackgroundResource(R.drawable.white_header);
	 		   textoUsername.setTextColor(Color.parseColor("#000000"));
	 		   textoTituloDoJogador.setTextColor(Color.parseColor("#000000"));
	 	   }
	 	  
	 	   //holder = new ViewHolderSalasCriadas();
	 	   holder.nomeDeUsuario = (TextView) convertView.findViewById(R.id.username);
	 	   holder.nivelDoUsuario = (TextView) convertView.findViewById(R.id.titulo_do_jogador);
	 	   
	 	  
	 	   
	 	   //convertView.setTag(holder);
	 	   SalaAbertaModoCasual salaEscolhidaPraJogar = arrayListSalasAbertas.get(position);
	 	   holder.nomeDeUsuario.setText(salaEscolhidaPraJogar.getNomeDeUsuario());
	 	   holder.nivelDoUsuario.setText(salaEscolhidaPraJogar.getNivelDoUsuario());
	 	  
	 	   
	 	   
	 	   LinkedList<String> categoriasTreinadasNaSala = salaEscolhidaPraJogar.getCategoriasSelecionadas();
	 	  
	 	   
	 	   new LinkedList<ImageView>();
	 	   LinearLayout linearLayoutAtualParaAdicionar = null;
	 	   RelativeLayout layoutIconesCategorias = (RelativeLayout) convertView.findViewById(R.id.campo_categorias);
	 	   layoutIconesCategorias.removeAllViewsInLayout();
	 	   for(int i = 0; i < categoriasTreinadasNaSala.size(); i++)
	 	   {
	 		   if(i == 0 || (i % 5) == 0)//5 categorias por linha
	 		   {
	 			   //adicionar novo LinearLayout no com uma linha nova para ícones de categorias PROGRAMATICAMENTE
	 			   LinearLayout novoLinearLayoutCategorias = new LinearLayout(contextoAplicacao);
	 			   RelativeLayout.LayoutParams parametrosNovoLinearLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	 			   parametrosNovoLinearLayout.addRule(RelativeLayout.CENTER_HORIZONTAL);
	 			   if(linearLayoutAtualParaAdicionar == null)
	 			   {
	 				   parametrosNovoLinearLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	 			   }
	 			   else
	 			   {
	 				   int idAntigoLinearLayoutAdicionarCategorias = linearLayoutAtualParaAdicionar.getId();
	 				   parametrosNovoLinearLayout.addRule(RelativeLayout.BELOW, idAntigoLinearLayoutAdicionarCategorias);
	 			   }
	 			   novoLinearLayoutCategorias.setLayoutParams(parametrosNovoLinearLayout);
	 			   novoLinearLayoutCategorias.setId(123456789 + i);
	 			   layoutIconesCategorias.addView(novoLinearLayoutCategorias);
	 			   linearLayoutAtualParaAdicionar = novoLinearLayoutCategorias;
	 			 //terminou de adicionar novo LinearLayout no com uma linha nova para ícones de categorias PROGRAMATICAMENTE
	 		   }
	 		   String nomeUmaCategoriaTreinada = categoriasTreinadasNaSala.get(i);
	 		   int idImagemCategoria = AssociaCategoriaComIcone.pegarIdImagemDaCategoria(contextoAplicacao, nomeUmaCategoriaTreinada);
	 		   if(idImagemCategoria != -1)
	 		   {
	 			   ImageView umImageViewCategoria = new ImageView(contextoAplicacao);
	 			   LinearLayout.LayoutParams parametrosNovaImageView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
	 			   umImageViewCategoria.setLayoutParams(parametrosNovaImageView);
	 			   umImageViewCategoria.setImageResource(idImagemCategoria);
	 			   umImageViewCategoria.setPadding(1, 0, 1, 0);
	 			   linearLayoutAtualParaAdicionar.addView(umImageViewCategoria);
	 		   }
	 	   }
	 	   
	 	   
	 	    /*convertView.setOnClickListener( new View.OnClickListener() { 
	 	     public void onClick(View v) {
	 	    	 
	 	     
	 	      
	 	     
	 	    	  
	 	     } 
	 	    }); */
	    }
	   
	  
	   
	  
	   return convertView;
	  
	  }

}
