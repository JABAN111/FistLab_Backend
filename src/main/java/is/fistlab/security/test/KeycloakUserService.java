//package is.fistlab.security.test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class KeycloakUserService {
//
//    @Autowired
//    private KeycloakService keycloakService;
//
//    @Value("${keycloak.auth-server-url}")
//    private String authServerUrl;
//
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public KeycloakUserService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public ResponseEntity<String> createUser(KeycloakUser user) {
//        String accessToken = keycloakService.getAccessToken();
//        String url = authServerUrl + "/admin/realms/" + realm + "/users";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//        headers.set("Content-Type", "application/json");
//
//        HttpEntity<KeycloakUser> request = new HttpEntity<>(user, headers);
//        return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
//    }
//}