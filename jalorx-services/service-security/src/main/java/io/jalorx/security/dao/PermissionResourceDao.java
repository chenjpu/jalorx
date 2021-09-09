package io.jalorx.security.dao;

import java.util.HashMap;
import java.util.List;

import io.jalorx.boot.entity.PermissionResource;
import io.jalorx.boot.repository.BaseRepository;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface PermissionResourceDao extends BaseRepository<PermissionResource> {
  /**
   * 批量删除记录
   * 
   * @param appCode
   * @param rids resources值集合
   */
  //void deleteByRescode(@Param("appCode") String appCode, @Param("rids") List<String> rids);

  /**
   * 获取所有权限项
   * 
   * @return
   */
	@Query("SELECT * FROM ("
			+ "			SELECT"
			+ "				t.CODE, t.DESP,"
			+ "				p.CODE AS PARENT_CODE,"
			+ "				P.APP_CODE"
			+ "			FROM"
			+ "				tpl_perm_oper_t t"
			+ "			LEFT JOIN tpl_perm_resource_t p ON t.PARENT_ID = p.ID"
			+ "			UNION"
			+ "				SELECT"
			+ "					pp.CODE, pp.DESP,"
			+ "					'Jalor' AS PARENT_CODE,"
			+ "					pp.APP_CODE"
			+ "				FROM"
			+ "					tpl_perm_resource_t pp"
			+ "		 ) a"
			+ "	   ORDER BY a.APP_CODE, a.PARENT_CODE, a.CODE")
   List<HashMap<String, Object>> getAllPermission();

}
