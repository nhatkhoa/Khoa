package cmap.model;

import java.io.Serializable;

public class ConceptVM implements Serializable {

	private static final long serialVersionUID = -1355844949031455808L;
	private int key;
	private String text;
	private String loc;
	
	public ConceptVM(){}
	public ConceptVM(int key, String text, String loc) {
		super();
		this.key = key;
		this.text = text;
		this.loc = loc;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	
}
