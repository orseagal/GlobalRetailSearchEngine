package com.websiteSearch.entity;

public class Results {

	private long id;
	
	private String title;
	
	private String description;
	
	private double price;
	
	private String image;
	
	private String currency; 

	private String viewItemURL;
	
	

	public Results(long id, String title, String description, double price, String image,String currency ,String viewItemURL) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.image = image;
		this.currency =currency;
		this.viewItemURL = viewItemURL;
	}
	
	public Results() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getViewItemURL() {
		return viewItemURL;
	}

	public void setViewItemURL(String viewItemURL) {
		this.viewItemURL = viewItemURL;
	}
	
	@Override
	public String toString() {
		return "Element [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
				+ ", image=" + image + ", currency=" + currency + ", viewItemURL=" + viewItemURL + "]";
	}
	
	
	
	
	
}
