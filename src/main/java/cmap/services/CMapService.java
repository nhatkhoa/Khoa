package cmap.services;

import java.util.List;
import java.util.Set;

import cmap.model.CMapDetail;
import cmap.model.CMapVM;
import cmap.model.ShareVM;


public interface CMapService {
	// --- Thêm CMap mới
	public int create(CMapDetail cmap, String username);
	// --- Lấy danh sách member chưa chia sẽ
	public ShareVM getShare(int cmap_id);

	// --- Lấy danh sách CMap theo user_id
	public Set<CMapVM> getList(String username);

	// --- Lấy CMap theo id
	public CMapDetail findById(int cmap_id, String username);

	// --- Cập nhật CMap
	public CMapVM update(CMapVM cmap, String username);

	// --- Xóa CMap
	public boolean delete(int cmap_id, String username);

	// --- Chia sẽ cmap
	public boolean share(int cmap_id, List<Integer> list, String username);

	// --- Hủy chia sẽ
	public boolean unShare(int mem_id, int cmap_id, String username);
	
	

}
