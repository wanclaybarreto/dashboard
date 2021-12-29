package br.com.wanclaybarreto.dashboard.domain;

import java.math.BigDecimal;

public class Vendas {
	
	private BigDecimal valor;
	private BigDecimal valorTotal;
	private BigDecimal percentualValor;
	private Integer quant;
	private Integer quantTotal;
	private BigDecimal percentualQuant;
	private String operador;
	private Vendedor vendedor;
	private Cliente cliente;
	private String formaPagamento;
	private Integer hora;
	
	public Vendas() {}
	
	public Vendas(BigDecimal valor, BigDecimal valorTotal, BigDecimal percentualValor, Integer quant,
			Integer quantTotal, BigDecimal percentualQuant, String operador, Vendedor vendedor, Cliente cliente,
			String formaPagamento, Integer hora) {
		this.valor = valor;
		this.valorTotal = valorTotal;
		this.percentualValor = percentualValor;
		this.quant = quant;
		this.quantTotal = quantTotal;
		this.percentualQuant = percentualQuant;
		this.operador = operador;
		this.vendedor = vendedor;
		this.cliente = cliente;
		this.formaPagamento = formaPagamento;
		this.hora = hora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getPercentualValor() {
		return percentualValor;
	}

	public void setPercentualValor(BigDecimal percentualValor) {
		this.percentualValor = percentualValor;
	}

	public Integer getQuant() {
		return quant;
	}

	public void setQuant(Integer quant) {
		this.quant = quant;
	}

	public Integer getQuantTotal() {
		return quantTotal;
	}

	public void setQuantTotal(Integer quantTotal) {
		this.quantTotal = quantTotal;
	}

	public BigDecimal getPercentualQuant() {
		return percentualQuant;
	}

	public void setPercentualQuant(BigDecimal percentualQuant) {
		this.percentualQuant = percentualQuant;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}
	
}
