package be.thomasmore.babili.controllers;

import be.thomasmore.babili.model.User;
import be.thomasmore.babili.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String register(Principal principal, Model model){
        if (principal != null){
            return "redirect:/";
        }
        return "/user/register";
    }

    @PostMapping("/register")
    public String registerPost(@RequestParam String userName,
                               @RequestParam String password,
                               @RequestParam String nickname,
                               Principal principal, Model model){
        if(principal == null && !userName.isBlank()){
            Optional<User> userWithUserName = userRepository.findByUsername(userName);
            if(!userWithUserName.isPresent()){
                User newUser = new User();
                newUser.setUsername(userName);
                String encode = passwordEncoder.encode(password);
                logger.info(String.format("password %s\n", encode));
                newUser.setPassword(encode);
                newUser.setUsername(userName);
                newUser.setRole("USER");
                userRepository.save(newUser);
            }
        }
        return "redirect:/";
    }
}
