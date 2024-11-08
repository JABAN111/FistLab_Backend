package is.fistlab.services;

import is.fistlab.database.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User createNewUser(User user);
    User getUserByUsername(String username);
    User updateUser(User user);
    void deleteUser(Long id);
    User getUser(Long id);
    User getUser(User user);
    boolean isUserExists(String username);
    UserDetailsService getUserDetailsService();
}