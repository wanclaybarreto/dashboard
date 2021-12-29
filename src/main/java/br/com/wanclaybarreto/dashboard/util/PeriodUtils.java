package br.com.wanclaybarreto.dashboard.util;

/**
 * Enum criado para representar períodos. O filtro de período de busca do dasboard são
 * baseados neste Enum. PARA ADICIONAR OU EDITAR OS ESSES FILTROS, ALTERE APENAS ESTE ENUM e 
 * o método getDateByPeriod da classe DateUtils.
 * Dessa forma, as alterações serão refletidas no dashboard. <br/> <br/>
 * 
 * Obs.: deixe sempre o elemento None na última posição; se for adicionar novos elementos (períodos),
 * nunca os adicione depois do elemento None e, lembre-se de reorganizar os índices.
 * 
 */
public enum PeriodUtils {
	
	Day(1, "Dia atual"),
	Week(2, "Semana atual"),
	Month(3, "Mês atual"),
	FirstSemester(4, "1º semestre do ano atual"),
	SecondSemester(5, "2º semestre do ano atual"),
	Year(6, "Ano atual"),
	None(7, "Especiicar período");
	
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
