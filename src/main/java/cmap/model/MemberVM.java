package cmap.model;

import java.io.Serializable;

public class MemberVM implements Serializable{
	
	/**
	 * Member Model View : Dùng cho hiển thị danh sách thành viên
	 */
	private static final long serialVersionUID = 6429736974435937794L;
	private int id;
	private String fullname;
	private String taikhoan;
	private String username;
	
	public MemberVM(int id, String fullname, String taikhoan, String username) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.taikhoan = taikhoan;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(String taikhoan) {
		this.taikhoan = taikhoan;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
