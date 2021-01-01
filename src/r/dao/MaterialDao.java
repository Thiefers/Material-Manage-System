package r.dao;

import java.util.List;

import r.domain.Material;

public interface MaterialDao {

	int addMaterial(Material material);

	Material queryMaterial(String materialNameProp, String manufacturerProp, String typeProp);

	List<Material> queryAllMaterial();

	int deleteMaterial(Material material);

}
