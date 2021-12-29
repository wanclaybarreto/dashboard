package br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.wanclaybarreto.dashboard.application.PersistenceException;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.connection.ConnectionJdbc;

public class PromissoriaDAO {
	
	public Integer findQuantPromQuit(String startDate, String endDate) throws PersistenceException {
		
		Integer quantPromQuit;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select count(*) as quant_prom_quit from contasreceber where (codTipo = 4 or codTipo = 8) and datapagamento between ? and ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			quantPromQuit = 0;
			
			while (rs.next()) {
				quantPromQuit = rs.getInt("quant_prom_quit");
			}
			
		} catch (Exception e) {
			
			quantPromQuit = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return quantPromQuit;
		
	}
	
	public Integer findQuantPromVenc(String date) throws PersistenceException {
		
		Integer quantPromVenc;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select count(*) as quant_prom_venc from contasreceber "
					   + "where (codTipo = 4 or codTipo = 8) and datavencimento > ? and datavencimento < date(now()) and datapagamento = null";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, date);
			
			ResultSet rs = ps.executeQuery();
			
			quantPromVenc = 0;
			
			while (rs.next()) {
				quantPromVenc = rs.getInt("quant_prom_venc");
			}
			
		} catch (Exception e) {
			
			quantPromVenc = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return quantPromVenc;
		
	}
	
}
