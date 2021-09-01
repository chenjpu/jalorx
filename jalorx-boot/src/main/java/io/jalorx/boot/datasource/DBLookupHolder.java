package io.jalorx.boot.datasource;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBLookupHolder {

	private static final Logger LOG = LoggerFactory.getLogger(DBLookupHolder.class);

	private static final ThreadLocal<String> dbIDHolder = new ThreadLocal<>();

	private static final Random R = new Random();

	public static void bind(String dbID) {
		LOG.debug("Binding datasource id `{}`", dbID);
		dbIDHolder.set(dbID);
	}

	public static void release() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Releasing datasource id `{}`", dbIDHolder.get());
		}
		dbIDHolder.set(null);
	}

	public static String get() {
		return dbIDHolder.get();
	}

	public static void master(String name) {
		bind("master-" + name);
	}

	public static void slave(String name, int slave) {
		int index = R.nextInt(slave) + 1;
		bind(String.format("slave%d-" + name, index));
	}
}
