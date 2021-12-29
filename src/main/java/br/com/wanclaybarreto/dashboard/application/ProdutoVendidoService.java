package br.com.wanclaybarreto.dashboard.application;

import java.util.List;

import br.com.wanclaybarreto.dashboard.domain.ProdutoVendido;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao.ProdutoVendidoDAO;
import br.com.wanclaybarreto.dashboard.util.DateUtils;
import br.com.wanclaybarreto.dashboard.util.PeriodUtils;

public abstract class ProdutoVendidoService {
	
	public static List<ProdutoVendido> getListProdutosMaisVendidos(PeriodUtils period, String startDate, String endDate) {
		
		List<ProdutoVendido> listaProdutosMaisVendidos;
		
		try {
			
			ProdutoVendidoDAO pvDAO = new ProdutoVendidoDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			listaProdutosMaisVendidos = pvDAO.listProdutosMaisVendidos(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			listaProdutosMaisVendidos = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return listaProdutosMaisVendidos;
	}
	
}
