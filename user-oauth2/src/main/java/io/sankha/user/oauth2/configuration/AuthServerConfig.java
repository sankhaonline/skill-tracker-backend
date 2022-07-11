package io.sankha.user.oauth2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

  private static final String SCOPE_ADMIN = "admin";
  private static final String SCOPE_READ = "read";
  private static final String SCOPE_WRITE = "write";

  @Value("${grant-type.password}")
  private String grantTypePassword;

  @Value("${grant-type.refresh_token}")
  private String grantTypeRefreshToken;

  @Value("${security.oauth2.client.client-id}")
  private String clientId;

  @Value("${security.oauth2.client.client-secret}")
  private String clientSecret;

  @Value("${security.oauth2.client.signing-key}")
  private String signingKey;

  @Value("${security.oauth2.client.token-validity-seconds}")
  private int tokenValidity;

  @Value("${security.oauth2.client.refresh-token-validity-seconds}")
  private int refreshTokenValidity;

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Bean
  public JwtAccessTokenConverter tokenConverter() {
    var converter = new JwtAccessTokenConverter();
    converter.setSigningKey(signingKey);

    return converter;
  }

  @Bean
  public JwtTokenStore tokenStore() {
    return new JwtTokenStore(tokenConverter());
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .authenticationManager(authenticationManager)
        .tokenStore(tokenStore())
        .accessTokenConverter(tokenConverter());
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        .withClient(clientId)
        .secret(new BCryptPasswordEncoder(12).encode(clientSecret))
        .scopes(SCOPE_READ, SCOPE_WRITE, SCOPE_ADMIN)
        .authorizedGrantTypes(grantTypePassword, grantTypeRefreshToken)
        .accessTokenValiditySeconds(tokenValidity)
        .refreshTokenValiditySeconds(refreshTokenValidity);
  }
}
