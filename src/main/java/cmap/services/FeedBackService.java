package cmap.services;

import cmap.model.FeedBackVM;


public interface FeedBackService {

	// --- Hàm lấy feedback theo id assign
	public FeedBackVM findByAssign(int assign, String username);
	
	
}
