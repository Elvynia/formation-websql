package fr.formation.websql;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.formation.websql.db.ArticleDao;
import fr.formation.websql.entity.Article;

public class DeleteArticleServlet extends HttpServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteArticleServlet.class);
	private static final long serialVersionUID = 1L;

	private ArticleDao articleDao = new ArticleDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String strId = req.getParameter("id");
		if (strId != null && !strId.isEmpty()) {
			LOGGER.debug("Demande de suppresion d'un article avec id={}", strId);
			final int id = Integer.parseInt(strId);
			if (id >= 0) {
				this.articleDao.delete(new Article(id));
				LOGGER.info("Article d'id={} supprimé !", id);
			}
		} else {
			LOGGER.error("Impossible de supprimer un article sans le paramètre index.");
		}
		resp.sendRedirect(this.getServletContext().getContextPath().concat("/blog.html"));
	}
}
