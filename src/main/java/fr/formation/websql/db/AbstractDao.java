package fr.formation.websql.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Modèle de DAO pour l'accès au tables de la base de données "blog".
 * 
 * @author Arcanis
 *
 * @param <ENTITY>
 *            POJO représentant une table de la base de données "blog".
 */
public abstract class AbstractDao<ENTITY> {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);
	private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/blog?user=root&password=root";
	private static Connection CONNECTION;

	protected Connection getConnection() {
		if (AbstractDao.CONNECTION == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				AbstractDao.CONNECTION = DriverManager.getConnection(CONNECTION_URL);
			} catch (SQLException | ClassNotFoundException e) {
				AbstractDao.LOGGER.debug("URL ou driver de connection à la base de données incorrecte : "
						+ "impossible d'établir la connexion avec la base de données MySQL.");
				throw new RuntimeException("Connexion à la base de données impossible, veuillez arrêter l'application.",
						e);
			}
		}
		return AbstractDao.CONNECTION;
	}

	/**
	 * Créer une entité dans la base de données.
	 * 
	 * @param entity
	 *            l'entité à créer avec id=null.
	 * @return ENTITY l'entité créée avec l'id rempli ou null.
	 */
	public abstract ENTITY create(final ENTITY entity);

	/**
	 * Récupérer une entité depuis la base de données.
	 * 
	 * @param entity
	 *            l'entité à recherche avec id rempli.
	 * @return ENTITY l'entité trouvée ou null.
	 */
	public abstract ENTITY read(final ENTITY entity);

	/**
	 * Mise à jour d'une entité dans la base de données.
	 * 
	 * @param entity
	 *            l'entité à mettre à jour avec id rempli.
	 * @return ENTITY l'entité mise à jour ou null.
	 */
	public abstract ENTITY update(final ENTITY entity);

	/**
	 * Suppression d'une entité dans la base de données.
	 * 
	 * @param entity
	 *            l'entité à supprimer avec id rempli.
	 * @return ENTITY l'entité supprimée ou null.
	 */
	public abstract ENTITY delete(final ENTITY entity);
}
