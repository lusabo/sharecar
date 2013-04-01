package persistence;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import entity.User;

@PersistenceController
public class UserDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Connection connection;

	@Transactional
	public void insert(User user) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into users (name, displayname, email, telephonenumber) ");
		sql.append("values (?, ?, ?, ?)");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setString(1, user.getName());
		pstmt.setString(2, user.getDisplayName());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getTelephoneNumber());

		pstmt.execute();
		pstmt.close();
	}
	
	@Transactional
	public void update(User user) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update users ");
		sql.append("set name = ?, displayname = ?, email = ?, telephonenumber = ? ");
		sql.append("where id = ? ");
		
		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setString(1, user.getName());
		pstmt.setString(2, user.getDisplayName());
		pstmt.setString(3, user.getEmail());
		pstmt.setString(4, user.getTelephoneNumber());
		pstmt.setInt(5, user.getId());
		
		pstmt.execute();
		pstmt.close();
	}

	public User loadByUsername(String username) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, name, displayname, email, telephonenumber ");
		sql.append("from users where name = ?");

		PreparedStatement pstmt = connection.prepareStatement(sql.toString());
		pstmt.setString(1, username);

		ResultSet rs = pstmt.executeQuery();
		User result = null;

		if (rs.next()) {
			result = new User();
			result.setId(rs.getInt("id"));
			result.setName(rs.getString("name"));
			result.setDisplayName(rs.getString("displayname"));
			result.setEmail(rs.getString("email"));
			result.setTelephoneNumber(rs.getString("telephonenumber"));
		}

		rs.close();
		pstmt.close();

		return result;
	}

	

}
