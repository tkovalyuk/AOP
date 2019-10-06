package oleksii.springaop.controllers;

import oleksii.springaop.service.AuthenticationService;
import oleksii.springaop.service.SecretService;
import oleksii.springaop.utils.Constants;
import oleksii.springaop.utils.authorization.HasRole;
import oleksii.springaop.utils.authorization.NotAuthenticatedException;
import oleksii.springaop.utils.authorization.NotAuthorizedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static oleksii.springaop.utils.authorization.Roles.ADMIN;

@Controller
public class MainController {
    private final SecretService secretService;
    private final AuthenticationService authenticationService;

    public MainController(SecretService secretService, AuthenticationService authenticationService) {
        this.secretService = secretService;
        this.authenticationService = authenticationService;
    }

    @HasRole(ADMIN)
    @GetMapping("")
    public String getMain(Model model){
        model.addAttribute("secret", secretService.getSecret());
        return "secretPage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthenticationPayload authenticationPayload, HttpServletResponse response, Model model){
        if(!authenticationService.authenticate(authenticationPayload.username, authenticationPayload.password)){
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
        response.addCookie(new Cookie(Constants.AUTH_COOKIE_NAME, ADMIN.toString()));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @ExceptionHandler({NotAuthorizedException.class, NotAuthenticatedException.class})
    public String handleNotAuthenticated(Exception e){
        System.out.println(e.getMessage());
        return "redirect:/login";
    }

    public static class AuthenticationPayload{
        private String username;
        private String password;

        public AuthenticationPayload() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
