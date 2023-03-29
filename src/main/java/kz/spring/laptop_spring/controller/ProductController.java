package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.model.Laptop;
import kz.spring.laptop_spring.model.TestClass;
import kz.spring.laptop_spring.service.LaptopService;
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

    @Autowired
    private LaptopService laptopService;

    @GetMapping("/")
    public String getAllProducts(Model model) {
//        List<Laptop> laptops = laptopService.getAllLaptopsPriceBetween(500000, 999999);
//        List<Laptop> laptops = laptopService.getAllLaptops();
//        List<Laptop> laptops = laptopService.getAllLaptopsOrderByCountDesc();
        List<Laptop> laptops = laptopService.getAllLaptopsOrderByModelAsc();
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
        laptopService.upsertLaptop(new Laptop(null, model, price, count));
        return "redirect:/";
    }

    @GetMapping("/edit/{idshka}")
    public String getEditPage(@PathVariable("idshka") Integer id, Model model) {
        Laptop l = laptopService.getLaptopById(id);
        model.addAttribute("laptop", l);
        model.addAttribute("title", "Edit page");

        return "edit";
    }

    @PostMapping("/edit")
    public String editProduct(@RequestParam("id") Integer id, @RequestParam("model") String model,
                              @RequestParam("count") Integer count, @RequestParam("price") Integer price) {
        System.out.println("id=" + id + "\nmodel=" + model);
        laptopService.upsertLaptop(new Laptop(id, model, price, count));
        return "redirect:/";
    }

    //todo delete

    @GetMapping("/delete/{idshka}")
    public String deleteProduct(@PathVariable("idshka") Integer id) {
        laptopService.deleteLaptop(id);
        return "redirect:/";
    }
}
