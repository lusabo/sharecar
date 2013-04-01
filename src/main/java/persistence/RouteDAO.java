package persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.map.ObjectMapper;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import business.UserBC;
import entity.Coordinate;
import entity.Route;
import entity.User;
import entity.Weekday;

@PersistenceController
public class RouteDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Connection connection;
	
	@Inject
	private UserBC userBC;

	@Transactional
	public void insert(Route rota, User user) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into routes (description, username, geom) ");
		sql.append("values (?, ?, geomfromtext(?, 4326))");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setString(1, rota.getDescription());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, parse(rota.getCoords()));

		pstmt.execute();
		pstmt.close();
	}

	public List<Route> find(Coordinate coordenada) throws Exception {
		return null;
	}

	public List<Route> find(User user) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, description from routes ");
		sql.append("where username = ? ");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setString(1, user.getName());

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

		public List<Route> find(Coordinate coord, Integer radius, User user, Weekday weekday, Time hourini, Time hourend) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct routes.id, routes.description, routes.username ");
		sql.append("from routes inner join schedules on (routes.id = schedules.route_id) ");
		sql.append("where schedules.hour between ? and ? ");
		sql.append("and schedules.weekday = ? ");
		sql.append("and intersects(geom,buffer(geomfromtext('POINT(" + coord.getLatitude() + " " + coord.getLongitude() + ")',4326),round(((? * 0.00001)/1.11),5),8))");

		//sql.append("and username != ? and ");
		
		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setTime(1, hourini);
		pstmt.setTime(2, hourend);
		pstmt.setInt(3, weekday.getValor());
		pstmt.setInt(4, radius);

		//pstmt.setString(5, user.getUsername());

		ResultSet rs = pstmt.executeQuery();
		List<Route> result = new ArrayList<Route>();

		while (rs.next()) {
			Route route = new Route();

			route.setId(rs.getInt("id"));
			route.setDescription(rs.getString("description"));
			route.setUser(userBC.load(rs.getString("username")));

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
			result.setUser(userBC.load(rs.getString("username")));
			result.setCoords(parse(rs.getString("json")));
		}

		rs.close();
		pstmt.close();

		return result;
	}
	
	@Transactional
	public void delete(Route route) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from routes where id = ?");
		
		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setInt(1, route.getId());
		pstmt.execute();
		pstmt.close();
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
