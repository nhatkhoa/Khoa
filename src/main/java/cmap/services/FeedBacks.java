package cmap.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmap.entity.Concept;
import cmap.entity.FeedBack;
import cmap.entity.Relation;
import cmap.model.FeedBackVM;
import cmap.model.FeedConceptVM;
import cmap.model.FeedRelationVM;
import cmap.repositories.AssignRepository;
import cmap.repositories.FeedBackRepository;

@Service
public class FeedBacks implements FeedBackService {

	@Autowired
	private AssignRepository assigns;
	
	@Autowired
	private FeedBackRepository feeds;
	
	// --- Xác định màu xác của bản vẽ trong bản feedback
	private final String TRUE = "#41D4CF";
	private final String FALSE = "#FF2321";
	
	@Override
	public FeedBackVM findByAssign(int assign_id, String username) {
		
		// --- Truy vấn feedback mới nhất thông qua assign và username
		Set<FeedBack> fs = feeds.getFeedBack(assign_id, username);
		// --- Lấy feedback đầu tiên trong danh sách được sắp xếp theo id lớn nhất (feedback mới nhất)
		FeedBack f = fs.iterator().next();
		if(f == null)
			return null;
		
		// --- Khởi tạo FeedBackVM từ feedback đang duyệt
		FeedBackVM feed = new FeedBackVM(f.getId(), f.getAssign().getTopic(), f.getAssign().getInfo(), 
										f.getScore(), f.getAssign().getCmap().getConcepts().size(), 
										f.getAssign().getCmap().getRelations().size(), 
										f.getCmap().conceptPass(), 
										f.getCmap().relationPass(), f.getDate_create());
			// --- Duyệt qua tất cả các concept trong CMap thuộc feedBack hiện tại
		for(Concept c : f.getCmap().getConcepts()){
			// --- Tạo mới một đối tượng FeedCOnceptVM từ concept đang duyệt
			FeedConceptVM con = new FeedConceptVM(c.getId(), c.getName(), c.getLoc(), (c.getPass() == -1)? FALSE : TRUE);
			
			// --- Thêm đối tượng vào list
			feed.getNodeDataArray().add(con);
		}
		
		
		// --- Duyệt qua tất cả các relation trong CMap thuộc feedBack hiện tại
		for(Relation r : f.getCmap().getRelations()){
			
			// --- Tạo mới một đối tượng FeedRelationVM từ concept đang duyệt
			FeedRelationVM rela = new FeedRelationVM(r.getFrom().getId(), r.getTo().getId(), r.getTitle(), (r.getPass() == -1)? FALSE : TRUE);
			
			// --- Thêm đối tượng vào list
			feed.getLinkDataArray().add(rela);
		}
		
		return feed;
	}

}
