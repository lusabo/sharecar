package entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

//@MappedSuperclass
// @Tuplizer(impl = XX.class)
public class Route implements Serializable {

	private static final long serialVersionUID = 1L;

	// @Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	// @Embedded
	private Usuario usuario;

	// @OneToMany(orphanRemoval = true, mappedBy = "rota", fetch = FetchType.EAGER)
	private Set<Periodo> periodos;

	private String descricao;

	// @Tuplizer(impl = X.class)
	// @Transient
	private List<Coordenada> caminho;

	// @Tuplizer(impl = X.class)
	private String x;

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public Route() {
	}

	public Route(Integer id, Usuario usuario, String descricao, List<Coordenada> caminho) {
		this.id = id;
		this.usuario = usuario;
		this.descricao = descricao;
		this.caminho = caminho;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Set<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(Set<Periodo> periodos) {
		this.periodos = periodos;
	}

	public List<Coordenada> getCaminho() {
		return caminho;
	}

	public void setCaminho(List<Coordenada> caminho) {
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
		Route other = (Route) obj;
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
