package com.Smart_contact_manager.repository;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Smart_contact_manager.entities.Contact;
import com.Smart_contact_manager.entities.User;

@Repository
public interface ContactRepo  extends JpaRepository<Contact, String>{

    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id=:userId ")
    List<Contact> findByUserId( @Param("userId") String userId);
} 

