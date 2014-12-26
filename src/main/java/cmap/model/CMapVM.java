package cmap.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CMapVM implements Serializable{
	private static final long serialVersionUID = -8343016908516719542L;

	private int id;
	private String title;
	private String info;
	private String date_create;
	private int author_id;
	private String author_name;
	private SimpleDateFormat date = new SimpleDateFormat("dd/M/yyyy");
	
	public CMapVM(int id, String title, String info, Date date_create,
			int author_id, String author_name) {
		super();
		this.id = id;
		this.title = title;
		this.info = info;
		this.date_create = date.format(date_create);
		this.author_id = author_id;
		this.author_name = author_name;
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
	public String getDate_create() {
		return date_create;
	}
	public void setDate_create(String date_create) {
		this.date_create = date_create;
	}
	public int getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	public String getAuthor_name() {
		return author_name;
	}
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}
	
	
}
