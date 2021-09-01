/**
 * 
 */
package io.jalorx.boot.mybatis.mapping;

import java.util.Date;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.scripting.xmltags.DynamicContext;

import io.jalorx.boot.utils.AuthInfoUtils;

/**
 * 
 * 绑定系统默认的参数
 * 
 * @author chenb
 *
 */
class BoundSqlUtil {

	static BoundSql initAdditional(BoundSql bsq) {
		Date currentDate = new Date();
		// AppEnv appEnv = BeanContext.getAppEnv();
		bsq.setAdditionalParameter("_currentDate", currentDate);
		// bsq.setAdditionalParameter("_env", appEnv);

		// 修改为用户指定的TenantId
		bsq.setAdditionalParameter("_tenantId", AuthInfoUtils.getCurrentTenantId());

		if (AuthInfoUtils.isLogin()) {
			bsq.setAdditionalParameter("_currentUserId", AuthInfoUtils.getCurrentUserId());
		}
		return bsq;
	}

	static void initAdditional(DynamicContext context) {
		Date currentDate = new Date();
		// AppEnv appEnv = BeanContext.getAppEnv();
		context.bind("_currentDate", currentDate);
		// context.bind("_env", appEnv);
		context.bind("_tenantId", AuthInfoUtils.getCurrentTenantId());
	}
}
