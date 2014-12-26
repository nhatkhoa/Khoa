package cmap.services;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import cmap.entity.Member;
import cmap.model.AccountVM;
import cmap.model.MemberVM;
import cmap.repositories.MemberRepository;
@Service
public class Members implements MemberService {

	// --- Tự động gán các đối tượng service
	@Autowired
	MemberRepository mems;

	@Autowired
	CMapService cmaps;

	@Autowired
	AssignService assigns;

	private Logger log = Logger.getLogger(this.getClass());
	@Override
	public boolean addMember(String username, String password, String fullname,	boolean teacher) 
	{
		// --- Truy vấn member có username trên nếu trùng thì trả về false
		Member m = mems.findByUsername(username);
		if(m != null)
			return false;
		// --- Mã hóa password md5
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		// --- Tạo một đối Member mới
		Member mem = new Member(username, md5.encodePassword(password, null), fullname, teacher);
		// --- Gọi JpaRepsitory Member lưu đối tượng này vào cơ sở dữ liệu
		mems.save(mem);
		// --- Trả về id của member
		return true;
	}

	@Override
	public AccountVM findById(int id) {
		log.info("------------------ Lấy tài khoản " + id + " --------------");
		// --- Tim member có id trùng
		Member mem = mems.findById(id);
		// --- Nếu không tồn tại member có id trên
		if (mem == null)
			return null;
		// --- Khởi tạo một AccountVM mới có thông tin ứng với member trên
		AccountVM account = new AccountVM(mem.getId(), mem.getUsername(),
				mem.getFullname(), mem.isTeacher());
		// --- Gán danh sách cmaps thông qua username;
		account.setCmaps(cmaps.getList(mem.getUsername()));
		// --- Gán danh sách assign thông qua username;
		account.setAssigns(assigns.getList(mem.getUsername()));
		// --- Trả về tài khoản chứa thông tin
		return account;
	}

	@Override
	public AccountVM findByUsername(String username) {
		log.info("---------------- Lấy thông tin của " + username + "----------------");
		// --- Tim member trùng username
		Member mem = mems.findByUsername(username);
		// --- Nếu không tồn tại member có id trên
		if (mem == null)
			return null;
		// --- Khởi tạo một AccountVM mới có thông tin ứng với member trên
		AccountVM account = new AccountVM(mem.getId(), mem.getUsername(),
				mem.getFullname(), mem.isTeacher());
		// --- Gán danh sách cmaps thông qua user id;
		account.setCmaps(cmaps.getList(username));
		// --- Gán danh sách assign thông qua user id;
		account.setAssigns(assigns.getList(username));
		// --- Trả về tài khoản chứa thông tin
		return account;
	}

	@Override
	public Set<MemberVM> getList() {
		// --- Khởi tạo một list member view model trống
		Set<MemberVM> members = new HashSet<MemberVM>(0);
		// --- Với mỗi mem trong CSDL : tạo ra một MemberVM mới thêm vào danh
		// sách
		for (Member m : mems.findAll()) {
			members.add(new MemberVM(m.getId(), m.getFullname(),
					(m.isTeacher()) ? "Giảng Viên" : "Sinh Viên", m
							.getUsername()));
		}
		return members;
	}

	@Override
	public boolean updateMember(int id, String password, String fullname, boolean teacher) {
		// --- Tim member có id trùng
		Member mem = mems.findById(id);
		// --- Nếu không tồn tại member có id trên
		if (mem == null)
			return false;
		// --- Cập nhật thông tin thành viên
		mem.setFullname(fullname);
		mem.setPassword(password);
		mem.setTeacher(teacher);
		// --- Lưu vào cơ sở dữ liệu
		mems.save(mem);
		return true;
	}

}
