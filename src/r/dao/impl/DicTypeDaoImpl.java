package r.dao.impl;

import java.util.List;

import r.dao.DicTypeDao;
import r.domain.DicType;

public class DicTypeDaoImpl extends BaseDao implements DicTypeDao {

	@Override
	public List<DicType> getTypeList() {
		String sql = "select * from t_dic_type";
		return queryForList(DicType.class, sql);
	}

}
