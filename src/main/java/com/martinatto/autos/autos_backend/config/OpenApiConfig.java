package com.martinatto.autos.autos_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI autosApi(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Sistema de Venta Particular de Autos")
                                .version("1.0.0")
                                .description("""
                                        API REST desarrollada con Spring Boot
                                        para la gestión de autos, clientes,
                                        ventas y pagos.
                                        """)
                                .contact(
                                        new Contact()
                                                .name("Maximiliano Martinatto")
                                                .email("martinattomaximiliano@gmail.com")
                                                .url("https://github.com/maximartinatto")
                                )
                );
    }
}
