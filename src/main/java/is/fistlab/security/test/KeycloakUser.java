package is.fistlab.security.test;

import lombok.Data;

@Data
public class KeycloakUser {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled;

    // getters and setters
}
