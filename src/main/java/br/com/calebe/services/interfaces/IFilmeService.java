package br.com.calebe.services.interfaces;

import java.util.List;

import br.com.calebe.dto.FilmeDTO;
import br.com.calebe.dto.IntervaloPremiosDTO;

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
	 * Verifica se possui registro na tabela pelo id
	 * @return boolean
	 */
	public boolean possuiRegistroPorId(Long id);
	
	/**
	 * Retorna DTO com o produtor que teve o maior intervalo entre 2 premios
	 * @return IntervaloPremiosDTO
	 */
	public IntervaloPremiosDTO buscarProdutorMaiorIntervaloPremios();
	
	/**
	 * Retorna DTO com o produtor que teve dois premios mais rapidos
	 * @return IntervaloPremiosDTO
	 */
	public IntervaloPremiosDTO buscarProdutorDoisPremiosMaisRapido();
	
	/**
	 * Popula a tabela de filmes
	 */
	public void popularTabela();

	/**
	 * Limpa a tabela de filmes
	 */
	public void limparTabela();
	
}
