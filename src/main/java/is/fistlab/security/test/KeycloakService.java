//package is.fistlab.security.test;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//
//@Slf4j
//@Service
//public class KeycloakService {
//
//    @Value("${keycloak.auth-server-url}")
//    private String authServerUrl;
//
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    @Value("${keycloak.client-id}")
//    private String clientId;
//
//    @Value("${keycloak.client-secret}")
//    private String clientSecret;
//
//    private RestTemplate restTemplate = new RestTemplate();
//
//
//    /**
//     *
//     * @return access token
//     */
//    public String getAccessToken() {
//        String url = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("grant_type", "client_credentials");
//        log.info("params: {}",params.toString());
//        ResponseEntity<Map> response = restTemplate.postForEntity(url, params, Map.class);
//        log.info("response: {}",response.getBody());
//        return (String) response.getBody().get("access_token");
//    }
//}