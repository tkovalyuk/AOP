package oleksii.springaop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExceptionController {

    @GetMapping("/someMapping")
    public String someMapping(){
        return "my";
    }
}
