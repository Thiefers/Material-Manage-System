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
		// ��ʽ
		String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
		// ʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		if ("������Ч".equals(source)) {
			date = new Date();
		} else {
			try {
				date = sdf.parse(source);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// ����
		Font font = new Font("Times New Roman", Font.BOLD, 14);
		// ���
		Dimension dimension = new Dimension(150, 24);

		datepick = new DatePicker(date, DefaultFormat, font, dimension);

		// ���ù���
		datepick.setLocale(Locale.CHINA);
		// ����ʱ�����ɼ�
		datepick.setTimePanleVisible(true);
		return datepick;
	}

	public static DatePicker getDatePicker() {
		final DatePicker datepick;
		// ��ʽ
		String DefaultFormat = "yyyy-MM-dd HH:mm:ss";
		// ʱ��
		Date date = new Date();
		// ����
		Font font = new Font("Times New Roman", Font.BOLD, 14);
		// ���
		Dimension dimension = new Dimension(150, 24);

		datepick = new DatePicker(date, DefaultFormat, font, dimension);

		// ���ù���
		datepick.setLocale(Locale.CHINA);
		// ����ʱ�����ɼ�
		datepick.setTimePanleVisible(true);
		return datepick;
	}

	public static DatePicker getDatePickerAndNoTimePanle() {
		final DatePicker datepick;
		// ��ʽ
		String DefaultFormat = "yyyy-MM-dd";
		// ʱ��
		Date date = new Date();
		// ����
		Font font = new Font("Times New Roman", Font.BOLD, 14);
		// ���
		Dimension dimension = new Dimension(90, 24);

		datepick = new DatePicker(date, DefaultFormat, font, dimension);

		// ���ù���
		datepick.setLocale(Locale.CHINA);
		// ����ʱ�����ɼ�
		datepick.setTimePanleVisible(false);
		return datepick;
	}

}