package entity;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonPropertyOrder({ "id", "description", "user", "coords" })
public class Route implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private User user;

	private List<Coordinate> coords;

	private Set<Schedule> periodos;

	public Route() {
	}

	public Route(String description, List<Coordinate> coords, User user) {
		this.description = description;
		this.coords = coords;
		this.user = user;
	}

	@JsonSerialize(include = Inclusion.NON_NULL)
	public Integer getId() {
		return id;
	}

	// @JsonIgnore
	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonSerialize(include = NON_NULL)
	public Set<Schedule> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Set<Schedule> periodos) {
		this.periodos = periodos;
	}

	@JsonSerialize(include = NON_NULL)
	public List<Coordinate> getCoords() {
		return coords;
	}

	public void setCoords(List<Coordinate> coords) {
		this.coords = coords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Route other = (Route) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
