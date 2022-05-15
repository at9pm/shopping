package com.jacky.shopping.entity;

public class ShoppingRecord {
	
	String itemString;
	int quantity;
	double price;

	public ShoppingRecord(String itemString, int quantity, double price) {
		super();
		this.itemString = itemString;
		this.quantity = quantity;
		this.price = price;
	}

	public String getItemString() {
		return itemString;
	}
	public void setItemString(String itemString) {
		this.itemString = itemString;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
