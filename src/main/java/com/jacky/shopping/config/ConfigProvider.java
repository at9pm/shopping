package com.jacky.shopping.config;

public class ConfigProvider implements IConfigProvider {

	String receiptLocation;
	Double taxRateDouble;

	public void setReceiptLocation(String receiptLocation) {
		this.receiptLocation = receiptLocation;
	}

	public String getReceiptLocation() {
		return AppConfig.FILEDIRECTORY_STRING;
	}

	public Double getTaxRate(String locationString) {
		return TAX_RATE.valueOf(locationString).getTaxRate();
	}

}