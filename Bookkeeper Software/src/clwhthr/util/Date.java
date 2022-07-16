package clwhthr.util;

import java.time.LocalDate;
import java.time.ZoneId;

import clwhthr.exception.DateFormatException;

public class Date implements Comparable<Date>{
	private int year;
	private int month;
	private int day;
	public Date(int year, int month, int day) throws DateFormatException {
		this.year = year;
		this.month = month;
		this.day = day;
		if(Date.qualified(this) == false)throw new DateFormatException();
	}
	public static Date getToday() {
		java.util.Date date = new java.util.Date();
		ZoneId timeZone = ZoneId.systemDefault();
		LocalDate getLocalDate = date.toInstant().atZone(timeZone).toLocalDate();
		try {
			return new Date(getLocalDate.getYear(), getLocalDate.getMonthValue(), getLocalDate.getDayOfMonth());
		} catch (DateFormatException e) {
			e.printStackTrace();
			return null;
		}
	}
	public int getYear() {
		return this.year;
	}
	public int getMonth() {
		return this.month;
	}
	public int getDay() {
		return this.day;
	}
	private static boolean qualified(Date date) {
		int year = date.year;
		int month = date.month;
		int day = date.day;
		if(date.year <= 0 || date.month <= 0 || date.day <=0 || date.month > 12 || date.day > 31)return false;
		if((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month ==12)&& day <= 31)return true;
		if ((month == 4 || month == 6 || month == 9 || month == 11) && day <= 30)return true;
		if(month == 2) {
			if(isLeap(year))return day <= 29;
			else return day <= 28;
		}
		return true;
	}
	
	//¶|¦~
	private static boolean isLeap(int year)
	{
	    return((((year) % 4) == 0 && ((year) % 100) != 0) || ((year) % 400) == 0);
	}
	@Override
	public int hashCode() {
		return year*10000 + month*100 + day;
	}
	@Override
	public String toString() {
		return String.valueOf(hashCode());
	}
	@Override
	public boolean equals(Object obj) {
		if((obj instanceof Date) == false)return false;
		return this.hashCode() == obj.hashCode();
	}
	@Override
	public int compareTo(Date date) {
		return Integer.compare(this.hashCode(), date.hashCode());
	}
	
}
