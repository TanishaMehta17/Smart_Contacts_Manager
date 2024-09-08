package com.Smart_contact_manager.services;

import com.Smart_contact_manager.entities.Contact;
import com.Smart_contact_manager.entities.User;

import java.util.*;

import org.springframework.data.domain.Page;
public interface ContactService {

  Contact save(Contact contact);
  Contact update(Contact contact);
  List<Contact>getAll();  
  Contact getById(String id);
  void delete(String id);
  List<Contact>search(String name);
  List<Contact>getByUserId(String userId);
  Page<Contact> getByUser(User user, int page, int size , String sortBy,String direction);
} 
