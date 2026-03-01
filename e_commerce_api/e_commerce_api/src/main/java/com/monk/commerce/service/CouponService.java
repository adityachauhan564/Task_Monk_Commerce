package com.monk.commerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.monk.commerce.entity.Coupon;
import com.monk.commerce.repository.CouponRepository;

@Service
public class CouponService {
	
	private final CouponRepository repository;

    public CouponService(CouponRepository repository) {
        this.repository = repository;
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
    
}
