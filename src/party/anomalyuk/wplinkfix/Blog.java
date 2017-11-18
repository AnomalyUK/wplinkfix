package party.anomalyuk.wplinkfix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
   
public class Blog {
    private String hostname;
    
    public Blog(String host) {
	hostname = host;
    }

    public Post post(int postid, String postcontent) {
	return new Post(postid, postcontent);
    }

    public class Post {
	private int id;
	private String content;

	public Post(int postid, String postcontent) {
	    id = postid; content = postcontent;
	}

	public int id() { return id; }
	public String content() { return content; }

	public String fixedContent() {
	    Pattern bad = Pattern.compile("href=\"(http:)//" + hostname + "/");
	    Matcher m = bad.matcher(content);
	    String good = m.replaceAll("href=\"https://" + hostname + "/");
	    return good;
	}
    }
}



	    
