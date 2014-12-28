package cmap.model;

import java.io.Serializable;

public class DocVM  implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String url;
	public DocVM(int id, String url) {
		super();
		this.id = id;
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
	
}
