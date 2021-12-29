package br.com.wanclaybarreto.dashboard.application;

import java.math.BigDecimal;
import java.util.List;

import br.com.wanclaybarreto.dashboard.domain.Vendas;
import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao.VendasDAO;
import br.com.wanclaybarreto.dashboard.util.DateUtils;
import br.com.wanclaybarreto.dashboard.util.PeriodUtils;

public abstract class VendasService {
	
	public static List<Vendas> getListVendasByOperador(PeriodUtils period, String startDate, String endDate) {
		
		List<Vendas> listaVendasPorOperador;
		
		try {
			
			VendasDAO vDAO = new VendasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			listaVendasPorOperador = vDAO.listVendasByOperador(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			listaVendasPorOperador = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return listaVendasPorOperador;
	}
	
	public static List<Vendas> getListVendasByVendedor(PeriodUtils period, String startDate, String endDate) {
		
		List<Vendas> listaVendasPorVendedor;
		
		try {
			
			VendasDAO vDAO = new VendasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			listaVendasPorVendedor = vDAO.listVendasByVendedor(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			listaVendasPorVendedor = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return listaVendasPorVendedor;
	}
	
	public static List<Vendas> getListVendasByCliente(PeriodUtils period, String startDate, String endDate) {
		
		List<Vendas> listaVendasPorCliente;
		
		try {
			
			VendasDAO vDAO = new VendasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			listaVendasPorCliente = vDAO.listVendasByCliente(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			listaVendasPorCliente = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return listaVendasPorCliente;
	}
	
	public static List<Vendas> getListVendasByFormaPagamento(PeriodUtils period, String startDate, String endDate) {
		
		List<Vendas> listaVendasPorFormaPagamento;
		
		try {
			
			VendasDAO vDAO = new VendasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			listaVendasPorFormaPagamento = vDAO.listVendasByFormaPagamento(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			listaVendasPorFormaPagamento = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return listaVendasPorFormaPagamento;
	}
	
	public static List<Vendas> getListVendasByHoraPico(PeriodUtils period, String startDate, String endDate) {
		
		List<Vendas> listaVendasPorHoraPico;
		
		try {
			
			VendasDAO vDAO = new VendasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			listaVendasPorHoraPico = vDAO.listVendasByHoraPico(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			listaVendasPorHoraPico = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return listaVendasPorHoraPico;
	}
	
	public static BigDecimal getValorVendas(PeriodUtils period, String startDate, String endDate) {
		
		BigDecimal valorVendas;
		
		try {
			
			VendasDAO vDAO = new VendasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			valorVendas = vDAO.findValorVendas(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			valorVendas = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return valorVendas;
	}
	
	public static Integer getQuantVendas(PeriodUtils period, String startDate, String endDate) {
		
		Integer quantVendas;
		
		try {
			
			VendasDAO vDAO = new VendasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			quantVendas = vDAO.findQuantVendas(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			quantVendas = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return quantVendas;
	}
	
	public static Vendas getVendasVendedorDestaque(PeriodUtils period, String startDate, String endDate) {
		
		Vendas vendas;
		
		try {
			
			VendasDAO vDAO = new VendasDAO();
			
			String[] dates = DateUtils.getDateByPeriod(period, startDate, endDate);
			
			vendas = vDAO.findVendasVendedorDestaque(dates[0], dates[1]);
			
		} catch (PersistenceException e) {
			
			vendas = null;
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		
		return vendas;
	}
	
}
