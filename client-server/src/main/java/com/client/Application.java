package com.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/api/user")
    public String userinfo(OAuth2AuthenticationToken authentication) {
        // authentication.getAuthorizedClientRegistrationId() returns the
        // registrationId of the Client that was authorized during the oauth2Login() flow
        OAuth2AuthorizedClient authorizedClient =
                this.authorizedClientService.loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        System.out.println(accessToken.getTokenType());

        return authentication.getName();
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
