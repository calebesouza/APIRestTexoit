package br.com.calebe.dto;

import java.io.Serializable;

import br.com.calebe.entities.Filme;
import br.com.calebe.tipos.TipoVencedor;

public class FilmeDTO implements Serializable {

	private static final long serialVersionUID = -3879461964268667743L;
	
	private String id;
	private String ano;
	private String titulo;
	private String estudio;
	private String produtor;
	private String vencedor;

	public FilmeDTO(String ano, String titulo, String estudio, String produtor, String vencedor) {
		super();
		this.ano = ano;
		this.titulo = titulo;
		this.estudio = estudio;
		this.produtor = (produtor.replace('\"', ' ')).trim();
		this.vencedor = vencedor;
	}

	public FilmeDTO(Filme filme) {
		super();
		if ( filme.getId() != null ) {
			this.id = filme.getId().toString();
		}
		if ( !"".equals(filme.getAno()) ) {
			this.ano = filme.getAno();
		}
		if ( !"".equals(filme.getTitulo()) ) {
			this.titulo = filme.getTitulo();
		}
		if ( !"".equals(filme.getEstudio()) ) {
			this.estudio = filme.getEstudio().toString();
		}
		if ( !"".equals(filme.getProdutor()) ) {
			this.produtor = filme.getProdutor().toString();
		}
		if ( filme.getVencedor() != null ) {
			this.vencedor = TipoVencedor.SIM.getDescricao();
		} else {
			this.vencedor = TipoVencedor.NAO.getDescricao();
		}
	}

	public String toString() {
		return "Filme{ano='" + ano + "\', titulo=" + titulo + ", estudio='" + estudio + ", produtor='" + produtor
				+ ", vencedor='" + vencedor + "''\'}";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEstudio() {
		return estudio;
	}

	public void setEstudio(String estudio) {
		this.estudio = estudio;
	}

	public String getProdutor() {
		return produtor;
	}

	public void setProdutor(String produtor) {
		this.produtor = produtor;
	}

	public String getVencedor() {
		return vencedor;
	}

	public void setVencedor(String vencedor) {
		this.vencedor = vencedor;
	}

}
