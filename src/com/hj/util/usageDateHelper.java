package com.hj.util;

public class usageDateHelper {
	
	/**zeroth cat
	 * a helper function that returns the days in the month that the user inputed
	 * @param year the year of the month inputed
	 * @param month the month inputed to find out how many days
	 * @return an int indicating the days in the month
	 */
	public static int days_in_month(int year, int month){
		
		//a way compare to see if the year is a leap year
		int leap_year_sample = 2000;
		
		if (month<1 || month>12){
			System.out.println("there is no month as the one you inputed");
			return -1;
		}
		
		//it is a leap year
		if ((year-leap_year_sample)%4==0){
			switch(month){
				case 1: return 31;

				case 2: return 29;
				
				case 3: return 31;
				
				case 4: return 30;
				
				case 5: return 31;
				
				case 6: return 30;
				
				case 7: return 31;
				
				case 8: return 31;
				
				case 9: return 30;
				
				case 10: return 31;
				
				case 11: return 30;
				
				case 12: return 31;
				
				default: return -1;
			}
		}
		
		else{
			switch(month){
			case 1: return 31;

			case 2: return 28;
			
			case 3: return 31;
			
			case 4: return 30;
			
			case 5: return 31;
			
			case 6: return 30;
			
			case 7: return 31;
			
			case 8: return 31;
			
			case 9: return 30;
			
			case 10: return 31;
			
			case 11: return 30;
			
			case 12: return 31;
			
			default: return -1;
			}
		}
		
	}
}
