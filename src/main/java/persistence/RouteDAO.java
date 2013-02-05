package persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Beans;
import entity.Coordinate;
import entity.Route;
import entity.User;

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

	public List<Route> find(Coordinate coordenada) {
		return null;
	}

	public Route load(Integer id) {
		Route result = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select id, description, username, ST_AsGeoJSON(geom) as json ");
			sql.append("from routes where id = ?");

			PreparedStatement pstmt = getConnection().prepareStatement(sql.toString());

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				result = new Route();

				result.setId(rs.getInt("id"));
				result.setDescription(rs.getString("description"));
				result.setUser(new User(rs.getString("username")));
				result.setCoords(parse(rs.getString("json")));
			}

			rs.close();
			pstmt.close();

		} catch (SQLException cause) {
			throw new RuntimeException(cause);
		}

		return result;
	}

	private String parse(List<Coordinate> coords) {
		String geometria = "LINESTRING(";
		int count = 0;
		for (Coordinate coordenada : coords) {
			if (count != 0) {
				geometria += ", ";
			}
			geometria += coordenada.getLatitude() + " " + coordenada.getLongitude();
			count++;
		}
		geometria += ")";
		return geometria;
	}

	@SuppressWarnings("unchecked")
	private List<Coordinate> parse(String json) {
		List<Coordinate> result = new ArrayList<Coordinate>();

		try {
			Map<String, Object> map = new ObjectMapper().readValue(json, Map.class);

			for (List<Double> coords : (List<List<Double>>) map.get("coordinates")) {
				result.add(new Coordinate(coords.get(0), coords.get(1)));
			}

		} catch (Exception cause) {
			throw new RuntimeException(cause);
		}

		return result;
	}

	private Connection getConnection() {
		if (this.connection == null) {
			this.connection = Beans.getReference(Connection.class);
		}

		return this.connection;
	}
}
