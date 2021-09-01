package io.jalorx.boot.security.context;

import io.jalorx.boot.AuthInfo;
import io.jalorx.boot.security.AuthInfoContext;
import io.micronaut.scheduling.instrument.Instrumentation;
import io.micronaut.scheduling.instrument.InvocationInstrumenter;
import io.micronaut.scheduling.instrument.InvocationInstrumenterFactory;
import io.micronaut.scheduling.instrument.ReactiveInvocationInstrumenterFactory;
import jakarta.inject.Singleton;

/**
 * 安全上下文环境注入
 * 
 * @author chenb
 *
 */
@Singleton
final class AuthInfoContextInstrumentation
		implements
		InvocationInstrumenterFactory,
		ReactiveInvocationInstrumenterFactory {

	/**
	 * Creates optional invocation instrumenter.
	 *
	 * @return An optional that contains the invocation instrumenter
	 */
	@Override
	public InvocationInstrumenter newInvocationInstrumenter() {
		return AuthInfoContext.current()
				.map(LocalInstrumenter::new)
				.orElse(null);
	}

	@Override
	public InvocationInstrumenter newReactiveInvocationInstrumenter() {
		return newInvocationInstrumenter();
	}

	static class LocalInstrumenter implements InvocationInstrumenter {

		private final AuthInfo invocationAuthInfo;
		private AuthInfo       currentAuthInfo;
		private boolean        isSet;

		public LocalInstrumenter(AuthInfo invocationAuthInfo) {
			this.invocationAuthInfo = invocationAuthInfo;
			isSet                   = false;
		}

		@Override
		public Instrumentation newInstrumentation() {

			currentAuthInfo = AuthInfoContext.current()
					.orElse(null);
			if (invocationAuthInfo != currentAuthInfo) {
				isSet = true;
				AuthInfoContext.set(invocationAuthInfo);
			}

			return (Instrumentation) cleanup -> {
				if (isSet || cleanup) {
					AuthInfoContext.set(cleanup ? null : currentAuthInfo);
					isSet = false;
				}
			};

		}
	}
}
