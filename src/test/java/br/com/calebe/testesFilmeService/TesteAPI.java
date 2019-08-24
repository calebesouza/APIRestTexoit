package br.com.calebe.testesFilmeService;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.calebe.dto.FilmeDTO;
import br.com.calebe.services.FilmeService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TesteAPI {

	@LocalServerPort
    private int port;
	
	private String uri;
	
	@PostConstruct
    public void init() {
		filmeService.limparTabela();
		List<FilmeDTO> filmesDTO = new ArrayList<>();
    	filmesDTO.add(new FilmeDTO("1980", "Titulo1", "Estudio1", "Produtor1", "yes"));
    	filmesDTO.add(new FilmeDTO("1980", "Titulo2", "Estudio1", "Produtor2", ""));
    	filmesDTO.add(new FilmeDTO("1982", "Titulo3", "Estudio2", "Produtor3", ""));
    	filmesDTO.add(new FilmeDTO("1983", "Titulo4", "Estudio1", "Produtor1", "yes"));
    	filmesDTO.add(new FilmeDTO("1985", "Titulo5", "Estudio2", "Produtor4", "yes"));
    	filmesDTO.add(new FilmeDTO("1986", "Titulo6", "Estudio3", "Produtor5", "yes"));
    	filmesDTO.add(new FilmeDTO("1988", "Titulo7", "Estudio4", "Produtor1", ""));
    	filmesDTO.add(new FilmeDTO("1990", "Titulo8", "Estudio4", "Produtor4", "yes"));
    	filmesDTO.add(new FilmeDTO("1992", "Titulo9", "Estudio3", "Produtor5", "yes"));
    	filmesDTO.add(new FilmeDTO("1996", "Titulo10", "Estudio3", "Produtor2", ""));
    	filmeService.cadastrar(filmesDTO);
        uri = "http://localhost:" + port;
    }
	
	@Autowired
	private FilmeService filmeService;
    
	// verificando se o link para listar filmes é valida
	@Test
	public void listarFilmesIsValid() {
    	
		RestAssured.
    	given().
    	when().
            get(uri+"/filmes").
        then().
            assertThat().
            statusCode(200);
    	
	}
	
	// verificando se o link para listar filmes retorna um JSON
	@Test
	public void listarFilmesReturnsJSON() {
    	
		RestAssured.
    	given().
    	when().
            get(uri+"/filmes").
        then().
            contentType(ContentType.JSON);
    	
	}
	
	// verificando se a lista de filmes esta vindo com todos os filmes
	@Test
	public void testarRequisicaoListarFilmesLengthValid() {

    	RestAssured.
    	given().
    	when().
            get(uri+"/filmes").
        then().
            assertThat().
            body("size()", Matchers.is(10)).
        and().
            contentType(ContentType.JSON);
    	
	}
	
	// verificando se todos os filmes da lista tem os seus atributos corretos
	@Test
	public void verificarAtributosDaLista() {
    	
		List<FilmeDTO> filmes = RestAssured.given().when().get(uri+"/filmes").getBody().jsonPath().getList("", FilmeDTO.class);

		assertThat(filmes, Matchers.everyItem(Matchers.hasProperty("id", Matchers.notNullValue())));
		assertThat(filmes, Matchers.everyItem(Matchers.hasProperty("ano", Matchers.notNullValue())));
		assertThat(filmes, Matchers.everyItem(Matchers.hasProperty("titulo", Matchers.notNullValue())));
		assertThat(filmes, Matchers.everyItem(Matchers.hasProperty("estudio", Matchers.notNullValue())));
		assertThat(filmes, Matchers.everyItem(Matchers.hasProperty("produtor", Matchers.notNullValue())));
		assertThat(filmes, Matchers.everyItem(Matchers.hasProperty("vencedor")));
		
	}
	
	// verificando se o link para buscar o produtor com maior intervalo de premios é valida
	@Test
	public void maiorIntervaloDePremiosIsValid() {
    	
		RestAssured.
    	given().
    	when().
            get(uri+"/maiorIntervaloDePremios").
        then().
            assertThat().
            statusCode(200);
    	
	}
	
	// verificando se o link para buscar o produtor com maior intervalo de premios retorna um JSON
	@Test
	public void maiorIntervaloDePremiosReturnsJSON() {
    	
		RestAssured.
    	given().
    	when().
            get(uri+"/maiorIntervaloDePremios").
        then().
            contentType(ContentType.JSON);
    	
	}
	
	// verificando se o link para buscar o produtor com maior intervalo de premios retorna valor correto
	@Test
	public void maiorIntervaloDePremiosReturnsValorCorreto() {
		
		RestAssured.
		given().
	        expect().
	        statusCode(200).
	        body("producer", Matchers.equalTo("Produtor5")).
	        body("interval", Matchers.equalTo(6)).
	        body("previousWin", Matchers.equalTo("1986")).
	        body("followingWin", Matchers.equalTo("1992")).
	    when().
	        get(uri+"/maiorIntervaloDePremios");
		
	}
	
	// verificando se o link para buscar o produtor que conseguiu 2 premios mais rapido é valida
	@Test
	public void doisPremiosMaisRapidoIsValid() {
    	
		RestAssured.
    	given().
    	when().
            get(uri+"/doisPremiosMaisRapido").
        then().
            assertThat().
            statusCode(200);
    	
	}
	
	// verificando se o link para buscar o produtor que conseguiu 2 premios mais rapido retorna um JSON
	@Test
	public void doisPremiosMaisRapidoReturnsJSON() {
    	
		RestAssured.
    	given().
    	when().
            get(uri+"/doisPremiosMaisRapido").
        then().
            contentType(ContentType.JSON);
    	
	}
	
	// verificando se o link para buscar o produtor que conseguiu 2 premios mais rapido retorna valor correto
	@Test
	public void doisPremiosMaisRapidoReturnsValorCorreto() {
		
		RestAssured.
		given().
	        expect().
	        statusCode(200).
	        body("producer", Matchers.equalTo("Produtor1")).
	        body("interval", Matchers.equalTo(3)).
	        body("previousWin", Matchers.equalTo("1980")).
	        body("followingWin", Matchers.equalTo("1983")).
	    when().
	        get(uri+"/doisPremiosMaisRapido");
		
	}
	
}
