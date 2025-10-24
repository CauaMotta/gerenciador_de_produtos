package br.com.ocauamotta.GerenciadorDeProdutos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe de configuração do CORS para a aplicação.
 * Define as regras de acesso de diferentes origens à API REST.
 */
@Configuration
public class CorsConfig {
    /**
     * Define e configura as regras de mapeamento do CORS.
     * Este {@code Bean} configura globalmente as permissões de acesso HTTP.
     *
     * @return Uma instância de {@code WebMvcConfigurer} com as configurações de CORS aplicadas.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * Adiciona as regras de CORS para todos os endpoints da aplicação.
             *
             * @param registry O registro de mapeamentos CORS do Spring MVC.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173", "http://localhost:3000", "http://localhost:4173/")
                        .allowedMethods("GET","POST","PUT","DELETE");
            }
        };
    }
}