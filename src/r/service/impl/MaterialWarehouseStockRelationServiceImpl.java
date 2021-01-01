package r.service.impl;

import java.util.List;

import r.dao.MaterialWarehouseStockRelationDao;
import r.dao.impl.BaseDao;
import r.dao.impl.MaterialWarehouseStockRelationDaoImpl;
import r.domain.MaterialWarehouseStockRelation;
import r.service.MaterialWarehouseStockRelationService;

public class MaterialWarehouseStockRelationServiceImpl extends BaseDao
		implements MaterialWarehouseStockRelationService {

	private MaterialWarehouseStockRelationDao mwsrDao = new MaterialWarehouseStockRelationDaoImpl();

	@Override
	public List<MaterialWarehouseStockRelation> queryRelation(String materialNameProp, String manufacturerProp,
			String typeProp, String specificationsProp, String warehouseNameProp) {
		MaterialWarehouseStockRelation mwsRelation = new MaterialWarehouseStockRelation();
		mwsRelation.setMaterialName(materialNameProp);
		mwsRelation.setManufacturer(manufacturerProp);
		mwsRelation.setType(typeProp);
		mwsRelation.setSpecifications(specificationsProp);
		mwsRelation.setWarehouseName(warehouseNameProp);
		return mwsrDao.queryRelation(mwsRelation);
	}

}
