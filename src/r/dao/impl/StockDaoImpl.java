package r.dao.impl;

import r.dao.StockDao;
import r.domain.Stock;
import r.domain.Warehouse;

public class StockDaoImpl extends BaseDao implements StockDao {

	@Override
	public Stock queryStockByMIdAndWId(String materialId, String warehouseId) {
		String sql = "select * from t_stock where `materialId`=? and `warehouseId`=?";
		return queryForOne(Stock.class, sql, materialId, warehouseId);
	}

	@Override
	public int addStock(Stock stock) {
		String sql = "insert into t_stock(id,materialId,warehouseId,stock,checkTime) values(?,?,?,?,?)";
		return update(sql, stock.getId(), stock.getMaterialId(), stock.getWarehouseId(), stock.getStock(),
				stock.getCheckTime());
	}

	@Override
	public int updateAddStock(Stock stock) {
		String sql = "update t_stock set `stock`=?,`checkTime`=? where `id`=?";
		return update(sql, stock.getStock(), stock.getCheckTime(), stock.getId());
	}

	@Override
	public int updateDelStock(String stockId, Integer stock, String checkTime) {
		String sql = "update t_stock set `stock`=?,`checkTime`=? where `id`=?";
		return update(sql, stock, checkTime, stockId);
	}

	@Override
	public Stock queryStockBySotckId(String stockId) {
		String sql = "select * from t_stock where `id`=?";
		return queryForOne(Stock.class, sql, stockId);
	}

	@Override
	public int deleteStock(String materialId, String warehouseId) {
		String sql = "delete from t_stock where materialId=? and warehouseId=? and stock=0";
		return update(sql, materialId, warehouseId);
	}

	// @Override
	// public List<Stock> queryAllStock() {
	// // TODO 自动生成的方法存根
	// String sql = "select * from t_stock";
	// return null;
	// }

}
