package r.service;

import java.util.List;

import r.domain.Material;

public interface MaterialService {

	boolean addStorageBill(String materialNameProp, String manufacturerProp, String typeProp, String specificationsProp,
			Integer countProp, String dateTimeProp, String entryUnitProp, String storageProp);

	List<Material> queryAllMaterial();

	boolean addOutBill(String materialName, String manufacturer, String type, String specifications, int outCount,
			String outUnit, String outbound, String stockId, String materialId, String warehouseId);

	boolean delMaterial(String materialName, String manufacturer, String type, String specifications,
			String warehouseInfos);

}