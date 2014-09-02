package com.phiworks.domodocasual;

import java.util.LinkedList;

public class DadosDaSalaModoCasual 
{
	private int idSalasMdoCasual;
	private String usernameQuemCriouSala;
	private LinkedList<String> categoriasSelecionadas;
	private String tituloDoJogador;
	public int getIdSalasMdoCasual() {
		return idSalasMdoCasual;
	}
	public void setIdSalasMdoCasual(int idSalasMdoCasual) {
		this.idSalasMdoCasual = idSalasMdoCasual;
	}
	public String getUsernameQuemCriouSala() {
		return usernameQuemCriouSala;
	}
	public void setUsernameQuemCriouSala(String emailQuemCriouSala) {
		this.usernameQuemCriouSala = emailQuemCriouSala;
	}
	public LinkedList<String> getCategoriasSelecionadas() {
		return categoriasSelecionadas;
	}
	
	public String getCategoriasSelecionadasEmString()
	{
		String categoriasSelecionadasEmString = "";
		for(int i = 0; i < categoriasSelecionadas.size(); i++)
		{
			categoriasSelecionadasEmString = categoriasSelecionadasEmString + categoriasSelecionadas.get(i);
			if(i + 1 != categoriasSelecionadas.size())
			{
				categoriasSelecionadasEmString = categoriasSelecionadasEmString + ",";
			}
		}
		return categoriasSelecionadasEmString;
	}
	
	public void setCategoriasSelecionadas(LinkedList<String> categoriasSelecionadas) {
		this.categoriasSelecionadas = categoriasSelecionadas;
	}
	public String getTituloDoJogador() {
		return tituloDoJogador;
	}
	public void setTituloDoJogador(String tituloDoJogador) {
		this.tituloDoJogador = tituloDoJogador;
	}
	
	

}
