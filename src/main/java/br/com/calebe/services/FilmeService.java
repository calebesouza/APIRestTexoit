package br.com.calebe.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.calebe.dto.FilmeDTO;
import br.com.calebe.dto.IntervaloPremiosDTO;
import br.com.calebe.entities.Filme;
import br.com.calebe.repositories.interfaces.FilmeRepository;
import br.com.calebe.services.interfaces.IFilmeService;

@Service
@Transactional
public class FilmeService implements IFilmeService {
	
	private final URL arquivoCSV = this.getClass().getResource("/movielist.csv");
	private final String separadorCSV = ";";
	
	@Autowired
	private FilmeRepository filmeRepository;
	
	public FilmeService() {
		super();
	}

	@Override
	public void popularTabela() {
		List<FilmeDTO> filmes = carregarDeArquivoCSV();
		this.cadastrar(filmes);
	}
	
	@Override
	public List<FilmeDTO> listar() {
		List<FilmeDTO> filmesDTO = new ArrayList<>();
		try {
			List<Filme> filmes = filmeRepository.findAll();
			if ( filmes.size() == 0 ) {
				return filmesDTO;
			}
			for (Filme filme : filmes) {
				filmesDTO.add(new FilmeDTO(filme));
			}
			return filmesDTO;
		} catch ( Exception e ) {
			return filmesDTO;
		}
	}
	
	@Override
	public void cadastrar( List<FilmeDTO> filmesDTO ) {
		if ( filmesDTO.isEmpty() ) {
			return;
		}
		List<Filme> filmes = new ArrayList<>();
		for (FilmeDTO filmeDTO : filmesDTO) {
			filmes.add(new Filme(filmeDTO));
		}
		filmeRepository.saveAll( filmes );
	}
	
	@Override
	public boolean possuiRegistroPorId(Long id) {
		Filme filme = filmeRepository.getOne(id);
		return (filme != null);
	}

	@Override
	public IntervaloPremiosDTO buscarProdutorMaiorIntervaloPremios() {
		List<IntervaloPremiosDTO> intervalosDTO = montarListaIntervalos();
		if ( intervalosDTO.isEmpty() ) {
			return new IntervaloPremiosDTO();
		}
		Collections.sort(intervalosDTO);
		return intervalosDTO.get(intervalosDTO.size()-1);
	}
	
	@Override
	public IntervaloPremiosDTO buscarProdutorDoisPremiosMaisRapido() {
		List<IntervaloPremiosDTO> intervalosDTO = montarListaIntervalos();
		if ( intervalosDTO.isEmpty() ) {
			return new IntervaloPremiosDTO();
		}
		Collections.sort(intervalosDTO);
		for (IntervaloPremiosDTO intervaloPremiosDTO : intervalosDTO) {
			if ( intervaloPremiosDTO.getInterval() == 0 ) {
				continue;
			}
			return intervaloPremiosDTO;
		}
		return new IntervaloPremiosDTO();
	}
	
	@Override
	public void limparTabela() {
		filmeRepository.limparTabela();
	}
	
	private List<FilmeDTO> carregarDeArquivoCSV() {
		List<FilmeDTO> filmes = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivoCSV.getPath())));
			String linha = null;
			int numeroLinha = 1;
			while ((linha = reader.readLine()) != null) {
				if ( numeroLinha == 1 ) {
					numeroLinha++;
					continue;
				}
				String[] filmeCampos = linha.split(separadorCSV);
				filmes.add(new FilmeDTO(filmeCampos[0], filmeCampos[1], filmeCampos[2], filmeCampos[3], filmeCampos.length == 5 ? "yes" : ""));
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return filmes;
	}
	
	private List<IntervaloPremiosDTO> montarListaIntervalos() {
		List<IntervaloPremiosDTO> intervalosPremiosDTO = new ArrayList<>();
		try {
			List<Filme> filmes = filmeRepository.findAll();
			if ( filmes.size() == 0 ) {
				return intervalosPremiosDTO;
			}
			for (Filme filme : filmes) {
				IntervaloPremiosDTO intervalo = new IntervaloPremiosDTO();
				intervalo.setProducer(filme.getProdutor());
				if ( intervalosPremiosDTO.contains(intervalo) ) {
					int index = intervalosPremiosDTO.indexOf(intervalo);
					if ( filme.getVencedor() ) {
						intervalosPremiosDTO.get(index).setFollowingWin(filme.getAno());
						intervalosPremiosDTO.get(index).setInterval( this.calcularIntervalo(intervalosPremiosDTO.get(index)) );
					}
				} else {
					IntervaloPremiosDTO novoIntervalo = new IntervaloPremiosDTO();
					novoIntervalo.setPreviousWin(filme.getAno());
					novoIntervalo.setFollowingWin(filme.getAno());
					novoIntervalo.setInterval(0);
					novoIntervalo.setProducer(filme.getProdutor());
					intervalosPremiosDTO.add(novoIntervalo);
				}
			}
			return intervalosPremiosDTO;
		} catch ( Exception e ) {
			return intervalosPremiosDTO;
		}
	}
	
	private int calcularIntervalo(IntervaloPremiosDTO intervaloPremiosDTO) {
		return Integer.valueOf(intervaloPremiosDTO.getFollowingWin())-Integer.valueOf(intervaloPremiosDTO.getPreviousWin());
	}

}
