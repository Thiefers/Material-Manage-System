package r.dao.impl;

import java.util.List;

import r.dao.MaterialDao;
import r.domain.Material;

public class MaterialDaoImpl extends BaseDao implements MaterialDao {

	@Override
	public int addMaterial(Material material) {
		String sql = "insert into t_material(`id`,`name`,`manufacturer`,`type`,`specifications`) values(?,?,?,?,?)";
		return update(sql, material.getId(), material.getName(), material.getManufacturer(), material.getType(),
				material.getSpecifications());
	}

	@Override
	public Material queryMaterial(String materialNameProp, String manufacturerProp, String typeProp) {
		String sql = "select * from t_material where `name`=? and `manufacturer`=? and `type`=?";
		return queryForOne(Material.class, sql, materialNameProp, manufacturerProp, typeProp);
	}

	@Override
	public List<Material> queryAllMaterial() {
		String sql = "select * from t_material";
		return queryForList(Material.class, sql);
	}

	@Override
	public int deleteMaterial(Material material) {
		String sql = "delete from t_material where name=? and manufacturer=? and type=? and specifications=?";
		return update(sql, material.getName(), material.getManufacturer(), material.getType(),
				material.getSpecifications());
	}

}
