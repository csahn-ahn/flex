package me.univ.flex.user.external;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import me.univ.flex.common.constants.SocialConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/user/kakao")
@Slf4j
public class KakaoController implements SocialController {

  private static final String OAUTH_URL = "https://kauth.kakao.com/oauth";
  private static final String API_URL = "https://kapi.kakao.com";
  private RestTemplate rest;

  @Value("${flex.social.kakao-rest-key:@null}")
  private String restKey;
  @Value("${flex.social.kakao-callback-url:@null}")
  private String callbackUrl;
  @Value("${flex.domain.web-server-url:@null}")
  private String domainApp;

  public KakaoController() {
    this.rest = new RestTemplate();
  }


  @Override
  @GetMapping("/login")
  public String login(HttpServletRequest req) {
    String state = generateState();
    req.getSession().setAttribute(KEY_SOCIAL_STATE, state);

    return "redirect:" + authorizeUrl(state);
  }

  @Override
  @GetMapping("/callback")
  public String callback(HttpServletRequest req, RedirectAttributes redirectAttributes) {
    String code = req.getParameter("code");
    HashMap<String, Object> profile = profile(accessToken(code));

    //redirectAttributes.addFlashAttribute(SocialConstants.KEY_RESULT, SimpleResponse.builder().data(profile).build());
    redirectAttributes.addFlashAttribute(SocialConstants.KEY_RESULT, profile);

    //HashMap<String, Object> properties = (HashMap<String, Object>) profile.get("properties");
    //HashMap<String, Object> kakao_account = (HashMap<String, Object>) profile.get("kakao_account");

    HttpSession session = req.getSession();
    session.setAttribute(SocialConstants.SNS_ID_KEY, profile.get("id").toString());
    session.setAttribute(SocialConstants.SNS_TYPE_KEY, "KAKAO");

    return "/user/auth/socialLoginCallback";
  }

  /**
   * ????????? ?????? ?????? ?????????
   *
   * @param req
   */
  @Override
  @GetMapping("/complete")
  public void complete(HttpServletRequest req) {

  }

  @Override
  public String accessToken(String state, String code) {
    return null;
  }

  public String accessToken(String code) {
    Map response = rest.getForObject(accessTokenUrl(code), Map.class);
    log.debug("????????? ?????? ?????? ?????? ??????: {}", response);

    return (String) response.get("access_token");
  }


  /**
   * ????????? ????????? ????????? ??????
   *
   * @param accessToken ?????? ??????
   * @return
   */
  @Override
  public HashMap<String, Object> profile(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);
    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<Map> responseEntity =
        rest.exchange(profileUrl(), HttpMethod.GET, entity, Map.class);

    HashMap response = (HashMap) responseEntity.getBody();
    log.debug("????????? ????????? ????????? ?????? ?????? ??????: {}", response);

    return response;
  }


  /**
   * ????????? ?????? URL ??????
   *
   * @param state ?????? ??????
   * @return ????????? ?????? URL
   */
  private String authorizeUrl(String state) {
    String url = UriComponentsBuilder.fromUriString(OAUTH_URL + "/authorize")
        .queryParam("client_id", restKey).queryParam("redirect_uri", callbackUrl)
        .queryParam("response_type", "code").build().toString();

    log.debug("authorizeUrl: {}", url);

    return url;
  }

  /**
   * ?????? ?????? ?????? URL ??????
   *
   * @param code ?????? ??????
   * @return ?????? ?????? ?????? URL
   */
  private String accessTokenUrl(String code) {
    String url = UriComponentsBuilder.fromUriString(OAUTH_URL + "/token")
        .queryParam("grant_type", "authorization_code").queryParam("client_id", restKey)
        .queryParam("redirect_uri", callbackUrl).queryParam("code", code).build().toString();

    log.debug("accessTokenUrl: {}", url);

    return url;
  }

  /**
   * ????????? ?????? ?????? URL ??????
   *
   * @return ????????? ?????? ?????? URL
   */
  private String profileUrl() {
    String url = UriComponentsBuilder.fromUriString(API_URL + "/v2/user/me").build().toString();
    log.debug("profileUrl: {}", url);

    return url;
  }

  /**
   * CSRF ?????? ?????? ?????? ??????
   *
   * @return ??????
   */
  private String generateState() {
    SecureRandom random = new SecureRandom();
    return new BigInteger(130, random).toString(32);
  }

}
