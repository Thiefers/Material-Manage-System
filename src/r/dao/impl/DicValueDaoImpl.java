package r.dao.impl;

import java.util.List;

import r.dao.DicValueDao;
import r.domain.DicValue;

public class DicValueDaoImpl extends BaseDao implements DicValueDao {

	@Override
	public List<DicValue> getValueListByCode(String code) {
		String sql = "select * from t_dic_value where `typeCode`=? order by orderNo";
		return queryForList(DicValue.class, sql, code);
	}

}
