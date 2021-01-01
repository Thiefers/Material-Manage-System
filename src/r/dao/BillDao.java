package r.dao;

import java.util.List;

import r.domain.Bill;
import r.domain.Material;

public interface BillDao {

	int addStorageBill(Bill storageBill);

	List<Bill> queryAllBill();

	int addOutBill(Bill outBill);

	List<Bill> queryBillWithStateAndDate(Bill bill, String dateBeginProp, String dateEndProp);

	List<Bill> queryBillWithDate(Bill bill, String dateBeginProp, String dateEndProp);

	int deleteBill(Material material, String warehouseInfos);

	int queryBillForAnalyzeSize(Material material, String warehouseInfos);

	int updateStock(Integer stock, String billId);

	List<Bill> queryYearBill(Bill bill);

	List<Bill> queryMonthBill(Bill bill, String addOneMonth);

	List<Bill> queryDayBill(Bill bill, String addOneDay);

}
