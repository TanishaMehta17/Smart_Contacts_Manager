package com.Smart_contact_manager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Smart_contact_manager.entities.User;
import com.Smart_contact_manager.helper.Helper;
import com.Smart_contact_manager.services.UserService;

//method in this controller will be executed for allt he request
public class RootController {

     @Autowired
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
     //model arrtribute annotation will allow u to access this function to all the api or url i.e dahsboard and prfile
    //it will run before controller for this whole page
    @ModelAttribute
    public void addLoggedInUserInformation(Authentication authentication, Model model) {
        if(authentication==null)
        return;
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in :{}", username);
        User user = userService.getUserByEmail(username);
        model.addAttribute("loggedInUser", user);
       
    }
}
