package com.Smart_contact_manager.controller;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.Smart_contact_manager.entities.Contact;
import com.Smart_contact_manager.entities.User;
import com.Smart_contact_manager.forms.ContactForm;
import com.Smart_contact_manager.helper.Helper;
import com.Smart_contact_manager.helper.Message;
import com.Smart_contact_manager.helper.MessageType;
import com.Smart_contact_manager.services.ContactService;
import com.Smart_contact_manager.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
@RequestMapping("/user/contact")
public class ContactController {
   
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private UserService userService;
    
    @RequestMapping("/add")
    public String addContactVoew(Model model){
        ContactForm contactForm= new ContactForm();
        contactForm.setName("Tanisha Mehta");
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "/user/add_contact";
    }

   @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession session)
    {

         if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }
        String username= Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contactService.save(contact);
        
        session.setAttribute("message",
        Message.builder()
                .content("You have successfully added a new contact")
                .type(MessageType.green)
                .build());
        return "redirect:/user/contacts/add";
    }
}
