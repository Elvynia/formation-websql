package fr.formation.websql;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteArticleServlet extends HttpServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteArticleServlet.class);
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String strIndex = req.getParameter("index");
		if (strIndex != null && !strIndex.isEmpty()) {
			LOGGER.debug("Demande de suppresion d'un article avec index={}", strIndex);
			final int index = Integer.parseInt(strIndex);
			if (index >= 0 && index < BlogServlet.ARTICLES.size()) {
				LOGGER.info("Article à l'indice {} supprimé !", index);
				BlogServlet.ARTICLES.remove(index);
			}
		} else {
			LOGGER.error("Impossible de supprimer un article sans le paramètre index.");
		}
		resp.sendRedirect(this.getServletContext().getContextPath().concat("/blog.html"));
	}
}
