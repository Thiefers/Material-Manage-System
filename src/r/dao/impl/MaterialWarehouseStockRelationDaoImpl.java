package r.dao.impl;

import java.util.List;

import r.dao.MaterialWarehouseStockRelationDao;
import r.domain.MaterialWarehouseStockRelation;

public class MaterialWarehouseStockRelationDaoImpl extends BaseDao implements MaterialWarehouseStockRelationDao {

	@Override
	public int updateStock(int newStock, String stockId) {
		String sql = "update t_material_warehouse_stock_relation set `stock`=? where `stockId`=?";
		return update(sql, newStock, stockId);
	}

	@Override
	public int addRelation(MaterialWarehouseStockRelation mwsRelation) {
		String sql = "insert into t_material_warehouse_stock_relation(id,materialId,warehouseId,stockId,materialName,stock,manufacturer,type,specifications,warehouseName,warehouseAddr,warehouseState) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		return update(sql, mwsRelation.getId(), mwsRelation.getMaterialId(), mwsRelation.getWarehouseId(),
				mwsRelation.getStockId(), mwsRelation.getMaterialName(), mwsRelation.getStock(),
				mwsRelation.getManufacturer(), mwsRelation.getType(), mwsRelation.getSpecifications(),
				mwsRelation.getWarehouseName(), mwsRelation.getWarehouseAddr(), mwsRelation.getWarehouseState());
	}

	@Override
	public MaterialWarehouseStockRelation queryRelationByStockId(String stockId) {
		String sql = "select * from t_material_warehouse_stock_relation where `stockId`=?";
		return queryForOne(MaterialWarehouseStockRelation.class, sql, stockId);
	}

	@Override
	public List<MaterialWarehouseStockRelation> queryRelation(MaterialWarehouseStockRelation mwsRelation) {
		String sql = "select * from t_material_warehouse_stock_relation where materialName like '%' ? '%' and manufacturer like '%' ? '%' and type like '%' ? '%' and specifications like '%' ? '%' and warehouseName like '%' ? '%'";
		return queryForList(MaterialWarehouseStockRelation.class, sql, mwsRelation.getMaterialName(),
				mwsRelation.getManufacturer(), mwsRelation.getType(), mwsRelation.getSpecifications(),
				mwsRelation.getWarehouseName());
	}

	@Override
	public int deleteRelation(String materialId, String warehouseId) {
		String sql = "delete from t_material_warehouse_stock_relation where materialId=? and warehouseId=? and stock=0";
		return update(sql, materialId, warehouseId);
	}

	@Override
	public MaterialWarehouseStockRelation queryRelationById(String mwsrIdBillNeed) {
		String sql = "select * from t_material_warehouse_stock_relation where id=?";
		return queryForOne(MaterialWarehouseStockRelation.class, sql, mwsrIdBillNeed);
	}

}