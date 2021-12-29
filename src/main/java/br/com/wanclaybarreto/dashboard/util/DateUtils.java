package br.com.wanclaybarreto.dashboard.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe de utilidades para datas. <br/>
 * Contém métodos que retornam datas do dia atual, semana atual, mês atual, prmeiro semestre
 * do ano atual, segundo semestre do ano atual e ano atual.
 * 
 */
public abstract class DateUtils {
	
	public static String[] getDateByPeriod(PeriodUtils period, String startDate, String endDate) {
		
		if (period == PeriodUtils.Day) {
			
			String date = DateUtils.getCurrentDay();
			return new String[] {date, date};
			
		} else if (period == PeriodUtils.Week) {
			
			String[] dates = DateUtils.getCurrentWeek();
			return dates;
			
		} else if (period == PeriodUtils.Month) {
			
			String[] dates = DateUtils.getCurrentMonth();
			return dates;
			
		} else if (period == PeriodUtils.FirstSemester) {
			
			String[] dates = DateUtils.getFirstSemester();
			return dates;
			
		} else if (period == PeriodUtils.SecondSemester) {
			
			String[] dates = DateUtils.getSecondSemester();
			return dates;
			
		} else if (period == PeriodUtils.Year) {
			
			String[] dates = DateUtils.getCurrentYear();
			return dates;
			
		} else {
			
			return new String[] {startDate, endDate};
			
		}
		
	}
	
	public static String getCurrentDay() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		return dateFormat.format(calendar.getTime());
	}
	
	public static String[] getCurrentWeek() {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar calendarAux = GregorianCalendar.getInstance();
		calendarAux.setTime(new Date());
		
		if (calendarAux.get(Calendar.DAY_OF_WEEK) == 1) {
			calendar.add(Calendar.DATE, -7);
		}
		
		String startDate = dateFormat.format(calendar.getTime());
		
		calendar.add(Calendar.DATE, 6);
		
		String endDate = dateFormat.format(calendar.getTime());
		
		return new String [] {startDate, endDate};
	}
	
	public static String[] getCurrentMonth() {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String startDate = dateFormat.format(calendar.getTime());
		
		//Adiciona um mês
		calendar.add(2, 1);
		
		//Remove um dia
		calendar.add(Calendar.DATE, -1);
		
		String endDate = dateFormat.format(calendar.getTime());
		
		return new String [] {startDate, endDate};
	}
	
	public static String[] getFirstSemester() {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String startDate = dateFormat.format(calendar.getTime());
		
		//Adiciona seis meses
		calendar.add(2, 6);
		
		//Remove um dia
		calendar.add(Calendar.DATE, -1);
		
		String endDate = dateFormat.format(calendar.getTime());
		
		return new String [] {startDate, endDate};
	}
	
	public static String[] getSecondSemester() {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		//Adiciona seis meses
		calendar.add(2, 6);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String startDate = dateFormat.format(calendar.getTime());
		
		//Adiciona seis meses
		calendar.add(2, 6);
		
		//Remove um dia
		calendar.add(Calendar.DATE, -1);
		
		String endDate = dateFormat.format(calendar.getTime());
		
		return new String [] {startDate, endDate};
	}
	
	public static String[] getCurrentYear() {
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, 1);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String startDate = dateFormat.format(calendar.getTime());
		
		//Adiciona doze meses
		calendar.add(2, 12);
		
		//Remove um dia
		calendar.add(Calendar.DATE, -1);
		
		String endDate = dateFormat.format(calendar.getTime());
		
		return new String [] {startDate, endDate};
	}
	
}
