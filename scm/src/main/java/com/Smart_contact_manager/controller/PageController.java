package com.Smart_contact_manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    
    @RequestMapping("/home")
    public String home()
    {
        System.out.println("home page");
        return "base";
    }
    @RequestMapping("/about")
    public String aboput()
    {
        return "about";
    }

}
