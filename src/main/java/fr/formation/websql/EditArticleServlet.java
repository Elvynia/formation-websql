package fr.formation.websql;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.formation.websql.db.ArticleDao;
import fr.formation.websql.entity.Article;

public class EditArticleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ArticleDao articleDao;
	private int editId;

	@Override
	public void init() throws ServletException {
		this.articleDao = new ArticleDao();
		this.editId = -1;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Une autre manière de gérer la conversion de l'id en entier :
		try {
			this.editId = Integer.parseInt(req.getParameter("id"));
		} catch (final NumberFormatException e) {
			// LOGGER...
		}
		if (this.editId >= 0) {
			final Article article = this.articleDao.read(new Article(this.editId));
			req.setAttribute("article", article);
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		final String title = req.getParameter("title");
		final String description = req.getParameter("description");
		final Article article = this.articleDao.update(new Article(this.editId, title, description));
		if (article != null) {
			// LOG
			resp.sendRedirect(this.getServletContext().getContextPath().concat("/blog.html"));
		} else {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
}
