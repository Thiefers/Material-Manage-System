package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_dic_type")
public class DicType {

	@MyField(columnName = "code", type = "varchar", length = 255)
	private String code;

	@MyField(columnName = "name", type = "varchar", length = 255)
	private String name;

	public DicType() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "DicType [code=" + code + ", name=" + name + "]";
	}

}
