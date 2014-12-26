package cmap.model;

import java.io.Serializable;

public class RelationVM implements Serializable {
	/**
	 * Mối quan hệ giữa 2 concept map với nhau
	 */
	private static final long serialVersionUID = -5027681184200937292L;
	private int from;
	private int to;
	private String text;

	public RelationVM(){}
	public RelationVM(int from, int to, String text) {
		super();
		this.from = from;
		this.to = to;
		this.text = text;
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

}
