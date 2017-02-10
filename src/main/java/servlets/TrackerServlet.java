package servlets;

import models.TrackedObject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HelperFunctions;
import utils.TrackerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class TrackerServlet extends HttpServlet {
    TrackerFactory trackerFactory = new TrackerFactory();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getMethod();
        SessionFactory factory =(SessionFactory)getServletContext().getAttribute(Init.ATT_SESSION_FACTORY);
        if ("GET".equals(method)) {

            String previousToken = request.getHeader("If-None-Match");
            String token = trackerFactory.getToken(request);

            if ((token != null) && (previousToken != null && previousToken.equals('"' + token + '"'))) {
                response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("Last-Modified", request.getHeader("If-Modified-Since"));
            }
            if (token != null) {
                response.setHeader("ETag", '"' + token + '"');
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.MILLISECOND, 0);
                Date lastModified = cal.getTime();

                TrackedObject trackedObject = new TrackedObject();
                trackedObject.setId(HelperFunctions.idObj++);
                trackedObject.setEtag(token);
                trackedObject.setDate(lastModified);

                Session session = factory.openSession();
                session.beginTransaction();
                session.save(trackedObject);
                session.getTransaction().commit();
                session.close();

                response.setDateHeader("Last-Modified", lastModified.getTime());
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
