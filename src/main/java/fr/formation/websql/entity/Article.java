package fr.formation.websql.entity;

public class Article {

	private Integer id;
	
	private String title;
	
	private String description;
	
	public Article() {
	}
	
	public Article(final String title, final String description) {
		this.title = title;
		this.description = description;
	}
	
	public Article(final Integer id, final String title, final String description) {
		this(title, description);
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
