package io.jalorx.boot.json;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.model.Id;
import io.jalorx.boot.utils.MetaUtils;
import io.micronaut.context.BeanContext;
import io.micronaut.core.reflect.GenericTypeUtils;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.inject.qualifiers.Qualifiers;
import jakarta.inject.Inject;

/**
 * 扩展属性过滤,对需要过滤的属性返回值。
 * 
 * key为$fieldName
 * 
 * @author chenb
 *
 */
@SuppressWarnings({"unchecked"})
public abstract class MetaDataCollector<A extends Annotation, T> {
	static Logger         LOG   = LoggerFactory.getLogger(MetaDataCollector.class);
	static final List<Id> EMPTY = Collections.emptyList();

	@Inject
	protected BeanContext  context;
	private final Class<A> annoType;

	public MetaDataCollector(Class<A> annoType) {
		this.annoType = annoType;
	}

	public MetaDataCollector() {
		this.annoType = GenericTypeUtils.resolveSuperTypeGenericArguments(this.getClass(), MetaDataCollector.class)[0];
	}

	protected abstract List<T> collector(A a, Object vaule);

	public List<Id> collector(Set<T> ids) {
		String name = getServiceName();
		return context.findBean(MetaDataClient.class, Qualifiers.byName(name))
				.map(mc -> mc.getDetails(ids))
				.orElseGet(() -> {
					LOG.info("Did not find bean name '{}' for MetaDataClient", name);
					return EMPTY;
				});
	}

	/**
	 * 服务名称
	 * 
	 * @return
	 */
	protected String getServiceName() {
		return annoName() + ".MetaDataClient";
	}

	public final String annoName() {
		return annoType.getSimpleName();
	}

	public final Class<A> annoType() {
		return annoType;
	}

	public final void collector0(Annotation a, Object vaule) {
		ServerRequestContext.currentRequest()
				.ifPresent(request -> {
					if (MetaUtils.isMetaEnable(request.getHeaders()
							.getFirst(MetaUtils.X_MOS_META))) {
						List<T> rest = collector(annoType.cast(a), vaule);
						if (LOG.isDebugEnabled()) {
							LOG.debug("[@{}] value = [{}]", annoName(), vaule);
						}
						Set<T> s = request.getAttribute(annoType.getName(), Set.class)
								.orElseGet(() -> {
									Set<T> n = new HashSet<>();
									request.setAttribute(annoType.getName(), n);
									return n;
								});
						s.addAll(rest);
					}
				});
	}

}
