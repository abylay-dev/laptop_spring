package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.database.DbManager;
import kz.spring.laptop_spring.model.Laptop;
import kz.spring.laptop_spring.model.TestClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("products")
public class ProductController {

    @Autowired
    private TestClass firstClass;

    @GetMapping("/")
    public String getAllProducts(Model model) {
        List<Laptop> laptops = DbManager.getLaptops();
        model.addAttribute("products", laptops);
        model.addAttribute("title", "Home page");
        System.out.println(firstClass + "\t" + firstClass.getText());
        return "home";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("title", "Add page");

        return "add";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam(value = "model", defaultValue = "Nout") String model,
                             @RequestParam("count") Integer count,
                             @RequestParam("price") Integer price) {
        DbManager.addLaptop(model, count, price);
        return "redirect:/";
    }

    @GetMapping("/edit/{idshka}")
    public String getEditPage(@PathVariable("idshka") Integer id, Model model) {
        Laptop l = DbManager.getLaptop(id);
        model.addAttribute("laptop", l);
        model.addAttribute("title", "Edit page");

        return "edit";
    }

    @PostMapping("/edit")
    public String editProduct(@RequestParam("id") Integer id, @RequestParam("model") String model,
                              @RequestParam("count") Integer count, @RequestParam("price") Integer price) {
        System.out.println("id=" + id + "\nmodel=" + model);
        //todo edit
        return "redirect:/";
    }

    //todo delete
}
