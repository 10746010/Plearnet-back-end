package tw.edu.ntub.imd.plearnet.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tw.edu.ntub.imd.plearnet.config.parammapping.RenamingProcessor;
import tw.edu.ntub.imd.plearnet.config.properties.FileProperties;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class Config implements WebMvcConfigurer {
    private final Logger logger = LogManager.getLogger(Config.class);
    private final FileProperties fileProperties;

    @Autowired
    public Config(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
        System.out.println(new BCryptPasswordEncoder().encode("plearnet"));
    }

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setDefaultCharset(StandardCharsets.UTF_8);
        return converter;
    }

    @Bean
    public TomcatServletWebServerFactory containerFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected void customizeConnector(Connector connector) {
                int maxSize = 50000000;
                super.customizeConnector(connector);
                connector.setMaxPostSize(maxSize);
                connector.setMaxSavePostSize(maxSize);
                connector.setParseBodyMethods("GET,POST,DELETE,PUT,PATCH");
                if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
                    ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(maxSize);
                    logger.info("Set MaxSwallowSize " + maxSize);
                }
            }
        };
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://211.75.1.204:50001",
                        "http://140.131.115.147:3000",
                        "http://140.131.115.162:3000",
                        "http://140.131.115.163:3000",
                        "http://localhost:3000"
                )
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("X-Auth-Token");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler(String.format("/%s/**", fileProperties.getName()))
                .addResourceLocations(String.format("file:%s", fileProperties.getPath()));
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/favicon.ico");

        logger.info("增加路徑對應：" + String.format("/%s/**", fileProperties.getName()));
        logger.info("對應到的實體路徑為：" + String.format("file:%s", fileProperties.getPath()));
        logger.info("增加路徑對應：/favicon.ico");
        logger.info("對應到的實體路徑為：classpath:/static/favicon.ico");
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(renamingProcessor());
    }

    @Bean
    public RenamingProcessor renamingProcessor() {
        return new RenamingProcessor(true);
    }
}
