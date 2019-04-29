package com.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Qualifier("restTemplate")
    @Autowired
    OAuth2RestTemplate restTemplate;

    @Autowired
    @Qualifier("clientCredentialTemplate")
    OAuth2RestTemplate clientCredentialTemplate;

    @Qualifier("userPasswordTemplate")
    @Autowired
    OAuth2RestTemplate userPasswordTemplate;

    @Qualifier("implicitTemplate")
    @Autowired
    OAuth2RestTemplate implicitTemplate;

    @GetMapping("/api/user")
    String getUserWithAuthCode() {
        String user = restTemplate.getForObject("http://auth.server.com:8080/api/user", String.class);
        return user;
    }

    @GetMapping("/api/v2/user")
    String getUserWithAuthCodeV2() {
        String user = restTemplate.getForObject("http://auth.server.com:8080/api/user", String.class);
        return user;
    }

    @GetMapping("/api/credential/user")
    String getUserWithClientCredential() {
        String user = clientCredentialTemplate.getForObject("http://auth.server.com:8080/api/user", String.class);
        return user;
    }

    @GetMapping("/api/password/user")
    String getUserWithPassword() {
        String user = userPasswordTemplate.getForObject("http://auth.server.com:8080/api/user", String.class);
        return user;
    }

    @GetMapping("/api/implicit/user")
    String getUserImplicit() {
        String user = implicitTemplate.getForObject("http://auth.server.com:8080/api/user", String.class);
        return user;
    }

}
