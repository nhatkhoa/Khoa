package cmap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

import cmap.services.Users;

@Configuration
public class AuthConfig extends
		GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private Users users;
	// --- Gán userService cho Cơ chế đăng nhập phân quyền
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(users);
	}
}