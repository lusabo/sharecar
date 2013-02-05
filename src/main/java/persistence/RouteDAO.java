package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.sql.DataSource;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import entity.Coordenada;
import entity.Route;

@RequestScoped
@PersistenceController
public class RouteDAO {

	@Inject
	private DataSource dataSource;

	public void insert(Route rota) {
		Connection connection;
		try {
			connection = dataSource.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("insert into routes (id, description, username, geom) values (?,?,?,geomfromtext(?))");
			pstmt.setInt(1, rota.getId());
			pstmt.setString(2, rota.getDescription());
			pstmt.setString(3, rota.getUsuario().getUsername());
			pstmt.setString(4, parse(rota.getCaminho()));
			pstmt.execute();
			pstmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
		for(Coordenada coordenada : coordenadas) {
			if(count != 0) {
				geometria += ", ";
			}
			geometria += coordenada.getLatitude() + " " + coordenada.getLongitude();
			count++;
		}
		geometria += ")";
		return geometria;
	}
}
