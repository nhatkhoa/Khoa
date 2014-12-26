package cmap.services;

import java.util.Set;

import cmap.model.AssignPost;
import cmap.model.AssignVM;
import cmap.model.MemberVM;

public interface AssignService {
	// --- Lấy Assign theo id
	public AssignVM findById(int id);

	// --- Lấy danh sách Assign của user theo user_id
	public Set<AssignVM> getList(String username);

	// --- Lấy danh sách member chưa được gán bài tập
	public Set<MemberVM> getMembers(int id);

	// --- Thêm Assign
	public AssignVM add(AssignPost assign);

	// --- Xóa assign
	public boolean delete(int assign_id);

	// --- Giao assign cho user
	public boolean postUser(int assign_id, int mem_id, String username);
}
