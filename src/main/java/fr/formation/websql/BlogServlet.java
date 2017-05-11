package fr.formation.websql;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public static final List<String> ARTICLES = new ArrayList<>(Arrays.asList(
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales ex neque, at vulputate urna finibus et. Pellentesque magna sapien, luctus non augue at, venenatis commodo velit. Ut dolor libero, porta ut blandit ut, elementum at justo. Praesent faucibus scelerisque odio sed sollicitudin. Morbi non sapien sit amet elit ullamcorper fermentum sed in quam. Maecenas in efficitur tellus. Nunc at elementum diam. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.",
			"Sed sed elementum turpis. Aenean sed augue finibus, convallis ex consectetur, suscipit tellus. Proin maximus ut ipsum at auctor. Aenean vitae justo placerat, gravida sapien sollicitudin, venenatis quam. Suspendisse pharetra diam sed risus hendrerit laoreet. Sed rutrum turpis ac mi ultrices sodales. Aenean erat lorem, placerat quis tincidunt id, fermentum eu metus. Etiam feugiat vehicula sapien ut fermentum. Maecenas massa mauris, ultricies sed pharetra eget, tincidunt in massa. Aliquam at sollicitudin nisi. Quisque tempus mi a varius condimentum. Etiam ornare ipsum vitae facilisis condimentum. Nullam posuere lacus vitae suscipit viverra. In commodo sodales ex et ornare. Duis luctus interdum sagittis.",
			"Integer at volutpat metus. Aenean quis vulputate ligula, et molestie sapien. Nunc lectus elit, ultrices sed felis non, vestibulum vehicula diam. Curabitur pellentesque libero sed erat elementum, vel egestas lacus porta. Aliquam erat volutpat. Vivamus pharetra diam lorem. Cras non rutrum massa, ut imperdiet justo. Nulla pretium egestas neque id euismod. Donec auctor tellus in nulla imperdiet, eu sodales metus pulvinar. Nullam rutrum est non ipsum lobortis, ac malesuada ex luctus. Sed vel lacus massa. Donec aliquam felis vitae sem gravida malesuada. Pellentesque fringilla nunc felis, nec tristique est posuere ac. Nam consequat nunc blandit, aliquam ligula in, placerat arcu.",
			"Morbi blandit commodo est sed ornare. In feugiat nunc ornare, facilisis nunc et, finibus enim. Nam bibendum sed est eget tempor. Quisque lorem ligula, pharetra pellentesque volutpat ut, facilisis sed nisi. Cras ultricies odio massa, non ullamcorper nunc vehicula ac. Mauris in felis commodo, pulvinar dui eu, lacinia urna. Etiam tincidunt lectus vel odio ullamcorper fringilla. Maecenas vestibulum diam non lectus venenatis egestas. Duis sagittis nec leo non ultricies. Proin elementum malesuada nisl id vestibulum. Quisque aliquam pretium augue, et sollicitudin ex gravida at. Proin sed purus at tortor sollicitudin placerat eu at libero. Mauris ultrices gravida tellus, sit amet vestibulum nibh suscipit ac.",
			"Duis in semper mi, a vestibulum velit. Quisque consectetur magna nunc, elementum vestibulum nunc sagittis pharetra. Pellentesque consequat laoreet nunc sit amet auctor. Donec vel accumsan turpis. Cras fermentum facilisis urna eu placerat. Donec viverra vulputate nibh non gravida. Fusce non leo sed enim dictum posuere ac at sapien. Sed ullamcorper nunc a dapibus viverra. Phasellus posuere non tortor pharetra auctor. Mauris ut nisi arcu. Sed non purus vitae magna feugiat facilisis quis ut tortor. Ut erat ex, faucibus a urna vel, feugiat porta arcu. Nullam eget maximus sapien. Quisque sit amet porttitor nisi. Morbi felis nisi, lacinia sed molestie ut, convallis a nisl. Donec a lacus feugiat, tempus lacus eget, tempus tellus."));

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
		BlogServlet.ARTICLES.add(description);
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
		req.setAttribute("articles", BlogServlet.ARTICLES);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/blog.jsp").forward(req, resp);
	}
}
