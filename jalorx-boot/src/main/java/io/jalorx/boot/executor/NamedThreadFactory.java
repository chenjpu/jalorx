package io.jalorx.boot.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.scheduling.TaskExecutors;

/**
 * @author chenb
 *
 */
public abstract class NamedThreadFactory implements ThreadFactory {
	private final ThreadGroup   group;
	private final AtomicInteger threadNumber = new AtomicInteger(1);
	private final String        id;

	public NamedThreadFactory(String id) {
		SecurityManager s = System.getSecurityManager();
		group   = (s != null) ? s.getThreadGroup()
				: Thread.currentThread()
						.getThreadGroup();
		this.id = id;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, String.format("%s-worker-%d", id, threadNumber.getAndIncrement()));
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}

	@Introspected
	public static class IOTF extends NamedThreadFactory {
		public IOTF() {
			super(TaskExecutors.IO);
		}
	}

	@Introspected
	public static class MCTF extends NamedThreadFactory {
		public MCTF() {
			super(TaskExecutors.MESSAGE_CONSUMER);
		}
	}

	@Introspected
	public static class SDTF extends NamedThreadFactory {
		public SDTF() {
			super(TaskExecutors.SCHEDULED);
		}
	}
}
