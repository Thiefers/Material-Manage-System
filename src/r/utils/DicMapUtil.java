package r.utils;

import java.util.Map;

import r.service.DicService;
import r.service.impl.DicServiceImpl;

public class DicMapUtil {

	private static DicService dicService = new DicServiceImpl();
	/**
	 * String==text==����
	 * List<DicValue>==value==���Ͷ�Ӧ��ֵ
	 */
	public static Map<String, String> dicMap = dicService.getAll();
	
}