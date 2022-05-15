package com.jacky.shopping.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static double roundUp(double value, double nearest) {
		return nearest * (Math.ceil(Math.abs(value / nearest)));
	}
}
