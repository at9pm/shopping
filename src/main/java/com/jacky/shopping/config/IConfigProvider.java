package com.jacky.shopping.config;

public interface IConfigProvider {

	String getReceiptLocation();
	void setReceiptLocation(String receiptLocation);
	Double getTaxRate(String locationString);

}