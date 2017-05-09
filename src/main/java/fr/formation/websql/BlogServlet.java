package fr.formation.websql;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlogServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String APP_TITLE = "Mon super blog";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final PrintWriter writer = resp.getWriter();
		writer.write("<html><body><h1>" + BlogServlet.APP_TITLE + "</h1></body></html>");
		writer.flush();
		writer.close();
	}
}
