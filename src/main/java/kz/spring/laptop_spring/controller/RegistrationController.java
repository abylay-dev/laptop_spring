package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.model.User;
import kz.spring.laptop_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String registration() {
        return "registration";
    }

    //todo
    @PostMapping("/add")
    public String addUser(@RequestParam(value = "user_fullname") String fullname,
                             @RequestParam("user_username") String username,
                             @RequestParam("user_password") String password,
                             @RequestParam("user_repassword") String repassword,
                            @RequestParam("role") String roleId) {
        boolean result = userService.upsertUser(new User(null, fullname, username, password, repassword), roleId);
        if (result){
            return "redirect:/login";
        }
        return "redirect:/registration?error";
    }
}
