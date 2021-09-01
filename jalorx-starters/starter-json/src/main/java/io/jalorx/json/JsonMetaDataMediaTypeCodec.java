package io.jalorx.json;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jalorx.boot.json.MetaDataCollector;
import io.jalorx.boot.model.ErrorVO;
import io.jalorx.boot.model.MetaResponce;
import io.jalorx.boot.utils.MetaUtils;
import io.micronaut.context.BeanProvider;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.codec.CodecConfiguration;
import io.micronaut.http.codec.CodecException;
import io.micronaut.http.context.ServerRequestContext;
import io.micronaut.jackson.codec.JsonMediaTypeCodec;
import io.micronaut.runtime.ApplicationConfiguration;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
//@BootstrapContextCompatible
@Replaces(JsonMediaTypeCodec.class)
public class JsonMetaDataMediaTypeCodec extends JsonMediaTypeCodec {

	public static final String CONFIGURATION_QUALIFIER = "json";

	static Logger LOG = LoggerFactory.getLogger(JsonMetaDataMediaTypeCodec.class);
	@Inject
	MetaDataCollector<? extends Annotation, ?>[] metaDataFilters;

	public JsonMetaDataMediaTypeCodec(ObjectMapper objectMapper, ApplicationConfiguration applicationConfiguration,
			@Named(CONFIGURATION_QUALIFIER) @Nullable CodecConfiguration codecConfiguration) {
		super(objectMapper, applicationConfiguration, codecConfiguration);
	}

	/**
	 * @param objectMapper             To read/write JSON
	 * @param applicationConfiguration The common application configurations
	 * @param codecConfiguration       The configuration for the codec
	 */
	@Inject
	public JsonMetaDataMediaTypeCodec(BeanProvider<ObjectMapper> objectMapper,
			ApplicationConfiguration applicationConfiguration,
			@Named(CONFIGURATION_QUALIFIER) @Nullable CodecConfiguration codecConfiguration) {
		super(objectMapper, applicationConfiguration, codecConfiguration);
	}

	@Override
	public <T> void encode(T object, OutputStream outputStream) throws CodecException {
		super.encode(object, outputStream);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> byte[] encode(T object) throws CodecException {
		byte[] body = super.encode(object);

		return ServerRequestContext.currentRequest().map(request -> {

			if (request.getMethod() != HttpMethod.GET) {
				return body;
			}

			if (request.getAttribute(ErrorVO.ATTR_NAME).isPresent()) {
				return body;
			}

			if (!MetaUtils.isMetaBodyResponse(request.getHeaders().getFirst(MetaUtils.X_MOS_META))) {
				return body;
			}

			MetaResponce<T> meta = MetaResponce.ok(object);

			for (MetaDataCollector<? extends Annotation, ?> collect : metaDataFilters) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Collector body meta data for type [{}]", collect.annoName());
				}
				request.getAttribute(collect.annoType().getName(), Set.class).ifPresent((ids) -> {
					meta.meta(collect.annoType(), collect.collector(ids));
				});
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream(128);
			byte[] head = "{\"meta\":".getBytes();
			out.write(head, 0, head.length);
			super.encode(meta.getMeta(), out);
			byte[] dela = ",\"data\":".getBytes();
			out.write(dela, 0, dela.length);
			return ArrayUtils.addAll(out.toByteArray(), ArrayUtils.add(body, (byte) '}'));
		}).orElse(body);
	}

}
