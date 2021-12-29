package br.com.wanclaybarreto.dashboard.application;

import br.com.wanclaybarreto.dashboard.infrastructure.model.jdbc.dao.FiltroDAO;

public abstract class FiltroService {
	
	public static int getIndexFilter() {
		
		int index = 0;
		
		try {
			
			FiltroDAO fDAO = new FiltroDAO();
			
			index = fDAO.findIndexFilter();
			
		} catch (PersistenceException e) {
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		return index;
	}
	
	public static String setIndexFilter(int index) {
		
		String msg = null;
		
		try {
			
			FiltroDAO fDAO = new FiltroDAO();
			
			fDAO.saveIndexFilter(index);
			
		} catch (PersistenceException e) {
			
			msg = "Erro: " + e.getMessage();
			
			e.printStackTrace();
			System.out.println("\n\n" + e.getMessage());
			
		}
		
		return msg;
		
	}
	
}
