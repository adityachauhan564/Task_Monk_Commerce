package com.monk.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monk.commerce.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	

}
