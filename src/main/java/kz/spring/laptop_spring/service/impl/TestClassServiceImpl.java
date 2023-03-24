package kz.spring.laptop_spring.service.impl;

import kz.spring.laptop_spring.model.TestClass;
import kz.spring.laptop_spring.service.TestClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestClassServiceImpl implements TestClassService {
    @Autowired
    private TestClass firstClass;

    @Override
    public void setTestClassText(String text) {
        firstClass.setText(text);
        System.out.println("Successfully changed!");
    }

    @Override
    public String getTestClassText() {
        return firstClass.getText() + " FROM SERVICE IMPLEMENTATION";
    }
}
