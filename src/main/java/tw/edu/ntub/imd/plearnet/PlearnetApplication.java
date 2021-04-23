package tw.edu.ntub.imd.plearnet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tw.edu.ntub.imd.plearnet.config.properties.FileProperties;
import tw.edu.ntub.imd.plearnet.dto.file.directory.Directory;
import tw.edu.ntub.imd.plearnet.dto.file.directory.DirectoryImpl;
import tw.edu.ntub.imd.plearnet.dto.file.uploader.MultipartFileUploader;
import tw.edu.ntub.imd.plearnet.service.BaseViewService;
import tw.edu.ntub.imd.plearnet.util.http.ResponseUtils;

import java.nio.file.Paths;

@SpringBootApplication
public class PlearnetApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlearnetApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return ResponseUtils.createMapper();
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(@Qualifier("transactionInterceptor") Advice advice) {
        AspectJExpressionPointcut expression = new AspectJExpressionPointcut();
        expression.setExpression("execution(* " + BaseViewService.class.getPackageName() + ".*.*(..))");
        return new DefaultPointcutAdvisor(expression, advice);
    }

    @Bean("fileDirectory")
    public Directory fileDirectory(FileProperties fileProperties) {
        return new DirectoryImpl(Paths.get(fileProperties.getPath()));
    }

    @Bean
    public MultipartFileUploader multipartFileUploader(Directory fileDirectory, FileProperties fileProperties) {
        return new MultipartFileUploader(fileDirectory, fileProperties.getUrl());
    }
}
