package r.dao;

import java.util.List;

import r.domain.Warehouse;

public interface WarehouseDao {

	public int addWarehouse(Warehouse warehouse);

	public List<Warehouse> queryWarehouse(Warehouse warehouse);

	public int delWarehouse(List<String> warehouseIdList);

	public int updateWarehouseState(Warehouse warehouse);

	public List<Warehouse> queryWarehouseByName(Warehouse warehouse);

	public Warehouse queryWarehouseByNameAndAddr(String warehouseName, String warehouseAddr);
	
}
