package com.Smart_contact_manager.services;

import org.springframework.web.multipart.MultipartFile;

public interface imageService {

   public  String uploadImage(MultipartFile contactImage, String filename);
   String getUrlFromPublicId(String publicId);
} 