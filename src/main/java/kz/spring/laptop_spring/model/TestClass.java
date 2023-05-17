package kz.spring.laptop_spring.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Getter
@Setter
public class TestClass {
    private String text;

    public TestClass() {
        System.out.println(" ");
    }

    public TestClass(String text) {
        this.text = text;
    }
}
