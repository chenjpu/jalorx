package io.jalorx.boot.security;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import io.jalorx.boot.AuthInfo;
import io.micrometer.core.lang.Nullable;

public final class AuthInfoContext {

	public static final String AUTHINFO_ATTRIBUTE = "jalorx.AUTHINFO";

	private static final ThreadLocal<AuthInfo> AUTHINFO = new ThreadLocal<>();

	private AuthInfoContext() {
	}

	public static void set(@Nullable AuthInfo info) {
		if (info == null) {
			AUTHINFO.remove();
		} else {
			AUTHINFO.set(info);
		}
	}

	/**
	 * Wrap the execution of the given runnable in request context processing.
	 *
	 * @param info     The AuthInfo
	 * @param runnable The runnable
	 */
	public static void with(AuthInfo info, Runnable runnable) {
		AuthInfo existing = AUTHINFO.get();
		boolean isSet = false;
		try {
			if (existing == null) {
				isSet = true;
				AUTHINFO.set(info);
			}
			runnable.run();
		} finally {
			if (isSet) {
				AUTHINFO.remove();
			}
		}
	}

	/**
	 * Return a new runnable by instrumenting the given runnable with request
	 * context handling.
	 *
	 * @param info     The AuthInfo
	 * @param runnable The runnable
	 * @return The newly instrumented runnable
	 */
	public static Runnable instrument(AuthInfo info, Runnable runnable) {
		return () -> with(info, runnable);
	}

	/**
	 * Wrap the execution of the given callable in request context processing.
	 *
	 * @param info     The AuthInfo
	 * @param callable The callable
	 * @param <T>      The return type of the callable
	 * @return The return value of the callable
	 */
	public static <T> T with(AuthInfo info, Supplier<T> callable) {
		AuthInfo existing = AUTHINFO.get();
		boolean isSet = false;
		try {
			if (existing == null) {
				isSet = true;
				AUTHINFO.set(info);
			}
			return callable.get();
		} finally {
			if (isSet) {
				AUTHINFO.remove();
			}
		}
	}

	/**
	 * Wrap the execution of the given callable in request context processing.
	 *
	 * @param info     The AuthInfo
	 * @param callable The callable
	 * @param <T>      The return type of the callable
	 * @return The return value of the callable
	 * @throws Exception If the callable throws an exception
	 */
	public static <T> T with(AuthInfo info, Callable<T> callable) throws Exception {
		AuthInfo existing = AUTHINFO.get();
		boolean isSet = false;
		try {
			if (existing == null) {
				isSet = true;
				AUTHINFO.set(info);
			}
			return callable.call();
		} finally {
			if (isSet) {
				AUTHINFO.remove();
			}
		}
	}

	/**
	 * Retrieve the current AuthInfo context.
	 *
	 * @param <T> The body type
	 * @return The AuthInfo context if it is present
	 */
	public static Optional<AuthInfo> current() {
		return Optional.ofNullable(AUTHINFO.get());
	}
}
