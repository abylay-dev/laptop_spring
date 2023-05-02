package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.model.Laptop;
import kz.spring.laptop_spring.model.User;
import kz.spring.laptop_spring.repository.RoleRepo;
import kz.spring.laptop_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepo roleRepo;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String getAllUsers (Model model) {
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("add-user-page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUserPage (Model model) {
        model.addAttribute("roles", roleRepo.findAll());
        return "adduser";
    }

    @PostMapping("adduser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUser(@RequestParam(value = "fullname") String fullname,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("repassword") String repassword,
                             @RequestParam("role") String roleId) {
        boolean result = userService.upsertUser(new User(null, fullname, username, password, repassword), roleId);
        if (result){
            return "redirect:/users";
        }
        return "redirect:/registration?error";
    }
}
