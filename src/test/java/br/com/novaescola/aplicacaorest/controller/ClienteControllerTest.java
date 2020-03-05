package br.com.novaescola.aplicacaorest.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import br.com.novaescola.aplicacaorest.ApiRestApplicationTests;
import br.com.novaescola.aplicacaorest.dto.ClienteDTO;
import io.restassured.http.ContentType;

/**
 * 
 * @author Uirá Haun de Oliveira
 *
 */
public class ClienteControllerTest extends ApiRestApplicationTests {

	@Test
	public void testBuscarListaClientesComPaginaELimite() throws Exception {
		given()
			.pathParam("pagina", "0")
			.pathParam("limite", "10")
		.get("/cliente?{pagina}&{limite}")
		.then()
			.log().body().and()
			.statusCode(HttpStatus.OK.value())
				.body("total", equalTo(7))
				.body("lista.id", hasItem(1));
	}
	
	@Test
	public void testBuscarClientePorId() throws Exception {
		given()
			.pathParam("id", 3)
		.get("/cliente/{id}")
		.then()
			.log().body().and()
			.statusCode(HttpStatus.OK.value())
				.body("id", equalTo(3))
				.body("nome", equalTo("Tiago Pires"));
	}

	@Test
	public void testCreateNovoCliente() throws Exception {
		final ClienteDTO clienteNovo = new ClienteDTO();
		clienteNovo.setNome("Marilia Pereira");
		clienteNovo.setEmail("marilia.pereira@email.com");
		clienteNovo.setDataDeNascimento(DateUtils.parseDate("1953-05-11", "yyyy-MM-dd"));
		
		given()
			.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(clienteNovo)
		.when()
		.post("/cliente")
		.then()
			.log().headers()
			.and()
			.log().body()
			.and()
				.statusCode(HttpStatus.CREATED.value())
				.body("id", equalTo(8),
						"nome", equalTo("Marilia Pereira"));
	}
	
	@Test
	public void testUpdateCliente() throws Exception {
		final ClienteDTO clienteAlteracao = new ClienteDTO();
		clienteAlteracao.setId(8L);
		clienteAlteracao.setNome("Marília Pereira da Cruz");
//		clienteNovo.setEmail("marilia.pereira@email.com");
//		clienteNovo.setDataDeNascimento(DateUtils.parseDate("1953-05-11", "yyyy-MM-dd"));
		
		given()
			.pathParam("id", 8)
			.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(clienteAlteracao)
		.when()
		.put("/cliente/{id}")
		.then()
			.log().headers()
			.and()
			.log().body()
			.and()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
}
