package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.model.Laptop;
import kz.spring.laptop_spring.model.Market;
import kz.spring.laptop_spring.model.TestClass;
import kz.spring.laptop_spring.service.CountryService;
import kz.spring.laptop_spring.service.LaptopService;
import kz.spring.laptop_spring.service.MarketService;
import kz.spring.laptop_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("products")
public class ProductController {

    @Autowired
    private TestClass firstClass;

    @Autowired
    private LaptopService laptopService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private UserService userService;

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
        model.addAttribute("countries", countryService.getAllCountries());
        return "add";
    }

    @PostMapping("/add")
    public String addProduct(@RequestParam(value = "model", defaultValue = "Nout") String model,
                             @RequestParam("count") Integer count,
                             @RequestParam("price") Integer price,
                             @RequestParam("country") Integer countryId) {
        laptopService.upsertLaptop(new Laptop(null, model, price, count), countryId);
        return "redirect:/";
    }

    @GetMapping("/edit/{idshka}")
    public String getEditPage(@PathVariable("idshka") Integer id, Model model) {
        Laptop l = laptopService.getLaptopById(id);
        model.addAttribute("laptop", l);
        model.addAttribute("title", "Edit page");
        List<Market> laptopMarkets = l.getMarkets();
        List<Market> markets = marketService.getAllMarkets();
        for (Market m : laptopMarkets) {
            markets.remove(m);
        }
        model.addAttribute("markets", markets);

        return "edit";
    }

    @PostMapping("/edit")
    public String editProduct(@RequestParam("id") Integer id, @RequestParam("model") String model,
                              @RequestParam("count") Integer count, @RequestParam("price") Integer price,
                              @RequestParam("country") Integer countryId) {
        System.out.println("id=" + id + "\nmodel=" + model);
        laptopService.upsertLaptop(new Laptop(id, model, price, count), countryId);
        return "redirect:/";
    }

    @GetMapping("/delete/{idshka}")
    public String deleteProduct(@PathVariable("idshka") Integer id) {
        laptopService.deleteLaptop(id);
        return "redirect:/";
    }

    @PostMapping("/assign-market")
    public String assignMarket(@RequestParam("laptop_id") Integer laptop_id,
                               @RequestParam("market_id") Integer market_id) {
        Market market = marketService.getMarket(market_id);
        if (market != null) {
            Laptop laptop = laptopService.getLaptopById(laptop_id);
            List<Market> laptop_markets = laptop.getMarkets();
            if (laptop_markets == null) {
                laptop_markets = new ArrayList<>();
            }
            System.out.println(laptop.getMarkets());
            laptop_markets.add(market);
            laptopService.upsertLaptop(laptop, laptop.getCountry().getId());
            return "redirect:/edit/" + laptop_id;
        }
        return "redirect:/";
    }

    @PostMapping("/delete-market")
    public String deleteMarket(@RequestParam("laptop_id") Integer laptop_id,
                               @RequestParam("market_id") Integer market_id) {
        Market market = marketService.getMarket(market_id);
        if (market != null) {
            Laptop laptop = laptopService.getLaptopById(laptop_id);
            List<Market> laptop_markets = laptop.getMarkets();
            if (laptop_markets == null) {
                laptop_markets = new ArrayList<>();
            }
            System.out.println(laptop.getMarkets());
            laptop_markets.remove(market);
            laptopService.upsertLaptop(laptop, laptop.getCountry().getId());
            return "redirect:/edit/" + laptop_id;
        }
        return "redirect:/";
    }

    /*@GetMapping("/registration")
    public String getAllUser(User use) {
        List<User> users = userService.getAllUsersOrderByLoginAsc();
        return "";
    }*/

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "register";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(name = "login", defaultValue = "Login") String login,
                          @RequestParam("password") String password,
                          @RequestParam("fio") String fio) {
//        if (userService.registrationUser(login, password, fio)){
//            return "redirect:/";
//        } else {
//        }
//            return "redirect:/registration-page";
        return userService.registrationUser(login, password, fio) ? "redirect:/" : "redirect:/register";
    }

    @GetMapping("/auth")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("login") String login, @RequestParam("password") String password) {
        if (userService.getAllUsersOrderByLoginAsc(login, password)) {
            return "redirect:/";
        }
        return "";
    }
}
