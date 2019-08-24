package br.com.calebe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.calebe.dto.FilmeDTO;
import br.com.calebe.dto.IntervalosDTO;
import br.com.calebe.services.FilmeService;

@RestController
public class FilmeController {

	@Autowired
	private FilmeService filmeService;

	public FilmeController(FilmeService filmeService) {
		this.filmeService = filmeService;
		this.filmeService.popularTabela();
	}
	
	@RequestMapping(value = "/filmes", method = RequestMethod.GET)
	public ResponseEntity<List<FilmeDTO>> listar() {
		List<FilmeDTO> filmes = filmeService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(filmes);
	}
	
	@RequestMapping(value = "/intervaloPremios", method = RequestMethod.GET)
	public ResponseEntity<IntervalosDTO> intervaloDePremios() {
		IntervalosDTO intervaloPremiosDTO = filmeService.buscarIntervaloPremios();
		return ResponseEntity.status(HttpStatus.OK).body(intervaloPremiosDTO);
	}
	
}
