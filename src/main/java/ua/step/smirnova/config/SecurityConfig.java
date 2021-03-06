package ua.step.smirnova.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/")
		.permitAll()
		.antMatchers("/admin**")
		.hasAuthority("ADMIN")
		.antMatchers("/artist/manage**")
		.hasAuthority("ARTIST")
		.and()
		.formLogin()
		.loginPage("/login")
		.failureUrl("/login-error")
		.defaultSuccessUrl("/index")
		.usernameParameter("username")
		.passwordParameter("password")
		.permitAll()
		.and()
		.logout()
		.logoutUrl("/logout")
		.deleteCookies("remember-me")
		.logoutSuccessUrl("/index")
		.permitAll()
		.and()
		.rememberMe();

	}

}