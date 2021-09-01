package io.jalorx.lookup;


import io.jalorx.boot.JalorVersion;
import io.jalorx.boot.annotation.OpenAPISecurity;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(info = @Info(title = "JalorX Lookup", version = JalorVersion.TAG, description = "Lookup API"))
@OpenAPISecurity
class OpenAPI {

}
