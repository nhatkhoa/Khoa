package cmap.model;

import java.io.Serializable;

public class FeedConceptVM implements Serializable {

	private static final long serialVersionUID = 147655223045012211L;
	
	private int key;
	private String text;
	private String loc;
	private String color;
	
	public FeedConceptVM(int key, String text, String loc, String color) {
		this.key = key;
		this.text = text;
		this.loc = loc;
		this.color = color;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
