package br.com.calebe.tipos;

public enum TipoVencedor {
	
	SIM("yes"), NAO("");
	
	private String descricao;

	TipoVencedor(String desc) {
		this.descricao = desc;
	}

	public String getDescricao() {
		return this.descricao;
	}
	
}
