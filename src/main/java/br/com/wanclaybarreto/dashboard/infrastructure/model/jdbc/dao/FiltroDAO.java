package br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.wanclaybarreto.dashboard.application.PersistenceException;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.connection.ConnectionJdbc;

/**
 * Os índices dos filtros de busca ficam fixos no código do Enum PeriodUtils. Apenas o índice
 * é salvo no banco de dados.
 * 
 */
public class FiltroDAO {
	
	public int findIndexFilter() throws PersistenceException {
		
		int index = 0;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select indice_filtro_dash from configuracoes";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				index = rs.getInt("indice_filtro_dash");
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return index;
	}
	
	public void saveIndexFilter(int index) throws PersistenceException {
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "update configuracoes set indice_filtro_dash = ? where codigo = 1";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, index);
			
			ps.execute();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
	}
	
}
