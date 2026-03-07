package com.monk.commerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monk.commerce.dto.ApplicableCouponResponse;
import com.monk.commerce.dto.CartWrapper;
import com.monk.commerce.dto.DiscountResponse;
import com.monk.commerce.entity.Coupon;
import com.monk.commerce.repository.CouponRepository;
import com.monk.commerce.service.CouponApplicationService;
import com.monk.commerce.service.CouponService;



@RestController
@RequestMapping("/coupons")
public class CouponController {
	
	private final CouponService couponService;
    private final CouponApplicationService applicationService;
    private final CouponRepository couponRepository;

    public CouponController(CouponService couponService,
                            CouponApplicationService applicationService, CouponRepository couponRepository) {
        this.couponService = couponService;
        this.applicationService = applicationService;
        this.couponRepository=couponRepository;
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
    
  
//get by coupon id    
    
@GetMapping("/{id}")
public ResponseEntity<?> getCouponById(@PathVariable Long id){
	
	Optional<Coupon> coupon= couponRepository.findById(id);
	
	if(coupon.isPresent()) {
		return ResponseEntity.ok(coupon.get());
	}
	
	   return ResponseEntity.status(HttpStatus.NOT_FOUND)
			   .body("Coupon not found of id :"+ id);
	
}
    
    //update coupon by id 

@PutMapping("/{id}")
public ResponseEntity<?> updateCoupon(
        @PathVariable Long id,
        @RequestBody Coupon updatedCoupon) {

    Optional<Coupon> existingCoupon = couponRepository.findById(id);

    if (existingCoupon.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Coupon not found of id: " + id);
    }

    Coupon coupon = existingCoupon.get();

    coupon.setType(updatedCoupon.getType());
    coupon.setCouponDetails(updatedCoupon.getCouponDetails());
    coupon.setActive(updatedCoupon.isActive());
    coupon.setCouponExpireDate(updatedCoupon.getCouponExpireDate());

    couponRepository.save(coupon);

    return ResponseEntity.ok(coupon);
}
    
    // delete a coupon by id 

@DeleteMapping("/{id}")
public ResponseEntity<?> deleteCoupon(@PathVariable Long id) {

    Optional<Coupon> coupon = couponRepository.findById(id);

    if (coupon.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Coupon not found of id: " + id);
    }

    couponRepository.deleteById(id);

    return ResponseEntity.ok("Coupon deleted successfully");
}
    
    // apply a specific coupon by id 
	
@PostMapping("/apply-coupon/{id}")
public ResponseEntity<?> applyCoupon(
        @PathVariable Long id,
        @RequestBody CartWrapper cartWrapper) {

    Optional<Coupon> couponOptional = couponRepository.findById(id);

    if (couponOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Coupon not found");
    }

    Coupon coupon = couponOptional.get();

    double discount = couponService.calculateDiscount(cartWrapper.getCart(), coupon);

    double totalPrice = cartWrapper.getCart().getItems().stream()
            .mapToDouble(i -> i.getProductPrice() * i.getProductQuantity())
            .sum();

    double finalPrice = totalPrice - discount;

    Map<String, Object> response = new HashMap<>();

    response.put("total_price", totalPrice);
    response.put("total_discount", discount);
    response.put("final_price", finalPrice);

    return ResponseEntity.ok(response);
}
 }
