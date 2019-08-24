package br.com.calebe.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.calebe.entities.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>  {

	@Modifying
    @Query(value = "TRUNCATE TABLE filme", nativeQuery = true)
    void limparTabela();
	
}
