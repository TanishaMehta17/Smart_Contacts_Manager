package com.Smart_contact_manager.controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        return "home";
    }



    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }


    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "service";
    }

    @GetMapping("/login")
    public String login() {
        return new String("login");
    }
    
    @GetMapping("/register")
    public String register() {
        return new String("register");
    }
    

}
