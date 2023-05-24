package kr.co.sukbinggo.hello.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.sukbinggo.hello.model.UserEntity;
import kr.co.sukbinggo.hello.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class OauthUserServiceImpl extends DefaultOAuth2UserService{

  @Autowired
  private UserRepository userRepository;
  

  public OauthUserServiceImpl() {
    super();
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    final OAuth2User oAuth2User = super.loadUser(userRequest);
    try {
      log.info("Oauth2 User Info{}", new ObjectMapper().writeValueAsString(oAuth2User));
    } catch (Exception e) {
      e.printStackTrace();
    }
    final String username = (String) oAuth2User.getAttribute("login");
    final String authProvider = userRequest.getClientRegistration().getClientName();


    UserEntity userEntity = null;
    if (!userRepository.existsByUsername(username)) {
      userEntity = UserEntity.builder().username(username).auth_provider(authProvider).build();

      userEntity = userRepository.save(userEntity);
    } else {
      userEntity = userRepository.findByUsername(username);
    }

    log.info("Successfully pulled user info username {} authProvider {}", username, authProvider);
    return new ApplicationOAuth2User(userEntity.getId(), oAuth2User.getAttributes());
  }
  
}
