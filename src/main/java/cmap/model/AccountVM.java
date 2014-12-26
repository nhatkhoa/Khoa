package cmap.model;



import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class AccountVM implements Serializable {

	private static final long serialVersionUID = 5623490514957122824L;
	
	private int id;
	private String username;
	private	String fullname;
	private	boolean teacher;
	
	private Set<CMapVM> cmaps = new HashSet<CMapVM>(0);
	private Set<AssignVM> assigns = new HashSet<AssignVM>(0);
	public AccountVM(int id, String username, String fullname, boolean teacher) {
		super();
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.teacher = teacher;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public boolean isTeacher() {
		return teacher;
	}
	public void setTeacher(boolean teacher) {
		this.teacher = teacher;
	}
	public Set<CMapVM> getCmaps() {
		return cmaps;
	}
	public void setCmaps(Set<CMapVM> cmaps) {
		this.cmaps = cmaps;
	}
	public Set<AssignVM> getAssigns() {
		return assigns;
	}
	public void setAssigns(Set<AssignVM> assigns) {
		this.assigns = assigns;
	}
	
	
}
