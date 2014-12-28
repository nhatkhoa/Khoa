package cmap.services;

import java.util.Set;

import cmap.model.AssignPost;
import cmap.model.AssignVM;
import cmap.model.DocVM;
import cmap.model.ListUpload;
import cmap.model.MemberVM;

public interface AssignService {
	// --- Load list upload tài liệu
	public ListUpload getUpload(int assign_id);
	
	public DocVM uploadDoc(int concept_id, String url);
	
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
	
	// --- Hàm chấm điểm bài tập submit
	public int compare(int cmap_id, int assign_id);
}
