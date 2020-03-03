package br.com.novaescola.aplicacaorest.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 84258995029281724L;

	private Long id;
	
	@NotNull(message = "Nome é obrigatório!")
	private String nome;
	
	@NotNull(message = "E-mail é obrigatório!")
	@Email
	private String email;

	@NotNull(message = "Data de Nascimento é obrigatório!")
	@JsonSerialize(using = DateSerializer.class)
	private Date dataDeNascimento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}	
	
}
