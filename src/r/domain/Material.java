package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_material")
public class Material {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id; // ��ʶ

	@MyField(columnName = "name", type = "varchar", length = 255)
	private String name; // ������

	@MyField(columnName = "manufacturer", type = "varchar", length = 255)
	private String manufacturer; // ����

	// �ͺ��Ǹ���Ʒ��д��һ�����ţ������ֲ�Ʒ���ͺ��п���ֱ�Ӱ����ò�Ʒ����Ҫ�����Ϣ
	@MyField(columnName = "type", type = "varchar", length = 255)
	private String type; // ����/�ͺ�

	// һ����Ʒ�Ĺ����ָ�ò�Ʒ�Ĵ�С�����ʵ���������
	@MyField(columnName = "specifications", type = "varchar", length = 255)
	private String specifications; // ���/���

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
