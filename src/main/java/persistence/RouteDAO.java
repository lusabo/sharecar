package persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.RequestScoped;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Beans;
import entity.Coordenada;
import entity.Route;

@RequestScoped
@PersistenceController
public class RouteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient Connection connection;

	@Transactional
	public void insert(Route rota) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into routes (description, username, geom) ");
			sql.append("values (?, ?, geomfromtext(?))");

			PreparedStatement pstmt = getConnection().prepareStatement(sql.toString());

			pstmt.setString(1, rota.getDescription());
			pstmt.setString(2, rota.getUser().getUsername());
			pstmt.setString(3, parse(rota.getCoords()));

			pstmt.execute();
			pstmt.close();

		} catch (SQLException cause) {
			throw new RuntimeException(cause);
		}
	}

	public List<Route> find(Coordenada coordenada) {
		return null;
	}

	public Route load(Integer id) {
		return null;
	}

	private String parse(List<Coordenada> coordenadas) {
		String geometria = "LINESTRING(";
		int count = 0;
		for (Coordenada coordenada : coordenadas) {
			if (count != 0) {
				geometria += ", ";
			}
			geometria += coordenada.getLatitude() + " " + coordenada.getLongitude();
			count++;
		}
		geometria += ")";
		return geometria;
	}

	private Connection getConnection() {
		if (this.connection == null) {
			this.connection = Beans.getReference(Connection.class);
		}

		return this.connection;
	}
}
