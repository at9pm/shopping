package com.jacky.shopping.controller;

import java.io.File;
import java.util.ArrayList;

import com.jacky.shopping.config.IConfigProvider;
import com.jacky.shopping.service.FileService;
import com.jacky.shopping.service.PrintingService;

public class FileController {

	FileService fileService;
	PrintingService printingService;
	IConfigProvider configProvider;

	public FileController(IConfigProvider configProvider, FileService fileService, PrintingService printingService) {
		super();
		this.configProvider = configProvider;
		this.fileService = fileService;
		this.printingService = printingService;
	}

	public void readInput() throws Exception {

		String receiptLocation = configProvider.getReceiptLocation();
		File[] files = new File(receiptLocation).listFiles();

		for (File file : files) {
			if (file.isFile()) {
				ArrayList<String[]> receiptContentStrings = fileService.printReceipt(file.getAbsolutePath());
				printingService.printReceipt(receiptContentStrings);
			}
		}
	}

}