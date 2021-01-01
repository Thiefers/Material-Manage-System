package r.service;

import java.util.List;

import r.domain.Warehouse;

public interface WarehouseService {

	/**
	 * ��Ӳֿ�
	 * @param nameProp �ֿ�����
	 * @param addrProp �ֿ��ַ
	 * @param stateProp �ֿ⿪��1���գ�0��
	 * @return
	 */
	public boolean addWarehouse(String nameProp, String addrProp, String stateProp);

	public List<Warehouse> queryWarehouse(String nameProp, String addrProp, String stateProp);

	/**
	 * ɾ���ֿ⣬֧������ɾ��
	 * @param warehouseIdList
	 * @return
	 */
	public boolean delWarehouse(List<String> warehouseIdList);

	public int updateWarehouseState(String id, String stateChange);
	
}
