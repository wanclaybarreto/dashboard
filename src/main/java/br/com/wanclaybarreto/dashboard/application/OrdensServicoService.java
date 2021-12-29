package br.com.wanclaybarreto.dashboard.application;

import java.util.List;

import br.com.wanclaybarreto.dashboard.domain.OrdensServico;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao.OrdensServicoDAO;
import br.com.wanclaybarreto.dashboard.util.DateUtils;
import br.com.wanclaybarreto.dashboard.util.PeriodUtils;

public class OrdensServicoService {
	
	public static List<OrdensServico> getListOrdensServicoByDtEmissao(PeriodUtils period, String startDate, String endDate) {
		
		List<OrdensServico> listaOrdensServicoPorDtEmissao;
		
		try {
			
			OrdensServicoDAO osDAO = new OrdensServicoDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			listaOrdensServicoPorDtEmissao = osDAO.listOrdensServicoByDtEmissao(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			listaOrdensServicoPorDtEmissao = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return listaOrdensServicoPorDtEmissao;
	}
	
	public static List<OrdensServico> getListOrdensServicoByDtFechamento(PeriodUtils period, String startDate, String endDate) {
		
		List<OrdensServico> listaOrdensServicoPorDtFechamento;
		
		try {
			
			OrdensServicoDAO osDAO = new OrdensServicoDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			listaOrdensServicoPorDtFechamento = osDAO.listOrdensServicoByDtFechamento(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			listaOrdensServicoPorDtFechamento = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return listaOrdensServicoPorDtFechamento;
	}
	
}
