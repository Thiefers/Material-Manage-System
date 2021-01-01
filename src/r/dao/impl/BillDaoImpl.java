package r.dao.impl;

import java.util.List;

import r.dao.BillDao;
import r.domain.Bill;
import r.domain.Material;

public class BillDaoImpl extends BaseDao implements BillDao {

	@Override
	public int addStorageBill(Bill storageBill) {
		String sql = "insert into t_bill(id,materialName,manufacturer,type,specifications,count,dateTime,state,entryUnit,storage) values(?,?,?,?,?,?,?,?,?,?)";
		return update(sql, storageBill.getId(), storageBill.getMaterialName(), storageBill.getManufacturer(),
				storageBill.getType(), storageBill.getSpecifications(), storageBill.getCount(),
				storageBill.getDateTime(), storageBill.getState(), storageBill.getEntryUnit(),
				storageBill.getStorage());
	}

	@Override
	public List<Bill> queryAllBill() {
		String sql = "select * from t_bill where state=1 order by `dateTime` desc";
		return queryForList(Bill.class, sql);
	}

	@Override
	public int addOutBill(Bill outBill) {
		String sql = "insert into t_bill(id,materialName,manufacturer,type,specifications,count,dateTime,state,outUnit,outbound) values(?,?,?,?,?,?,?,?,?,?)";
		return update(sql, outBill.getId(), outBill.getMaterialName(), outBill.getManufacturer(), outBill.getType(),
				outBill.getSpecifications(), outBill.getCount(), outBill.getDateTime(), outBill.getState(),
				outBill.getOutUnit(), outBill.getOutbound());
	}

	@Override
	public List<Bill> queryBillWithStateAndDate(Bill bill, String dateBeginProp, String dateEndProp) {
		String sql = "select * from t_bill where state=? and dateTime>=? and datetime<=? and materialName like '%' ? '%' order by dateTime desc";
		return queryForList(Bill.class, sql, bill.getState(), dateBeginProp, dateEndProp, bill.getMaterialName());
	}

	@Override
	public List<Bill> queryBillWithDate(Bill bill, String dateBeginProp, String dateEndProp) {
		String sql = "select * from t_bill where dateTime>=? and datetime<=? and materialName like '%' ? '%' order by dateTime desc";
		return queryForList(Bill.class, sql, dateBeginProp, dateEndProp, bill.getMaterialName());
	}

	@Override
	public int deleteBill(Material material, String warehouseInfos) {
		String sql1 = "delete from t_bill where materialName=? and manufacturer=? and type=? and specifications=? and entryUnit=?";
		String sql2 = "delete from t_bill where materialName=? and manufacturer=? and type=? and specifications=? and outUnit=?";
		int count1 = update(sql1, material.getName(), material.getManufacturer(), material.getType(),
				material.getSpecifications(), warehouseInfos);
		int count2 = update(sql2, material.getName(), material.getManufacturer(), material.getType(),
				material.getSpecifications(), warehouseInfos);
		return count1 + count2;
	}

	@Override
	public int queryBillForAnalyzeSize(Material material, String warehouseInfos) {
		String sql1 = "select * from t_bill where materialName=? and manufacturer=? and type=? and specifications=? and entryUnit=?";
		String sql2 = "select * from t_bill where materialName=? and manufacturer=? and type=? and specifications=? and outUnit=?";
		List<Bill> entryBill = queryForList(Bill.class, sql1, material.getName(), material.getManufacturer(),
				material.getType(), material.getSpecifications(), warehouseInfos);
		List<Bill> outBill = queryForList(Bill.class, sql2, material.getName(), material.getManufacturer(),
				material.getType(), material.getSpecifications(), warehouseInfos);
		return entryBill.size() + outBill.size();
	}

	@Override
	public int updateStock(Integer stock, String billId) {
		String sql = "update t_bill set stock=? where id=?";
		return update(sql, stock, billId);
	}

	@Override
	public List<Bill> queryYearBill(Bill bill) {
		String sql = "select * from t_bill where state like '%' ? '%' and dateTime>? and dateTime<? and materialName like '%' ? '%' order by dateTime desc";
		return queryForList(Bill.class, sql, bill.getState(), bill.getDateTime(),
				String.valueOf(Integer.parseInt(bill.getDateTime()) + 1), bill.getMaterialName());
	}

	@Override
	public List<Bill> queryMonthBill(Bill bill, String addOneMonth) {
		String sql = "select * from t_bill where state like '%' ? '%' and dateTime>? and dateTime<? and materialName like '%' ? '%' order by dateTime desc";
		return queryForList(Bill.class, sql, bill.getState(), bill.getDateTime(), addOneMonth, bill.getMaterialName());
	}

	@Override
	public List<Bill> queryDayBill(Bill bill, String addOneDay) {
		String sql = "select * from t_bill where state like '%' ? '%' and dateTime>? and dateTime<? and materialName like '%' ? '%' order by dateTime desc";
		return queryForList(Bill.class, sql, bill.getState(), bill.getDateTime(), addOneDay, bill.getMaterialName());
	}

}
