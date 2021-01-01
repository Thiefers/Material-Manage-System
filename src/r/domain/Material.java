package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_material")
public class Material {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id; // 标识

	@MyField(columnName = "name", type = "varchar", length = 255)
	private String name; // 物资名

	@MyField(columnName = "manufacturer", type = "varchar", length = 255)
	private String manufacturer; // 厂商

	// 型号是给产品编写的一个代号，但部分产品的型号中可能直接包含该产品的主要规格信息
	@MyField(columnName = "type", type = "varchar", length = 255)
	private String type; // 类型/型号

	// 一个产品的规格是指该产品的大小，或功率等性能数据
	@MyField(columnName = "specifications", type = "varchar", length = 255)
	private String specifications; // 简介/规格

	public Material() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "Material [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", type=" + type
				+ ", specifications=" + specifications + "]";
	}

}
