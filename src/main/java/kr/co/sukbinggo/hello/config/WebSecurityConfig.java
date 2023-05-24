package kr.co.sukbinggo.hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.filter.CorsFilter;

import kr.co.sukbinggo.hello.security.JwtAuthenticationFilter;
import kr.co.sukbinggo.hello.security.OAuthSuccessHandler;
import kr.co.sukbinggo.hello.security.OauthUserServiceImpl;
import kr.co.sukbinggo.hello.security.RedirectUrlCookieFilter;
import kr.co.sukbinggo.hello.service.UserService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  private OauthUserServiceImpl oauthUserServiceImpl;
  // @Autowired
  // private UserService userService;

  @Autowired
  private OAuthSuccessHandler oAuthSuccessHandler;

  @Autowired
  private RedirectUrlCookieFilter redirectUrlFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and().csrf().disable().httpBasic().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/", "/auth/**", "/oauth2/**", "/mustache/**").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .authorizationEndpoint().baseUri("/oauth2/auth")
        .and()
        .redirectionEndpoint()
        .baseUri("/oauth2/callback/*")
        .and()
        .userInfoEndpoint()
        .userService(oauthUserServiceImpl)
        .and()
        .successHandler(oAuthSuccessHandler)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new Http403ForbiddenEntryPoint()); // success Handler 등록;

    http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
    http.addFilterBefore(redirectUrlFilter, OAuth2AuthorizationRequestRedirectFilter.class);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
