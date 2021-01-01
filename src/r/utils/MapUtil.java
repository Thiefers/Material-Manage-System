package r.utils;

import java.lang.reflect.Field;

import r.myAnnotation.MyField;

public class MapUtil {

	public static String getColumnName(Field field) {
		if (field.isAnnotationPresent(MyField.class)) {
			MyField myField = field.getAnnotation(MyField.class);
			String columnName = myField.columnName();
			return columnName;
		}
		return null;
	}
	
	public static String getColumnType(Field field) {
		if (field.isAnnotationPresent(MyField.class)) {
			MyField myField = field.getAnnotation(MyField.class);
			String columnType = myField.type();
			return columnType;
		}
		return null;
	}

//	public static void main(String[] args) {
//		try {
//			System.out.println(getColumnName(User.class.getDeclaredField("lockState")));
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	}
}
