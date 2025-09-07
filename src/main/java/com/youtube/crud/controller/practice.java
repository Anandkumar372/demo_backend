package com.youtube.crud.controller;

import java.util.ArrayList;
import java.util.List;

public class practice {
	
	public static void main(String[] args) {
		
		
		int [] numbers = {1, 2, 3, 4, 5,6, 7, 8, 9, 10};	
		
		List<Integer> evennumbers =new ArrayList<>();
		
		for(int i=0; i<numbers.length; i++) {
			if(numbers[i] % 2 == 0) {
				evennumbers.add(numbers[i]);
			}
		}
		
//		for (int number : numbers) {
//            if (number % 2 != 0) {
//            	evennumbers.add(number);
//            }
//}
		
		System.out.println("the even numbers are: "+evennumbers);
	
}
}
