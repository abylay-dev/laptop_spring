package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.model.TestClass;
import kz.spring.laptop_spring.service.TestClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class TestController {
    @Autowired
    private TestClassService service;

    @GetMapping("")
    public String test() {
        System.out.println(service.getTestClassText());
        return "testing/test";
    }

    @GetMapping("/set")
    public String settest() {
        service.setTestClassText("hello from testController");
        return "testing/test";
    }
}
