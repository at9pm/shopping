package com.jacky.shopping;

import com.jacky.shopping.config.ConfigProvider;
import com.jacky.shopping.controller.FileController;
import com.jacky.shopping.service.FileService;
import com.jacky.shopping.service.PrintingService;

public class App 
{
    public static void main( String[] args )
    {
    	ConfigProvider configProvider = new ConfigProvider();
    	PrintingService printingService = new PrintingService();
    	FileService fileService = new FileService(configProvider);
    	FileController fileController = new FileController(configProvider, fileService, printingService);
    	try {
			fileController.readInput();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
