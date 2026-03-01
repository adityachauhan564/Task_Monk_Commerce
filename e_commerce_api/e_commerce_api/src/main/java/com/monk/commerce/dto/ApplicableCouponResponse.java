package com.monk.commerce.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicableCouponResponse {

    @JsonProperty("applicable_coupons")
    private List<DiscountResponse> applicableCoupons;

    public ApplicableCouponResponse(List<DiscountResponse> applicableCoupons) {
        this.applicableCoupons = applicableCoupons;
    }

    public List<DiscountResponse> getApplicableCoupons() {
        return applicableCoupons;
    }
}