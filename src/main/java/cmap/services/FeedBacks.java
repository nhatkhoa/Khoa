package cmap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmap.entity.Assign;
import cmap.entity.Concept;
import cmap.entity.FeedBack;
import cmap.entity.Relation;
import cmap.model.FeedBackVM;
import cmap.model.FeedConceptVM;
import cmap.model.FeedRelationVM;
import cmap.repositories.AssignRepository;

@Service
public class FeedBacks implements FeedBackService {

	@Autowired
	private AssignRepository assigns;
	
	// --- Xác định màu xác của bản vẽ trong bản feedback
	private final String TRUE = "red";
	private final String FALSE = "green";
	
	@Override
	public FeedBackVM findByAssign(int assign_id, String username) {
		// --- Truy vấn assign với assign id truyền vào
		Assign assign = assigns.findById(assign_id);
		
		// --- Duyệt danh sách feedback và lấy feedback đầu tiên của username trên
		for(FeedBack f : assign.getFeedbacks()){
			if(f.getCmap().getAuthor().getUsername().contains(username))
			{
				// --- Khởi tạo FeedBackVM từ feedback đang duyệt
				FeedBackVM feed = new FeedBackVM(f.getId(), assign.getTopic(), assign.getInfo(), f.getScore());
				
					// --- Duyệt qua tất cả các concept trong CMap thuộc feedBack hiện tại
				for(Concept c : f.getCmap().getConcepts()){
					// --- Tạo mới một đối tượng FeedCOnceptVM từ concept đang duyệt
					FeedConceptVM con = new FeedConceptVM(c.getId(), c.getName(), c.getLoc(), (c.getPass() == -1)? TRUE : FALSE);
					
					// --- Thêm đối tượng vào list
					feed.getNodeDataArray().add(con);
				}
				
				
				// --- Duyệt qua tất cả các relation trong CMap thuộc feedBack hiện tại
				for(Relation r : f.getCmap().getRelations()){
					
					// --- Tạo mới một đối tượng FeedRelationVM từ concept đang duyệt
					FeedRelationVM rela = new FeedRelationVM(r.getFrom().getId(), r.getTo().getId(), r.getTitle(), (r.getPass() == -1)? TRUE : FALSE);
					
					// --- Thêm đối tượng vào list
					feed.getLinkDataArray().add(rela);
				}
				
				return feed;
			}
			
		}
		// --- Nếu không tồn tại trả về null;
		return null;
	}

}
