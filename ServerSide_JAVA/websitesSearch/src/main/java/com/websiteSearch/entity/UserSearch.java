package com.websiteSearch.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_search")
public class UserSearch {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	private String keywords;
	private String websites;
	private String blacklist;
	
	
	
	public UserSearch(String keywords, String websites, String blacklist) {
		super();
		this.keywords = keywords;
		this.websites = websites;
		this.blacklist = blacklist;
	}

	public UserSearch(String blacklist) {
		this.blacklist = blacklist;
	}

	public UserSearch() {
		super();
	}
	
	
	
	
}
