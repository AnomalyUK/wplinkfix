package party.anomalyuk.wplinkfix;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

public class FixHttp {
    public static void main(String args[]) throws Exception {
	Properties properties = new Properties();
	properties.load(FixHttp.class.getResourceAsStream("/db.properties"));
	
	MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();

	ds.setURL(properties.getProperty("db"));
	ds.setUser(properties.getProperty("user"));
	ds.setPassword(properties.getProperty("password"));
	Blog blog = new Blog(properties.getProperty("host"));

	HashMap<Integer,String> newContent = new HashMap<>();
	
	Connection c = ds.getConnection();
	PreparedStatement s = c.prepareStatement("select id, post_content from wp0_2_posts");
	ResultSet rs = s.executeQuery();
	while (rs.next()) {
	    Blog.Post p = blog.post(rs.getInt(1), rs.getString(2));

	    String fixed = p.fixedContent();

	    if (!fixed.equals(p.content())) {
		newContent.put(p.id(), fixed);
	    }
	}
	s.close();

	PreparedStatement up = c.prepareStatement("update wp0_2_posts set post_content=? where id=?");
	for (int i : newContent.keySet()) {
	    up.setInt(2,i);
	    up.setString(1, newContent.get(i));
	    up.executeUpdate();
	}
	up.close();
	    
	c.close();
    }
}
