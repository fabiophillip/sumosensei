package lojinha;

import java.io.Serializable;

public class MaceteKanjiParaListviewSelecionavel implements Serializable {
	private String labelKanji = null;
	private boolean jahComprado = false;
	private String nomeUrlDoMaceteKanji = null;
	
	public MaceteKanjiParaListviewSelecionavel(String labelKanji, boolean jahComprado, String nomeUrlDoMaceteKanji) {
		  super();
		  this.labelKanji = labelKanji;
		  this.jahComprado = jahComprado;
		  this.nomeUrlDoMaceteKanji = nomeUrlDoMaceteKanji;
		 }

	public String getLabelKanji() {
		return labelKanji;
	}

	public void setLabelKanji(String labelKanji) {
		this.labelKanji = labelKanji;
	}

	public boolean isJahComprado() {
		return jahComprado;
	}

	public void setJahComprado(boolean jahComprado) {
		this.jahComprado = jahComprado;
	}

	public String getNomeUrlDoMaceteKanji() {
		return nomeUrlDoMaceteKanji;
	}

	public void setNomeUrlDoMaceteKanji(String nomeUrlDoMaceteKanji) {
		this.nomeUrlDoMaceteKanji = nomeUrlDoMaceteKanji;
	}
	
	

}
