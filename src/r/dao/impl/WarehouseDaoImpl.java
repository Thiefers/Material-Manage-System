package r.dao.impl;

import java.util.List;

import r.dao.WarehouseDao;
import r.domain.Warehouse;
import r.utils.JDBCUtil;

public class WarehouseDaoImpl extends BaseDao implements WarehouseDao {

	@Override
	public int addWarehouse(Warehouse warehouse) {
		String sql = "insert into t_warehouse(`id`,`name`,`addr`,`state`) values(?,?,?,?)";
		int count = update(sql, warehouse.getId(), warehouse.getName(), warehouse.getAddr(), warehouse.getState());
		JDBCUtil.commitAndClose();
		return count;
	}

	@Override
	public List<Warehouse> queryWarehouse(Warehouse warehouseProp) {
		String sql = "select * from t_warehouse where `name` like '%' ? '%' and `addr` like '%' ? '%' and `state` like '%' ? '%'";
		List<Warehouse> warehouseList = queryForList(Warehouse.class, sql, warehouseProp.getName(),
				warehouseProp.getAddr(), warehouseProp.getState());
		JDBCUtil.commitAndClose();
		return warehouseList;
	}

	@Override
	public int delWarehouse(List<String> warehouseIdList) {
		// TODO 暂不考虑有其他地方使用着仓库,只考虑是否闭仓
		String sql = "DELETE FROM t_warehouse WHERE `id` IN(";
		StringBuffer sb = new StringBuffer(sql);
		for (int i = 0; i < warehouseIdList.size(); i++) {
			if (i == warehouseIdList.size() - 1) {
				sb = sb.append("?)");
			} else {
				sb = sb.append("?,");
			}
		}
		sql = sb.append(" AND `state`=0").toString();
		int count = update(sql, warehouseIdList);
		JDBCUtil.commitAndClose();
		return count;
	}

	@Override
	public int updateWarehouseState(Warehouse warehouse) {
		String sql = "update t_warehouse set `state`=? where `id`=?";
		int count = update(sql, warehouse.getState(), warehouse.getId());
		JDBCUtil.commitAndClose();
		return count;
	}

	@Override
	public List<Warehouse> queryWarehouseByName(Warehouse warehouse) {
		String sql = "select * from t_warehouse where `name`=?";
		List<Warehouse> warehouseList = queryForList(Warehouse.class, sql, warehouse.getName());
		return warehouseList;
	}

	@Override
	public Warehouse queryWarehouseByNameAndAddr(String warehouseName, String warehouseAddr) {
		String sql = "select * from t_warehouse where name=? and addr=? and state=1";
		return queryForOne(Warehouse.class, sql, warehouseName, warehouseAddr);
	}

}
