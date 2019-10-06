package oleksii.springaop.service;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public boolean authenticate(String username, String password){
        return "user".equals(username) && "1234".equals(password);
    }
}
