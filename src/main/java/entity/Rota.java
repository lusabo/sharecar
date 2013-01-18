package entity;

import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.LineString;

@Entity
public class Rota {

	private Integer id;

	private Usuario usuario;

	private Set<Periodo> periodos;

	private String descricao;

	private LineString caminho;

	public Rota() {
	}

	public Rota(Integer id, Usuario usuario, String descricao, LineString lineString) {
		this.id = id;
		this.usuario = usuario;
		this.descricao = descricao;
		this.caminho = lineString;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Embedded
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(orphanRemoval = true, mappedBy = "rota", fetch = FetchType.EAGER)
	public Set<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Set<Periodo> periodos) {
		this.periodos = periodos;
	}

	@Type(type = "org.hibernate.spatial.GeometryType")
	public LineString getCaminho() {
		return caminho;
	}

	public void setCaminho(LineString caminho) {
		this.caminho = caminho;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		Rota other = (Rota) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

}
