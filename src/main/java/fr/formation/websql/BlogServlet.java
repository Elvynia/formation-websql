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

public class BlogServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogServlet.class);
	private static final String APP_TITLE = "Mon super blog";

	private final ArticleDao articleDao = new ArticleDao();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		BlogServlet.LOGGER.debug("Titre saisi : {}", title);
		String description = req.getParameter("description");
		BlogServlet.LOGGER.debug("Description saisie : {}", description);
		final Article article = this.articleDao.create(new Article(title, description));
		if (article != null && article.getId() != null) {
			BlogServlet.LOGGER.debug("L'article de titre '{}' a bien été créé avec l'id : {}", title, article.getId());
		} else {
			BlogServlet.LOGGER.error("L'article n'a pas pu être créé ou l'identifiant n'a pas pu être récupéré.");
		}
		this.forward(req, resp);
	}

	private void forward(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("page_title", BlogServlet.APP_TITLE);
		req.setAttribute("articles", this.articleDao.readAll());
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/blog.jsp").forward(req, resp);
	}
}
