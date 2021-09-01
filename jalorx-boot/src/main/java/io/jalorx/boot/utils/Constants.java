/**
 * 
 */
package io.jalorx.boot.utils;

/**
 * @author chenb
 *
 */
public interface Constants {

	String X_REQUESTED_URL = "X-Requested-URL";

	String X_REQUESTED_WITH = "X-Requested-With";

	String X_SESSION_STATUS = "X-Session-Status";

	/**
	 * 框架默认模块命名
	 */
	String AS_MODEL = "JalorX";

	/**
	 * 核心框架内部资源编码
	 */
	// 1.安全模块(security)
	int RES_SECURITY_ACTION         = 10111;
	int RES_SECURITY_BASEUSER       = 10100;
	int RES_SECURITY_DATAPERMISSION = 10112;
	int RES_SECURITY_GROUP          = 10107;
	int RES_SECURITY_MENU           = 10101;
	int RES_SECURITY_ORG            = 10106;
	int RES_SECURITY_PERMISSION     = 10103;
	int RES_SECURITY_ROLE           = 10102;
	int RES_SECURITY_RULE           = 10105;
	int RES_SECURITY_USER           = 10100;

	// 2.注册表模块(registry)
	int RES_REGISTRY_REGISTRY = 10201;

	// 3.附件模块(attachment)
	int RES_ATTACHMENT_ATTACHMENT = 10104;

	// 4.任务模块(job)
	int RES_JOB_JOB = 10600;

	// 5.lookup模块(lookup)
	int RES_LOOKUP_AREA   = 10302;
	int RES_LOOKUP_LOOKUP = 10301;

	// 6.开发运维模块(devops)
	int RES_DEVOPS_BUILDTEMPLATELOG = 10541;
	int RES_DEVOPS_CACHE            = 10544;
	int RES_DEVOPS_GENERATOR        = 19001;
	int RES_DEVOPS_METRICS          = 19003;
	int RES_DEVOPS_PROJECT          = 10540;
	int RES_DEVOPS_SERVER           = 10542;
	int RES_DEVOPS_STAT             = 19002;
	int RES_DEVOPS_SERVICE          = 19005;
	int RES_DEVOPS_APPCON           = 19006;
	int RES_DEVOPS_CACHECON         = 19007;

	// 7、下载模块(download)
	int RES_DOWNLOAD_DOWNLOAD = 13002;

	// 8、国际化管理(i18n)
	int RES_I18N_I18N = 10120;

	// 9、上传模块(upload)
	int RES_UPLOAD_UPLOAD = 13001;

	// 10、工作流模块(workflow)
	int RES_WORKFLOW_DRAFT     = 12005;
	int RES_WORKFLOW_PROCDEF   = 12099;
	int RES_WORKFLOW_WFMONITOR = 12002;
	int RES_WORKFLOW_WORKSPACE = 12001;
}
