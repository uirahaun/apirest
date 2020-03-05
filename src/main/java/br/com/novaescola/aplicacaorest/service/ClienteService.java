package br.com.novaescola.aplicacaorest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.novaescola.aplicacaorest.entity.Cliente;
import br.com.novaescola.aplicacaorest.exception.ClienteNomeDuplicadoException;
import br.com.novaescola.aplicacaorest.repository.ClienteRepository;

/**
 * 
 * @author Uirá Haun de Oliveira
 *
 */
@Service
public class ClienteService {

	@Autowired
	public ClienteRepository clienteRepository;
	
	public ClienteService(ClienteRepository clienteRepository) {
		super();
		this.clienteRepository = clienteRepository;
	}

	public Cliente salvar(Cliente novoCliente) throws ClienteNomeDuplicadoException {
		
		Optional<Cliente> optional = clienteRepository.findByNome(novoCliente.getNome());
		
		if( optional.isPresent() ) {
            throw new ClienteNomeDuplicadoException("Já existe cliente cadastrado com o nome '"+novoCliente.getNome()+"'");
        }
		
		return clienteRepository.save(novoCliente);
	}

	public ResponseEntity<Cliente> findById(long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);

		if (cliente.isPresent()) {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		}

		return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
	}

	public Page<Cliente> findAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		return clienteRepository.findAll(pageRequest);
	}

	public Cliente alterarCliente(Cliente clienteExistente, Cliente clienteAlter) {
		if(!StringUtils.isEmpty(clienteAlter.getNome()) && !clienteAlter.getNome().equalsIgnoreCase(clienteExistente.getNome())) {
			clienteExistente.setNome(clienteAlter.getNome());
		}
		
		if(clienteAlter.getDataDeNascimento() != null && !clienteAlter.getDataDeNascimento().equals(clienteExistente.getDataDeNascimento())) {
			clienteExistente.setDataDeNascimento(clienteAlter.getDataDeNascimento());
		}
		
		if(!StringUtils.isEmpty(clienteAlter.getEmail()) && !clienteAlter.getEmail().equalsIgnoreCase(clienteExistente.getEmail())) {
			clienteExistente.setEmail(clienteAlter.getEmail());
		}

		clienteRepository.save(clienteExistente);

		return clienteExistente;
	}

	public ResponseEntity<Object> removerCliente(long id) {
		Optional<Cliente> clienteDelete = clienteRepository.findById(id);

		if (clienteDelete.isPresent()) {
			clienteRepository.delete(clienteDelete.get());

			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
