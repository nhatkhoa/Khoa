package cmap.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cmap.model.CMapDetail;
import cmap.model.ShareVM;
import cmap.services.CMapService;
import cmap.services.MemberService;

//--- Sử dụng giao thức RestFul service chỉ trả về body dưới dạng json
@RestController
// --- Url mặc định cho controller này xiziu.com/data/{action}
@RequestMapping(value = "/data/cmaps")
public class CMapController {

	// --- Tự động Liên kết đối tượng cần sử dụng
	@Autowired
	private CMapService cmaps;

	// --- Tạo đối tượng ghi sử lược các hoạt động lên console
	Logger log = Logger.getLogger(CMapController.class);

	@Autowired
	private MemberService mems;

	@RequestMapping(value = "/khoa", method = RequestMethod.POST)
	public String test(@RequestBody String cmap) {
		return cmap;
	}

	// --- Action Tạo mới concept map
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CMapDetail> create(@RequestBody CMapDetail cmap,
			Principal prin) {
		log.info("--> Action : Tạo mới concept map " + cmap.getTitle());
		// --- Lấy username người dùng hiện tại
		String username = prin.getName();
		try {
			// --- Gọi service tạo cmap mới từ dữ liệu client đưa lên
			int id = cmaps.create(cmap, username);
			// --- Nếu không tạo được cmap mới thì trả về thất bại
			if (id == -1)
				return new ResponseEntity<CMapDetail>(HttpStatus.BAD_REQUEST);
			// --- Gọi service lấy cmap theo id
			CMapDetail temp = cmaps.findById(id, username);

			return new ResponseEntity<CMapDetail>(temp, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			log.info("--> Yêu cầu tạo cmap mới thất bại : " + e.getMessage());
			return new ResponseEntity<CMapDetail>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ResponseEntity<String> delete(@PathVariable("id") int id,
			Principal prin) {
		String username = prin.getName();
		// --- Gọi service xóa concept map;
		if (cmaps.delete(id, username))
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

	}

	// --- Action lấy thông tin chi tiết của một CMAP
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CMapDetail> get(@PathVariable("id") int id,
			Principal prin) {
		// --- Lấy username người dùng hiện tại
		String username = prin.getName();
		// --- Gọi service lấy dữ liệu CMap
		CMapDetail cmap = cmaps.findById(id, username);
		if (cmap == null) {
			return new ResponseEntity<CMapDetail>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CMapDetail>(cmap, HttpStatus.ACCEPTED);
	}

	// --- Action get danh sách chia sẽ và chưa chia sẽ theo cmap id
	@RequestMapping(value = "/{id}/members", method = RequestMethod.GET)
	public ResponseEntity<ShareVM> share(@PathVariable("id") int id) {
		try {
			// --- Trả về thông tin chia sẽ của cmap trên
			return new ResponseEntity<ShareVM>(cmaps.getShare(id),
					HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ShareVM>(HttpStatus.NOT_FOUND);
		}

	}

	// --- Chia sẽ concept map
	@RequestMapping(value = "/{id}/share", method = RequestMethod.POST)
	public ResponseEntity<String> share(@RequestBody List<Integer> listUser,
			@PathVariable("id") int id, Principal prin) {
		// --- Lấy username người dùng hiện tại
		String username = prin.getName();

		try {
			// --- Gọi service share concept map cho danh sach id của member
			cmaps.share(id, listUser, username);
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

	}

	// --- Hủy chia sẽ của một cmap với một user
	@RequestMapping(value = "/{id}/unshare/{user}", method = RequestMethod.GET)
	public ResponseEntity<String> unShare(@PathVariable("id") int id,
			@PathVariable("user") int user, Principal prin) {
		try {
			// --- Lấy username người dùng hiện tại
			String username = prin.getName();
			
			log.info("Yêu cầu hủy chia sẽ của " + username);
			// --- Gọi service cmaps hủy chia sẽ
			cmaps.unShare(user, id, username);
			// --- Trả về : chấp nhận yêu cầu
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
