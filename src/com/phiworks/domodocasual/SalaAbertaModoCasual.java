package com.phiworks.domodocasual;

import java.io.Serializable;
import java.util.LinkedList;

public class SalaAbertaModoCasual implements Serializable
{
	private int idDaSala;
	private String nomeDeUsuario;
	private String nivelDoUsuario;
	private LinkedList<String> categoriasSelecionadas;
	
	public SalaAbertaModoCasual(String nomeUser, String nivelUser, LinkedList<String> categorias)
	{
		super();
		this.nomeDeUsuario = nomeUser;
		this.nivelDoUsuario = nivelUser;
		this.categoriasSelecionadas = categorias;
	}
	
	public SalaAbertaModoCasual()
	{
		
	}
	
	public String getNomeDeUsuario() {
		return nomeDeUsuario;
	}
	public void setNomeDeUsuario(String nomeDeUsuario) {
		this.nomeDeUsuario = nomeDeUsuario;
	}
	public String getNivelDoUsuario() {
		return nivelDoUsuario;
	}
	public void setNivelDoUsuario(String nivelDoUsuario) {
		this.nivelDoUsuario = nivelDoUsuario;
	}
	public LinkedList<String> getCategoriasSelecionadas() {
		return categoriasSelecionadas;
	}
	public void setCategoriasSelecionadas(LinkedList<String> categoriasSelecionadas) {
		this.categoriasSelecionadas = categoriasSelecionadas;
	}

	public int getIdDaSala() {
		return idDaSala;
	}
	public void setIdDaSala(int idDaSala) {
		this.idDaSala = idDaSala;
	}

	
	
	
	
	

}
