package io.jalorx.boot.utils;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流处理相关工具类
 * 
 * @author chenb
 *
 */
public class StreamUtils {
	private static Logger LOG = LoggerFactory.getLogger(StreamUtils.class);

	public static void safeClose(Closeable... steams) {

		for (Closeable steam : steams) {
			if (steam != null) {
				try {
					steam.close();
				} catch (IOException e) {
					LOG.warn("Failed to close a stream.", e);
				}
			}
		}
	}
}
