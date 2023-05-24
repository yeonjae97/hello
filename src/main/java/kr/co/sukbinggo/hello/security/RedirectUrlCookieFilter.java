package kr.co.sukbinggo.hello.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedirectUrlCookieFilter extends OncePerRequestFilter {
  
  public static final String REDIRECT_URI_PARAM = "redirect_url";
  private static final int MAX_AGE = 180;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getRequestURI().startsWith("/oauth2/auth")) {
      Cookie cookie = new Cookie(REDIRECT_URI_PARAM, request.getParameter(REDIRECT_URI_PARAM));
      cookie.setPath("/");
      cookie.setHttpOnly(true);
      cookie.setMaxAge(MAX_AGE);
      response.addCookie(cookie);
    }
    filterChain.doFilter(request, response);
    
  }
}
