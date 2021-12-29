package br.com.wanclaybarreto.dashboard.util;

/**
 * Enum criado para representar per�odos. O filtro de per�odo de busca do dasboard s�o
 * baseados neste Enum. PARA ADICIONAR OU EDITAR OS ESSES FILTROS, ALTERE APENAS ESTE ENUM e 
 * o m�todo getDateByPeriod da classe DateUtils.
 * Dessa forma, as altera��es ser�o refletidas no dashboard. <br/> <br/>
 * 
 * Obs.: deixe sempre o elemento None na �ltima posi��o; se for adicionar novos elementos (per�odos),
 * nunca os adicione depois do elemento None e, lembre-se de reorganizar os �ndices.
 * 
 */
public enum PeriodUtils {
	
	Day(1, "Dia atual"),
	Week(2, "Semana atual"),
	Month(3, "M�s atual"),
	FirstSemester(4, "1� semestre do ano atual"),
	SecondSemester(5, "2� semestre do ano atual"),
	Year(6, "Ano atual"),
	None(7, "Especiicar per�odo");
	
	private int idx;
	private String desc;
	
	private PeriodUtils(int index, String description) {
		idx = index;
		desc = description;
	}
	
	public int index() {
		return idx;
	}
	
	public String description() {
		return desc;
	}
	
	public static PeriodUtils period(int i) {
		switch (i) {
			case 1:
				return PeriodUtils.Day;
			case 2:
				return PeriodUtils.Week;
			case 3:
				return PeriodUtils.Month;
			case 4: 
				return PeriodUtils.FirstSemester;
			case 5: 
				return PeriodUtils.SecondSemester;
			case 6:
				return PeriodUtils.Year;
			default:
				return PeriodUtils.None;
		}
	}
	
}
