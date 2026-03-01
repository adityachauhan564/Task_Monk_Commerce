package com.monk.commerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monk.commerce.dto.ApplicableCouponResponse;
import com.monk.commerce.dto.CartWrapper;
import com.monk.commerce.dto.DiscountResponse;
import com.monk.commerce.entity.Coupon;
import com.monk.commerce.service.CouponApplicationService;
import com.monk.commerce.service.CouponService;

@RestController
@RequestMapping("/coupons")
public class CouponController {
	
	private final CouponService couponService;
    private final CouponApplicationService applicationService;

    public CouponController(CouponService couponService,
                            CouponApplicationService applicationService) {
        this.couponService = couponService;
        this.applicationService = applicationService;
    }

    @PostMapping
    public Coupon create(@RequestBody Coupon coupon) {
        return couponService.create(coupon);
    }

    @GetMapping
    public List<Coupon> getAll() {
        return couponService.getAll();
    }

    @PostMapping(
    	    value = "/applicable-coupons",
    	    consumes = "application/json",
    	    produces = "application/json"
    	)
    	public ApplicableCouponResponse applicable(
    	        @RequestBody CartWrapper wrapper) {

    	    List<DiscountResponse> list =
    	            applicationService.getApplicableCoupons(wrapper.getCart());

    	    return new ApplicableCouponResponse(list);
    	}
	
	
 }
