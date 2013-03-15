package entity;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import service.serializer.TimeDeserializer;
import service.serializer.TimeSerializer;
import service.serializer.WeekdayDeserializer;
import service.serializer.WeekdaySerializer;

@JsonPropertyOrder({ "id", "weekday", "time", "route" })
public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Weekday weekday;

	private Date time;

	private Route route;

	public Schedule() {}

	public Schedule(Route route, int weekday, Date time) {
		this.route = route;
		this.weekday = Weekday.valueOf(weekday);
		this.time = time;
	}

	@JsonSerialize(include = Inclusion.NON_NULL)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonSerialize(using = WeekdaySerializer.class, include = NON_NULL)
	public Weekday getWeekday() {
		return weekday;
	}

	@JsonDeserialize(using = WeekdayDeserializer.class)
	public void setWeekday(int weekday) {
		this.weekday = Weekday.valueOf(weekday);
	}

	@JsonSerialize(using = TimeSerializer.class, include = NON_NULL)
	public Date getTime() {
		return time;
	}

	@JsonDeserialize(using = TimeDeserializer.class)
	public void setTime(Date time) {
		this.time = time;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((route == null) ? 0 : route.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((weekday == null) ? 0 : weekday.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (route == null) {
			if (other.route != null)
				return false;
		} else if (!route.equals(other.route))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (weekday != other.weekday)
			return false;
		return true;
	}

}
