package r.service.impl;

import java.util.List;

import r.dao.BillDao;
import r.dao.MaterialDao;
import r.dao.MaterialWarehouseStockRelationDao;
import r.dao.StockDao;
import r.dao.WarehouseDao;
import r.dao.impl.BillDaoImpl;
import r.dao.impl.MaterialDaoImpl;
import r.dao.impl.MaterialWarehouseStockRelationDaoImpl;
import r.dao.impl.StockDaoImpl;
import r.dao.impl.WarehouseDaoImpl;
import r.domain.Bill;
import r.domain.Material;
import r.domain.MaterialWarehouseStockRelation;
import r.domain.Stock;
import r.domain.Warehouse;
import r.service.MaterialService;
import r.utils.DateTimeUtil;
import r.utils.UUIDUtil;

public class MaterialServiceImpl implements MaterialService {

	private MaterialDao mDao = new MaterialDaoImpl();
	private BillDao bDao = new BillDaoImpl();
	private WarehouseDao wDao = new WarehouseDaoImpl();
	private StockDao sDao = new StockDaoImpl();
	private MaterialWarehouseStockRelationDao mwsrDao = new MaterialWarehouseStockRelationDaoImpl();

	@Override
	public boolean addStorageBill(String materialNameProp, String manufacturerProp, String typeProp,
			String specificationsProp, Integer countProp, String dateTimeProp, String entryUnitProp,
			String storageProp) {
		boolean flag = true;
		// ���µ��ݱ�
		Bill storageBill = new Bill();
		storageBill.setId(UUIDUtil.getUUID());
		storageBill.setMaterialName(materialNameProp);
		storageBill.setManufacturer(manufacturerProp);
		storageBill.setType(typeProp);
		storageBill.setSpecifications(specificationsProp);
		storageBill.setCount(countProp);
		storageBill.setDateTime(dateTimeProp);
		storageBill.setState("1");
		storageBill.setEntryUnit(entryUnitProp);
		storageBill.setStorage(storageProp);
		int count1 = bDao.addStorageBill(storageBill);
		if (count1 != 1) {
			flag = false;
		}
		// �ж��Ƿ�������ʱ�
		Material material = new Material();
		material.setId(UUIDUtil.getUUID());
		material.setName(materialNameProp);
		material.setManufacturer(manufacturerProp);
		material.setType(typeProp);
		material.setSpecifications(specificationsProp);
		Material queryMaterial = mDao.queryMaterial(materialNameProp, manufacturerProp, typeProp);
		if (queryMaterial == null) {
			// ��������
			int count2 = mDao.addMaterial(material);
			if (count2 != 1) {
				flag = false;
			}
		}
		// ���¿���(��Ҫ�õ��ֿ�id������id)
		// ��ȡ����id
		String materialId = "";
		if (queryMaterial != null) {
			materialId = queryMaterial.getId();
		} else {
			materialId = material.getId();
		}
		// ��ȡ�ֿ�id
		Warehouse warehouse = new Warehouse();
		warehouse.setName(entryUnitProp.split("/")[0]);
		List<Warehouse> queryWarehouse = wDao.queryWarehouseByName(warehouse);
		String warehouseId = "";
		if (queryWarehouse != null) {
			warehouseId = queryWarehouse.get(0).getId();
		} else {
			flag = false;
		}
		// ���¿���
		Stock queryStock = sDao.queryStockByMIdAndWId(materialId, warehouseId);
		int count3 = 0;
		Stock stock = new Stock();
		if (queryStock == null) {
			// ����¿���¼
			stock.setId(UUIDUtil.getUUID());
			stock.setMaterialId(materialId);
			stock.setWarehouseId(warehouseId);
			stock.setStock(countProp);
			stock.setCheckTime(DateTimeUtil.getSysTime());
			count3 = sDao.addStock(stock);
		} else {
			// ���¿���¼
			queryStock.setStock(queryStock.getStock() + countProp);
			queryStock.setCheckTime(DateTimeUtil.getSysTime());
			count3 = sDao.updateAddStock(queryStock);
		}
		if (count3 != 1) {
			flag = false;
		}
		// �������ʲֿ����ϵ��
		// materialId��ͬ��֤��materialName, manufacturer, type������һ����ͬ
		// stockId��ͬ��֤��materialId, warehouseId����һ����ͬ
		// �����id�Ƿ�һ��
		// һ��==������У����ڵ�ǰ�ֿ����ʣ�ֱ����stock������warehouseId�òֿ���Ϣ������materialId��������Ϣ
		// ��ͬ==����ޣ���Ҫ����
		MaterialWarehouseStockRelation mwsRelation = new MaterialWarehouseStockRelation();
		int count4 = 0;
		String mwsrIdBillNeed = "";
		if (queryStock != null) {
			MaterialWarehouseStockRelation queryMwsRelation = mwsrDao.queryRelationByStockId(queryStock.getId());
			mwsrIdBillNeed = queryMwsRelation.getId();
			count4 = mwsrDao.updateStock(queryMwsRelation.getStock() + countProp, queryStock.getId());
		} else {
			mwsRelation.setId(UUIDUtil.getUUID());
			if (queryMaterial == null) {
				// ������
				mwsRelation.setMaterialId(material.getId());
				mwsRelation.setMaterialName(material.getName());
				mwsRelation.setManufacturer(material.getManufacturer());
				mwsRelation.setType(material.getType());
				mwsRelation.setSpecifications(material.getSpecifications());
			} else {
				mwsRelation.setMaterialId(queryMaterial.getId());
				mwsRelation.setMaterialName(queryMaterial.getName());
				mwsRelation.setManufacturer(queryMaterial.getManufacturer());
				mwsRelation.setType(queryMaterial.getType());
				mwsRelation.setSpecifications(queryMaterial.getSpecifications());
			}
			mwsRelation.setWarehouseId(queryWarehouse.get(0).getId());
			mwsRelation.setWarehouseName(queryWarehouse.get(0).getName());
			mwsRelation.setWarehouseAddr(queryWarehouse.get(0).getAddr());
			mwsRelation.setWarehouseState(queryWarehouse.get(0).getState());
			mwsRelation.setStockId(stock.getId());
			mwsRelation.setStock(stock.getStock());
			mwsrIdBillNeed = mwsRelation.getId();
			count4 = mwsrDao.addRelation(mwsRelation);
		}
		if (count4 != 1) {
			flag = false;
		}

		String billId = storageBill.getId();// update t_bill set stock=relationsStock where id=billId
		MaterialWarehouseStockRelation queryMwsRelation = mwsrDao.queryRelationById(mwsrIdBillNeed);
		int count5 = bDao.updateStock(queryMwsRelation.getStock(), billId);
		if (count5 != 1) {
			flag = false;
		}

		// ���ؽ��
		return flag;
	}

	@Override
	public List<Material> queryAllMaterial() {
		return mDao.queryAllMaterial();
	}

	@Override
	public boolean addOutBill(String materialName, String manufacturer, String type, String specifications,
			int outCount, String outUnit, String outbound, String stockId, String materialId, String warehouseId) {
		boolean flag = true;
		// ���ⵥ
		Bill outBill = new Bill();
		outBill.setId(UUIDUtil.getUUID());
		outBill.setMaterialName(materialName);
		outBill.setManufacturer(manufacturer);
		outBill.setType(type);
		outBill.setSpecifications(specifications);
		outBill.setCount(outCount);
		outBill.setDateTime(DateTimeUtil.getSysTime());
		outBill.setState("0");
		outBill.setOutUnit(outUnit);
		outBill.setOutbound(outbound);
		int count1 = bDao.addOutBill(outBill);
		if (count1 != 1) {
			flag = false;
		}
		// ���¿�����
		Stock stock = sDao.queryStockByMIdAndWId(materialId, warehouseId);
		int count2 = sDao.updateDelStock(stock.getId(), stock.getStock() - outCount, outBill.getDateTime());
		if (count2 != 1) {
			flag = false;
		}
		// ���¹�ϵ����
		MaterialWarehouseStockRelation mwsRelation = mwsrDao.queryRelationByStockId(stockId);
		int count3 = mwsrDao.updateStock(mwsRelation.getStock() - outCount, stockId);
		if (count3 != 1) {
			flag = false;
		}

		String billId = outBill.getId();
		int count4 = bDao.updateStock(mwsRelation.getStock() - outCount, billId);
		if (count4 != 1) {
			flag = false;
		}
		// ���ؽ��
		return flag;
	}

	@Override
	public boolean delMaterial(String materialName, String manufacturer, String type, String specifications,
			String warehouseInfos) {
		boolean flag = true;
		Material material = new Material();
		material.setName(materialName);
		material.setManufacturer(manufacturer);
		material.setType(type);
		material.setSpecifications(specifications);
		Material queryMaterial = mDao.queryMaterial(materialName, manufacturer, type);
		String materialId = queryMaterial.getId();
		int count1 = mDao.deleteMaterial(material);
		if (count1 != 1) {
			flag = false;
		}

		String[] warehouseinfoArr = warehouseInfos.split("/");
		String warehouseName = warehouseinfoArr[0];
		String warehouseAddr = warehouseinfoArr[1];
		Warehouse warehouse = wDao.queryWarehouseByNameAndAddr(warehouseName, warehouseAddr);
		String warehouseId = warehouse.getId();

		int count2 = sDao.deleteStock(materialId, warehouseId);
		if (count2 != 1) {
			flag = false;
		}

		int count3 = mwsrDao.deleteRelation(materialId, warehouseId);
		if (count3 != 1) {
			flag = false;
		}

		int count4 = bDao.queryBillForAnalyzeSize(material, warehouseInfos);
		int count5 = bDao.deleteBill(material, warehouseInfos);
		if (count4 != count5) {
			flag = false;
		}

		return flag;
	}

}