package com.monk.commerce.service.rules;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monk.commerce.dto.CartItem;
import com.monk.commerce.dto.CartRequest;
import com.monk.commerce.dto.DiscountResponse;
import com.monk.commerce.entity.Coupon;

@Component("BXGY")
public class BxGyRule implements CouponEligibility {

	    private final ObjectMapper mapper = new ObjectMapper();

	    @Override
	    public boolean isApplicable(CartRequest cart, Coupon coupon) {
	        try {
	            Map<String, Object> details =
	                    mapper.readValue(coupon.getCouponDetails(), Map.class);

	            List<Integer> buyProducts =
	                    (List<Integer>) details.get("buyProducts");

	            int buyQty =
	                    ((Number) details.get("buyQuantity")).intValue();

	            long totalBuy = cart.getItems().stream()
	                    .filter(i -> buyProducts.contains(i.getProductId().intValue()))
	                    .mapToLong(CartItem :: getProductQuantity)
	                    .sum();

	            return totalBuy >= buyQty;

	        } catch (Exception e) {
	            return false;
	        }
	    }

	    @Override
	    public DiscountResponse applyCoupon(CartRequest cart, Coupon coupon) {

	        try {
	            Map<String, Object> details =
	                    mapper.readValue(coupon.getCouponDetails(), Map.class);

	            List<Integer> buyProducts =
	                    (List<Integer>) details.get("buyProducts");

	            List<Integer> getProducts =
	                    (List<Integer>) details.get("getProducts");

	            int buyQty =
	                    ((Number) details.get("buyQuantity")).intValue();

	            int getQty =
	                    ((Number) details.get("getQuantity")).intValue();

	            int repetitionLimit =
	                    ((Number) details.get("repetitionLimit")).intValue();

	            long totalBuy = cart.getItems().stream()
	                    .filter(i -> buyProducts.contains(i.getProductId().intValue()))
	                    .mapToLong(CartItem :: getProductQuantity)
	                    .sum();

	            int applicableTimes =
	                    (int) Math.min(totalBuy / buyQty, repetitionLimit);

	            double discount = cart.getItems().stream()
	                    .filter(i -> getProducts.contains(i.getProductId().intValue()))
	                    .mapToDouble(i ->
	                            Math.min(i.getProductQuantity(),
	                                    applicableTimes * getQty) * i.getProductPrice())
	                    .sum();

	            return new DiscountResponse(
	                    coupon.getCouponId(),
	                    coupon.getType().name().toLowerCase().replace("_", "-"),
	                    discount
	            );

	        } catch (Exception e) {
	            throw new RuntimeException("Error applying BXGY coupon");
	        }
	    }	

   }
