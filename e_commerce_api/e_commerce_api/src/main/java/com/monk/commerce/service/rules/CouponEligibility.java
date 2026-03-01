package com.monk.commerce.service.rules;

import com.monk.commerce.dto.CartRequest;
import com.monk.commerce.dto.DiscountResponse;
import com.monk.commerce.entity.Coupon;

public interface  CouponEligibility {
	
	boolean isApplicable(CartRequest cart, Coupon coupon);
	
	DiscountResponse applyCoupon(CartRequest cart, Coupon coupon);

}
