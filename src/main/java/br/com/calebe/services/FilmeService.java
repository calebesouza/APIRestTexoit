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
import br.com.calebe.dto.IntervalosDTO;
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
			List<Filme> filmes = this.getFilmes();
			for (Filme filme : filmes) {
				filmesDTO.add(new FilmeDTO(filme));
			}
			return filmesDTO;
		} catch ( Exception e ) {
			return filmesDTO;
		}
	}
	
	private List<Filme> getFilmes(){
		List<Filme> filmes = filmeRepository.findAll();
		if ( filmes.size() == 0 ) {
			return new ArrayList<>();
		}
		return filmes;
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
	public IntervalosDTO buscarIntervaloPremios(){
		IntervalosDTO intervalosDTO = new IntervalosDTO();
		intervalosDTO.setMin(this.buscarProdutorDoisPremiosMaisRapido());
		intervalosDTO.setMax(this.buscarProdutorMaiorIntervaloPremios()); 
		return intervalosDTO;
	}
	
	private IntervaloPremiosDTO buscarProdutorMaiorIntervaloPremios() {
		List<IntervaloPremiosDTO> intervalosDTO = new ArrayList<>();
		try {
			List<Filme> filmes = this.getFilmes();
			if ( filmes.size() == 0 ) {
				return new IntervaloPremiosDTO();
			}
			for (Filme filme : filmes) {
				if ( !filme.getVencedor() ) {
					continue;
				}
				IntervaloPremiosDTO intervalo = new IntervaloPremiosDTO();
				for (String produtor : this.getProdutores(filme)) {
					intervalo.setProducer(produtor);
					if ( intervalosDTO.contains(intervalo) ) {
						int index = intervalosDTO.indexOf(intervalo);
						intervalosDTO.get(index).setFollowingWin(filme.getAno());
						intervalosDTO.get(index).setInterval( this.calcularIntervalo(intervalosDTO.get(index)) );
					} else {
						IntervaloPremiosDTO novoIntervalo = new IntervaloPremiosDTO();
						novoIntervalo.setPreviousWin(filme.getAno());
						novoIntervalo.setFollowingWin(filme.getAno());
						novoIntervalo.setInterval(0);
						novoIntervalo.setProducer(produtor);
						intervalosDTO.add(novoIntervalo);
					}
				}
			}
			Collections.sort(intervalosDTO);
			return intervalosDTO.get(intervalosDTO.size()-1);
		} catch ( Exception e ) {
			return new IntervaloPremiosDTO();
		}
	}
	
	private IntervaloPremiosDTO buscarProdutorDoisPremiosMaisRapido() {
		List<IntervaloPremiosDTO> intervalosDTO = new ArrayList<>();
		try {
			List<Filme> filmes = this.getFilmes();
			if ( filmes.size() == 0 ) {
				return new IntervaloPremiosDTO();
			}
			for (Filme filme : filmes) {
				if ( !filme.getVencedor() ) {
					continue;
				}
				IntervaloPremiosDTO intervalo = new IntervaloPremiosDTO();
				for (String produtor : this.getProdutores(filme)) {
					intervalo.setProducer(produtor);
					int index = intervalosDTO.indexOf(intervalo);
					if ( intervalosDTO.contains(intervalo) ) {
						if ( !intervalosDTO.get(index).getFollowingWin().equals(intervalosDTO.get(index).getPreviousWin()) ) {
							continue;
						}
						intervalosDTO.get(index).setFollowingWin(filme.getAno());
						intervalosDTO.get(index).setInterval(this.calcularIntervalo(intervalosDTO.get(index)));
					} else {
						IntervaloPremiosDTO novoIntervalo = new IntervaloPremiosDTO();
						novoIntervalo.setPreviousWin(filme.getAno());
						novoIntervalo.setFollowingWin(filme.getAno());
						novoIntervalo.setInterval(0);
						novoIntervalo.setProducer(produtor);
						intervalosDTO.add(novoIntervalo);
					}
				}
			}
			Collections.sort(intervalosDTO);
			for (IntervaloPremiosDTO intervalo : intervalosDTO) {
				if (intervalo.getInterval() != 0 ) {
					return intervalo;
				}
			}
			return intervalosDTO.get(0);
		} catch ( Exception e ) {
			return new IntervaloPremiosDTO();
		}
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
	
	private List<String> getProdutores(Filme filme) {
		List<String> nomes = new ArrayList<>();
		if ( !filme.getProdutor().toLowerCase().contains(" and ".toLowerCase()) ) {
			nomes.add(filme.getProdutor());
			return nomes;
		}
		String[] produtoresSepAnd = filme.getProdutor().split(" and ");
		nomes.add(produtoresSepAnd[1]);
		if ( !produtoresSepAnd[0].toLowerCase().contains(", ".toLowerCase()) ) {
			nomes.add(produtoresSepAnd[0]);
			return nomes;
		}
		String[] produtoresSepVirgula = produtoresSepAnd[0].split(", ");
		for (String nome : produtoresSepVirgula) {
			nomes.add(nome);
		}
		return nomes;
	}
	
	private int calcularIntervalo(IntervaloPremiosDTO intervaloPremiosDTO) {
		return Integer.valueOf(intervaloPremiosDTO.getFollowingWin())-Integer.valueOf(intervaloPremiosDTO.getPreviousWin());
	}

}
