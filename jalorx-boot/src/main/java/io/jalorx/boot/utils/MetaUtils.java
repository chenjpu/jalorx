package io.jalorx.boot.utils;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class MetaUtils {

	/**
	 * 扩展属性标志请求头
	 * 
	 * 0:关闭
	 * 
	 * 1：打开
	 * 
	 * 不设置头部相应元信息
	 */
	public static final String X_MOS_META = "x-mos-meta";

	/**
	 * 是否元数据body相应
	 * 
	 * @param metaHead
	 * @return
	 */
	public static boolean isMetaBodyResponse(Optional<String> metaHead) {
		return metaHead.map(h -> StringUtils.equals(h, "1"))
				.orElse(false);
	}

	/**
	 * 是否开启元数据收集
	 * 
	 * @param metaHead
	 * @return
	 */
	public static boolean isMetaEnable(Optional<String> metaHead) {
		return metaHead.map(h -> !StringUtils.equals(h, "0"))
				.orElse(true);
	}
}
