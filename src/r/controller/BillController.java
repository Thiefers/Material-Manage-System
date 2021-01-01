package r.controller;

import java.util.List;

import r.domain.Bill;
import r.service.BillService;
import r.service.impl.BillServiceImpl;
import r.utils.JDBCUtil;

public class BillController {

	private BillService bService = new BillServiceImpl();

	public List<Bill> queryAllBill() {
		List<Bill> bills = bService.queryAllBill();
		if (bills == null) {
			JDBCUtil.rollbackAndClose();
		} else {
			JDBCUtil.commitAndClose();
		}
		return bills;
	}

	public List<Bill> queryBill(String materialNameProp, String manufacturerProp, String typeProp,
			String specificationsProp, String warehouseNameProp, String dateBeginProp, String dateEndProp,
			String stateProp) {
		List<Bill> bills = bService.queryBill(materialNameProp, manufacturerProp, typeProp, specificationsProp,
				warehouseNameProp, dateBeginProp, dateEndProp, stateProp);
		if (bills == null) {
			JDBCUtil.rollbackAndClose();
		} else {
			JDBCUtil.commitAndClose();
		}
		return bills;
	}

	public List<Bill> queryBill(String materialNameProp, String dateBeginProp, String dateEndProp, String stateProp) {
		List<Bill> bills = bService.queryBill(materialNameProp, dateBeginProp, dateEndProp, stateProp);
		if (bills == null) {
			JDBCUtil.rollbackAndClose();
		} else {
			JDBCUtil.commitAndClose();
		}
		return bills;
	}

	public List<Bill> queryYearBill(String materialNameProp, String dateBeginProp, String stateProp) {
		List<Bill> bills = bService.queryYearBill(materialNameProp, dateBeginProp, stateProp);
		if (bills == null) {
			JDBCUtil.rollbackAndClose();
		} else {
			JDBCUtil.commitAndClose();
		}
		return bills;
	}

	public List<Bill> queryMonthBill(String materialNameProp, String dateBeginProp, String stateProp) {
		List<Bill> bills = bService.queryMonthBill(materialNameProp, dateBeginProp, stateProp);
		if (bills == null) {
			JDBCUtil.rollbackAndClose();
		} else {
			JDBCUtil.commitAndClose();
		}
		return bills;
	}

	public List<Bill> queryDayBill(String materialNameProp, String dateBeginProp, String stateProp) {
		List<Bill> bills = bService.queryDayBill(materialNameProp, dateBeginProp, stateProp);
		if (bills == null) {
			JDBCUtil.rollbackAndClose();
		} else {
			JDBCUtil.commitAndClose();
		}
		return bills;
	}

}
