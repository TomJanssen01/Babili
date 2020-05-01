package be.thomasmore.babili.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.sql.DataSource;
import java.security.Principal;

@Controller
public class LoginController {

//    @GetMapping("/login/student")
//    public String loginStudent() {
//        return "login/login-student";
//    }

//    @GetMapping("/login/teacher")
//    public String loginTeacher() {
//        return "login/login-teacher";
//    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/register/student")
    public String registerStudent() {
        return "register/register-student";
    }

    @GetMapping("/register/teacher")
    public String registerTeacher() {
        return "register/register-teacher";
    }

}
