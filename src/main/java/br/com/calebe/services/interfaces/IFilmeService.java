package br.com.calebe.services.interfaces;

import java.util.List;

import br.com.calebe.dto.FilmeDTO;
import br.com.calebe.dto.IntervalosDTO;

public interface IFilmeService {
	
	/**
	 * Retorna todos os filmes gravados no banco de dados
	 * @return List<FilmeDTO>
	 */
	public List<FilmeDTO> listar();
	
	/**
	 * Cadastra uma lista de filmes no banco de dados
	 */
	public void cadastrar( List<FilmeDTO> filmes );
	
	/**
	 * Retorna DTO com o produtor que teve o maior intervalo entre 2 premios e 
	 * o produtor que teve dois premios mais rapidos
	 * @return IntervalosDTO
	 */
	public IntervalosDTO buscarIntervaloPremios();
	
	/**
	 * Popula a tabela de filmes
	 */
	public void popularTabela();

	/**
	 * Limpa a tabela de filmes
	 */
	public void limparTabela();

}
