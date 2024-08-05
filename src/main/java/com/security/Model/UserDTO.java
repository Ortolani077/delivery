// ClienteDTO.java
package com.security.Model;

import java.util.List;

public class UserDTO {
    private String nome;
    private String email;
    private String telefone;
    private List<Long> enderecoIds; // Lista de IDs de endereços
    private String senha;
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
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public List<Long> getEnderecoIds() {
		return enderecoIds;
	}
	public void setEnderecoIds(List<Long> enderecoIds) {
		this.enderecoIds = enderecoIds;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public UserDTO(String nome, String email, String telefone, List<Long> enderecoIds, String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.enderecoIds = enderecoIds;
		this.senha = senha;
	}
	public UserDTO() {
		super();
	}

  
}
