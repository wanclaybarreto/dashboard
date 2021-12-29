package br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.wanclaybarreto.dashboard.application.PersistenceException;
import br.com.wanclaybarreto.dashboard.domain.OrdensServico;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.connection.ConnectionJdbc;

public class OrdensServicoDAO {
	
	public List<OrdensServico> listOrdensServicoByDtEmissao(String startDate, String endDate) throws PersistenceException {
		
		List<OrdensServico> listaOrdensServicoPorDtEmissao;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select\r\n"
					   + "    status, sum(valortotal_av) as valor_av, sum(valortotal_ap) as valor_ap, sum(valorfinal) as valor_final, count(status) as quant\r\n"
					   + "from\r\n"
					   + "    os2\r\n"
					   + "where\r\n"
					   + "    dataemissao between ? and ?\r\n"
					   + "group\r\n"
					   + "    by status";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			listaOrdensServicoPorDtEmissao = new ArrayList<OrdensServico>();
			
			while (rs.next()) {
				OrdensServico ordensServico = new OrdensServico(rs.getString("status"),
																rs.getBigDecimal("valor_av"), rs.getBigDecimal("valor_ap"),
																rs.getBigDecimal("valor_final"),
																rs.getInt("quant"));
				
				ordensServico.formatStatus();
				
				listaOrdensServicoPorDtEmissao.add(ordensServico);
			}
			
		} catch (Exception e) {
			
			listaOrdensServicoPorDtEmissao = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return listaOrdensServicoPorDtEmissao;
		
	}
	
	public List<OrdensServico> listOrdensServicoByDtFechamento(String startDate, String endDate) throws PersistenceException {
		
		List<OrdensServico> listaOrdensServicoPorDtFechamento;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select\r\n"
					   + "    status, sum(valortotal_av) as valor_av, sum(valortotal_ap) as valor_ap, sum(valorfinal) as valor_final, count(status) as quant\r\n"
					   + "from\r\n"
					   + "    os2\r\n"
					   + "where\r\n"
					   + "    datafechamento between ? and ?\r\n"
					   + "group\r\n"
					   + "    by status";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			listaOrdensServicoPorDtFechamento = new ArrayList<OrdensServico>();
			
			while (rs.next()) {
				OrdensServico ordensServico = new OrdensServico(rs.getString("status"),
																rs.getBigDecimal("valor_av"), rs.getBigDecimal("valor_ap"),
																rs.getBigDecimal("valor_final"),
																rs.getInt("quant"));
				
				ordensServico.formatStatus();
				
				listaOrdensServicoPorDtFechamento.add(ordensServico);
			}
			
		} catch (Exception e) {
			
			listaOrdensServicoPorDtFechamento = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return listaOrdensServicoPorDtFechamento;
		
	}
	
}
