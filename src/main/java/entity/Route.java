package entity;

import static org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_NULL;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonPropertyOrder({ "id", "description", "coords", "user" })
public class Route implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonSerialize(include = NON_NULL)
	private Integer id;

	private String description;

	private List<Coordenada> coords;

	private Set<Periodo> periodos;

	private Usuario user;

	public Route() {
	}

	public Route(String description, List<Coordenada> coords, Usuario user) {
		this.description = description;
		this.coords = coords;
		this.user = user;
	}

	@JsonSerialize(include = Inclusion.NON_NULL)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return user;
	}

	public void setUsuario(Usuario usuario) {
		this.user = usuario;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonSerialize(include = NON_NULL)
	public Set<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Set<Periodo> periodos) {
		this.periodos = periodos;
	}

	public List<Coordenada> getCaminho() {
		return coords;
	}

	public void setCaminho(List<Coordenada> caminho) {
		this.coords = caminho;
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
