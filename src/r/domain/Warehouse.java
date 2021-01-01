package r.domain;

import r.myAnnotation.MyField;
import r.myAnnotation.MyTable;

@MyTable("t_warehouse")
public class Warehouse {

	@MyField(columnName = "id", type = "varchar", length = 32)
	private String id; // 标识

	@MyField(columnName = "name", type = "varchar", length = 255)
	private String name; // 仓库名

	@MyField(columnName = "addr", type = "varchar", length = 255)
	private String addr; // 仓库地址

	@MyField(columnName = "state", type = "char", length = 1)
	private String state; // 仓库开(1)/闭(0)

	public Warehouse() {
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

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Warehouse [id=" + id + ", name=" + name + ", addr=" + addr + ", state=" + state + "]";
	}

}
