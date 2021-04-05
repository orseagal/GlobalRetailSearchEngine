package com.websiteSearch.entity;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;
	@NotBlank(message="Username is required")
	@Column(name = "username", unique = true)
	private String username;
	@NotBlank(message="Password is required")
	private String password;
	@Email
	@NotEmpty(message="Email is required")
	@Column(name = "email", unique = true)
	private String email;
	
	private Instant created;
	private boolean enabled;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="user_search_id")
	private UserSearch userSearch;
	
	
	
	

}
