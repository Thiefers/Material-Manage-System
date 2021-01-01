package r.service;

import java.util.List;

import r.domain.Bill;

public interface BillService {

	List<Bill> queryAllBill();

	List<Bill> queryBill(String materialNameProp, String manufacturerProp, String typeProp, String specificationsProp,
			String warehouseNameProp, String dateBeginProp, String dateEndProp, String stateProp);

	List<Bill> queryBill(String materialNameProp, String dateBeginProp, String dateEndProp, String stateProp);

	List<Bill> queryYearBill(String materialNameProp, String dateBeginProp, String stateProp);

	List<Bill> queryMonthBill(String materialNameProp, String dateBeginProp, String stateProp);

	List<Bill> queryDayBill(String materialNameProp, String dateBeginProp, String stateProp);

}
