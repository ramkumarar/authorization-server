package com.rbsg.ob.aspspbank.authorisation.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private Environment environment;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(jwtTokenEnhancer())
                .accessTokenConverter(jwtTokenEnhancer()) //add this line.
                .authenticationManager(authenticationManager)
                .requestFactory(customOAuth2RequestFactory());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        String pwd = environment.getProperty("keystore.password");
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new ClassPathResource("jwt.jks"),
                pwd.toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("pisp")
                .secret("secret")
                .authorizedGrantTypes("authorization_code", "refresh_token","password")
                .scopes("openpay")
                .authorities(TwoFactorAuthenticationFilter.ROLE_TWO_FACTOR_AUTHENTICATION_ENABLED);
               // .authorities("ROLE_RS_READ");;

    }

    @Bean
    public DefaultOAuth2RequestFactory customOAuth2RequestFactory(){
        return new CustomOAuth2RequestFactory(clientDetailsService);
    }

    @Bean
    public org.springframework.boot.context.embedded.FilterRegistrationBean twoFactorAuthenticationFilterRegistration(){
        org.springframework.boot.context.embedded.FilterRegistrationBean registration = new org.springframework.boot.context.embedded.FilterRegistrationBean();
        registration.setFilter(twoFactorAuthenticationFilter());
        registration.addUrlPatterns("/oauth/authorize");
        registration.setName("twoFactorAuthenticationFilter");
        return registration;
    }

    @Bean
    public TwoFactorAuthenticationFilter twoFactorAuthenticationFilter(){
        return new TwoFactorAuthenticationFilter();
    }
}