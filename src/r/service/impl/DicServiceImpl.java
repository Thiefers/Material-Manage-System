package r.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import r.dao.DicTypeDao;
import r.dao.DicValueDao;
import r.dao.impl.DicTypeDaoImpl;
import r.dao.impl.DicValueDaoImpl;
import r.domain.DicType;
import r.domain.DicValue;
import r.service.DicService;

public class DicServiceImpl implements DicService {

	private DicTypeDao dicTypeDao = new DicTypeDaoImpl();
	private DicValueDao dicValueDao = new DicValueDaoImpl();
	
	@Override
	public Map<String, String> getAll() {
		// TODO 自动生成的方法存根
		Map<String, String> map = new HashMap<String, String>();
		List<DicType> dicTypes = dicTypeDao.getTypeList();
		for(DicType dicType : dicTypes) {
			String code = dicType.getCode();
			List<DicValue> dicValues = dicValueDao.getValueListByCode(code);
			for(DicValue dicValue : dicValues) {
				map.put(dicValue.getText(), dicValue.getValue());
			}
		}
		return map;
	}

}
