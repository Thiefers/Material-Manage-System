package r.controller;

import java.util.List;

import r.domain.Warehouse;
import r.service.WarehouseService;
import r.service.impl.WarehouseServiceImpl;

public class WarehouseController {

	private WarehouseService warehouseService = new WarehouseServiceImpl();

	public WarehouseController() {
	}

	public boolean addWarehouse(String nameProp, String addrProp, String stateProp) {
		// TODO 自动生成的方法存根
		if ("".equals(nameProp) || "".equals(addrProp) || "".equals(stateProp)) {
			return false;
		} else {
			return warehouseService.addWarehouse(nameProp.trim(), addrProp.trim(), stateProp.trim());
		}
	}

	public List<Warehouse> queryWarehouse(String nameProp, String addrProp, String stateProp) {
		// TODO 自动生成的方法存根
		if ("".equals(stateProp)) {
			return null;
		} else {
			return warehouseService.queryWarehouse(nameProp.trim(), addrProp.trim(), stateProp.trim());
		}
	}

	public boolean delWarehouse(List<String> warehouseIdList) {
		// TODO 自动生成的方法存根
		return warehouseService.delWarehouse(warehouseIdList);
	}

	public int updateWarehouseState(String id, String stateChange) {
		// TODO 自动生成的方法存根
		return warehouseService.updateWarehouseState(id, stateChange);
	}
}
