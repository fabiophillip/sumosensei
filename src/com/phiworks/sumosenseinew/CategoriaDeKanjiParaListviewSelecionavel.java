package com.phiworks.sumosenseinew;

public class CategoriaDeKanjiParaListviewSelecionavel {
	   
	
	 String name = null;
	 boolean selected = false;
	   
	 public CategoriaDeKanjiParaListviewSelecionavel(String name, boolean selected) {
	  super();
	  this.name = name;
	  this.selected = selected;
	 }
	   
	
	 public String getName() {
	  return name;
	 }
	 public void setName(String name) {
	  this.name = name;
	 }
	  
	 public boolean isSelected() {
	  return selected;
	 }
	 public void setSelected(boolean selected) {
	  this.selected = selected;
	 }
	   
	}
