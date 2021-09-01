package io.jalorx.security;

import io.jalorx.boot.JalorVersion;
import io.jalorx.boot.annotation.OpenAPISecurity;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(info = @Info(title = "JalorX Security", version = JalorVersion.TAG, description = "Security API"))
@OpenAPISecurity
class OpenAPI {

}
