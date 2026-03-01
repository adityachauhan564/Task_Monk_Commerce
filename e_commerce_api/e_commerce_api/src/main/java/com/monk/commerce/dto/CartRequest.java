package com.monk.commerce.dto;

import java.util.List;

public class CartRequest {
	
	private List<CartItem> items;

	//getters & setters
	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	

}
