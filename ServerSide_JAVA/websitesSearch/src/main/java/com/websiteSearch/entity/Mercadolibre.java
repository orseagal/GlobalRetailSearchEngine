package com.websiteSearch.entity;

public class Mercadolibre {
	
	private int id;
	private String keywords;
	private String location;
	private int priceRange_min;
	private int priceRange_max;
	private String conversionTo;
	private String condition;
	private String category;
	private String sortBy;
	
	
	
	public Mercadolibre() {
		super();
	}
	
	public Mercadolibre(int id, String keywords, String location, int priceRange_min, int priceRange_max,
			String conversionTo, String condition, String category, String sortBy) {
		super();
		this.id = id;
		this.keywords = keywords;
		this.location = location;
		this.priceRange_min = priceRange_min;
		this.priceRange_max = priceRange_max;
		this.conversionTo = conversionTo;
		this.condition = condition;
		this.category = category;
		this.sortBy = sortBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPriceRange_min() {
		return priceRange_min;
	}
	public void setPriceRange_min(int priceRange_min) {
		this.priceRange_min = priceRange_min;
	}
	public int getPriceRange_max() {
		return priceRange_max;
	}
	public void setPriceRange_max(int priceRange_max) {
		this.priceRange_max = priceRange_max;
	}
	public String getConversionTo() {
		return conversionTo;
	}
	public void setConversionTo(String conversionTo) {
		this.conversionTo = conversionTo;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	@Override
	public String toString() {
		return "Mercadolibre [id=" + id + ", keywords=" + keywords + ", location=" + location + ", priceRange_min="
				+ priceRange_min + ", priceRange_max=" + priceRange_max + ", conversionTo=" + conversionTo
				+ ", condition=" + condition + ", category=" + category + ", sortBy=" + sortBy + "]";
	}
	
	
	
	
}
