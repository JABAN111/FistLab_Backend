package is.fistlab.controllers;

import is.fistlab.database.entities.User;
import is.fistlab.dto.JwtAuthenticationResponse;
import is.fistlab.dto.UserDto;
import is.fistlab.security.sevices.Impl.AuthServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@AllArgsConstructor
public class AuthorizationController {
    private final AuthServiceImpl authenticationService;

    @GetMapping("/hello")
    @CrossOrigin
    public String hello() {
        log.info("hello");
        return "hi";
    }

    //метод временно мертв, нужна доделать админку
    @PostMapping("/request-admin")
    public String requestAdmin(@RequestBody User user) {
        log.error("method not implemented");
        return "error";
    }

    @PostMapping("/create-user")
    public JwtAuthenticationResponse registration(@RequestBody UserDto dto){
        return authenticationService.signUp(dto);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody UserDto dto){
        return authenticationService.signIn(dto);
    }
}