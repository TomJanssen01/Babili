package be.thomasmore.babili.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/register")
    public String register(Principal principal, Model model){
        if (principal != null){
            return "redirect:/";
        }
        return "/user/register";
    }
}
