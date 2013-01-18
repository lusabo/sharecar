package entity;

public enum DiaSemana {

	DOMINGO(1),	SEGUNDA(2),	TERCA(3), QUARTA(4), QUINTA(5),	SEXTA(6), SABADO(7);

	private int valor;

	private DiaSemana(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}

	public static DiaSemana valueOf(int valor) {
		return DiaSemana.values()[valor - 1];
	}
}

