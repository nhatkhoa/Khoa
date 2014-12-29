package cmap.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cmap.model.AssignPost;
import cmap.model.AssignVM;
import cmap.model.DocVM;
import cmap.model.FeedBackVM;
import cmap.model.ListUpload;
import cmap.model.MemberVM;
import cmap.services.AssignService;
import cmap.services.FeedBackService;

// --- Sử dụng giao thức RestFul service chỉ trả về body dưới dạng json
@RestController
// --- Url mặc định cho controller này xiziu.com/data/{action}
@RequestMapping("/data/assigns")
public class AssignController {
	// --- Tự động liên kết assign service : Dependency injection
	@Autowired
	private AssignService assigns;

	@Autowired
	private FeedBackService feeds;

	@Autowired
	private ServletContext servle;

	// --- /data/assigns/1 : Lấy bài tập theo id
	@RequestMapping("/get/{id}")
	public AssignVM get(@PathVariable("id") int id) {

		return null;
	}

	// --- /data/assigns : Action Tạo bài tập mới
	@RequestMapping(method = RequestMethod.POST)
	public AssignVM create(@RequestBody AssignPost assign) {
		System.out.print(assign.getDeadline());
		return assigns.add(assign);
	}

	// --- /data/assigns/submit : Action Nộp bài tập
	// --- Vì quyền post trong controller này chỉ dành cho giáo viên nên ta sử
	// dụng GET
	@RequestMapping(value = "/submit/{id}/{cmap_id}", method = RequestMethod.GET)
	public int submit(@PathVariable("id") int id,
			@PathVariable("cmap_id") int cmap_id) {
		return assigns.compare(cmap_id, id);
	}

	// --- /data/assigns/2/feedback : Action Lấy feedback
	@RequestMapping(value = "/{id}/feedback", method = RequestMethod.GET)
	public ResponseEntity<FeedBackVM> feedback(@PathVariable("id") int id,
			Principal prin) {

		// --- Gọi dịch vụ trả về feedback, sử dụng username để xác nhận người
		// dùng
		FeedBackVM feed = feeds.findByAssign(id, prin.getName());
		// --- Nếu không tồn tài feed thì báo not found
		if (feed == null)
			return new ResponseEntity<FeedBackVM>(HttpStatus.NOT_FOUND);
		// --- Trả về với status Chấp Nhận yêu cầu
		return new ResponseEntity<FeedBackVM>(feed, HttpStatus.ACCEPTED);

	}

	// --- /data/assigns/1/members : Lấy danh sách thành viên chưa share bài tập
	@RequestMapping("/{id}/members")
	public Set<MemberVM> members(@PathVariable("id") int id) {
		return assigns.getMembers(id);

	}

	// --- /data/assigns/1/3 : Giao bài tập cho sinh viên
	@RequestMapping(value = "/{id}/{mem_id}")
	public ResponseEntity<String> postUser(@PathVariable("id") int id,
			@PathVariable("mem_id") int mem_id, Principal prin) {
		// --- Gọi dịch vụ thực thi giao bài tập
		if (assigns.postUser(id, mem_id, prin.getName()))
			// --- Nếu thành công thì trả về chấp nhận
			return new ResponseEntity<String>(HttpStatus.ACCEPTED);
		// --- Nếu không thành công trả về not found
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

	}

	// --- /data/assigns/1/uploads : Lấy danh sách upload cho assign
	@RequestMapping(value = "/{id}/uploads")
	public ResponseEntity<ListUpload> uploadList(@PathVariable("id") int id) {
		// --- Gọi dịch vụ lấy ListUpload
		return new ResponseEntity<ListUpload>(assigns.getUpload(id),
				HttpStatus.OK);
	}

	// --- /data/assigns/docs/1 : Lấy danh sách tài liệu của concept
	@RequestMapping(value = "/docs/{id}")
	public ResponseEntity<Set<DocVM>> getDocs(@PathVariable("id") int id) {
		// --- Gọi dịch vụ lấy List tài liệu theo id của concept
		return new ResponseEntity<Set<DocVM>>(assigns.getDocs(id),
				HttpStatus.OK);
	}

	// --- /data/assigns/1/uploads : Thêm link tài liệu
	@RequestMapping(value = "/{id}/posturl", method = RequestMethod.POST)
	public ResponseEntity<DocVM> postUrl(@PathVariable("id") int id,
			@RequestBody String url) {

		return new ResponseEntity<DocVM>(assigns.uploadDoc(id, url),
				HttpStatus.OK);
	}

	// --- /data/assigns/docs/3/delete : Xóa tài liệu
	@RequestMapping(value = "docs/{id}/delete", method = RequestMethod.POST)
	public ResponseEntity<String> postUrl(@PathVariable("id") int id) {
		if (assigns.deleteDoc(id))
			return new ResponseEntity<String>(HttpStatus.OK);
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}

	// --- /data/assigns/docs : upload tài liệu
	@RequestMapping(value = "/docs", method = RequestMethod.POST, headers = "content-type=multipart/*")
	public ResponseEntity<String> upload(@RequestParam("id") int concept_id,
			@RequestParam("file") MultipartFile file) {
		System.out.print("--- Upload file : " + file.getOriginalFilename());
		// --- Nếu tồn tại file được chọn
		try {
			// --- Lấy tên file /document/31313_kacaaca.pdf
			String vir = "documents/" + concept_id + "_"
					+ file.getOriginalFilename();
			String name = servle.getRealPath("/") + "/" + vir;
			// --- Đọc file upload lên
			byte[] bytes = file.getBytes();
			// --- Stream buffer file
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(name)));
			// --- Ghi file
			stream.write(bytes);
			stream.close();

			// --- Thêm url tại liệu mới vào concept
			assigns.uploadDoc(concept_id, vir);
			// --- Trả về đường dẫn file
			return new ResponseEntity<String>(vir, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
		}

	}
}
