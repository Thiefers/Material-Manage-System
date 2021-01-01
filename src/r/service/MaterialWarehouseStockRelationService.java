package r.service;

import java.util.List;

import r.domain.MaterialWarehouseStockRelation;

public interface MaterialWarehouseStockRelationService {

	List<MaterialWarehouseStockRelation> queryRelation(String materialNameProp, String manufacturerProp,
			String typeProp, String specificationsProp, String warehouseNameProp);

}
