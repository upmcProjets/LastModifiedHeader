package servlets;

import utils.HelperFunctions;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class TrackerFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) req;
        HttpServletResponse servletResponse = (HttpServletResponse) resp;

        String id = servletRequest.getRequestURI();
        String qStr = servletRequest.getQueryString();
        if (qStr != null) {
            id += qStr;
        }
        String token = '"' + HelperFunctions.getSHA1(id) + '"';
        servletResponse.setHeader("ETag", token);
        String previousToken = servletRequest.getHeader("If-None-Match");

        if (previousToken != null && previousToken.equals(token)) {
            servletResponse.sendError(HttpServletResponse.SC_NOT_MODIFIED);
            servletResponse.setHeader("Last-Modified", servletRequest.getHeader("If-Modified-Since"));
        } else  {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MILLISECOND, 0);
            Date lastModified = cal.getTime();
            servletResponse.setDateHeader("Last-Modified", lastModified.getTime());
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {}

}
