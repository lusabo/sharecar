package br.gov.frameworkdemoiselle.internal.producer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DataSourceProducer {

	@Produces
	@ApplicationScoped
	public DataSource create() {
		DataSource result;

		try {
			Context ctx = new InitialContext();
			result = (DataSource) ctx.lookup("java:jboss/datasources/sharecarDS");

			/*
			 * if (ctx == null) throw new Exception("Boom - No Context");
			 */

			// if (ds != null) {
			// Connection conn = ds.getConnection();
			// //
			// // if(conn != null) {
			// // foo = "Got Connection "+conn.toString();
			// // Statement stmt = conn.createStatement();
			// // ResultSet rst =
			// // stmt.executeQuery(
			// // "select id, foo, bar from testdata");
			// // if(rst.next()) {
			// // foo=rst.getString(2);
			// // bar=rst.getInt(3);
			// // }
			// conn.close();
			// // }
			// }
		} catch (Exception e) {
			result = null;
		}

		return result;
	}
}
