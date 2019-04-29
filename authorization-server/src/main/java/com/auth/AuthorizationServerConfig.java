package com.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String RESOURCE_ID = "spark";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("star1")
                    .resourceIds(RESOURCE_ID)
                    .secret(new BCryptPasswordEncoder().encode("sec"))
                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                    .redirectUris("http://auth.client.com:8081/api/user")
                    .authorizedGrantTypes( "authorization_code")
                    .scopes("read", "write")
                .and()
                .withClient("star2")
                    .resourceIds(RESOURCE_ID)
                    .secret(new BCryptPasswordEncoder().encode("sec"))
                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                    .redirectUris("http://demo.client.com:8082/login/oauth2/code/shopping")
                    .authorizedGrantTypes( "authorization_code")
                    .scopes("read", "write")
                .and()
                .withClient("star_client_credentials")
                    .resourceIds(RESOURCE_ID)
                    .secret(new BCryptPasswordEncoder().encode("sec"))
                    .authorities("ROLE_CLIENT")
                    .authorizedGrantTypes("client_credentials")
                    .scopes("read")
                .and()
                .withClient("star_user_password")
                    .resourceIds(RESOURCE_ID)
                    .secret(new BCryptPasswordEncoder().encode("sec"))
                    .authorities("ROLE_CLIENT")
                    .authorizedGrantTypes("password")
                    .scopes("read")
                .and()
                    .withClient("star_implicit")
                    .resourceIds(RESOURCE_ID)
                    .secret(new BCryptPasswordEncoder().encode("sec"))
                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                    .authorizedGrantTypes("implicit")
                    .redirectUris("http://localhost:8080/api/user")
                    .scopes("read");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
}
