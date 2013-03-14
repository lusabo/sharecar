package entity;

import java.util.Calendar;

public enum Weekday {

	SUNDAY(Calendar.SUNDAY), MONDAY(Calendar.MONDAY), TUESDAY(Calendar.TUESDAY), WEDNESDAY(Calendar.WEDNESDAY), THURSDAY(
			Calendar.THURSDAY), FRIDAY(Calendar.FRIDAY), SATURDAY(Calendar.SATURDAY);

	private int valor;

	private Weekday(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static Weekday valueOf(int valor) {
		return Weekday.values()[valor - 1];
	}
}
