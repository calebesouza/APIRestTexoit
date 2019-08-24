package br.com.calebe.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import br.com.calebe.dto.FilmeDTO;

@Entity
@Component
@Table(name = "filme")
public class Filme implements Serializable {

	private static final long serialVersionUID = 6020617195244815009L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "ano", nullable = false)
	private String ano;

	@Column(name = "titulo", nullable = false)
	private String titulo;

	@Column(name = "estudio", nullable = false)
	private String estudio;

	@Column(name = "produtor", nullable = false)
	private String produtor;

	@Column(name = "vencedor", nullable = true)
	private Boolean vencedor;

	public Filme() {
		
	}
	
	public Filme(Long id, String ano, String titulo, String estudio, String produtor, Boolean vencedor) {
		super();
		this.id = id;
		this.ano = ano;
		this.titulo = titulo;
		this.estudio = estudio;
		this.produtor = produtor;
		this.vencedor = vencedor;
	}

	public Filme(FilmeDTO filmeDTO) {
		if (!"".equals(filmeDTO.getAno())) {
			this.ano = filmeDTO.getAno();
		}
		if (!"".equals(filmeDTO.getTitulo())) {
			this.titulo = filmeDTO.getTitulo();
		}
		if (!"".equals(filmeDTO.getEstudio())) {
			this.estudio = filmeDTO.getEstudio();
		}
		if (!"".equals(filmeDTO.getProdutor())) {
			this.produtor = filmeDTO.getProdutor();
		}
		if (!"".equals(filmeDTO.getVencedor())) {
			this.vencedor = true;
		} else {
			this.vencedor = false;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Boolean getVencedor() {
		return vencedor;
	}

	public void setVencedor(Boolean vencedor) {
		this.vencedor = vencedor;
	}

	public String toString() {
		return "Filme{ano='" + ano + "\', titulo=" + titulo + ", estudio='" + estudio + ", produtor='" + produtor
				+ ", vencedor='" + vencedor + "''\'}";
	}

}
