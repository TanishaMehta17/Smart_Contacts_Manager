package com.Smart_contact_manager.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.Smart_contact_manager.entities.Contact;
import com.Smart_contact_manager.entities.User;
import com.Smart_contact_manager.helper.ResourceNotFoundException;
import com.Smart_contact_manager.repository.ContactRepo;
import com.Smart_contact_manager.services.ContactService;

public class ContactServiceImpl implements ContactService {

  @Autowired
  private ContactRepo contactRepo;

  @Override
  public Contact save(Contact contact) {
    String contactId = UUID.randomUUID().toString();
    contact.setId(contactId);
    return contactRepo.save(contact);
  }

  @Override
  public Contact update(Contact contact) {
    return null;
  }

  @Override
  public List<Contact> getAll() {
    return contactRepo.findAll();
  }

  @Override
  public Contact getById(String id) {
    return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not fount"));
  }

  @Override
  public void delete(String id) {
    var contact = contactRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Contact not found with the given id"));
    contactRepo.delete(contact);

  }

  @Override
  public List<Contact> search(String name) {
    return null;
  }

  @Override
  public List<Contact> getByUserId(String userId) {
    return contactRepo.findByUserId(userId);
  }

  @Override
  public Page<Contact> getByUser(User user, int page, int size,String sortBy,String direction) {

    Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

    var pageable = PageRequest.of(page, size, sort);

    return contactRepo.findByUser(user, pageable);
  }

}
