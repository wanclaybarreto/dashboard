package br.com.wanclaybarreto.dashboard.util;

import java.util.Random;

/**
 * Classe de utilidades para cores RGBA.
 * 
 */
public abstract class ColorRgbaUtils {
	
	/**
	 * Retorna a String de uma cor RGBA de forma randômica. <br/>
	 * 
	 * Exemplo de cor que pode ser retornada: "rgba(200, 100, 50, 0.5)".
	 * 
	 */
	public static String gerenateColor() {
		Random random = new Random();
		
		int r = random.nextInt(256);
		int g = random.nextInt(256);
		int b = random.nextInt(256);
		
		return "\"rgba("+r+", "+g+", "+b+", 0.5)\"";
	}
	
}
