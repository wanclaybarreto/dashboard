package br.com.wanclaybarreto.dashboard.application;

import br.com.wanclaybarreto.dashboard.domain.Usuario;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao.UsuarioDAO;
import br.com.wanclaybarreto.dashboard.util.PairUtils;

public abstract class UsuarioService {
	
	public static PairUtils<Usuario, String> entrar(String usuario, String senha) {
		
		PairUtils<Usuario, String> pairUsrMsg;
		
		Usuario u = null;
		UsuarioDAO uDAO = new UsuarioDAO();
		
		try {
			
			u = uDAO.validar(usuario, senha);
			
			if (u == null) {
				pairUsrMsg = new PairUtils<Usuario, String>(null, "Usuário ou senha inválido(a)!");
			} else {
				pairUsrMsg = new PairUtils<Usuario, String>(u, null);
			}
			
			return pairUsrMsg;
			
		} catch (PersistenceException e) {
			
			pairUsrMsg = new PairUtils<Usuario, String>(null, "Ocorreu um erro! Tente novamente!");
			
			e.printStackTrace();
			System.out.println(e.getMessage());
			
			return pairUsrMsg;
			
		}
		
	}
	
}
