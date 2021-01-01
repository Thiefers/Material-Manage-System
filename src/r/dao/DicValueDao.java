package r.dao;

import java.util.List;

import r.domain.DicValue;

public interface DicValueDao {

	List<DicValue> getValueListByCode(String code);

}
