package cmap.model;

import java.io.Serializable;

public class FeedRelationVM implements Serializable {

	private static final long serialVersionUID = 6741982913590457957L;
	private int from;
	private int to;
	private String text;
	private String color;
	public FeedRelationVM(int from, int to, String text, String color) {
		super();
		this.from = from;
		this.to = to;
		this.text = text;
		this.color = color;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	

}
