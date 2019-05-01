package com.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Qualifier("restTemplate")
    @Autowired
    OAuth2RestTemplate restTemplate;

    @GetMapping("/api/userinfo")
    public String userinfo(OAuth2AuthenticationToken authentication) {
        // authentication.getAuthorizedClientRegistrationId() returns the
        // registrationId of the Client that was authorized during the oauth2Login() flow
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        System.out.println(authorizedClient.getAccessToken().getTokenType().getValue());

        String user = restTemplate.getForObject("http://localhost:8081/api/user", String.class);
        return user;
    }

    @RequestMapping("/login/oauth2/code/shopping")
    public String shopping() {
        return "success";
    }

    @RequestMapping("/")
    public String index() {
        return "success";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
