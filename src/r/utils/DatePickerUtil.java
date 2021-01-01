package r.utils;

import java.awt.Dimension;
import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.eltima.components.ui.DatePicker;

public class DatePickerUtil {

	public static DatePicker getDatePicker(String source) {
		final DatePicker datepick;
		// 格式
		String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
		// 时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if ("永久有效".equals(source)) {
			date = new Date();
		} else {
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 字体
		Font font = new Font("Times New Roman", Font.BOLD, 14);
		// 宽高
		Dimension dimension = new Dimension(150, 24);

		datepick = new DatePicker(date, DefaultFormat, font, dimension);

		// 设置国家
		datepick.setLocale(Locale.CHINA);
		// 设置时钟面板可见
		datepick.setTimePanleVisible(true);
		return datepick;
	}

	public static DatePicker getDatePicker() {
		final DatePicker datepick;
		// 格式
		String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
		// 时间
		Date date = new Date();
		// 字体
		Font font = new Font("Times New Roman", Font.BOLD, 14);
		// 宽高
		Dimension dimension = new Dimension(150, 24);

		datepick = new DatePicker(date, DefaultFormat, font, dimension);

		// 设置国家
		datepick.setLocale(Locale.CHINA);
		// 设置时钟面板可见
		datepick.setTimePanleVisible(true);
		return datepick;
	}

	public static DatePicker getDatePickerAndNoTimePanle() {
		final DatePicker datepick;
		// 格式
		String DefaultFormat = "yyyy-MM-dd";
		// 时间
		Date date = new Date();
		// 字体
		Font font = new Font("Times New Roman", Font.BOLD, 14);
		// 宽高
		Dimension dimension = new Dimension(90, 24);

		datepick = new DatePicker(date, DefaultFormat, font, dimension);

		// 设置国家
		datepick.setLocale(Locale.CHINA);
		// 设置时钟面板可见
		datepick.setTimePanleVisible(false);
		return datepick;
	}

}