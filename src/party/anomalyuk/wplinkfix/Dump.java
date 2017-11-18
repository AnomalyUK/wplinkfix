package party.anomalyuk.wplinkfix;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class Dump {
    public static void main(String args[]) throws Exception {
	String pwd = new BufferedReader(new InputStreamReader(System.in)).readLine();

	MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
	ds.setURL("jdbc:mysql://localhost/wp_database");
	ds.setUser("wpuser");
	ds.setPassword(pwd);

	Connection c = ds.getConnection();
	PreparedStatement s = c.prepareStatement("select id, post_content from wp0_2_posts");
	ResultSet rs = s.executeQuery();
	while (rs.next()) {
	    String post = rs.getString(2);
	    Post p = new Post(rs.getString(1), post);
	    for (String l : p.links()) {
		System.out.println(l);
	    }
	}
	s.close();
	c.close();
    }
}
