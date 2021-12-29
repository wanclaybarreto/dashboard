package br.com.wanclaybarreto.dashboard.domain;

import java.math.BigDecimal;

public class OrdensServico {
	
	private String status;
	private BigDecimal valorAVista;
	private BigDecimal valorAPrazo;
	private BigDecimal valorFinal;
	private int quant;
	
	public OrdensServico() {}

	public OrdensServico(String status, BigDecimal valorAVista, BigDecimal valorAPrazo, BigDecimal valorFinal, int quant) {
		this.status = status;
		this.valorAVista = valorAVista;
		this.valorAPrazo = valorAPrazo;
		this.valorFinal = valorFinal;
		this.quant = quant;
	}
	
	/**
	 * Formata a string contida no atributo status para que ela fique apenas com a primeira letra maiúscula.
	 */
	public void formatStatus() {
		String formatedStatus = this.status.toLowerCase();
		this.status = formatedStatus.substring(0,1).toUpperCase().concat(formatedStatus.substring(1));
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getValorAVista() {
		return valorAVista;
	}

	public void setValorAVista(BigDecimal valorAVista) {
		this.valorAVista = valorAVista;
	}

	public BigDecimal getValorAPrazo() {
		return valorAPrazo;
	}

	public void setValorAPrazo(BigDecimal valorAPrazo) {
		this.valorAPrazo = valorAPrazo;
	}

	public BigDecimal getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(BigDecimal valorFinal) {
		this.valorFinal = valorFinal;
	}

	public int getQuant() {
		return quant;
	}

	public void setQuant(int quant) {
		this.quant = quant;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + quant;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valorAPrazo == null) ? 0 : valorAPrazo.hashCode());
		result = prime * result + ((valorAVista == null) ? 0 : valorAVista.hashCode());
		result = prime * result + ((valorFinal == null) ? 0 : valorFinal.hashCode());
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
		OrdensServico other = (OrdensServico) obj;
		if (quant != other.quant)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (valorAPrazo == null) {
			if (other.valorAPrazo != null)
				return false;
		} else if (!valorAPrazo.equals(other.valorAPrazo))
			return false;
		if (valorAVista == null) {
			if (other.valorAVista != null)
				return false;
		} else if (!valorAVista.equals(other.valorAVista))
			return false;
		if (valorFinal == null) {
			if (other.valorFinal != null)
				return false;
		} else if (!valorFinal.equals(other.valorFinal))
			return false;
		return true;
	}
	
}
