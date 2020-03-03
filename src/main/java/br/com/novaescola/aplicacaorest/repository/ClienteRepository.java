package br.com.novaescola.aplicacaorest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.novaescola.aplicacaorest.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> { }