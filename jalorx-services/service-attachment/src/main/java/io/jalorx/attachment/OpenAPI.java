package io.jalorx.attachment;

import io.jalorx.boot.JalorVersion;
import io.jalorx.boot.annotation.OpenAPISecurity;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(info = @Info(title = "JalorX Attachment", version = JalorVersion.TAG, description = "Attachment API"))
@OpenAPISecurity
class OpenAPI {

}
