package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_stock")
public class Stock {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id; // ��ʶ

	@MyField(columnName = "materialId", type = "varchar", length = 32)
	private String materialId; // ����id

	@MyField(columnName = "warehouseId", type = "varchar", length = 32)
	private String warehouseId; // �ֿ�id

	@MyField(columnName = "stock", type = "int", length = 11)
	private Integer stock; // �����

	@MyField(columnName = "checkTime", type = "varchar", length = 19)
	private String checkTime; // ���һ�μ��ʱ��

	public Stock() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", materialId=" + materialId + ", warehouseId=" + warehouseId + ", stock=" + stock
				+ ", checkTime=" + checkTime + "]";
	}
}
