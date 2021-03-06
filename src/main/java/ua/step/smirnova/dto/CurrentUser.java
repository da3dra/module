package ua.step.smirnova.dto;

import org.springframework.security.core.authority.AuthorityUtils;

import ua.step.smirnova.entities.Role;
import ua.step.smirnova.entities.User;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
	private static final long serialVersionUID = 1L;

	private User user;

	public CurrentUser(User user) {
		super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public Integer getId() {
	
		return user.getId();
	}

	public String getName() {
		return user.getUsername();
	}

	public Role getRole() {
		return user.getRole();
	}

}