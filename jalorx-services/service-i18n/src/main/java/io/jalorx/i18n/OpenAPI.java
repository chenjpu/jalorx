package io.jalorx.i18n;


import io.jalorx.boot.JalorVersion;
import io.jalorx.boot.annotation.OpenAPISecurity;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(info = @Info(title = "JalorX I18N", version = JalorVersion.TAG, description = "I18N API"))
@OpenAPISecurity
class OpenAPI {

}
