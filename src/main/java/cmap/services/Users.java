package cmap.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cmap.entity.Member;
import cmap.repositories.MemberRepository;


@Service
public class Users implements UserDetailsService {
	// --- Tự động Inject MemberRepository
	@Autowired
	private MemberRepository repo;

	// --- Viết lại phương thức get Người dùng đang đăng nhập bằng username
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		// ---- Truy vấn thành viên trùng username
		Member user = repo.findByUsername(username);
		// ---- Nếu không tồn tại thành viên nào có username như trên thì trả về null
		if (user == null) {
			return null;
		}
		// --- Khởi tạo danh sách quyền hạn cho người dùng
		List<GrantedAuthority> auth;
		// --- Nếu người dùng là có boolean Teacher là true : thêm quyền hạn TEACHER
		if (user.isTeacher()) {
			auth = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_TEACHER");
		}
		else{
			// --- Thêm quyền sinh viên
			auth = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_STUDENT");
		}
		// --- Lấy mật khẩu 
		String password = user.getPassword();
		// --- Trả về chi tiết đăng nhập người dùng 
		return new org.springframework.security.core.userdetails.User(username, password,
				auth);
	}

}