package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.transaction.Transactional;

public class X {

	@Inject
	@Name("conn2")
	private Connection connection;

	@Startup
	@Transactional
	public void x() throws Exception {
		PreparedStatement pstmt = connection.prepareStatement("select * from dossies");
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			System.out.println(rs.getString(7));
		}

		rs.close();
		pstmt.close();
	}
}
