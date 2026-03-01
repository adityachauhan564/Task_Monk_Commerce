package com.monk.commerce.entity;

import java.time.LocalDateTime;

import com.monk.commerce.enums.CouponType;	

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long couponId;
	
	@Enumerated(EnumType.STRING)
	private CouponType type;
	
	@Column(columnDefinition = "TEXT")
	private String couponDetails ;
	
	private LocalDateTime couponExpireDate;
	
	private boolean active;
	
	// getters and setters 

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public String getCouponDetails() {
		return couponDetails;
	}

	public void setCouponDetails(String couponDetails) {
		this.couponDetails = couponDetails;
	}

	public LocalDateTime getCouponExpireDate() {
		return couponExpireDate;
	}

	public void setCouponExpireDate(LocalDateTime couponExpireDate) {
		this.couponExpireDate = couponExpireDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	

}
