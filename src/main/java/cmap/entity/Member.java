package cmap.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue
	private int id;
	private String fullname;
	private String username;
	private String password;
	private boolean teacher;

	// --- Danh sách cmap cá nhân
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "author")
	private Set<CMap> cmaps = new HashSet<CMap>(0);
	// --- Danh sách cmap được share
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "shared", joinColumns = { @JoinColumn(name = "member_id") }, inverseJoinColumns = { @JoinColumn(name = "cmap_id", nullable = false, updatable = true, insertable = true) })
	private Set<CMap> shares = new HashSet<CMap>(0);
	// --- Danh sách bài tập được gán
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "assign_member", joinColumns = { @JoinColumn(name = "member_id") }, inverseJoinColumns = { @JoinColumn(name = "assign_id", nullable = false, updatable = true, insertable = true) })
	private Set<Assign> assigns = new HashSet<Assign>(0);

	public Member() {

	}

	public Member(String username, String password, String fullname,
			boolean teacher) {
		this.username = username;
		this.password = password;
		this.teacher = teacher;
		this.fullname = fullname;
	}

	// --- Tìm và trả về cmap trong danh sách được shares
	public CMap findInShare(int cmap_id) {
		// --- Duyệt từng phần tử trong danh sách cmap được share
		for (CMap c : this.shares) {
			// --- nếu cmap này trùng id với cmap_id thì trả về cmap đó
			if (c.getId() == cmap_id) {
				return c;
			}
		}
		return null;
	}

	// --- Trả về true nếu cmap này đã được chia sẽ
	public boolean checkShare(int cmap_id){
		 // ---  Duyệt trong danh sách được chia sẽ
		for (CMap c : this.shares) {
			// --- nếu cmap này trùng id với cmap_id thì trả về true
			if (c.getId() == cmap_id) {
				return true;
			}
		}
		return false;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isTeacher() {
		return teacher;
	}

	public void setTeacher(boolean teacher) {
		this.teacher = teacher;
	}

	public Set<CMap> getCmaps() {
		return cmaps;
	}

	public void setCmaps(Set<CMap> cmaps) {
		this.cmaps = cmaps;
	}

	public Set<CMap> getShares() {
		return shares;
	}

	public void setShares(Set<CMap> shares) {
		this.shares = shares;
	}

	public Set<Assign> getAssigns() {
		return assigns;
	}

	public void setAssigns(Set<Assign> assigns) {
		this.assigns = assigns;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}