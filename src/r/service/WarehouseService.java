package r.service;

import java.util.List;

import r.domain.Warehouse;

public interface WarehouseService {

	/**
	 * Ìí¼Ó²Ö¿â
	 * @param nameProp ²Ö¿âÃû³Æ
	 * @param addrProp ²Ö¿âµØÖ·
	 * @param stateProp ²Ö¿â¿ª£¨1£©±Õ£¨0£©
	 * @return
	 */
	public boolean addWarehouse(String nameProp, String addrProp, String stateProp);

	public List<Warehouse> queryWarehouse(String nameProp, String addrProp, String stateProp);

	/**
	 * É¾³ı²Ö¿â£¬Ö§³ÖÅúÁ¿É¾³ı
	 * @param warehouseIdList
	 * @return
	 */
	public boolean delWarehouse(List<String> warehouseIdList);

	public int updateWarehouseState(String id, String stateChange);
	
}
