package com.Smart_contact_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Smart_contact_manager.entities.Contact;
import com.Smart_contact_manager.services.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private ContactService contactService;
    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId)
    {
          return contactService.getById(contactId);
    }

}
