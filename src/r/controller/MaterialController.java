package r.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import r.domain.Material;
import r.service.MaterialService;
import r.service.impl.MaterialServiceImpl;
import r.utils.JDBCUtil;

public class MaterialController {

	private MaterialService mService = new MaterialServiceImpl();

	public boolean addStorageBill(String materialNameProp, String manufacturerProp, String typeProp,
			String specificationsProp, String countProp, String dateTimeProp, String entryUnitProp,
			String storageProp) {
		if ("".equals(materialNameProp) || "".equals(manufacturerProp) || "".equals(typeProp)
				|| "".equals(specificationsProp) || "".equals(countProp) || "".equals(dateTimeProp)
				|| "".equals(entryUnitProp) || "".equals(storageProp)) {
			return false;
		}
		// 限制输入只能为数字
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher isNum = pattern.matcher(countProp);
		if (!isNum.matches()) {
			return false;
		} else {
			boolean flag = mService.addStorageBill(materialNameProp, manufacturerProp, typeProp, specificationsProp,
					Integer.parseInt(countProp), dateTimeProp, entryUnitProp, storageProp);
			if (flag) {
				JDBCUtil.commitAndClose();
			} else {
				JDBCUtil.rollbackAndClose();
			}
			return flag;
		}
	}

	public List<Material> queryAllMaterial() {
		List<Material> materials = mService.queryAllMaterial();
		if (materials == null) {
			JDBCUtil.rollbackAndClose();
		} else {
			JDBCUtil.commitAndClose();
		}
		return materials;
	}

	public boolean addOutBill(String materialName, String manufacturer, String type, String specifications,
			int outCount, String outUnit, String outbound, String stockId, String materialId, String warehouseId) {
		if ("".equals(materialName) || "".equals(manufacturer) || "".equals(type) || "".equals(specifications)
				|| "".equals(outUnit) || "".equals(outbound) || "".equals(stockId)) {
			return false;
		} else {
			boolean flag = mService.addOutBill(materialName, manufacturer, type, specifications, outCount, outUnit,
					outbound, stockId, materialId, warehouseId);
			if (flag) {
				JDBCUtil.commitAndClose();
			} else {
				JDBCUtil.rollbackAndClose();
			}
			return flag;
		}
	}

	public boolean delMaterial(String materialName, String manufacturer, String type, String specifications,
			String warehouseInfos) {
		boolean flag = mService.delMaterial(materialName, manufacturer, type, specifications, warehouseInfos);
		if (flag) {
			JDBCUtil.commitAndClose();
		} else {
			JDBCUtil.rollbackAndClose();
		}
		return flag;
	}

}