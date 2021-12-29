package br.com.wanclaybarreto.dashboard.application;

import java.math.BigDecimal;

import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao.ContasDAO;
import br.com.wanclaybarreto.dashboard.util.DateUtils;
import br.com.wanclaybarreto.dashboard.util.PeriodUtils;

public abstract class ContasService {
	
	public static Integer getQuantContasRecebidas(PeriodUtils period, String startDate, String endDate) {
		
		Integer quantContasRecebidas;
		
		try {
			
			ContasDAO cDAO = new ContasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			quantContasRecebidas = cDAO.findQuantContasRecebidas(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			quantContasRecebidas = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return quantContasRecebidas;
	}
	
	public static BigDecimal getValorContasRecebidas(PeriodUtils period, String startDate, String endDate) {
		
		BigDecimal valorContasRecebidas;
		
		try {
			
			ContasDAO vDAO = new ContasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			valorContasRecebidas = vDAO.findValorContasRecebidas(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			valorContasRecebidas = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return valorContasRecebidas;
	}
	
	public static Integer getQuantContasPagas(PeriodUtils period, String startDate, String endDate) {
		
		Integer quantContasPagas;
		
		try {
			
			ContasDAO cDAO = new ContasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			quantContasPagas = cDAO.findQuantContasPagas(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			quantContasPagas = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return quantContasPagas;
	}
	
	public static BigDecimal getValorContasPagas(PeriodUtils period, String startDate, String endDate) {
		
		BigDecimal valorContasPagas;
		
		try {
			
			ContasDAO vDAO = new ContasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			valorContasPagas = vDAO.findValorContasPagas(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			valorContasPagas = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return valorContasPagas;
	}
	
}
