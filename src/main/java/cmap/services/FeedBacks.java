package cmap.services;

import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmap.entity.Assign;
import cmap.entity.CMap;
import cmap.entity.Concept;
import cmap.entity.FeedBack;
import cmap.entity.Relation;
import cmap.repositories.AssignRepository;
import cmap.repositories.CMapRepository;
import cmap.repositories.FeedBackRepository;
import cmap.repositories.RelationRepository;

@Service
public class FeedBacks implements FeedBackService {

	// --- Tự động liên kết các đối tượng
	@Autowired
	private CMapRepository cmaps;
	
	@Autowired
	private FeedBackRepository feeds;
	
	@Autowired
	private AssignRepository assigns;
	
	@Autowired
	private RelationRepository relations;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public int compare(int cmap_id, int assign_id){
		// --- Tìm cmap theo id
		CMap cmap = cmaps.findById(cmap_id);
		
		// --- Tìm assign theo id
		Assign assign = assigns.findById(assign_id);
		
		// --- Nếu 1 trong 2 cái không tồn tại thì hủy
		if(cmap == null || assign == null)
			return -1;
		
		// --- Tạo mới một thực thể Feedback 
		FeedBack feed = new FeedBack(-1, new Date(), assign, cmap);
		
		// --- Bắt đầu quá trình chấm bài
		
		// --- Lây danh sách Relation của đáp án
		Set<Relation> relationKey = assign.getCmap().getRelations();
		// --- Lấy danh sách Realation của bài làm
		Set<Relation> relationList = cmap.getRelations() ;
		// --- Duyệt từng phần tử trong danh sách đáp án
		for (Relation key : relationKey){
			// --- Duyệt qua danh sách bài làm 
			for(Relation relation : relationList){
				log.info("-- Compare relation " + relation.getTitle() + " với " + key.getTitle());
				// --- nếu trùng nhau thì xóa khỏi Danh sách đáp án (override)
				if(relation.equals(key))
				{
					log.info(relation.getTitle() + " Đúng !");
					// --- Xác nhập đúng cho 2 concept from và to trong relation đúng
					// --- Gán pass của concept đúng với id concetp đáp án
					relation.getFrom().setPass(key.getFrom().getId());
					relation.getTo().setPass(key.getTo().getId());
					// --- Cập nhật vào cơ sở dữ liệu
					relations.save(relation);
					
					// --- Xóa relation đúng khỏi bài làm
					relationList.remove(relation);
				}
			}
		}
		
		// --- Lấy số lượng concept đúng trong cmap bài làm
		double pass = 0;
		for(Concept c : cmap.getConcepts()){
			// --- Nếu pass của c trỏ đến concept đáp án (khi nó đúng)
			if(c.getPass() != 0)
				pass++;
		}
		
		// --- Lấy tổng số concept của cmap đáp án
		double count = assign.getCmap().getConcepts().size();
		
		// --- Tinh điểm 
		int score = (int)  (count / pass) * 100;
		
		log.info("-- Kết thúc chấm điểm, điểm tổng cộng là : " + score);
		feed.setScore(score);
		
		feeds.save(feed);
		
		return score;
		
	}
}
