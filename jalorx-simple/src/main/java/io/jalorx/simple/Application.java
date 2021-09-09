package io.jalorx.simple;

import io.jalorx.boot.JalorX;
import io.jalorx.boot.annotation.OpenAPISecurity;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "JalorX Demo", version = "2.0.0", description = "Demo API"))
@OpenAPISecurity
public class Application {

	public static void main(String[] args) {
		//及时初始化单例bean策略
		JalorX.run(Application.class, true, args);
		//延迟初始化单例bean策略
		//JalorX.run(Application.class, args);
	}

}
