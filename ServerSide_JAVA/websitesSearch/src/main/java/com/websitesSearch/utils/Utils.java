package com.websitesSearch.utils;

import java.util.Date;

public class Utils {

	
	
	public static long calculateTotalProcessTime(Date startTime) {
		
		long results =  (new Date().getTime() - startTime.getTime())/1000;
		
		return results;
	}
	
	
//	public static void main(String[] args) {
//		
//		long startTime = new Date().getTime();
//		
//		try {
//			Thread.sleep(7000);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		System.out.println((new Date().getTime() - startTime)/1000);
//		
//	}
}
