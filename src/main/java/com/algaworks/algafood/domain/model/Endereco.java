package com.algaworks.algafood.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class Endereco {
	
	@Column(name = "endereco_cep")
	private String cep;
	@Column(name = "endereco_bairro")
	private String bairro;
	@Column(name = "endereco_logradouro")
	private String logradouro;
	@Column(name = "endereco_numero")
	private String numero;
	@ManyToOne
	@JoinColumn(name = "endereco_cidade_id")
	private Cidade cidade;

	public Endereco(String cep, String bairro, String logradouro, String numero, Cidade cidade) {
		this.cep = cep;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.cidade = cidade;
	}

	public Endereco() {
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
