package io.jalorx.boot.loader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jalorx.boot.annotation.Menu;
import io.jalorx.boot.annotation.Operation;
import io.jalorx.boot.annotation.Resource;
import io.jalorx.boot.context.Loader;
import io.jalorx.boot.entity.PermissionOperation;
import io.jalorx.boot.entity.PermissionResource;
import io.micronaut.context.BeanContext;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.http.annotation.UriMapping;
import io.micronaut.inject.BeanDefinition;
import io.micronaut.inject.qualifiers.Qualifiers;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * 获取权限码资源
 */
@Singleton
public class PermissionLoader
		implements
		Loader<List<PermissionResource>>,
		ApplicationEventListener<StartupEvent> {

	private static Logger LOG = LoggerFactory.getLogger(PermissionLoader.class);

	private List<PermissionResource> permissionResource = new ArrayList<PermissionResource>();

	@Inject
	private Environment environment;

	/**
	 * 生成操作权限点
	 * 
	 * @param resource
	 * @param operation
	 * @return
	 */
	private PermissionOperation build(int resCode, int optCode, String optDesc) {
		PermissionOperation perm = new PermissionOperation();
		perm.setCode(String.format("%03d", optCode));
		perm.setDesc(String.format("%s", optDesc));
		perm.setParentId(Long.valueOf(resCode));
		return perm;
	}

	public List<PermissionResource> load() {
		synchronized (this) {
			return permissionResource;
		}
	}

	@Override
	public void onApplicationEvent(StartupEvent event) {

		String serviceCode = environment.getProperty("service.code", String.class, "00000");

		LOG.info("Loading Permission Resources, Service Code is `{}`", serviceCode);

		BeanContext beanContext = event.getSource();

		Collection<BeanDefinition<?>> definitions = beanContext.getBeanDefinitions(Qualifiers.byStereotype(Resource.class));

		Map<Integer, PermissionResource> resourcesMap = new HashMap<>();

		Map<String, PermissionOperation> operationsMap = new HashMap<>();

		for (BeanDefinition<?> definition : definitions) {
			AnnotationValue<Resource> resourceAnn = definition.getAnnotation(Resource.class);
			int                       resCode     = resourceAnn.getRequiredValue(Integer.class);
			String                    model       = resourceAnn.getRequiredValue("model", String.class);
			if (StringUtils.isEmpty(model)) {
				model = "JalorX";
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("Loading Resource `{}`,Code is `{}` model is '{}'", definition.getName(), resCode, model);
			}
			String prefixPath = "/";

			if (definition.hasAnnotation(UriMapping.class)) {
				AnnotationValue<UriMapping> uriMappingAnn = definition.getAnnotation(UriMapping.class);
				prefixPath = uriMappingAnn.getRequiredValue(String.class);
			}

			String resDesc = resourceAnn.get("desc", String.class)
					.orElse(String.format("%s(%05d)", model, resCode));

			resourcesMap.putIfAbsent(resCode, new PermissionResource(serviceCode, String.valueOf(resCode), resDesc, model));

			PermissionResource permissionResource = resourcesMap.get(resCode);

			Set<PermissionOperation> operationsSet = new HashSet<PermissionOperation>();

			if (definition.hasAnnotation(Menu.class)) {
				AnnotationValue<Menu> menuAnn    = definition.getAnnotation(Menu.class);
				Operation[]           operations = menuAnn.getRequiredValue(Operation[].class);
				for (Operation opt : operations) {
					int code = opt.value() == 0 ? opt.code() : opt.value();;
					String key = String.format("%05d.%03d", resCode, code);
					operationsMap.putIfAbsent(key, build(resCode, code, opt.desc()));
					operationsSet.add(operationsMap.get(key));
				}
			}

			final String fillPrefixPath = prefixPath;

			definition.getExecutableMethods()
					.forEach(method -> {

						String fullPath = fillPrefixPath;

						if (method.hasStereotype(UriMapping.class)) {
							AnnotationValue<UriMapping> uriMappingAnn = definition.getAnnotation(UriMapping.class);
							fullPath += uriMappingAnn.getRequiredValue(String.class);
						}

						final String fillFullPath = fullPath;

						method.findAnnotation(Operation.class)
								.ifPresent(opt -> {

									int code = opt.getRequiredValue(Integer.class);
									String key = String.format("%05d.%03d", resCode, code);
									String desc = opt.get("desc", String.class)
											.orElse("Operation/" + code);
									operationsMap.putIfAbsent(key, build(resCode, code, desc));
									operationsSet.add(operationsMap.get(key)
											.addUrl(fillFullPath));

								});

						definition.findAnnotation(Operation.class)
								.ifPresent(opt -> {

									int code = opt.getRequiredValue(Integer.class);
									String key = String.format("%05d.%03d", resCode, code);
									String desc = opt.get("desc", String.class)
											.orElse("Operation/" + code);
									operationsMap.putIfAbsent(key, build(resCode, code, desc));
									operationsSet.add(operationsMap.get(key)
											.addUrl(fillFullPath));

								});

					});
			permissionResource.addAll(operationsSet);
		}

		synchronized (this) {
			this.permissionResource = new ArrayList<>(resourcesMap.values());
		}

	}

}
