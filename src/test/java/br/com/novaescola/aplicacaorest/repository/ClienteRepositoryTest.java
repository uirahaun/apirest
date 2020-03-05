package br.com.novaescola.aplicacaorest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.novaescola.aplicacaorest.entity.Cliente;

/**
 * 
 * @author Uirá Haun de Oliveira
 *
 */
@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	public void testBuscaClientePeloCodigo() throws Exception {
		Optional<Cliente> optional = clienteRepository.findById(1L);

		assertThat(optional.isPresent()).isTrue();

		Cliente clienteEncontrado = optional.get();

		assertThat(clienteEncontrado.getNome()).isEqualTo("João Silva");
	}

	@Test
	public void testBuscarTodosClientes() throws Exception {
		List<Cliente> lista = clienteRepository.findAll();

		assertThat(lista).isNotNull();
		assertThat(lista).isNotEmpty();
	}

	@Test
	public void testBuscaClientePeloCodigoInexistente() throws Exception {
		Optional<Cliente> optional = clienteRepository.findById(10L);

		assertThat(optional.isPresent()).isFalse();
	}
	
	@Test
	public void testBuscarClienteComLimiteEPagina() throws Exception {
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<Cliente> pageCliente = clienteRepository.findAll(pageRequest);
		
		assertThat(pageCliente.hasContent()).isTrue();
	}
}
