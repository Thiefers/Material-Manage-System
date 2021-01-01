package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_bill")
public class Bill {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id; // ���ݱ�ʶ

	@MyField(columnName = "materialName", type = "varchar", length = 255)
	private String materialName; // ������

	@MyField(columnName = "manufacturer", type = "varchar", length = 255)
	private String manufacturer; // ����

	@MyField(columnName = "type", type = "varchar", length = 255)
	private String type; // �ͺ�

	@MyField(columnName = "specifications", type = "varchar", length = 255)
	private String specifications; // ���

	@MyField(columnName = "count", type = "int", length = 11)
	private Integer count; // ����

	@MyField(columnName = "dateTime", type = "varchar", length = 19)
	private String dateTime; // ����

	@MyField(columnName = "state", type = "char", length = 1)
	private String state; // ���1/����0

	@MyField(columnName = "entryUnit", type = "varchar", length = 255)
	private String entryUnit; // ��ⵥλ

	@MyField(columnName = "storage", type = "varchar", length = 255)
	private String storage; // �ɹ�Ա���ͽ��õ�λ��

	@MyField(columnName = "outUnit", type = "varchar", length = 255)
	private String outUnit; // ���ⵥλ

	@MyField(columnName = "outbound", type = "varchar", length = 255)
	private String outbound; // ȡ���ˣ��Ӹõ�λ�����

	@MyField(columnName = "stock", type = "int", length = 11)
	private Integer stock; // ȡ���ˣ��Ӹõ�λ�����

	public Bill() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEntryUnit() {
		return entryUnit;
	}

	public void setEntryUnit(String entryUnit) {
		this.entryUnit = entryUnit;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getOutUnit() {
		return outUnit;
	}

	public void setOutUnit(String outUnit) {
		this.outUnit = outUnit;
	}

	public String getOutbound() {
		return outbound;
	}

	public void setOutbound(String outbound) {
		this.outbound = outbound;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Bill [id=" + id + ", materialName=" + materialName + ", manufacturer=" + manufacturer + ", type=" + type
				+ ", specifications=" + specifications + ", count=" + count + ", dateTime=" + dateTime + ", state="
				+ state + ", entryUnit=" + entryUnit + ", storage=" + storage + ", outUnit=" + outUnit + ", outbound="
				+ outbound + ", stock=" + stock + "]";
	}

}