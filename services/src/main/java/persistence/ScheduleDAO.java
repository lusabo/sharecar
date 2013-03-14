package persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import entity.Schedule;


@PersistenceController
public class ScheduleDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Connection connection;
	
	@Transactional
	public void insert(Schedule schedule) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into schedules (route_id, weekday, hour) ");
		sql.append("values (?, ?, ?)");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setInt(1, schedule.getRoute().getId());
		pstmt.setInt(2, schedule.getWeekday().getValor());
		pstmt.setString(3, schedule.getTime().toString());

		pstmt.execute();
		pstmt.close();
	}
}
