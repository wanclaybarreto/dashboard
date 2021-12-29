package br.com.wanclaybarreto.dashboard.domain;

import java.math.BigDecimal;

public class ProdutoVendido {
	
	private Integer id;
	private String nome;
	private BigDecimal quant;
	private BigDecimal acrescimo;
	private BigDecimal valor;
	private BigDecimal desconto;
	private BigDecimal subtotal;
	private BigDecimal percentualSubtotal;

	public ProdutoVendido(Integer id, String nome, BigDecimal quant, BigDecimal acrescimo, BigDecimal valor,
			BigDecimal desconto, BigDecimal subtotal) {
		this.id = id;
		this.nome = nome;
		this.quant = quant;
		this.acrescimo = acrescimo;
		this.valor = valor;
		this.desconto = desconto;
		this.subtotal = subtotal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getQuant() {
		return quant;
	}

	public void setQuant(BigDecimal quant) {
		this.quant = quant;
	}

	public BigDecimal getAcrescimo() {
		return acrescimo;
	}

	public void setAcrescimo(BigDecimal acrescimo) {
		this.acrescimo = acrescimo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getPercentualSubtotal() {
		return percentualSubtotal;
	}

	public void setPercentualSubtotal(BigDecimal percentualSubtotal) {
		this.percentualSubtotal = percentualSubtotal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoVendido other = (ProdutoVendido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
