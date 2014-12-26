package cmap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import cmap.services.Users;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	 // --- Cấu hình phân quyền đăng nhập tài khoản
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// --- Bỏ qua đăng nhập đối với trang welcome
		http
			.csrf().disable() // --- Tắt chức năng bảo vệ qua cross site
			.authorizeRequests()
			.antMatchers("/welcome.html","/login.html",
						"/js/**","/css/**","/fonts/*",
						"/images/*","/signup.html","/register","/database")
						.permitAll() // --- Cho phép những url này không cần đăng nhập
			.antMatchers(HttpMethod.POST,"/data/assigns/**").hasRole("TEACHER") // --- Chỉ member có quyền giảng viên mới được sử dụng
			.anyRequest().authenticated() // --- bất cứ yêu cầu nào khác đều phải đăng nhập
		.and()
			.formLogin().defaultSuccessUrl("/") // --- Đường dẫn khi đăng nhập thành công
			.loginPage("/welcome.html")
			.loginProcessingUrl("/login")
			.failureUrl("/login.html?SaiMatKhau") // --- Đường dẫn khi đăng nhập thất bại
			.and().logout() // --- Đăng Xuất
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // --- Link thoát tài khoản
		.and()
			.exceptionHandling().accessDeniedPage("/access?error"); // --- Link báo lỗi khi bị hạn chế quyền
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		// --- Bật chế độ mã hóa mật khẩu bằng md5
		auth.userDetailsService(getUser()).passwordEncoder(passwordEncoder());
		//super.configure(auth);
	}


	@Bean  
	public Md5PasswordEncoder passwordEncoder() throws Exception {  
	  return new Md5PasswordEncoder();  
	}
	
	@Bean
	public UserDetailsService getUser(){
		return new Users();
	}

	
	
	
	
	
	


}
