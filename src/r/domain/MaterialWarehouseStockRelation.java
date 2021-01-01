package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_material_warehouse_stock_relation")
public class MaterialWarehouseStockRelation {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id;

	@MyField(columnName = "materialId", type = "varchar", length = 32)
	private String materialId;

	@MyField(columnName = "warehouseId", type = "varchar", length = 32)
	private String warehouseId;

	@MyField(columnName = "stockId", type = "varchar", length = 32)
	private String stockId;

	@MyField(columnName = "materialName", type = "varchar", length = 255)
	private String materialName;

	@MyField(columnName = "stock", type = "int", length = 11)
	private Integer stock;

	@MyField(columnName = "manufacturer", type = "varchar", length = 255)
	private String manufacturer;

	@MyField(columnName = "type", type = "varchar", length = 255)
	private String type;

	@MyField(columnName = "specifications", type = "varchar", length = 255)
	private String specifications;

	@MyField(columnName = "warehouseName", type = "varchar", length = 255)
	private String warehouseName;

	@MyField(columnName = "warehouseAddr", type = "varchar", length = 255)
	private String warehouseAddr;

	@MyField(columnName = "warehouseState", type = "char", length = 1)
	private String warehouseState;

	public MaterialWarehouseStockRelation() {
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

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseAddr() {
		return warehouseAddr;
	}

	public void setWarehouseAddr(String warehouseAddr) {
		this.warehouseAddr = warehouseAddr;
	}

	public String getWarehouseState() {
		return warehouseState;
	}

	public void setWarehouseState(String warehouseState) {
		this.warehouseState = warehouseState;
	}

	@Override
	public String toString() {
		return "MaterialWarehouseStockRelation [id=" + id + ", materialId=" + materialId + ", warehouseId="
				+ warehouseId + ", stockId=" + stockId + ", materialName=" + materialName + ", stock=" + stock
				+ ", manufacturer=" + manufacturer + ", type=" + type + ", specifications=" + specifications
				+ ", warehouseName=" + warehouseName + ", warehouseAddr=" + warehouseAddr + ", warehouseState="
				+ warehouseState + "]";
	}

}