package application;

import java.util.Date;

public class productData {
	private Integer id;
	private String productId;
	private String productName;
	private String type;
	private String status;
	private Integer stock;
	private Double price;
	private Date date;
	private String image;
	
	public productData(Integer id, String productId, String productName,String type, String status, Integer stock, Double price,
			Date date, String imageString) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.type=type;
		this.status = status;
		this.stock = stock;
		this.price = price;
		this.date = date;
		this.image = imageString;
	}
	
	public productData(Integer id, String productId, String productName, Double price, String image) {
		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.image = image;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getType() {
		return this.type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
