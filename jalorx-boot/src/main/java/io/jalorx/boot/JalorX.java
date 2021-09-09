package io.jalorx.boot;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;

/**
 * @author chenb
 *
 */
public abstract class JalorX {

	public static ApplicationContext run(Class<?> cls, String... args) {
		return JalorX.run(new Class<?>[]{cls}, false, args);
	}

	public static ApplicationContext run(Class<?> cls, boolean eagerInitSingletons, String... args) {
		return JalorX.run(new Class<?>[]{cls}, eagerInitSingletons, args);
	}

	public static ApplicationContext run(Class<?>[] classes, String... args) {
		return JalorX.run(classes, false, args);
	}

	public static ApplicationContext run(Class<?>[] classes, boolean eagerInitSingletons, String... args) {
		JalorVersion.printBanner();
		return Micronaut.build(args)
				.classes(classes)
				.banner(false)
				.eagerInitSingletons(false)
				.start();
	}

}
