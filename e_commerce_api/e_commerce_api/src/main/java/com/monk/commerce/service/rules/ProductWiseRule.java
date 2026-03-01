package com.monk.commerce.service.rules;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.commerce.dto.CartRequest;
import com.monk.commerce.dto.DiscountResponse;
import com.monk.commerce.entity.Coupon;

@Component("PRODUCT_WISE")
public class ProductWiseRule implements CouponEligibility {

	    private final ObjectMapper mapper = new ObjectMapper();

	    @Override
	    public boolean isApplicable(CartRequest cart, Coupon coupon) {
	        try {
	            Map<String, Object> details =
	                    mapper.readValue(coupon.getCouponDetails(), Map.class);

	            Long productId =
	                    Long.valueOf(details.get("productId").toString());

	            return cart.getItems()
	                    .stream()
	                    .anyMatch(i -> i.getProductId().equals(productId));

	        } catch (Exception e) {
	            return false;
	        }
	    }

	    @Override
	    public DiscountResponse applyCoupon(CartRequest cart, Coupon coupon) {
	        try {
	            Map<String, Object> details =
	                    mapper.readValue(coupon.getCouponDetails(), Map.class);

	            Long productId =
	                    Long.valueOf(details.get("productId").toString());

	            double percent =
	                    ((Number) details.get("discountPercentage")).doubleValue();

	            double discount = cart.getItems()
	                    .stream()
	                    .filter(i -> i.getProductId().equals(productId))
	                    .mapToDouble(i -> i.getProductPrice() * i.getProductQuantity() * percent / 100)
	                    .sum();

	            return new DiscountResponse(
	                    coupon.getCouponId(),
	                    coupon.getType().name().toLowerCase().replace("_", "-"),
	                    discount
	            );

	        } catch (Exception e) {
	            throw new RuntimeException("Error applying product coupon");
	        }

  }
}
