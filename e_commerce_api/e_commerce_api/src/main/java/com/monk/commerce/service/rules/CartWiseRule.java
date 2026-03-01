package com.monk.commerce.service.rules;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.commerce.dto.CartRequest;
import com.monk.commerce.dto.DiscountResponse;
import com.monk.commerce.entity.Coupon;
import com.monk.commerce.util.CartUtils;

@Component("CART_WISE")
public class CartWiseRule implements CouponEligibility {
	
	 private final ObjectMapper mapper = new ObjectMapper();

	    @Override
	    public boolean isApplicable(CartRequest cart, Coupon coupon) {
	    	 try {
	    	        Map<String, Object> details =
	    	                mapper.readValue(coupon.getCouponDetails(), Map.class);

	    	        double threshold =
	    	                ((Number) details.get("threshold")).doubleValue();

	    	        double total = CartUtils.calculateTotal(cart);

	    	        // 👇 ADD DEBUG HERE
	    	        System.out.println("Cart Total = " + total);
	    	        System.out.println("Threshold = " + threshold);

	    	        return total  >= threshold;

	    	    } catch (Exception e) {
	    	        return false;
	    	    }
	    }

	    @Override
	    public DiscountResponse applyCoupon(CartRequest cart, Coupon coupon) {
	        try {
	            Map<String, Object> details =
	                    mapper.readValue(coupon.getCouponDetails(), Map.class);

	            double percent =
	                    ((Number) details.get("discountPercentage")).doubleValue();

	            double total = CartUtils.calculateTotal(cart);
	            double discount = total * percent / 100;

	            return new DiscountResponse(
	                    coupon.getCouponId(),
	                    coupon.getType().name().toLowerCase().replace("_", "-"),//cart-wise
	                    discount
	            );

	        } catch (Exception e) {
	            throw new RuntimeException("Error applying cart coupon");
	        }

     }
}
