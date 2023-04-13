package kz.spring.laptop_spring.conf;

import kz.spring.laptop_spring.model.TestClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
