package be.thomasmore.babili;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    private String naam = "home";
    private String hallo = "hallo";

    @EnableWebSecurity
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/admin/**").authenticated()
                    .anyRequest().permitAll().and().formLogin();
            http.csrf().ignoringAntMatchers("/h2-console/**").and()
                    .headers().frameOptions().sameOrigin();
        }
    }

    @GetMapping("/")
    public String home(Principal principal, Model model){
        String loggedInName = principal != null ? principal.getName() : "nobody";
        logger.info("logged in: " + loggedInName);
        model.addAttribute("name", naam);
        model.addAttribute("loggin", loggedInName);
        return "home";
    }

    @GetMapping("/test1")
    public String test1(Principal principal){
        String loggedInName = principal != null ? principal.getName() : "nobody";
        logger.info("logged in: " + loggedInName);
        return "test1";
    }

    @GetMapping("/admin/test2")
    public String test2(Principal principal){
        String loggedInName = principal != null ? principal.getName() : "nobody";
        logger.info("logged in: " + loggedInName);
        return "test2";
    }
}