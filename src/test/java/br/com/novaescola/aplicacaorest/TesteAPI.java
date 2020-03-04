package br.com.novaescola.aplicacaorest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import br.com.novaescola.aplicacaorest.service.ClienteService;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class TesteAPI {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private ClienteService clienteService;

}
