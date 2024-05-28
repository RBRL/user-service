package com.auth.user.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Test {
public static void main(String[] args) {
		
		DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		System.out.println(ZoneId.systemDefault());
		System.out.println(LocalDateTime.now(ZoneId.systemDefault()).format(FORMATTER));
		LocalDateTime datetime = LocalDateTime.now(ZoneId.systemDefault());
		DateTimeFormatter formatters1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		String text1 = datetime.format(formatters1);
		LocalDateTime parsedDate1 = LocalDateTime.parse(text1, formatters1);

		System.out.println("dateTime--: " + datetime);
		System.out.println("Text format--- " + text1);
		System.out.println("parsedDate Date:-- " + parsedDate1);
		System.out.println("parsedDate:-- " + parsedDate1.format(formatters1));
		
		
		//string to localdatetime
		 
		 String strDateTime = "27-05-2024 01:08:43";
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        LocalDateTime odt = LocalDateTime.parse(strDateTime, dtf);
	        System.out.println(odt);


	}
}
