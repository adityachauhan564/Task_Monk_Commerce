package com.monk.commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monk.commerce.dto.CartRequest;
import com.monk.commerce.dto.DiscountResponse;
import com.monk.commerce.entity.Coupon;
import com.monk.commerce.repository.CouponRepository;

@Service
public class CouponService {
	
	private final CouponRepository repository;
	private final CouponApplicationService applicationService;

    public CouponService(CouponRepository repository, CouponApplicationService applicationService) {
        this.repository = repository;
        this.applicationService=applicationService;
    }

    public Coupon create(Coupon coupon) {
        return repository.save(coupon);
    }

    public List<Coupon> getAll() {
        return repository.findAll();
    }

    public Coupon getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));
    }

    public void delete(Long id) {
        repository.deleteById(id);	

   }
    
    public double calculateDiscount(CartRequest cart, Coupon coupon) {

        List<DiscountResponse> discounts = applicationService.getApplicableCoupons(cart);

        for (DiscountResponse d : discounts) {
            if (d.getCouponId().equals(coupon.getCouponId())) {
                return d.getDiscount();
            }
        }

        return 0;
    }
    
}
