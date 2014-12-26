package cmap.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CMapDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String title;
	private String info;
	private int author_id;

	private ArrayList<ConceptVM> nodeDataArray = new ArrayList<ConceptVM>(0);
	private ArrayList<RelationVM> linkDataArray = new ArrayList<RelationVM>(0);

	public CMapDetail(){}
	public CMapDetail(int id, String title, String info, int author_id) {
		super();
		this.id = id;
		this.title = title;
		this.info = info;
		this.author_id = author_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}

	public ArrayList<ConceptVM> getNodeDataArray() {
		return nodeDataArray;
	}

	public void setNodeDataArray(ArrayList<ConceptVM> nodeDataArray) {
		this.nodeDataArray = nodeDataArray;
	}

	public ArrayList<RelationVM> getLinkDataArray() {
		return linkDataArray;
	}

	public void setLinkDataArray(ArrayList<RelationVM> linkDataArray) {
		this.linkDataArray = linkDataArray;
	}



}
