package r.dao;

import java.util.List;

import r.domain.MaterialWarehouseStockRelation;

public interface MaterialWarehouseStockRelationDao {

	int updateStock(int newStock, String stockId);

	int addRelation(MaterialWarehouseStockRelation mwsRelation);

	MaterialWarehouseStockRelation queryRelationByStockId(String id);

	List<MaterialWarehouseStockRelation> queryRelation(MaterialWarehouseStockRelation mwsRelation);

	int deleteRelation(String materialId, String warehouseId);

	MaterialWarehouseStockRelation queryRelationById(String mwsrIdBillNeed);

}
