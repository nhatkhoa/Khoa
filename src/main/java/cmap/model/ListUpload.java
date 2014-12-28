package cmap.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ListUpload implements Serializable {

	private static final long serialVersionUID = -5229308387968447010L;
	private int id;
	private String topic;
	// --- Danh sách các tài liệu
	Set<ConceptDoc> concept = new HashSet<ConceptDoc>(0);
	
	public ListUpload(int id, String topic) {
		super();
		this.id = id;
		this.topic = topic;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public Set<ConceptDoc> getConcept() {
		return concept;
	}
	public void setConcept(Set<ConceptDoc> concept) {
		this.concept = concept;
	}
	
}
