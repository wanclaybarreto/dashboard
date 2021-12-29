package br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.wanclaybarreto.dashboard.application.PersistenceException;
import br.com.wanclaybarreto.dashboard.domain.Usuario;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.connection.ConnectionJdbc;

public class UsuarioDAO {
	
	public Usuario validar (String nome, String senha) throws PersistenceException {
		
		Usuario u = null;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select codigo, nome, senha from usuario where nome = ? and senha = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, nome);
			ps.setString(2, senha);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				
				u = new Usuario();
				u.setId(rs.getInt("codigo"));
				u.setNome(rs.getString("nome"));
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return u;
		
	}
	
}
