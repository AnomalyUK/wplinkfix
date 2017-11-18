package party.anomalyuk.wplinkfix;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Post {
    private String id;
    private String content;
    
    public Post(String id, String content) {
	this.id = id; this.content = content;
    }

    public Iterable<String> links() {
	Pattern p = linkPattern();
	LinkedList<MatchResult> matches = new LinkedList<>();
	Matcher m = p.matcher(content);
	while (m.find()) {
	    matches.add(m.toMatchResult());
	}

	return matches.stream().map( r -> r.group(0) )::iterator;
    }

    private Pattern linkPattern() {
	return Pattern.compile("\"https?://blog.anomalyuk.party/[^\"]*\"");
    }
}
    
	
