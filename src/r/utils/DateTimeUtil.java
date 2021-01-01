package r.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateTimeUtil {

	/**
	 * @return 系统时间
	 */
	public static String getSysTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * @return 日期
	 */
	public static String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String dateStr = sdf.format(date);
		return dateStr;
	}

	/**
	 * @return 时间
	 */
	public static String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String timeStr = sdf.format(date);
		return timeStr;
	}

	public static String addYear(String now) {
		String futureYear = String.valueOf((Integer.parseInt(now.substring(0, 4)) + 100));
		String tail = now.substring(4, now.length());
		return futureYear.concat(tail);
	}
	
	public static String addOneMonth(String month) {
		LocalDate nextMonth = LocalDate.parse(month).plusMonths(1);
		return nextMonth.toString().substring(0, 7);//yyyy-MM
	}
	
	public static String addOneDay(String day) {
		LocalDate nextDay = LocalDate.parse(day).plusDays(1);
		return nextDay.toString();//yyyy-MM-dd
	}

	public static void main(String[] args) {
		LocalDate nextMonth = LocalDate.parse("2020-02-29").plusDays(1);
		String month = nextMonth.toString();
		System.out.println(month);
	}
	
}