package persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import entity.Coordinate;
import entity.Route;
import entity.User;

@PersistenceController
public class RouteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	// @Name("default")
	private Connection connection;

	//@Transactional
	public void insert(Route rota) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into routes (description, username, geom) ");
		sql.append("values (?, ?, geomfromtext(?, 4326))");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());

		pstmt.setString(1, rota.getDescription());
		pstmt.setString(2, rota.getUser().getUsername());
		pstmt.setString(3, parse(rota.getCoords()));

		pstmt.execute();
		pstmt.close();
	}

	public List<Route> find(Coordinate coordenada) throws Exception {
		return null;
	}

	public List<Route> findAll() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, description from routes");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();
		List<Route> result = new ArrayList<Route>();

		while (rs.next()) {
			Route route = new Route();

			route.setId(rs.getInt("id"));
			route.setDescription(rs.getString("description"));

			result.add(route);
		}
		rs.close();
		pstmt.close();

		return result;
	}

	public List<Route> find(Coordinate coord, Integer radius) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, description, username from routes ");
		sql.append("where intersects(geom,buffer(geomfromtext('POINT(" + coord.getLatitude() + " " + coord.getLongitude() + ")',4326),round(((? * 0.00001)/1.11),5),8))");
		PreparedStatement pstmt = connection.prepareStatement(sql.toString());

		pstmt.setInt(1, radius);

		ResultSet rs = pstmt.executeQuery();
		List<Route> result = new ArrayList<Route>();

		while (rs.next()) {
			Route route = new Route();

			route.setId(rs.getInt("id"));
			route.setDescription(rs.getString("description"));
			route.setUser(new User(rs.getString("username")));

			result.add(route);
		}
		rs.close();
		pstmt.close();

		return result;
	}

	public Route load(Integer id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, description, username, ST_AsGeoJSON(geom) as json ");
		sql.append("from routes where id = ?");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());

		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();
		Route result = null;

		if (rs.next()) {
			result = new Route();

			result.setId(rs.getInt("id"));
			result.setDescription(rs.getString("description"));
			result.setUser(new User(rs.getString("username")));
			result.setCoords(parse(rs.getString("json")));
		}

		rs.close();
		pstmt.close();

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
	private List<Coordinate> parse(String json) throws Exception {
		Map<String, Object> map = new ObjectMapper().readValue(json, Map.class);
		List<Coordinate> result = new ArrayList<Coordinate>();

		for (List<Double> coords : (List<List<Double>>) map.get("coordinates")) {
			result.add(new Coordinate(coords.get(0), coords.get(1)));
		}

		return result;
	}
}
