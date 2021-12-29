package br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.wanclaybarreto.dashboard.application.PersistenceException;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.connection.ConnectionJdbc;

public class ContasDAO {
	
	public Integer findQuantContasRecebidas(String startDate, String endDate) throws PersistenceException {
		
		Integer quantContasRecebidas;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select count(*) as quant_contas_recebidas from contasreceber where datapagamento between ? and ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			quantContasRecebidas = 0;
			
			while (rs.next()) {
				quantContasRecebidas = rs.getInt("quant_contas_recebidas");
			}
			
			if (quantContasRecebidas == 0) quantContasRecebidas = null;
			
		} catch (Exception e) {
			
			quantContasRecebidas = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return quantContasRecebidas;
		
	}
	
	public BigDecimal findValorContasRecebidas(String startDate, String endDate) throws PersistenceException {
		
		BigDecimal valorContasRecebidas;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select sum(valorpago - troco) as valor_contas_recebidas from contasreceber where datapagamento between ? and ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			valorContasRecebidas = new BigDecimal("0.00");
			
			while (rs.next()) {
				valorContasRecebidas = rs.getBigDecimal("valor_contas_recebidas");
			}
			
		} catch (Exception e) {
			
			valorContasRecebidas = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return valorContasRecebidas;
		
	}
	
	public Integer findQuantContasPagas(String startDate, String endDate) throws PersistenceException {
		
		Integer quantContasPagas;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select count(*) as quant_contas_pagas from contaspagar where datapagamento between ? and ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			quantContasPagas = 0;
			
			while (rs.next()) {
				quantContasPagas = rs.getInt("quant_contas_pagas");
			}
			
			if (quantContasPagas == 0) quantContasPagas = null;
			
		} catch (Exception e) {
			
			quantContasPagas = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return quantContasPagas;
		
	}
	
	public BigDecimal findValorContasPagas(String startDate, String endDate) throws PersistenceException {
		
		BigDecimal valorContasPagas;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select sum(valorpago - troco) as valor_contas_pagas from contaspagar where datapagamento between ? and ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			valorContasPagas = new BigDecimal("0.00");
			
			while (rs.next()) {
				valorContasPagas = rs.getBigDecimal("valor_contas_pagas");
			}
			
		} catch (Exception e) {
			
			valorContasPagas = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return valorContasPagas;
		
	}
	
}
