package kr.co.sukbinggo.hello.security;

import java.util.Map;

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
    // final String username = (String) oAuth2User.getAttribute("login");
    final String authProvider = userRequest.getClientRegistration().getClientName();


    log.info("{}", authProvider);

    // 여러 개의 oauth 계정을 다루는 것이라면 username은 유일값이면 절대로 안되기 때문에 반드시 유효성을 통해서 검증한다.
    String username = null;
    if (authProvider.equalsIgnoreCase("github")) {
      username = (String) oAuth2User.getAttribute("login");
    } else if (authProvider.equalsIgnoreCase("google")) {
      username = (String) oAuth2User.getAttribute("email");
    } else if (authProvider.equalsIgnoreCase("kakao")) {
      Map<String, String> map =  oAuth2User.getAttribute("kakao_account");
      username = map.get("email");
      log.info(username); // 유저네임 추가
    } 

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
