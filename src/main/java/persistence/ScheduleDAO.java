package persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import entity.Route;
import entity.Schedule;


@PersistenceController
public class ScheduleDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Connection connection;
	
	@Inject
	private RouteDAO routeDAO;
	
	@Transactional
	public void insert(Schedule schedule) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into schedules (route_id, weekday, hour) ");
		sql.append("values (?, ?, ?)");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setInt(1, schedule.getRoute().getId());
		pstmt.setInt(2, schedule.getWeekday().getValor());
		pstmt.setTime(3, schedule.getHour());

		pstmt.execute();
		pstmt.close();
	}
	
	public List<Schedule> find(Route route) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, weekday, hour, route_id from schedules ");
		sql.append("where route_id = ? order by 2, 3");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setInt(1, route.getId());

		ResultSet rs = pstmt.executeQuery();
		List<Schedule> result = new ArrayList<Schedule>();

		while (rs.next()) {

			Schedule schedule = new Schedule();
			schedule.setId(rs.getInt("id"));
			schedule.setWeekday(rs.getInt("weekday"));
			schedule.setHour(rs.getTime("hour"));
			schedule.setRoute(routeDAO.load(rs.getInt("route_id")));
			
			result.add(schedule);
		}
		
		rs.close();
		pstmt.close();
		return result;
	}
	
	@Transactional
	public void delete(Integer id) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from schedules where id = ?");
		
		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setInt(1, id);
		pstmt.execute();
		pstmt.close();
	}	
	
	@Transactional
	public void delete(Route route) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from schedules where route_id = ?");
		
		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setInt(1, route.getId());
		pstmt.execute();
		pstmt.close();
	}
	
	public Schedule load(Integer id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, route_id, weekday, hour ");
		sql.append("from schedules where id = ?");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();
		Schedule result = null;

		if (rs.next()) {
			result = new Schedule();
			result.setId(rs.getInt("id"));
			result.setRoute(routeDAO.load(rs.getInt("route_id")));
			result.setWeekday(rs.getInt("weekday"));
			result.setHour(rs.getTime("hour"));
		}

		rs.close();
		pstmt.close();

		return result;
	}	
}
