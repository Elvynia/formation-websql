package fr.formation.websql.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
			// La première colonne ID est null car MySQL se charge de calculer
			// sa valeur avec l'auto-increment qui permet de générer des
			// identifiants uniques facilement.
			statement.execute("INSERT INTO ARTICLES VALUES (null, '" + entity.getTitle() + "', '"
					+ entity.getDescription() + "');", Statement.RETURN_GENERATED_KEYS);
			// Récupération de l'ensemble des clés/id générés par MySQL durant l'exécution.
			final ResultSet rs = statement.getGeneratedKeys();
			// L'objet ResultSet fonctionne avec un curseur qui parcourt le
			// tableau de données donné en résultat par MySQL. C'est exactement
			// le même tableau que celui donné en ligne de commande si vous
			// exécutez la même requête. Lorsque l'on récupère l'instance du
			// ResultSet, le curseur est positionné "avant" la première ligne,
			// il faut donc l'avancer une fois avec next() pour le déplacer sur
			// la première ligne.
			rs.next();
			// Récupération de l'ID auto increment et stockage dans l'id de l'article.
			entity.setId(rs.getInt("GENERATED_KEY"));
			result = entity;
		} catch (SQLException e) {
			ArticleDao.LOGGER.error("Erreur durant la création d'un nouvel article", e);
		}
		return result;
	}

	/**
	 * {@inheritDoc} L'article est récupéré grâce à son id.
	 */
	@Override
	public Article read(final Article entity) {
		this.checkId(entity);
		Article result = null;
		try {
			final Statement stmt = this.getConnection().createStatement();
			stmt.executeQuery("SELECT TITLE, DESCRIPTION FROM ARTICLES WHERE ID=" + entity.getId() + ";");
			final ResultSet rs = stmt.getResultSet();
			// Ici on peut se servir de la valeur de retour du premier déplacement du curseur.
			// En effet si il n'y a aucun résultat, le premier next() renvoi false.
			if (rs.next()) {
				entity.setTitle("TITLE");
				entity.setDescription("DESCRIPTION");
				result = entity;
			}
		} catch (SQLException e) {
			ArticleDao.LOGGER.error("Erreur durant la récupération d'un article avec id=" + entity.getId(), e);
		}
		return result;
	}

	/**
	 * {@inheritDoc} Met à jour le titre et la description en fonction de l'id.
	 */
	@Override
	public Article update(Article entity) {
		this.checkId(entity);
		Article result = null;
		try {
			final Statement stmt = this.getConnection().createStatement();
			// Attention aux quotes pour les chaines de caractères en SQL ! Il est parfois utile de logger
			// les requêtes SQL produites en debug afin de vérifier qu'elles sont bien formulées.
			final int updateCount = stmt.executeUpdate("UPDATE ARTICLES SET TITLE='" + entity.getTitle()
					+ "', DESCRIPTION='" + entity.getDescription() + "' WHERE ID=" + entity.getId() + ";");
			// On peut aussi retrouver updateCount avec stmt.getUpdateCount().
			if (updateCount == 1) {
				result = entity;
			} // else
				// Le cas ou l'update n'est pas bon est géré par le retour de result avec une valeur null. Cela veut
				// dire que c'est à la méthode appelante de vérifier si l'update s'est correctement passé.

			// Petit rappel car j'ai mentionné ça rapidement mais c'est important. Nous n'avons pas besoin d'appeler
			// this.getConnection().commit(); car par défaut l'objet Connection est en mode auto-commit. Chaque
			// requête modifiant les données dans la base nécessite un commit pour que les changements soient réellement
			// effectués. Le mode auto-commit est sympa pour nous dans ce petit projet, mais il fait partie des
			// "mauvaises pratiques". En effet ce mode ne permet pas d'attendre plusieurs requêtes avant de faire un
			// commit, et il est possible d'inscrire des données partiellement valides qui n'ont pas de sens car la
			// requête précédente/suivante a planté une erreur côté SGBD (MySQL dans notre cas).
		} catch (SQLException e) {
			ArticleDao.LOGGER.error("Erreur durant la mise à jour d'un article avec id=" + entity.getId(), e);
		}
		return result;
	}

	@Override
	public Article delete(Article entity) {
		this.checkId(entity);
		Article result = null;
		try {
			final Statement stmt = this.getConnection().createStatement();
			// Attention aux quotes, chaque caractère compte ! Il est parfois utile de logger
			// les requêtes SQL produites en debug afin de vérifier qu'elles sont bien formulées.
			final int deleteCount = stmt.executeUpdate("DELETE FROM ARTICLES WHERE ID=" + entity.getId() + ";");
			// Pour DELETE ça fonctionne aussi, on a le nombre de lignes supprimées.
			if (deleteCount == 1) {
				result = entity;
			} // else
				// Idem update, si la suppression s'est mal passée c'est dans la méthode appelante avec != null qu'on
				// le vérifie. Ici les vérifications restent techniques car le composant DAO est un composant technique,
				// ce n'est pas lui qui doit juger si il y avait bien quelque chose à supprimer, mais à son composant
				// parent. En général les composants parents sont appelés des services.
		} catch (SQLException e) {
			ArticleDao.LOGGER.error("Erreur durant la suppression d'un article avec id=" + entity.getId(), e);
		}
		return result;
	}

	/**
	 * Cette méthode n'existe pas dans AbstractDao car elle ne fait pas partie du design pattern DAO. En revanche on a
	 * toujours besoin de cette méthode, gardez bien en tête que les méthodes CRUD d'un DAO manipulent un objet mais
	 * qu'il n'est pas suffisant tout seul.
	 * 
	 * @return List<Article> tous les articles en base.
	 */
	public List<Article> readAll() {
		final List<Article> results = new ArrayList<>();
		Statement stmt;
		try {
			stmt = this.getConnection().createStatement();
			stmt.executeQuery("SELECT * FROM ARTICLES");
			final ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				final Article article = new Article();
				article.setId(rs.getInt("ID"));
				article.setTitle(rs.getString("TITLE"));
				article.setDescription(rs.getString("DESCRIPTION"));
				results.add(article);
			}
		} catch (SQLException e) {
			ArticleDao.LOGGER.error("Erreur durant la récupération de l'ensemble des articles.", e);
		}
		return results;
	}

	/**
	 * Méthode permettant de factoriser la vérification de la présence d'un id valide.
	 * 
	 * @param entity
	 */
	private void checkId(Article entity) {
		if (entity == null || entity.getId() == null) {
			throw new IllegalArgumentException("L'article et son id ne doivent pas être null.");
		}
	}

}
