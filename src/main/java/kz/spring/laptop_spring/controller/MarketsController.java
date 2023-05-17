package kz.spring.laptop_spring.controller;


import kz.spring.laptop_spring.model.Market;
import kz.spring.laptop_spring.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/markets")
public class MarketsController {


    @Autowired
    private MarketService marketService;

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String getAllMarkets (Model model) {
        List<Market> markets = marketService.getAllMarkets();

        model.addAttribute("markets", markets);
        model.addAttribute("title", "Markets page");
        return "markets";
    }

    @GetMapping("/edit-page/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String editMarket (@PathVariable("id") Integer id, Model model) {
        Market marketFromDb = marketService.getMarket(id);

        model.addAttribute("market", marketFromDb);
        return "editmarkets";
    }

    @PostMapping("/editmarket")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String editMarket(@RequestParam(value = "id") Integer id,
                             @RequestParam("name") String name) {
         marketService.editMarket(id, name);
         return "redirect:/markets";
    }

    @GetMapping("/delete-market/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteMarket(@PathVariable("id") Integer id) {
        marketService.deleteMarketById(id);
//        System.out.println("++++++++++++ WORKING!!!!" + id);
        return "redirect:/markets";
    }

    @GetMapping("/add-market-page")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addMarket (Model model) {
        return "addmarket";
    }

    @PostMapping("/addmarket")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addMarket(@RequestParam(value = "name") String name) {
        marketService.addMarket(name);
        return "redirect:/markets";
    }

}
