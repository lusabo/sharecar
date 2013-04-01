package entity;

import java.util.Calendar;

public enum Weekday {

	DOMINGO(Calendar.SUNDAY), SEGUNDA(Calendar.MONDAY), TERÇA(Calendar.TUESDAY), QUARTA(Calendar.WEDNESDAY), QUINTA(
			Calendar.THURSDAY), SEXTA(Calendar.FRIDAY), SÁBADO(Calendar.SATURDAY);

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
