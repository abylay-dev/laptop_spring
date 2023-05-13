package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.model.Laptop;
import kz.spring.laptop_spring.model.Role;
import kz.spring.laptop_spring.model.User;
import kz.spring.laptop_spring.repository.RoleRepo;
import kz.spring.laptop_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/add-user-page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addUserPage (Model model) {
        model.addAttribute("roles", roleRepo.findAll());
        return "adduser";
    }

    @PostMapping("/adduser")
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

    @GetMapping("/edit-user-page/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editUserPage (@PathVariable("username")String username, Model model) {
        User userFromDb = userService.getUserByUsername(username);
        model.addAttribute("user", userFromDb);

        for (Role user_roles: userFromDb.getRoles()){
            model.addAttribute("selectedRoleId", user_roles.getId());
            break;
        }
        model.addAttribute("roles", roleRepo.findAll());
        return "editUser";
    }

    @PostMapping("/edituser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editUser(@RequestParam(value = "fullname") String fullname,
                          @RequestParam("username") String username,
                          @RequestParam("role") Long roleId) {
        boolean result = userService.editUser(new User(null, fullname, username), roleId);
        if (result){
            return "redirect:/users";
        }
        return "redirect:/registration?error";
    }

    @GetMapping("/reset-password/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String resetPassword (@PathVariable("username")String username) {
        userService.resetPassword(username);

        return "redirect:/users/edit-user-page/" + username;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
//        System.out.println("++++++++++++ WORKING!!!!" + id);
        return "redirect:/users";
    }
}
