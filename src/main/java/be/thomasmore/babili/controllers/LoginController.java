package be.thomasmore.babili.controllers;

import be.thomasmore.babili.model.User;
import be.thomasmore.babili.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {


    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register/student")
    public String registerStudent(Principal principal, Model model){
        if (principal != null){
            return "redirect:/user/overview-tasks";
        }
        return "register/register-student";
    }

    @PostMapping("/register/student")
    public String registerStudentPost(@RequestParam String userName,
                                      @RequestParam String password,
                                      @RequestParam String password2,
                                      @RequestParam String name,
                                      @RequestParam String email,
                                      Principal principal, Model model){
        if(principal == null && !userName.isBlank()){
            Optional<User> userWithUserName = userRepository.findByUsername(userName);
            if(!userWithUserName.isPresent() && password.equals(password2) && !userRepository.findByEmail(email).isPresent()){
                User newUser = new User();
                newUser.setUsername(userName);
                String encode = passwordEncoder.encode(password);
                logger.info(String.format("password %s\n", encode));
                newUser.setPassword(encode);
                newUser.setUsername(userName);
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setRole("STUDENT");
                userRepository.save(newUser);

                autologin(userName, password);
            }
        }
        return "redirect:/user/overview-tasks";
    }

    @GetMapping("/register/teacher")
    public String registerTeacher(Principal principal, Model model){
        if (principal != null){
            return "redirect:/user/overview-tasks";
        }
        return "register/register-teacher";
    }

    @PostMapping("/register/teacher")
    public String registerTeacherPost(@RequestParam String userName,
                                      @RequestParam String password,
                                      @RequestParam String password2,
                                      @RequestParam String name,
                                      @RequestParam String email,
                                      Principal principal, Model model){
        if(principal == null && !userName.isBlank()){
            Optional<User> userWithUserName = userRepository.findByUsername(userName);
            if(!userWithUserName.isPresent() && password.equals(password2) && !userRepository.findByEmail(email).isPresent()){
                User newUser = new User();
                newUser.setUsername(userName);
                String encode = passwordEncoder.encode(password);
                logger.info(String.format("password %s\n", encode));
                newUser.setPassword(encode);
                newUser.setUsername(userName);
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setRole("TEACHER");
                userRepository.save(newUser);

                autologin(userName, password);
            }
        }
        return "redirect:/user/overview-tasks";
    }

    private void autologin(String userName, String password) {
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            Authentication auth = authenticationManager.authenticate(token);
            logger.info("authentication done - result is " + auth.isAuthenticated());
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }

    // Login form
    @RequestMapping("/")
    public String login(Principal principal, Model model){
        if (principal != null) return "redirect:/user/overview-tasks";
        return "home";
    }

    @RequestMapping("/errorLogin")
    public String errorLogin(Principal principal, Model model){
        if (principal != null) return "redirect:/user/overview-tasks";
        model.addAttribute("error", "De gebruikersnaam en paswoord komen niet overeen.");
        return "home";
    }

}
