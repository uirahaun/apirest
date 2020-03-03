package br.com.novaescola.aplicacaorest.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.novaescola.aplicacaorest.dto.ClienteDTO;
import br.com.novaescola.aplicacaorest.entity.Cliente;
import br.com.novaescola.aplicacaorest.response.Response;
import br.com.novaescola.aplicacaorest.service.ClienteService;

@RestController
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	/*
	 * testar se os valores estão sendo passados para cada função
	 */

	/**
	 * Método para salvar um novo cliente.
	 * @param novoCliente
	 * @return Cliente (novo com id)
	 */
	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	public ResponseEntity<Response<Cliente>> salvarCliente(@Valid @RequestBody ClienteDTO cliente, BindingResult result) {
		Response<Cliente> response = new Response<Cliente>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Cliente clienteSalvo = clienteService.salvar(cliente);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();

		response.setData(clienteSalvo);

		return ResponseEntity.created(location).body(response);
	}

	/**
	 * Método para listar os clientes cadastrados na base
	 * @param pagina 
     * @param limite
     * @return Lista de clientes
	 */
	@RequestMapping(value = "/cliente", method = RequestMethod.GET)
	public Page<Cliente> obterClientes(@RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
			@RequestParam(value = "limite", required = false, defaultValue = "10") int limite) {
			
		return clienteService.findAll(pagina, limite);
	}

	/**
	 * Método para obter o cliente ao informar o Id
	 * @param id
	 * @return Cliente
	 */
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response<Cliente>> obterClientePorId(@PathVariable(value = "id") long id) {
		Response<Cliente> response = new Response<Cliente>();
		
		ResponseEntity<Cliente> clienteExiste = clienteService.findById(id);
		
		if(HttpStatus.NOT_FOUND.equals(clienteExiste.getStatusCode())) {
			response.getErrors().add("Cliente com id ["+id+"] não encontrado!");
			return ResponseEntity.badRequest().body(response);
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteExiste.getBody().getId())
				.toUri();

		response.setData(clienteExiste.getBody());

		return ResponseEntity.created(location).body(response); 
	}

	/**
	 * 
	 * @param id - Identificador do Cliente a ser alterado
	 * @param clienteAlter - Entidade com os campos a serem alterados
	 * @return Cliente - Entidade alterada
	 */
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Response<Cliente>> alterarCliente(@PathVariable(value = "id") long id,
			@Valid @RequestBody Cliente clienteAlter) {
		Response<Cliente> response = new Response<Cliente>();

		ResponseEntity<Cliente> clienteExiste = clienteService.findById(id);
		
		if(HttpStatus.NOT_FOUND.equals(clienteExiste.getStatusCode())) {
			response.getErrors().add("Cliente com id ["+id+"] não encontrado!");
			return ResponseEntity.badRequest().body(response);
		}

		ResponseEntity<Cliente> cliente = clienteService.alterarCliente(clienteExiste.getBody(), clienteAlter);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getBody().getId())
				.toUri();

		response.setData(cliente.getBody());

		return ResponseEntity.created(location).body(response);
	}

	/**
	 * Método para remover um cliente ao se informar o ID 
	 * @param id - Identificador do cliente a ser removido
	 * @return 
	 */
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> removerCliente(@PathVariable(value = "id") long id) {
		Response<Cliente> response = new Response<Cliente>();
		
		ResponseEntity<Cliente> clienteExiste = clienteService.findById(id);
		
		if(HttpStatus.NOT_FOUND.equals(clienteExiste.getStatusCode())) {
			response.getErrors().add("Cliente com id ["+id+"] não encontrado!");
			return ResponseEntity.badRequest().body(response);
		}

		return clienteService.removerCliente(id);
	}
}
