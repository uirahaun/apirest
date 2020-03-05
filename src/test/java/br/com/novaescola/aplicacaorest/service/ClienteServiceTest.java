package br.com.novaescola.aplicacaorest.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.novaescola.aplicacaorest.entity.Cliente;
import br.com.novaescola.aplicacaorest.exception.ClienteNaoEncontradoException;
import br.com.novaescola.aplicacaorest.exception.ClienteNomeDuplicadoException;
import br.com.novaescola.aplicacaorest.repository.ClienteRepository;

/**
 * 
 * @author Uirá Haun de Oliveira
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@TestPropertySource("classpath:test.properties")
public class ClienteServiceTest {
	
	private static final Long CLIENTE_ID = 9L;

	private static final String FORMATO_DATA = "yyyy-MM-dd";

	private static final String DT_NASCIMENTO_CLIENTE = "1980-02-02";

	private static final String EMAIL_CLIENTE = "uirahaun@gmail.com";

	private static final String NOME_CLIENTE = "Uirá Haun";

	private Cliente cliente;
	
	@MockBean
	private ClienteRepository clienteRepository;
	
	@MockBean
	private ClienteService clienteService;

	@Rule
    public ExpectedException expectedException = ExpectedException.none();
	
	@Before
    public void setUp() throws Exception {
		clienteService = new ClienteService(clienteRepository);
		
		cliente = new Cliente();
		cliente.setId(CLIENTE_ID);
		cliente.setNome(NOME_CLIENTE);
		cliente.setEmail(EMAIL_CLIENTE);
		cliente.setDataDeNascimento(DateUtils.parseDate(DT_NASCIMENTO_CLIENTE, FORMATO_DATA));
	}
	
	@Test
	public void testSalvarCliente() throws ClienteNomeDuplicadoException {
		clienteService.salvar(cliente);

		verify(clienteRepository).save(cliente);
	}
	
	@Test
	public void testNaoPodeSalvarComMesmoNome() throws ClienteNomeDuplicadoException {
		when(clienteRepository.findByNome(NOME_CLIENTE)).thenReturn(Optional.of(cliente));
		
		expectedException.expect(ClienteNomeDuplicadoException.class);
        expectedException.expectMessage("Já existe cliente cadastrado com o nome '"+ NOME_CLIENTE +"'");
		
		clienteService.salvar(cliente);
	}
	
	@Test
	public void testClienteNaoExiste() throws ClienteNomeDuplicadoException {
		expectedException.expect(ClienteNaoEncontradoException.class);
        expectedException.expectMessage("Não existe cliente com o id (" + CLIENTE_ID +")");
		
		clienteService.salvar(cliente);
	}
	
}
