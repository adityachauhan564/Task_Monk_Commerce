package com.monk.commerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartItem {
	
	 @JsonProperty("product_id")
	private Long productId;
	 
	 @JsonProperty("quantity")
	private int productQuantity;
	
	 @JsonProperty("price")
	private double productPrice;
	
	//getters & Setters 

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	

	
}
