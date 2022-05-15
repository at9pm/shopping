package com.jacky.shopping.config;

import java.util.ArrayList;

public enum TAX_RATE {

	CA(0.0975),
	NY(0.0875);

	private double taxRate;
	
	public double getTaxRate() {
		return taxRate;
	}

	TAX_RATE(double taxRate) {
		this.taxRate = taxRate;
	}
	
}
