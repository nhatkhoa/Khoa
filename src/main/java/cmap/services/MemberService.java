package cmap.services;

import java.util.Set;

import cmap.model.AccountVM;
import cmap.model.MemberVM;

public interface MemberService {
	// --- Thêm tài khoản mới
	public boolean addMember(String username, String password, String fullname, boolean teacher);

	// --- Lấy thông tin tài khoản bằng id
	public AccountVM findById(int id);

	// --- Lấy thông tin tài khoản bằng username
	public AccountVM findByUsername(String username);
	
	// --- Lấy danh sách tất cả member
	public Set<MemberVM> getList();
	
	// --- Update member
	public boolean updateMember(int id, String password, String fullname, boolean teacher);
	

}
