package r.controller;

import r.service.StockService;
import r.service.impl.StockServiceImpl;

public class StockController {

	private StockService sService = new StockServiceImpl();
	
//	public List<Stock> queryAllStock() {
//		// TODO 自动生成的方法存根
//		List<Stock> stocks = sService.queryAllStock();
//		if (stocks == null) {
//			JDBCUtil.rollbackAndClose();
//			return null;
//		} else {
//			JDBCUtil.commitAndClose();
//			return stocks;
//		}
//	}

}
