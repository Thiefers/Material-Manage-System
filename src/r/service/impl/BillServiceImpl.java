package r.service.impl;

import java.util.List;

import r.dao.BillDao;
import r.dao.impl.BillDaoImpl;
import r.domain.Bill;
import r.service.BillService;
import r.utils.DateTimeUtil;

public class BillServiceImpl implements BillService {

	private BillDao bDao = new BillDaoImpl();

	@Override
	public List<Bill> queryAllBill() {
		return bDao.queryAllBill();
	}

	@Override
	public List<Bill> queryBill(String materialNameProp, String manufacturerProp, String typeProp,
			String specificationsProp, String warehouseNameProp, String dateBeginProp, String dateEndProp,
			String stateProp) {
		// TODO 自动生成的方法存根
		Bill bill = new Bill();
		bill.setMaterialName(materialNameProp);
		bill.setManufacturer(manufacturerProp);
		bill.setType(typeProp);
		bill.setSpecifications(specificationsProp);
		bill.setState(stateProp);
		if (!"".equals(stateProp)) {
			if ("0".equals(stateProp)) {
				bill.setOutUnit(warehouseNameProp);
			} else {
				bill.setEntryUnit(warehouseNameProp);
			}
			// 有state的sql
			return bDao.queryBillWithStateAndDate(bill, dateBeginProp, dateEndProp);
		} else {
			// 无state的sql
			bill.setOutUnit(warehouseNameProp);
			bill.setEntryUnit(warehouseNameProp);
			return bDao.queryBillWithDate(bill, dateBeginProp, dateEndProp);
		}
	}

	@Override
	public List<Bill> queryBill(String materialNameProp, String dateBeginProp, String dateEndProp, String stateProp) {
		Bill bill = new Bill();
		bill.setMaterialName(materialNameProp);
		bill.setState(stateProp);
		if (!"".equals(stateProp)) {
			// 有state的sql
			return bDao.queryBillWithStateAndDate(bill, dateBeginProp, dateEndProp);
		} else {
			// 无state的sql
			return bDao.queryBillWithDate(bill, dateBeginProp, dateEndProp);
		}
	}

	@Override
	public List<Bill> queryYearBill(String materialNameProp, String dateBeginProp, String stateProp) {
		Bill bill = new Bill();
		bill.setMaterialName(materialNameProp);
		bill.setState(stateProp);
		String[] dates = dateBeginProp.split("-");
		bill.setDateTime(dates[0]);
		return bDao.queryYearBill(bill);
	}

	@Override
	public List<Bill> queryMonthBill(String materialNameProp, String dateBeginProp, String stateProp) {
		Bill bill = new Bill();
		bill.setMaterialName(materialNameProp);
		bill.setState(stateProp);
		String[] dates = dateBeginProp.split("-");
		bill.setDateTime(dates[0] + "-" + dates[1]);
		return bDao.queryMonthBill(bill, DateTimeUtil.addOneMonth(dateBeginProp));
	}

	@Override
	public List<Bill> queryDayBill(String materialNameProp, String dateBeginProp, String stateProp) {
		Bill bill = new Bill();
		bill.setMaterialName(materialNameProp);
		bill.setState(stateProp);
		bill.setDateTime(dateBeginProp);
		return bDao.queryDayBill(bill, DateTimeUtil.addOneDay(dateBeginProp));
	}

}