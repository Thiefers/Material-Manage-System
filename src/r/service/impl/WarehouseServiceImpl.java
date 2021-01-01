package r.service.impl;

import java.util.List;

import r.dao.WarehouseDao;
import r.dao.impl.WarehouseDaoImpl;
import r.domain.Warehouse;
import r.service.WarehouseService;
import r.utils.DicMapUtil;
import r.utils.UUIDUtil;

public class WarehouseServiceImpl implements WarehouseService {

	private WarehouseDao warehouseDao = new WarehouseDaoImpl();

	@Override
	public boolean addWarehouse(String nameProp, String addrProp, String stateProp) {
		boolean flag = true;
		Warehouse warehouse = new Warehouse();
		warehouse.setId(UUIDUtil.getUUID());
		warehouse.setName(nameProp);
		warehouse.setAddr(addrProp);
		warehouse.setState(DicMapUtil.dicMap.get(stateProp));
		int count = warehouseDao.addWarehouse(warehouse);
		if (count != 1) {
			flag = false;
		}
		return flag;
	}

	@Override
	public List<Warehouse> queryWarehouse(String nameProp, String addrProp, String stateProp) {
		Warehouse warehouse = new Warehouse();
		warehouse.setName(nameProp);
		warehouse.setAddr(addrProp);
		if ("¿ª²Ö&±Õ²Ö".equals(stateProp)) {
			warehouse.setState("");
		} else {
			warehouse.setState(DicMapUtil.dicMap.get(stateProp));
		}
		return warehouseDao.queryWarehouse(warehouse);
	}

	@Override
	public boolean delWarehouse(List<String> warehouseIdList) {
		boolean flag = true;
		int allCount = warehouseIdList.size();
		int delCount = warehouseDao.delWarehouse(warehouseIdList);
		if (allCount != delCount) {
			flag = false;
		}
		return flag;
	}

	@Override
	public int updateWarehouseState(String id, String stateChange) {
		Warehouse warehouse = new Warehouse();
		warehouse.setId(id);
		warehouse.setState(DicMapUtil.dicMap.get(stateChange));
		return warehouseDao.updateWarehouseState(warehouse);
	}

}
