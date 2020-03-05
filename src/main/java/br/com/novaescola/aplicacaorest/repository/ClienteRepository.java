package br.com.novaescola.aplicacaorest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.novaescola.aplicacaorest.entity.Cliente;

/**
 * 
 * @author Uirá Haun de Oliveira
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	Optional<Cliente> findByNome(String nome);
}