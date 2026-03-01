package com.monk.commerce.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.monk.commerce.dto.CartRequest;
import com.monk.commerce.dto.DiscountResponse;
import com.monk.commerce.entity.Coupon;
import com.monk.commerce.repository.CouponRepository;
import com.monk.commerce.service.rules.CouponEligibility;
import com.monk.commerce.service.rules.CouponRuleFactory;

@Service
public class CouponApplicationService {
	
	private final CouponRepository repository;
    private final CouponRuleFactory factory;

    public CouponApplicationService(CouponRepository repository,
                                     CouponRuleFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public List<DiscountResponse> getApplicableCoupons(CartRequest cart) {

        return repository.findAll()
                .stream()
                .filter(Coupon::isActive)
                .filter(c -> c.getCouponExpireDate() == null ||
                        c.getCouponExpireDate().isAfter(LocalDateTime.now()))
                .map(c -> {
                    CouponEligibility strategy =
                            factory.getCouponEligibility(c.getType());
                    if (strategy.isApplicable(cart, c)) {
                        return strategy.applyCoupon(cart, c);
                    }
                    return null;
                })
                .filter(r -> r != null)
                .collect(Collectors.toList());

   }
}
