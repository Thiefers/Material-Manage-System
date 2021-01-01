package r.dao;

import r.domain.Stock;
import r.domain.Warehouse;

public interface StockDao {

	Stock queryStockByMIdAndWId(String materialId, String warehouseId);

	int addStock(Stock stock);

	int updateAddStock(Stock stock);

	int updateDelStock(String id, Integer stock, String dateTime);

	Stock queryStockBySotckId(String stockId);

	int deleteStock(String materialId, String warehouseId);

	// List<Stock> queryAllStock();

}
