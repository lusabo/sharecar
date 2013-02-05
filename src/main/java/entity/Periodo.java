package entity;

import java.util.Date;

//@Entity
public class Periodo {

	// @Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	// @NotNull
	private DiaSemana diaSemana;

	// @Temporal(TemporalType.TIME)
	// @NotNull
	private Date hora;

	// @ManyToOne(optional = false)
	// @JoinColumn(name = "id_rota", referencedColumnName = "id")
	// @ForeignKey(name = "rota_periodo_fk")
	private Route rota;

	public Periodo() {
	}

	public Periodo(DiaSemana diaSemana, Date hora) {
		this.diaSemana = diaSemana;
		this.hora = hora;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public Route getRota() {
		return rota;
	}

	public void setRota(Route rota) {
		this.rota = rota;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((diaSemana == null) ? 0 : diaSemana.hashCode());
		result = prime * result + ((getHora() == null) ? 0 : getHora().hashCode());
		result = prime * result + ((rota == null) ? 0 : rota.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Periodo other = (Periodo) obj;
		if (diaSemana != other.diaSemana)
			return false;
		if (getHora() == null) {
			if (other.getHora() != null)
				return false;
		} else if (!getHora().equals(other.getHora()))
			return false;
		if (rota == null) {
			if (other.rota != null)
				return false;
		} else if (!rota.equals(other.rota))
			return false;
		return true;
	}

}
