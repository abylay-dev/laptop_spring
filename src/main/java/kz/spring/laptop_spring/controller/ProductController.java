package kz.spring.laptop_spring.controller;

import kz.spring.laptop_spring.model.Laptop;
import kz.spring.laptop_spring.model.Market;
import kz.spring.laptop_spring.model.TestClass;
import kz.spring.laptop_spring.model.User;
import kz.spring.laptop_spring.service.CountryService;
import kz.spring.laptop_spring.service.LaptopService;
import kz.spring.laptop_spring.service.MarketService;
import kz.spring.laptop_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.security.PermitAll;
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
    @PermitAll
    public String getAllProducts(Model model) {
//        List<Laptop> laptops = laptopService.getAllLaptopsPriceBetween(500000, 999999);
        List<Laptop> laptops = laptopService.getAllLaptops();
//        List<Laptop> laptops = laptopService.getAllLaptopsOrderByCountDesc();
//        List<Laptop> laptops = laptopService.getAllLaptopsOrderByModelAsc();
        model.addAttribute("products", laptops);
        model.addAttribute("title", "Home page");
        model.addAttribute("user", getUserData());
        System.out.println(firstClass + "\t" + firstClass.getText());
        return "home";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String getAddPage(Model model) {
        model.addAttribute("title", "Add page");
        model.addAttribute("countries", countryService.getAllCountries());
        return "add";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addProduct(@RequestParam(value = "model", defaultValue = "Nout") String model,
                             @RequestParam("count") Integer count,
                             @RequestParam("price") Integer price,
                             @RequestParam("country") Integer countryId) {
        laptopService.upsertLaptop(new Laptop(null, model, price, count), countryId);
        return "redirect:/";
    }

    @GetMapping("/edit/{idshka}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String getEditPage(@PathVariable("idshka") Integer id, Model model) {
        Laptop l = laptopService.getLaptopById(id);
        model.addAttribute("laptop", l);
        model.addAttribute("title", "Edit page");
        model.addAttribute("markets", marketService.getAllMarkets());

        return "edit";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String editProduct(@RequestParam("id") Integer id, @RequestParam("model") String model,
                              @RequestParam("count") Integer count, @RequestParam("price") Integer price,
                              @RequestParam("country") Integer countryId) {
        System.out.println("id=" + id + "\nmodel=" + model);
        laptopService.upsertLaptop(new Laptop(id, model, price, count), countryId);
        return "redirect:/";
    }

    //todo delete

    @GetMapping("/delete/{idshka}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteProduct(@PathVariable("idshka") Integer id) {
        laptopService.deleteLaptop(id);
        return "redirect:/";
    }

    @PostMapping("/assign-market")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
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

    @GetMapping("/403")
    public String accessForbidden() {
        return "403";
    }

    private User getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            org.springframework.security.core.userdetails.User secUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

            return userService.getUserByUsername(secUser.getUsername());
        }
        return null;
    }
}
