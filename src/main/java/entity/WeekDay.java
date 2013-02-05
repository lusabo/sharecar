package entity;

import java.util.Calendar;

public enum WeekDay {

	SUNDAY(Calendar.SUNDAY), MONDAY(Calendar.MONDAY), TUESDAY(Calendar.TUESDAY), WEDNESDAY(Calendar.WEDNESDAY), THURSDAY(
			Calendar.THURSDAY), FRIDAY(Calendar.FRIDAY), SATURDAY(Calendar.SATURDAY);

	private int valor;

	private WeekDay(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static WeekDay valueOf(int valor) {
		return WeekDay.values()[valor - 1];
	}
}
