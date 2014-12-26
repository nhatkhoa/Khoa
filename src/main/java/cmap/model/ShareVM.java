package cmap.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShareVM implements Serializable {

	/**
	 * Chứa danh sách thành viên chưa share và đã share 
	 */
	private static final long serialVersionUID = 6382014971383334431L;
	private int id;
	private List<MemberVM> share = new ArrayList<MemberVM>(0);
	private List<MemberVM> unshare = new ArrayList<MemberVM>(0);
	
	public ShareVM(int cmap_id){
		this.id = cmap_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MemberVM> getShare() {
		return share;
	}

	public void setShare(List<MemberVM> share) {
		this.share = share;
	}

	public List<MemberVM> getUnshare() {
		return unshare;
	}

	public void setUnshare(List<MemberVM> unshare) {
		this.unshare = unshare;
	}
	

}
