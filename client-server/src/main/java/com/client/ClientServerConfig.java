package com.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class ClientServerConfig {

    private static final String RESOURCE_ID = "spark";

    @Bean
    BaseOAuth2ProtectedResourceDetails authorizationCodeResource() {
        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
        resource.setId(RESOURCE_ID);
        resource.setClientId("star1");
        resource.setClientSecret("sec");
        resource.setScope(Arrays.asList("read", "write"));
        resource.setAccessTokenUri("http://auth.server.com:8080/oauth/token");
        resource.setUserAuthorizationUri("http://auth.server.com:8080/oauth/authorize");
        resource.setClientAuthenticationScheme(AuthenticationScheme.query);
        resource.setAuthenticationScheme(AuthenticationScheme.query);
        return resource;
    }

    @Bean
    BaseOAuth2ProtectedResourceDetails clientCredentialResource() {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setId(RESOURCE_ID + "/client");
        resource.setClientId("star_client_credentials");
        resource.setClientSecret("sec");
        resource.setScope(Arrays.asList("read"));
        resource.setAccessTokenUri("http://auth.server.com:8080/oauth/token");
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAuthenticationScheme(AuthenticationScheme.form);
        return resource;
    }

    @Bean
    BaseOAuth2ProtectedResourceDetails passwordResourceDetails() {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
        resource.setClientId("star_user_password");
        resource.setClientSecret("sec");
        resource.setUsername("user2");
        resource.setPassword("123456");
        resource.setId(RESOURCE_ID + "/password");
        resource.setAccessTokenUri("http://auth.server.com:8080/oauth/token");
        resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        resource.setAuthenticationScheme(AuthenticationScheme.form);
        return resource;
    }

    BaseOAuth2ProtectedResourceDetails implicitResourceDetails() {
        ImplicitResourceDetails resource = new ImplicitResourceDetails();
        resource.setUserAuthorizationUri("http://auth.server.com:8080/oauth/authorize?response_type=token");
        resource.setAccessTokenUri("http://auth.server.com:8080/oauth/token");
        resource.setClientId("star_implicit");
        resource.setScope(Arrays.asList("read"));
        resource.setClientSecret("sec");
        resource.setId(RESOURCE_ID + "/implicit");
        resource.setClientAuthenticationScheme(AuthenticationScheme.query);
        resource.setAuthenticationScheme(AuthenticationScheme.query);
        return resource;
    }

    @Bean(name = "restTemplate")
    public OAuth2RestTemplate restTemplate(OAuth2ClientContext context) {
        OAuth2RestTemplate rest = new OAuth2RestTemplate(authorizationCodeResource(), context);
        /*AuthorizationCodeAccessTokenProvider authCodeProvider = new AuthorizationCodeAccessTokenProvider();
        authCodeProvider.setStateMandatory(false);
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                Arrays.asList(authCodeProvider));
        rest.setAccessTokenProvider(provider);*/
        return rest;
    }

    @Bean(name = "clientCredentialTemplate")
    public OAuth2RestTemplate clientCredentialTemplate(OAuth2ClientContext context) {
        OAuth2RestTemplate rest = new OAuth2RestTemplate(clientCredentialResource(), context);
        return rest;
    }

    @Bean(name = "userPasswordTemplate")
    public OAuth2RestTemplate userPasswordTemplate(OAuth2ClientContext context) {
        OAuth2RestTemplate rest = new OAuth2RestTemplate(passwordResourceDetails(), context);
        return rest;
    }

    @Bean(name = "implicitTemplate")
    public OAuth2RestTemplate implicitTemplate(OAuth2ClientContext context) {
        OAuth2RestTemplate rest = new OAuth2RestTemplate(implicitResourceDetails(), context);
        return rest;
    }

}
