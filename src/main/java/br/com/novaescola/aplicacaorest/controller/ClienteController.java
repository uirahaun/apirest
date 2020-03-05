package br.com.novaescola.aplicacaorest.controller;

import java.util.List;

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

import br.com.novaescola.aplicacaorest.dto.ClienteDTO;
import br.com.novaescola.aplicacaorest.dto.ClienteListaDTO;
import br.com.novaescola.aplicacaorest.entity.Cliente;
import br.com.novaescola.aplicacaorest.exception.ClienteNaoEncontradoException;
import br.com.novaescola.aplicacaorest.exception.ClienteNomeDuplicadoException;
import br.com.novaescola.aplicacaorest.service.ClienteService;

/**
 * 
 * @author Uirá Haun de Oliveira
 *
 */
@RestController
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	/**
	 * Método para salvar um novo cliente.
	 * @param novoCliente
	 * @return Cliente (novo com id)
	 * @throws ClienteNomeDuplicadoException 
	 */
	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	public ResponseEntity<Cliente> salvarCliente(@Valid @RequestBody ClienteDTO cliente, BindingResult result) throws ClienteNomeDuplicadoException {
		Cliente clienteNovo = new Cliente(cliente.getNome(), cliente.getEmail(), cliente.getDataDeNascimento());

		Cliente clienteSalvo = clienteService.salvar(clienteNovo);

		return new ResponseEntity<Cliente>(clienteSalvo,HttpStatus.CREATED);
	}

	/**
	 * Método para listar os clientes cadastrados na base
	 * @param pagina 
     * @param limite
     * @return Lista de clientes
	 */
	@RequestMapping(value = "/cliente", method = RequestMethod.GET)
	public ResponseEntity<ClienteListaDTO> obterClientes(@RequestParam(value = "pagina", required = false, defaultValue = "0") int pagina,
			@RequestParam(value = "limite", required = false, defaultValue = "10") int limite) {
			
		Page<Cliente> page = clienteService.findAll(pagina, limite);
		
		List<Cliente> lista = page.getContent();
		
		ClienteListaDTO clienteRetorno = new ClienteListaDTO();
		clienteRetorno.setLista(lista);
		clienteRetorno.setTotal(page.getTotalElements());
		
		return new ResponseEntity<ClienteListaDTO>(clienteRetorno, HttpStatus.OK);
	}

	/**
	 * Método para obter o cliente ao informar o Id
	 * @param id
	 * @return Cliente
	 */
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> obterClientePorId(@PathVariable(value = "id") long id) {
		ResponseEntity<Cliente> clienteExiste = clienteService.findById(id);
		
		return new ResponseEntity<Cliente>(clienteExiste.getBody(), HttpStatus.OK); 
	}

	/**
	 * 
	 * @param id - Identificador do Cliente a ser alterado
	 * @param clienteAlter - Entidade com os campos a serem alterados
	 * @return Cliente - Entidade alterada
	 */
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Cliente> alterarCliente(@PathVariable(value = "id") long id,
			@Valid @RequestBody Cliente clienteAlter) throws ClienteNaoEncontradoException {
		ResponseEntity<Cliente> clienteExiste = clienteService.findById(id);
		
		if(HttpStatus.NOT_FOUND.equals(clienteExiste.getStatusCode())) {
			throw new ClienteNaoEncontradoException("Cliente com id ["+id+"] não encontrado!");
		}
		
		Cliente cliente = clienteService.alterarCliente(clienteExiste.getBody(), clienteAlter);

		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}

	/**
	 * Método para remover um cliente ao se informar o ID 
	 * @param id - Identificador do cliente a ser removido
	 * @return 
	 */
	@RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> removerCliente(@PathVariable(value = "id") long id) throws ClienteNaoEncontradoException {
		ResponseEntity<Cliente> clienteExiste = clienteService.findById(id);
		
		if(HttpStatus.NOT_FOUND.equals(clienteExiste.getStatusCode())) {
			throw new ClienteNaoEncontradoException("Cliente com id ["+id+"] não encontrado!");
		}

		return clienteService.removerCliente(id);
	}
	
}
