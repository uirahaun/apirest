package br.com.novaescola.aplicacaorest.dto;

import java.io.Serializable;
import java.util.List;

import br.com.novaescola.aplicacaorest.entity.Cliente;

/**
 * 
 * @author Uirá Haun de Oliveira
 *
 */
public class ClienteListaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2353135969998310845L;

	private Long total;
	
	private List<Cliente> lista;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<Cliente> getLista() {
		return lista;
	}

	public void setLista(List<Cliente> lista) {
		this.lista = lista;
	}
}
