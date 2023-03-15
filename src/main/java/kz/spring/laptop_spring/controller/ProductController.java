package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.database.DbManager;
import kz.spring.laptop_spring.model.Laptop;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("products")
public class ProductController {

    @GetMapping("/")
    public String getAllProducts(Model model) {
        List<Laptop> laptops = DbManager.getLaptops();
        model.addAttribute("products", laptops);

        return "home";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
//        List<Laptop> laptops = DbManager.getLaptops();
//        model.addAttribute("products", laptops);

        return "add";
    }
}
