package r.utils;

import java.util.Map;

import r.service.DicService;
import r.service.impl.DicServiceImpl;

public class DicMapUtil {

	private static DicService dicService = new DicServiceImpl();
	/**
	 * String==text==类型
	 * List<DicValue>==value==类型对应的值
	 */
	public static Map<String, String> dicMap = dicService.getAll();
	
}