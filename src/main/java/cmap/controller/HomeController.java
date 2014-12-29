package cmap.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cmap.services.AssignService;
import cmap.services.FeedBackService;
import cmap.services.MemberService;

@Controller
public class HomeController {

	@Autowired
	MemberService mems;

	// --- Tự động liên kết assign service : Dependency injection
	@Autowired
	private AssignService assigns;

	@Autowired
	private FeedBackService feeds;

	@Autowired
	private ServletContext servle;

	// --- Tạo user tạm thời
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String create(
			@RequestParam("fullname") String fullname,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "teacher", required = false, defaultValue = "false") boolean teacher) {
		// --- Nếu yêu cầu thêm thành viên được chấp nhận
		if (mems.addMember(username, password, fullname, teacher)) {
			return "redirect:login.html";
		}
		return "redirect:signup.html?Ten-tai-khoan-da-ton-tai";
	}

	// --- /data/assigns/1/upload : upload
	@RequestMapping(value = "docs/{id}", method = RequestMethod.POST, headers="content-type=multipart/*")
	public ResponseEntity<String> upload(@PathVariable("id") int concept_id,
			@RequestParam("file") MultipartFile file) {
		System.out.print("--- Upload file : " + file.getOriginalFilename());
		// --- Nếu tồn tại file được chọn
		if (!file.isEmpty()) {
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
		} else {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}

	}

}
