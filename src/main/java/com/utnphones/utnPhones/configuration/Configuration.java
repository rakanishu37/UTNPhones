package com.utnphones.utnPhones.configuration;

import com.utnphones.utnPhones.session.SessionFilter;
import com.utnphones.utnPhones.session.SuperUserSessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@org.springframework.context.annotation.Configuration
@PropertySource("app.properties")
@EnableScheduling
public class Configuration {

    @Autowired
    SessionFilter sessionFilter;
    @Autowired
    SuperUserSessionFilter superUserSessionFilter;

   /* @Value("${db.driver}")
    String driver;
    @Value("${db.name}")
    String db;
    @Value("${db.host}")
    String host;
    @Value("${db.port}")
    int port;
    @Value("${db.user}")
    String username;
    @Value("${db.password}")
    String password;*/

    @Bean
    public FilterRegistrationBean sessionFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean superUserSessionFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(superUserSessionFilter);
        registration.addUrlPatterns("/superuser/*");
        return registration;
    }

}

