package com.Smart_contact_manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.Smart_contact_manager.services.impl.SecurityCustomUserDetailService;



//if you dont use password encoder bean then you wont be able to login as spring security needs a password encoder to proceed make sure to add that bean as well
//By defining this bean, you’re telling Spring Security to use NoOpPasswordEncoder as the default password encoding strategy. If you don’t provide a PasswordEncoder bean, Spring Security will expect that passwords are encoded and may throw an exception if it cannot find a suitable encoder.
@Configuration
 
public class SecurityConfig {

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     // Creating a UserDetails object with username, password, and roles
    //     UserDetails user1 = User
    //         .withDefaultPasswordEncoder()
    //         .username("admin123")
    //         .password("admin123")
    //         .roles("ADMIN","USER")
    //         .build();

    //         UserDetails user2 = User
    //         .withDefaultPasswordEncoder()
    //         .username("user123")
    //         .password("user123")
    //         .build();

    //     // Creating an InMemoryUserDetailsManager with the UserDetails
    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryUserDetailsManager;
    // }

    
    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;
    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider= new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    
    //form login method explaination :-
    //Form-Based Authentication: This method enables form-based login for your application. It provides a default login form where users can enter their credentials (username and password) to authenticate themselves.
     //Customization: The Customizer.withDefaults() part means that the default configuration for the login form is used. This default configuration typically includes settings such as the default login page URL (/login) and handling of login failures.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        //configuration
        httpSecurity.authorizeHttpRequests(authorize->{
       //  authorize.requestMatchers("/home","/register","/service").permitAll();
       authorize.requestMatchers("/user/**").authenticated();
       authorize.anyRequest().permitAll();
        });

       // httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.formLogin(formLogin ->{
        formLogin.loginPage("/login")
        .loginProcessingUrl("/authenticate")
        .successForwardUrl("/user/profile")
        .usernameParameter("email")
        .passwordParameter("password");
        
       });
       httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.logout(logoutForm->{
          logoutForm.logoutUrl("/do-logout");
          logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        //oauth configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });


        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
       return new BCryptPasswordEncoder(); 
    }
}
