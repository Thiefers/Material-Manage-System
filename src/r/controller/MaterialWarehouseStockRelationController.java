package r.controller;

import java.util.List;

import r.domain.MaterialWarehouseStockRelation;
import r.service.MaterialWarehouseStockRelationService;
import r.service.impl.MaterialWarehouseStockRelationServiceImpl;
import r.utils.JDBCUtil;

public class MaterialWarehouseStockRelationController {

	private MaterialWarehouseStockRelationService mwsrService = new MaterialWarehouseStockRelationServiceImpl();

	public List<MaterialWarehouseStockRelation> queryRelation(String materialNameProp, String manufacturerProp,
			String typeProp, String specificationsProp, String warehouseNameProp) {
		List<MaterialWarehouseStockRelation> mwsRelations = mwsrService.queryRelation(materialNameProp,
				manufacturerProp, typeProp, specificationsProp, warehouseNameProp);
		if (mwsRelations == null) {
			JDBCUtil.rollbackAndClose();
		} else {
			JDBCUtil.commitAndClose();
		}
		return mwsRelations;
	}

}
