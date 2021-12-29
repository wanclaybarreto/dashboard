package br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.wanclaybarreto.dashboard.application.PersistenceException;
import br.com.wanclaybarreto.dashboard.domain.ProdutoVendido;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.connection.ConnectionJdbc;

public class ProdutoVendidoDAO {
	
	public List<ProdutoVendido> listProdutosMaisVendidos(String startDate, String endDate) throws PersistenceException {
		
		List<ProdutoVendido> listaProdutosMaisVendidos;
		
		try (Connection con = ConnectionJdbc.getInstance().getConnection()) {
			
			String sql = "select\r\n"
					   + "    itensvenda.id_item as id_item,\r\n"
					   + "    itensvenda.nome as nome,\r\n"
					   + "    sum(itensvenda.quantidade) as quantidadevenda ,\r\n"
					   + "    sum(itensvenda.acrescimo) as acrescimo,\r\n"
					   + "    sum(valortotal) as valortotal,\r\n"
					   + "    sum(itensvenda.desconto) as  desconto,\r\n"
					   + "    ( sum(valortotal) ) + ( sum(itensvenda.acrescimo) ) - ( sum(itensvenda.desconto) ) as subtotal\r\n"
					   + "from\r\n"
					   + "    itensvenda\r\n"
					   + "    inner join produto on produto.codigo = itensvenda.id_item\r\n"
					   + "where\r\n"
					   + "    itensvenda.datavenda between ? and ?\r\n"
					   + "group by\r\n"
					   + "    id_item\r\n"
					   + "order by\r\n"
					   + "    quantidadevenda desc\r\n"
					   + "limit 5";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			
			ResultSet rs = ps.executeQuery();
			
			listaProdutosMaisVendidos = new ArrayList<ProdutoVendido>();
			
			BigDecimal somaSubtotal = new BigDecimal("0.00");
			
			while (rs.next()) {
				ProdutoVendido pv = new ProdutoVendido(rs.getInt("id_item"), rs.getString("nome"),
													   rs.getBigDecimal("quantidadevenda"), rs.getBigDecimal("acrescimo"),
													   rs.getBigDecimal("valortotal"), rs.getBigDecimal("desconto"),
													   rs.getBigDecimal("subtotal"));
				
				listaProdutosMaisVendidos.add(pv);
				
				somaSubtotal = somaSubtotal.add(rs.getBigDecimal("subtotal"));
			}
			
			for (ProdutoVendido pv : listaProdutosMaisVendidos) {
				pv.setPercentualSubtotal((pv.getSubtotal().multiply(new BigDecimal("100.00"))).divide(somaSubtotal, 2, RoundingMode.HALF_UP));
			}
			
		} catch (Exception e) {
			
			listaProdutosMaisVendidos = null;
			
			e.printStackTrace();
			throw new PersistenceException(e.getMessage(), e);
			
		}
		
		return listaProdutosMaisVendidos;
	}
	
}
