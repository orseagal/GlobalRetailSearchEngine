package com.websiteSearch.entity;

public class Element {

	private String id;

	private String title;

	private String description;

	private double price;

	private String image;

	private String currency;

	private String viewItemURL;

	private String webSource;
	
	private int counter;

	
	public Element(String id, String title, String description, double price, String image, String currency,
			String viewItemURL, String webSource, int counter) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.image = image;
		this.currency = currency;
		this.viewItemURL = viewItemURL;
		this.webSource = webSource;
		this.counter = counter;
	}

	public Element() {
	}

	public String getId() {
		return id;
	}

	public String setId(String id) {
		return this.id = id;
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

	public String getWebSource() {
		return webSource;
	}

	public void setWebSource(String webSource) {
		this.webSource = webSource;
	}

	
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}



	@Override
	public String toString() {
		return "Element [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
				+ ", image=" + image + ", currency=" + currency + ", viewItemURL=" + viewItemURL + ", webSource="
				+ webSource + ", counter=" + counter + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

//    @Override
//    public boolean equals(Object obj) {
//
//        Element element = (Element)obj;
//
//        if(title.equals(element.title) &&
//                id.equals(element.id) &&
//                image.equals(element.image))
//                
//               
//        {
//            hashCode = element.hashCode;
//            return true;
//        }else{
//            hashCode = super.hashCode();
//            return false;
//        }
//    }
}
