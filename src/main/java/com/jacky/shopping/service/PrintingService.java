package com.jacky.shopping.service;

import java.util.ArrayList;

public class PrintingService {

	public void printReceipt(ArrayList<String[]> receiptContentStrings) {
		for (String[] receiptContentString : receiptContentStrings) {
			printLine(receiptContentString);
		}
		System.out.println("--------end of receipt-------");
	}

	private void printLine(String[] receiptContentString) {
		char[] oneLineArray = new char[60];
		oneLineArray = prepareOneLineArray(receiptContentString);
		System.out.println(oneLineArray);
	}

	private char[] prepareOneLineArray(String[] receiptContentString) {
		char[] returnArray = new char[60];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 20; j++) {
				if (j < receiptContentString[i].length()) {
					returnArray[20 * i + j] = receiptContentString[i].charAt(j);
				} else {
					returnArray[20 * i + j] = ' ';
				}
			}
		}
		return returnArray;
	}

}
