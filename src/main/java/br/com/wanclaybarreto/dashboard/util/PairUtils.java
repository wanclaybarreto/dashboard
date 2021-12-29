package br.com.wanclaybarreto.dashboard.util;

/**
 * Classe de utilidades para trabalhar com um par de tipo genéricos. Um exemplo de utilização
 * dessa classe pode ser visto na classe UsuarioService deste projeto.
 *
 * @param <T>
 * @param <S>
 */
public class PairUtils <T, S> {
	
	private T a;
	private S b;
	
	public PairUtils(T a, S b) {
		this.a = a;
		this.b = b;
	}

	public T getA() {
		return a;
	}

	public void setA(T a) {
		this.a = a;
	}

	public S getB() {
		return b;
	}

	public void setB(S b) {
		this.b = b;
	}
	
}
