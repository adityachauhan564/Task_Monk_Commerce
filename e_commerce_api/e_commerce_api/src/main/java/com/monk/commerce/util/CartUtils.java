package com.monk.commerce.util;

import com.monk.commerce.dto.CartRequest;

public class CartUtils {
	
		
		public static double calculateTotal(CartRequest cart) {
		    return cart.getItems()
		            .stream()
		            .mapToDouble(i -> i.getProductPrice() * i.getProductQuantity())
		            .sum();
		}
		
	}

