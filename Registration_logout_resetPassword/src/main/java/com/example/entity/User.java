package com.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_logout")
public class User {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'ROLE_USER'")
	// Comma-separated list of roles, default to 'ROLE_USER'

	private String roles;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private PasswordResetToken passwordResetToken;

	public User() {

	}

	public User(Long id, String name, String email, String password, String roles,
			PasswordResetToken passwordResetToken) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.passwordResetToken = passwordResetToken;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public PasswordResetToken getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}

	

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", roles=" + roles
				+ ", passwordResetToken=" + passwordResetToken + "]";
	}

}
