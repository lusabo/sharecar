package entity;

import java.util.Date;

public class Schedule {

	private Integer id;

	private WeekDay weekDay;

	private Date time;

	private Route route;

	public Schedule() {
	}

	public Schedule(WeekDay weekDay, Date time) {
		this.weekDay = weekDay;
		this.time = time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WeekDay getDiaSemana() {
		return weekDay;
	}

	public void setDiaSemana(WeekDay diaSemana) {
		this.weekDay = diaSemana;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((weekDay == null) ? 0 : weekDay.hashCode());
		result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
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
		Schedule other = (Schedule) obj;
		if (weekDay != other.weekDay)
			return false;
		if (getTime() == null) {
			if (other.getTime() != null)
				return false;
		} else if (!getTime().equals(other.getTime()))
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		return true;
	}
}
