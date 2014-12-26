package cmap.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cmap.entity.CMap;
import cmap.entity.Concept;
import cmap.entity.Member;
import cmap.entity.Relation;
import cmap.model.CMapDetail;
import cmap.model.CMapVM;
import cmap.model.ConceptVM;
import cmap.model.MemberVM;
import cmap.model.RelationVM;
import cmap.model.ShareVM;
import cmap.repositories.CMapRepository;
import cmap.repositories.ConceptRepository;
import cmap.repositories.MemberRepository;
import cmap.repositories.RelationRepository;

@Service
public class CMaps implements CMapService {

	@Autowired
	private CMapRepository cmaps;

	@Autowired
	private ConceptRepository concepts;

	private Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private MemberRepository mems;

	@Autowired
	private RelationRepository relations;

	@Override
	public int create(CMapDetail cmap, String username) {
		log.info(" ----> Tạo Concept map mới với từ " + username );
		try {
			// ---- Tìm member thông qua author_id
			Member author = mems.findByUsername(username);

			// ---- Tạo thực thể CMap mới
			CMap entityCMap = new CMap(cmap.getTitle().trim(), cmap.getInfo(), author);
			cmaps.save(entityCMap);

			// ---- Danh sách Node (ứng với Concept)
			ArrayList<ConceptVM> listNode = cmap.getNodeDataArray();

			// ---- Danh sách Link (ứng với Relation)
			ArrayList<RelationVM> listLink = cmap.getLinkDataArray();

			// ---- Duyệt qua tất cả concept view model trong danh sách các conceptvm(node)
			for (ConceptVM conceptvm : listNode) {

				// --- Tạo mới một entity Concept
				Concept temp = new Concept(conceptvm.getText().trim(), conceptvm.getLoc(),
						entityCMap);
				concepts.save(temp);

				// --- Thay đổi tất key của các link có chứa key concept cũ
				// --- Ngay từ đầu link được trỏ đến những key concept tạm thời
				// (vd : -1,-2...)
				// --- Việc thêm mới concept thì sẽ tạo ra một id (key) mới cho
				// concept này (vd : 12,13,14...)
				// --- Để đảm bảo liên kết trước được thì phải cập nhật lại tất
				// cả.
				updateLinkArray(conceptvm.getKey(), temp.getId(), listLink);
			}

			// ---- Tạo mới các quan hệ
			for (RelationVM relation : listLink) {
				// ---- Lấy concept To và From từ cơ sở dữ liệu (đã được tạo từ
				// bên trên)
				// --- From và To đã đươc cập nhật lại key tương ứng ở trên
				Concept from = concepts.findById(relation.getFrom());
				Concept to = concepts.findById(relation.getTo());
				// --- Tạo mới một đối tượng Relation
				Relation temp = new Relation(from, to, relation.getText().trim(),
						entityCMap);
				relations.save(temp);
			}

			cmaps.save(entityCMap);

			return entityCMap.getId();
		}

		// ---- Nếu không tồn tại users trong yêu cầu trả về thì catch lỗi trả
		// về -1
		catch (Exception e) {
			return -1;
		}

	}

	@Override
	public boolean delete(int cmap_id, String username) {
		log.info("-- Xóa Concept map "+ cmap_id + " bởi " + username);
		try {
			// --- Tìm cmap theo id
			CMap cmap = cmaps.findById(cmap_id);

			// --- Kiểm tra quyền sở hữu (quyền chủ sở hữu)
			if (cmap.ownership(username) != 1)
				return false;

			cmaps.delete(cmap);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public CMapDetail findById(int cmap_id, String username) {
		// --- Tìm kiếm CMap theo id
		CMap cmap = cmaps.findById(cmap_id);
		if (cmap == null)
			return null;

		// --- Kiểm tra quyền sở hữu theo username
		if (cmap.ownership(username) == 0)
			return null;

		// --- Khởi tạo một CmapDetail mới bằng dữ liệu cmap
		CMapDetail temp = new CMapDetail(cmap.getId(), cmap.getTitle(),
				cmap.getInfo(), cmap.getAuthor().getId());

		// --- Duyệt qua danh sách concept của cmap
		for (Concept concept : cmap.getConcepts()) {
			// --- Tạo đối tượng ConceptVM mới từ concept đang duyệt tới
			ConceptVM con = new ConceptVM(concept.getId(), concept.getName(),
					concept.getLoc());
			// --- Thêm con vào danh sách
			temp.getNodeDataArray().add(con);
		}

		// --- Duyệt danh sách relation của cmap
		for (Relation relation : cmap.getRelations()) {
			// --- Tạo RelationVM mới từ relation đang duyệt tới
			RelationVM rela = new RelationVM(relation.getFrom().getId(),
					relation.getTo().getId(), relation.getTitle());
			temp.getLinkDataArray().add(rela);
		}

		return temp;
	}

	@Override
	public Set<CMapVM> getList(String username) {
		// --- Khởi tạo danh sách chứa CMapVM
		Set<CMapVM> list = new HashSet<CMapVM>(0);
		// --- Tìm member trùng id trên
		Member mem = mems.findByUsername(username);
		// --- Nếu không tồn tại member trên thì return null
		if (mem == null)
			return null;
		// --- Lấy danh sách concept map của cá nhân
		// --- Với một CMap từ danh sách truy vấn theo user id -> chuyển thành
		// CMapVM
		for (CMap c : mem.getCmaps()) {
			// --- Khởi tạo mới một đối tượng CMapVM
			CMapVM temp = new CMapVM(c.getId(), c.getTitle(), c.getInfo(),
					c.getDate_create(), c.getAuthor().getId(), c.getAuthor()
							.getFullname());
			list.add(temp);
		}
		// --- Lấy danh sách concept map được chia sẽ
		for (CMap c : mem.getShares()) {
			// --- Khởi tạo mới một đối tượng CMapVM
			CMapVM temp = new CMapVM(c.getId(), c.getTitle(), c.getInfo(),
					c.getDate_create(), c.getAuthor().getId(), c.getAuthor()
							.getFullname());
			list.add(temp);
		}
		return list;
	}

	@Override
	public ShareVM getShare(int cmap_id) {
		log.info("---------------- Lấy danh sách chia sẽ của cmap " + cmap_id
				+ "----------------");
		// --- Truy vấn đến cmap có id trên
		CMap cmap = cmaps.findById(cmap_id);
		// --- Tạo đối tượng ShareVM mới chứa danh sách member chưa share và đã share
		ShareVM shares = new ShareVM(cmap_id);
		// --- Với mỗi mem trong danh sách thành viên
		for (Member m : mems.findAll()) {
			// --- Nếu m là người sở hữu thì bỏ qua
			if(cmap.getAuthor() == m)
				continue;
			
			// --- Tạo một MemberVM mới
			MemberVM temp = new MemberVM(m.getId(), m.getFullname(),
					(m.isTeacher()) ? "Giảng Viên" : "Sinh Viên",
					m.getUsername());
			
			// --- cmap này chứa member đang duyệt thì thêm vào danh sách share
			if (cmap.getMembers().contains(m)) {
				shares.getShare().add(temp);
			} else {
				// --- nếu không thì thêm vào danh sách unshare
				shares.getUnshare().add(temp);
			}
		}
		return shares;
	}

	@Override
	public boolean share(int cmap_id, List<Integer> list, String username) {
		// --- Tìm cmap theo id
		CMap cmap = cmaps.findById(cmap_id);

		// --- Nếu không tồn tại cmap yêu cầu thì trả về false
				if (cmap == null)
					return false;
				
		// --- Kiểm quyền sở hữu là người tạo ra
		if (cmap.ownership(username) != 1)
			return false;

		// --- Duyệt qua danh sách các id của user cần chia sẽ
		// --- với mỗi id user thì lấy user và thêm vào concept map
		for (int mem_id : list) {
			// --- Tìm member theo id
			Member mem = mems.findById(mem_id);

			// --- Nếu danh sách share không chứa cmap trên và member này không
			// phải là người tạo ra cmap
			if (!mem.getShares().contains(cmap) && cmap.getAuthor() != mem) {
				// --- Thêm cmap này vào danh sách được chia sẽ
				mem.getShares().add(cmap);
				// --- Cập nhật thay đổi
				mems.save(mem);
			}
		}
		return true;
	}

	@Override
	public boolean unShare(int mem_id, int cmap_id, String username) {
		// --- Tìm member có id trùng với mem_id
		Member mem = mems.findById(mem_id);
		// --- Lấy cmap ứng với cmap_id trong danh sách share của mem trên
		CMap cmap = mem.findInShare(cmap_id);
		// --- Nếu không tồn tại (chưa được share)
		if (cmap == null)
			return false;
		// --- Kiểm tra quyền sở hữu ( chỉ được hủy chia sẽ do mình tạo ra)
		if (!cmap.getAuthor().getUsername().contains(username))
			return false;
		// --- Xóa cmap khỏi danh sách share của mem trên
		mem.getShares().remove(cmap);
		// --- Cập nhật thay đổi lên csdl
		mems.save(mem);

		return true;
	}

	@Override
	public CMapVM update(CMapVM cmap, String username) {
		// --- Truy vấn cmap tương ứng
		CMap temp = cmaps.findById(cmap.getId());
		// --- Xóa trống concept và relation cũ
		temp.getConcepts().clear();
		temp.getRelations().clear();
		// --- Lưu lại
		cmaps.save(temp);
		
		return null;
	}

	// --- Cập nhật lại danh sách liên kết của các concept
	// --- o là key (id) của concept cũ
	// --- n là key (id) của concept đó sau khi đã được thêm vào CSDL
	// --- Quét tìm tất cả các to và from của liên kết nếu == key cũ thì thay
	// bằng key mới
	public void updateLinkArray(int o, int n, ArrayList<RelationVM> listLink) {
		// --- Duyệt qua tất cả các liên kết
		for (RelationVM relation : listLink) {
			// --- Nếu from của liên kết == key cũ thì cập nhật lại key mới cho nó
			if (relation.getFrom() == o)
				relation.setFrom(n);

			if (relation.getTo() == o)
				relation.setTo(n);
		}
	}

}
