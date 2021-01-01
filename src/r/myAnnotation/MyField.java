package r.myAnnotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyField {
	/**
	 * 列名
	 * @return
	 */
	String columnName();
	/**
	 * 类型
	 * @return
	 */
	String type();
	/**
	 * 长度
	 * @return
	 */
	int length();
}
