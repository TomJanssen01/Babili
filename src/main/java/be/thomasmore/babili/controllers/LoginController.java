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
    @Autowired
    private DataSource dataSource;

    private Logger logger = LoggerFactory.getLogger(HomeController.class);
    private String naam = "home";

    @EnableWebSecurity
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                    .anyRequest().permitAll().and().formLogin();
            http.csrf().ignoringAntMatchers("/h2-console/**").and()
                    .headers().frameOptions().sameOrigin();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth)
                throws Exception{
            auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(
                    "select username,password,true from user where username = ?")
                    .authoritiesByUsernameQuery(
                            "select username, role from user where username = ?");
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }
    }

    @GetMapping("/")
    public String home(Principal principal, Model model){
        String loggedInName = principal != null ? principal.getName() : "nobody";
        logger.info("logged in: " + loggedInName);
        model.addAttribute("name", naam);
        model.addAttribute("login", loggedInName);
        return "home";
    }

    @GetMapping("/test1")
    public String test1(Principal principal, Model model){
        String loggedInName = principal != null ? principal.getName() : "nobody";
        logger.info("logged in: " + loggedInName);
        model.addAttribute("name", naam);
        model.addAttribute("login", loggedInName);
        return "test1";
    }

    @GetMapping("/admin/test2")
    public String test2(Principal principal, Model model){
        String loggedInName = principal != null ? principal.getName() : "nobody";
        logger.info("logged in: " + loggedInName);
        model.addAttribute("name", naam);
        model.addAttribute("login", loggedInName);
        return "test2";
    }
}
