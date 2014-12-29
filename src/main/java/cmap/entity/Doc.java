package cmap.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "documents")
public class Doc {
	
	@Id
	@GeneratedValue
	private int id;
	private String url;
	
	// --- Chứa concept tương ứng
	@ManyToOne(fetch = FetchType.EAGER)
	private Concept concept;

	public Doc() {
	}
	
	public Doc(String url) {
		super();
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Concept getConcept() {
		return concept;
	}

	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	
	
	
}
