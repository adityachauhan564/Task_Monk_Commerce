package com.monk.commerce.service.rules;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.monk.commerce.enums.CouponType;

@Component
public class CouponRuleFactory {
	
	private final Map<String, CouponEligibility> rules;
	
	 public CouponRuleFactory(Map<String, CouponEligibility> rules) {
	        this.rules = rules;
	
	 }
	
	  public CouponEligibility getCouponEligibility(CouponType type) {
	            return rules.get(type.name());
	        }
    
   }
 