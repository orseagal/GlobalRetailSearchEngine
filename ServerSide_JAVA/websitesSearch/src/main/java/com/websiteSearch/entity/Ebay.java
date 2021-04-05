package com.websiteSearch.entity;

public class Ebay {

	private int id;
	private String keywords;
	private String location;
	private int priceRange_min;
	private int priceRange_max;
	private String conversionTo;
	private String condition;
	private String category;
	private String buyingFormat;
	private String returns;
	private boolean ExpeditedShippingType;
	private boolean FreeShippingOnly;
	private int handlingTime;
	private String sortBy;
	
	
	public Ebay() {}
	
	
	public Ebay(int id, String keywords, String location, int priceRange_min, int priceRange_max, String condition,String category,
			String buyingFormat, String returns, boolean expeditedShippingType, boolean freeShippingOnly,
			int handlingTime, String sortBy ,String conversionTo) {
		super();
		this.id = id;
		this.keywords = keywords;
		this.location = location;
		this.priceRange_min = priceRange_min;
		this.priceRange_max = priceRange_max;
		this.condition = condition;
		this.category = category;
		this.buyingFormat = buyingFormat;
		this.returns = returns;
		ExpeditedShippingType = expeditedShippingType;
		FreeShippingOnly = freeShippingOnly;
		this.handlingTime = handlingTime;
		this.sortBy = sortBy;
		this.conversionTo =conversionTo;
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
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getBuyingFormat() {
		return buyingFormat;
	}
	public void setBuyingFormat(String buyingFormat) {
		this.buyingFormat = buyingFormat;
	}
	public String getReturns() {
		return returns;
	}
	public void setReturns(String returns) {
		this.returns = returns;
	}
	public boolean isExpeditedShippingType() {
		return ExpeditedShippingType;
	}
	public void setExpeditedShippingType(boolean expeditedShippingType) {
		ExpeditedShippingType = expeditedShippingType;
	}
	public boolean isFreeShippingOnly() {
		return FreeShippingOnly;
	}
	public void setFreeShippingOnly(boolean freeShippingOnly) {
		FreeShippingOnly = freeShippingOnly;
	}
	public int getHandlingTime() {
		return handlingTime;
	}
	public void setHandlingTime(int handlingTime) {
		this.handlingTime = handlingTime;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	
	
	public String getConversionTo() {
		return conversionTo;
	}


	public void setConversionTo(String conversionTo) {
		this.conversionTo = conversionTo;
	}


	@Override
	public String toString() {
		return "Ebay [id=" + id + ", keywords=" + keywords + ", location=" + location + ", priceRange_min="
				+ priceRange_min + ", priceRange_max=" + priceRange_max + ", conversionTo=" + conversionTo
				+ ", condition=" + condition + ", category=" + category + ", buyingFormat=" + buyingFormat
				+ ", returns=" + returns + ", ExpeditedShippingType=" + ExpeditedShippingType + ", FreeShippingOnly="
				+ FreeShippingOnly + ", handlingTime=" + handlingTime + ", sortBy=" + sortBy + "]";
	}



	
	

}
