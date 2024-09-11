package com.Smart_contact_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {


@Bean 
public Cloudinary cloudinary()
{

    return new  Cloudinary(
    ObjectUtils.asMap(
        "cloud_name","dh1g3ropu",
        "api_key","133564758886637",
        "api_secret","e5Xw_w1jnOJJG-qJDku2BI6zitU")
    );
}
}
