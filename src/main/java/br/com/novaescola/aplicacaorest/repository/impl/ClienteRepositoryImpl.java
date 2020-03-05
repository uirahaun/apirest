package br.com.novaescola.aplicacaorest.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Uirá Haun de Oliveira
 *
 */
@Component
public class ClienteRepositoryImpl {

	@PersistenceContext
	private EntityManager entityManager;
}
