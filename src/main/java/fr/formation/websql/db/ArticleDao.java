package fr.formation.websql.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.formation.websql.entity.Article;

public class ArticleDao extends AbstractDao<Article> {

	private final static Logger LOGGER = LoggerFactory.getLogger(ArticleDao.class);

	/**
	 * {@inheritDoc} Spécification de l'implémentation d'ArticleDao...
	 */
	@Override
	public Article create(Article entity) {
		Article result = null;
		try {
			final Statement statement = this.getConnection().createStatement();
			statement.execute("INSERT INTO ARTICLES VALUES (null, '" + entity.getTitle() + "', '"
					+ entity.getDescription() + "');", Statement.RETURN_GENERATED_KEYS);
			// Récupération de l'ensemble des clés/id générés par MySQL
			// durant l'exécution.
			final ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			// Récupération de l'ID auto increment et stockage dans l'id de
			// l'article.
			entity.setId(rs.getInt("GENERATED_KEY"));
			result = entity;
		} catch (SQLException e) {
			ArticleDao.LOGGER.error("Erreur durant la création d'un nouvel article", e);
		}
		return result;
	}

	@Override
	public Article read(Article entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article update(Article entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article delete(Article entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
