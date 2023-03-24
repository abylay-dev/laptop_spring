package kz.spring.laptop_spring.beans;

import kz.spring.laptop_spring.model.TestClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConf {
    @Bean
    @Scope("singleton")
    public TestClass firstClass() {
        System.out.println("Creating TestClass BEAN");
        return new TestClass("test message");
    }

    @Bean
    @Scope("prototype")
    public TestClass secondClass() {
        System.out.println("Creating TestClass BEAN");
        return new TestClass();
    }
}
