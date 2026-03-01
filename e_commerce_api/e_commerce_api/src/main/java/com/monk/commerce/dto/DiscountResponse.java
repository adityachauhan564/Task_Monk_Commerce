package com.monk.commerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiscountResponse {
	
	@JsonProperty("coupon_id")
	private Long couponId;
	
	private String type;
	
	private double discount;

	
	public DiscountResponse(Long couponId, String type, double discount) {
		
		this.couponId = couponId;
		this.type = type;
		this.discount = discount;
	}

	// getters 
	
	public Long getCouponId() {
		return couponId;
	}

	public String getType() {
		return type;
	}


	public double getDiscount() {
		return discount;
	}


	
	

}
