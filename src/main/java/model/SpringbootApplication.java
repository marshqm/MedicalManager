package model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.web.bind.annotation.RestController;

/**
 该注解指定项目为springboot，由此类当作程序入口
 自动装配 web 依赖的环境

 **/
@SpringBootApplication
@RestController
public class SpringbootApplication implements EmbeddedServletContainerCustomizer {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {

    }

}