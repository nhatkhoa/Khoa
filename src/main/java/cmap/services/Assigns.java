package cmap.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmap.entity.Assign;
import cmap.entity.CMap;
import cmap.entity.FeedBack;
import cmap.entity.Member;
import cmap.entity.Relation;
import cmap.model.AssignPost;
import cmap.model.AssignVM;
import cmap.model.MemberVM;
import cmap.repositories.AssignRepository;
import cmap.repositories.CMapRepository;
import cmap.repositories.ConceptRepository;
import cmap.repositories.FeedBackRepository;
import cmap.repositories.MemberRepository;
import cmap.repositories.RelationRepository;

@Service
public class Assigns implements AssignService {

	@Autowired
	private MemberRepository mems;

	@Autowired
	private CMapRepository cmaps;

	@Autowired
	private FeedBackRepository feeds;

	@Autowired
	private RelationRepository relations;

	@Autowired
	private ConceptRepository cons;

	@Autowired
	private AssignRepository assigns;

	private Logger log = Logger.getLogger(this.getClass());

	@Override
	public AssignVM findById(int id) {
		// --- truy vấn assiegn trùng id
		Assign a = assigns.findById(id);
		// --- Từ assign entity tạo một thành AssignVM
		AssignVM assign = new AssignVM(a.getId(), a.getTopic(),
				a.getDeadline(), a.getInfo(), a.getCmap().getAuthor().getId(),
				a.getCmap().getAuthor().getFullname(), 100, new Date(), a
						.getFeedbacks().size(), a.getCmap().getConcepts()
						.size(), a.getMembers().size());
		return assign;
	}

	@Override
	public Set<AssignVM> getList(String username) {

		// --- Tìm user trùng id
		Member mem = mems.findByUsername(username);
		// --- Nếu không tồn tại member nào
		if (mem == null)
			return null;
		// --- Khởi tạo một list AssignVM rỗng
		Set<AssignVM> list = new HashSet<AssignVM>(0);

		log.info("------------------ Lấy danh sách assign của " + username
				+ " có tất cả : " + mem.getAssigns().size()
				+ " assign --------------");
		// --- Tạo dữ liệu theo Giảng Viên hoặc sinh viên
		if (mem.isTeacher()) {
			// --- Duyệt danh sách bài tập của member trên
			for (Assign a : assigns.findByAuthor(mem.getId())) {

				// --- Tạo một AssignVM mới tham chiếu Assign
				AssignVM assign = new AssignVM(a.getId(), a.getTopic(),
						a.getDeadline(), a.getInfo(), a.getCmap().getAuthor()
								.getId(),
						a.getCmap().getAuthor().getFullname(), 100, new Date(),
						a.getFeedbacks().size(), a.getCmap().getConcepts()
								.size(), a.getMembers().size());
				// --- Thêm vào list
				list.add(assign);
			}
		} else {
			// --- Duyệt danh sách bài tập của member trên
			for (Assign a : mem.getAssigns()) {
				// ---- Trả về feedback ứng với member trong danh sách
				FeedBack feed = findByUser(mem.getId(), a.getFeedbacks());
				// --- Tạo một AssignVM mới tham chiếu Assign
				AssignVM assign = new AssignVM(a.getId(), a.getTopic(),
						a.getDeadline(), a.getInfo(), a.getCmap().getAuthor()
								.getId(),
						a.getCmap().getAuthor().getFullname(),
						(feed != null) ? feed.getScore() : -1,
						feed != null ? feed.getDate_create() : new Date(), a
								.getFeedbacks().size(), a.getCmap()
								.getConcepts().size(), a.getMembers().size());
				// --- Thêm vào list
				list.add(assign);
			}
		}

		return list;
	}

	public FeedBack findByUser(int mem_id, Set<FeedBack> feeds) {
		// --- Duyệt danh sách feeds, trả về feed có mem_id trùng mới nhất
		for (FeedBack feed : feeds) {
			if (feed.getCmap().getAuthor().getId() == mem_id)
				return feed;
		}
		return null;
	}

	@Override
	public AssignVM add(AssignPost assign) {
		// --- Truy vấn đến cmap trong assign yêu cầu
		CMap cmap = cmaps.findById(assign.getCmap_id());
		// --- Tạo mới một đối tượng assign
		Assign temp = new Assign(assign.getTopic(), assign.getInfo(),
				assign.getDeadline(), cmap);
		// --- Lưu vào dữ liệu
		assigns.save(temp);
		return findById(temp.getId());
	}

	@Override
	public boolean delete(int assign_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean postUser(int assign_id, int mem_id, String username) {

		// --- Truy vấn member có id trên
		Member mem = mems.findById(mem_id);

		// --- Truy vấn bài tập từ id
		Assign assign = assigns.findById(assign_id);

		// --- Kiểm tra quyền sở hữu : 1 NGười tạo
		if (assign.getCmap().ownership(username) != 1)
			return false;

		// --- Kiểm tra đã giao bài tập chưa
		if (mem.getAssigns().contains(assign))
			return false;

		mem.getAssigns().add(assign);
		mems.save(mem);

		return true;
	}

	@Override
	public Set<MemberVM> getMembers(int id) {
		// --- Khởi tạo danh sách chứa member
		Set<MemberVM> list = new HashSet<MemberVM>(0);
		// --- Truy vấn assign có id trên
		Assign assign = assigns.findById(id);
		// --- Duyệt danh sách member
		for (Member m : mems.findAll()) {
			// --- Nếu m là người tạo thì bỏ qua
			if (assign.getCmap().getAuthor().equals(m))
				continue;
			// --- Nếu là teacher bỏ qua
			if (m.isTeacher())
				continue;
			// --- Nếu đã gán thì bỏ qua
			if (assign.getMembers().contains(m))
				continue;
			// --- Khởi tạo đối tượng MemberVM từ m
			MemberVM mem = new MemberVM(m.getId(), m.getFullname(),
					"Sinh Viên", m.getUsername());
			list.add(mem);
		}

		return list;
	}

	public int compare(int cmap_id, int assign_id) {
		// --- Tìm cmap theo id
		CMap cmap = cmaps.findById(cmap_id);

		// --- Tìm assign theo id
		Assign assign = assigns.findById(assign_id);

		// --- Nếu 1 trong 2 cái không tồn tại thì hủy
		if (cmap == null || assign == null)
			return -1;

		// --- Tạo mới một thực thể Feedback
		FeedBack feed = new FeedBack(-1, new Date(), assign, cmap);

		// --- Bắt đầu quá trình chấm bài

		// --- Lây danh sách Relation của đáp án
		Set<Relation> relationKey = assign.getCmap().getRelations();

		// --- Lấy danh sách Realation của bài làm
		Set<Relation> relationList = cmap.getRelations();

		// --- Duyệt từng phần tử trong danh sách đáp án
		for (Relation key : relationKey) {
			// --- Duyệt qua danh sách bài làm
			for (Relation relation : relationList) {
				log.info("-- Compare relation " + relation.getTitle() + " với "
						+ key.getTitle());

				// --- Lấy điểm trả về từ so sánh
				int score = relation.isPass(key);

				// --- Nếu đúng hoàn toàn
				if (score == 3) {

					// --- Ghi history
					log.info(relation.getTitle() + " Đúng hoàn toàn !");

					// --- Gán pass của from concept đúng với id concept đáp án
					relation.getFrom().setPass(key.getFrom().getId());

					// --- Gán pass của to concept đúng với id concept đáp án
					relation.getTo().setPass(key.getTo().getId());

					// --- Gán pass của relation với id relation đáp án
					relation.setPass(key.getId());
					// --- Cập nhật vào cơ sở dữ liệu
					relations.save(relation);

				}
				// --- Nếu chỉ 2 concept đúng còn quan hệ sai
				if (score == 2) {

					// --- Ghi history
					log.info(relation.getTitle() + " chỉ đúng 2 concept !");

					// --- Gán pass của from concept đúng với id concept đáp án
					relation.getFrom().setPass(key.getFrom().getId());

					// --- Gán pass của to concept đúng với id concept đáp án
					relation.getTo().setPass(key.getTo().getId());

					// --- Cập nhật xuống cơ sở dữ liệu
					relations.save(relation);

				}
			}
		}

		// --- Lấy tổng số concept và relation của cmap đáp án
		int c = assign.getCmap().getConcepts().size()
				+ assign.getCmap().getRelations().size();

		// --- Truy vấn lại cmap để lấy cập nhật sau việc chấm bài
		cmap = cmaps.findById(cmap_id);

		// --- Lấy số concept và relation đúng
		int pass = cmap.pass();

		// --- Tính điểm : số concept + relation đúng / c + r, + 0.5 để làm tròn
		// số chính xác
		int sum = (int) ((pass == 0) ? 0 : (float) pass / c * 100 + 0.5);

		log.info("-- Kết thúc chấm điểm, điểm tổng cộng là : " + sum + "|"
				+ pass + "/" + c);
		// --- Gán điểm cho feedback mới
		feed.setScore(sum);


		// --- Lưu feedback mới
		feeds.save(feed);

		return sum;

	}

}
