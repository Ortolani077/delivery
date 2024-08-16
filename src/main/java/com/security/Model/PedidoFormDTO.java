package com.security.Model;

import java.util.List;

public class PedidoFormDTO {
	 private String nomeCliente;
	    private String emailCliente;
	    private String telefoneCliente;
	    private String enderecoCliente;
	    private boolean entrega;
	    private String observacoes;
	    private Double preco;
	    private List<Long> idsPizzas; // IDs dos sabores de pizza no pedido
		public String getNomeCliente() {
			return nomeCliente;
		}
		public void setNomeCliente(String nomeCliente) {
			this.nomeCliente = nomeCliente;
		}
		public String getEmailCliente() {
			return emailCliente;
		}
		public void setEmailCliente(String emailCliente) {
			this.emailCliente = emailCliente;
		}
		public String getTelefoneCliente() {
			return telefoneCliente;
		}
		public void setTelefoneCliente(String telefoneCliente) {
			this.telefoneCliente = telefoneCliente;
		}
		public String getEnderecoCliente() {
			return enderecoCliente;
		}
		public void setEnderecoCliente(String enderecoCliente) {
			this.enderecoCliente = enderecoCliente;
		}
		public boolean isEntrega() {
			return entrega;
		}
		public void setEntrega(boolean entrega) {
			this.entrega = entrega;
		}
		public String getObservacoes() {
			return observacoes;
		}
		public void setObservacoes(String observacoes) {
			this.observacoes = observacoes;
		}
		public Double getPreco() {
			return preco;
		}
		public void setPreco(Double preco) {
			this.preco = preco;
		}
		public List<Long> getIdsPizzas() {
			return idsPizzas;
		}
		public void setIdsPizzas(List<Long> idsPizzas) {
			this.idsPizzas = idsPizzas;
		}

}
