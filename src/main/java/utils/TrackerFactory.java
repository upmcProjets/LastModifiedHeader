package utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TrackerFactory {

	private Map<String, Integer> counts = Collections.synchronizedMap(new HashMap<String, Integer>());

	public String getToken(HttpServletRequest request) {
		String view = request.getRequestURI();
		Integer count = counts.get(view);
		if (count == null) {return null;}
		return count.toString();
	}
}