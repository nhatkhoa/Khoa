package cmap.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ConceptDoc  implements Serializable{

	private static final long serialVersionUID = -1364639477797646891L;
	private int id;
	private String name;
	private int count;
	private Set<DocVM> docs = new HashSet<DocVM>(0);
	public ConceptDoc(int id, String name, int count) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<DocVM> getDocs() {
		return docs;
	}
	public void setDocs(Set<DocVM> docs) {
		this.docs = docs;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
}
