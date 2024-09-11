package com.Smart_contact_manager.controller;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import com.Smart_contact_manager.entities.Contact;
import com.Smart_contact_manager.entities.User;
import com.Smart_contact_manager.forms.ContactForm;
import com.Smart_contact_manager.forms.ContactSearchForm;
import com.Smart_contact_manager.helper.AppConstants;
import com.Smart_contact_manager.helper.Helper;
import com.Smart_contact_manager.helper.Message;
import com.Smart_contact_manager.helper.MessageType;
import com.Smart_contact_manager.services.ContactService;
import com.Smart_contact_manager.services.imageService;
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
    private imageService imageService;

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public String addContactVoew(Model model) {
        ContactForm contactForm = new ContactForm();
        contactForm.setName("Tanisha Mehta");
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "/user/add_contact";
    }

    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {

        // process the form data
        // 1 validate form
        if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact

        User user = userService.getUserByEmail(username);
        // 2 process the contact picture
        // image process
        // uplod karne ka code
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

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setClodinaryImagePublicId(filename);

        }
        contactService.save(contact);
        System.out.println(contactForm);

        // 3 set the contact picture url
        // 4 `set message to be displayed on the view

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts/add";

    }

    @RequestMapping
    public String viewContacts(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Authentication authentication,
            Model model) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<Contact> pageConatct = contactService.getByUser(user, page, size, sortBy, direction);
        model.addAttribute("contacts", pageConatct);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    // search handler
    @RequestMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, 
            Model model, Authentication authentication) {
        Page<Contact> pageContact = null;
        var user= userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        else  if (contactSearchForm.getField().equalsIgnoreCase("phoneNumber")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction,user);
        }
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("contactSerachForm", contactSearchForm);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }

    //delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId)
    {
        contactService.delete(contactId);
        return "redirect:/user/contacts";
    }

    //update contact 
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
       @PathVariable("contactId") String contactId, Model model
    )
    {
       var contact= contactService.getById(contactId);
       ContactForm contactForm= new ContactForm();
       contactForm.setName(contact.getName());
       contactForm.setEmail(contact.getEmail());
       contactForm.setPhoneNumber(contact.getPhoneNumber());
       contactForm.setAddress(contact.getAddress());
       contactForm.setDescription(contact.getDescription());
       contactForm.setFavorite(contact.isFavorite()); 
       contactForm.setWebsiteLink(contact.getWebsiteLink());
       contactForm.setLinkedInLink(contact.getLinkedInLink());
       contactForm.setPicture(contact.getPicture());
       model.addAttribute("contactForm", contactForm);
       model.addAttribute("contactId", contactId);
       return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method=RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId, @ModelAttribute ContactForm contactForm, Model model, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "user/update_contact_view";
        }
       var con= contactService.getById(contactId);
       con.setId(contactId);
       con.setName(contactForm.getName());
       con.setAddress(contactForm.getAddress());
       con.setDescription(contactForm.getDescription());
       con.setFavorite(contactForm.isFavorite());
       con.setWebsiteLink(contactForm.getWebsiteLink());
       con.setLinkedInLink(contactForm.getLinkedInLink());
       con.setEmail(contactForm.getEmail());
       con.setPhoneNumber(contactForm.getPhoneNumber());

       //process image
        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty())
        {
            String filename= UUID.randomUUID().toString();
            String imageUrl= imageService.uploadImage(contactForm.getContactImage(), filename);
            con.setClodinaryImagePublicId(filename);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        }
       var updateCon= contactService.update(con);
       model.addAttribute("message", Message.builder().content("Contact Uploaded").type(MessageType.green));

        return "redirect:/user/contacts/view/"+contactId;
    }
}
