package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_dic_value")
public class DicValue {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id;

	@MyField(columnName = "value", type = "varchar", length = 255)
	private String value;

	@MyField(columnName = "text", type = "varchar", length = 255)
	private String text;

	@MyField(columnName = "orderNo", type = "varchar", length = 255)
	private String orderNo;

	@MyField(columnName = "typeCode", type = "varchar", length = 255)
	private String typeCode;

	public DicValue() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Override
	public String toString() {
		return "DicValue [id=" + id + ", value=" + value + ", text=" + text + ", orderNo=" + orderNo + ", typeCode="
				+ typeCode + "]";
	}

}
