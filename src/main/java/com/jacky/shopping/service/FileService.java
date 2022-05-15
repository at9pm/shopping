package com.jacky.shopping.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.jacky.shopping.config.CATEGORY;
import com.jacky.shopping.config.IConfigProvider;
import com.jacky.shopping.config.TAX_EXEMPT;
import com.jacky.shopping.config.TAX_RATE;
import com.jacky.shopping.entity.ShoppingRecord;
import com.jacky.shopping.util.MathUtil;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class FileService {

	IConfigProvider configProvider;
	Double subTotoalDouble;
	Double taxDouble;
	String locationString;

	public FileService(IConfigProvider configProvider) {
		super();
		this.configProvider = configProvider;
	}

	public ArrayList<String[]> printReceipt(String absolutePath) throws IOException, CsvException {

		ArrayList<String[]> receiptStrings = new ArrayList<String[]>();
		try (CSVReader reader = new CSVReader(new FileReader(absolutePath))) {
			subTotoalDouble = 0.00;
			taxDouble= 0.00;
			List<String[]> csvContent = reader.readAll();
			locationString = readLocation(csvContent);
			ArrayList<ShoppingRecord> shoppingRecords = getShoppingRecords(csvContent);
			receiptStrings = prepareReceipt(shoppingRecords, locationString);
		} catch (Exception e) {
			System.out.println("error handling csv at " + absolutePath);
		}
		return receiptStrings;
	}

	private ArrayList<String[]> prepareReceipt(ArrayList<ShoppingRecord> shoppingRecords, String locationString) {
		ArrayList<String[]> returnString = new ArrayList<>();
		returnString = prepareReceiptHeader(returnString);
		for (ShoppingRecord shoppingRecord : shoppingRecords) {
			returnString = prepareReceiptContent(shoppingRecord, returnString, locationString);
		}
		returnString = prepareReceiptSubtotal(returnString);
		returnString = prepareReceiptTax(returnString, taxDouble);
		returnString = prepareReceiptTotal(returnString, subTotoalDouble, taxDouble);
		return returnString;
	}

	private ArrayList<String[]> prepareReceiptTotal(ArrayList<String[]> returnString, Double subTotoalDouble, Double taxDouble) {
		Double totalDouble = subTotoalDouble + taxDouble;
		Double roundDouble = MathUtil.round(totalDouble, 2);
		String receiptLine[] = new String[3];
		receiptLine[0] = "total:";
		receiptLine[1] = "";
		receiptLine[2] = "$" + roundDouble;
		returnString.add(receiptLine);
		return returnString;
	}

	private ArrayList<String[]> prepareReceiptTax(ArrayList<String[]> returnString, Double taxDouble) {
		Double roundDouble = MathUtil.round(taxDouble, 2);
		String receiptLine[] = new String[3];
		receiptLine[0] = "tax:";
		receiptLine[1] = "";
		receiptLine[2] = "$" + roundDouble;
		returnString.add(receiptLine);
		return returnString;
	}

	private ArrayList<String[]> prepareReceiptSubtotal(ArrayList<String[]> returnString) {
		Double roundDouble = MathUtil.round(subTotoalDouble, 2);
		String receiptLine[] = new String[3];
		receiptLine[0] = "subtotal:";
		receiptLine[1] = "";
		receiptLine[2] = "$" + roundDouble;
		returnString.add(receiptLine);
		return returnString;
	}

	private ArrayList<String[]> prepareReceiptContent(ShoppingRecord shoppingRecord, ArrayList<String[]> returnString, String locationString) {
		String receiptLine[] = new String[3];
		String itemString = shoppingRecord.getItemString();
		Double priceDouble = shoppingRecord.getPrice();
		int quantity = shoppingRecord.getQuantity();
		Double itemTaxRateDouble = getItemTaxRateDouble(locationString, itemString);

		taxDouble += MathUtil.roundUp(priceDouble * quantity* itemTaxRateDouble, 0.05) ;
		receiptLine[0] = itemString;
		receiptLine[1] = Double.toString(priceDouble);
		receiptLine[2] = String.valueOf(quantity);
		returnString.add(receiptLine);
		subTotoalDouble += priceDouble * quantity;
		return returnString;
	}

	private Double getItemTaxRateDouble(String locationString, String itemString) {
		Double locationTaxRate = TAX_RATE.valueOf(locationString).getTaxRate();
		Boolean isExemptTaxBoolean = false;
		try {
			ImmutableList<String> exemptCategories = (ImmutableList<String>) TAX_EXEMPT.class.getDeclaredField(locationString).get(null);
			for (String exemptCategory : exemptCategories) {
				ImmutableList<String> exemptItems = (ImmutableList<String>) CATEGORY.class.getDeclaredField(exemptCategory).get(null);
				if (exemptItems.contains(itemString)){
					isExemptTaxBoolean = true;
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (isExemptTaxBoolean) {
			return 0.0;
		} else {
			return locationTaxRate;
		}
	}

	private ArrayList<String[]> prepareReceiptHeader(ArrayList<String[]> returnString) {
		String receiptLine[] = new String[3];
		receiptLine[0] = "item";
		receiptLine[1] = "price";
		receiptLine[2] = "qty";
		returnString.add(receiptLine);
		return returnString;
	}

	private String readLocation(List<String[]> csvContent) {
		String[] firstRow = csvContent.get(0);
		return firstRow[0];
	}

	private ArrayList<ShoppingRecord> getShoppingRecords(List<String[]> csvContent) {
		ArrayList<ShoppingRecord> shoppingRecords = new ArrayList<ShoppingRecord>();
		for (int i = 1; i < csvContent.size(); i++) {
			String[] line = csvContent.get(i);
			String itemString = line[0];
			int quantity = Integer.parseInt(line[1]);
			double price = Double.parseDouble(line[2]);
			ShoppingRecord shoppingRecord = new ShoppingRecord(itemString, quantity, price);
			shoppingRecords.add(shoppingRecord);
		}
		return shoppingRecords;
	}
}