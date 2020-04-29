package be.thomasmore.babili;

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
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class HomeController {
    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    private String naam = "home";
    private String pw;

    @EnableWebSecurity
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/admin/**").authenticated()
                    .anyRequest().permitAll().and().formLogin();
            http.csrf().ignoringAntMatchers("/h2-console/**").and()
                    .headers().frameOptions().sameOrigin();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception{
            auth.inMemoryAuthentication().withUser("admin")
                    .password("$2a$10$fNEP0vLPDboAhB7ZM1vUxOzsj2Wt5IsY1hH3FYLUaE4YyQYFAZVOC")
                    .roles("ADMIN");
            auth.inMemoryAuthentication().withUser("talha")
                    .password("$2a$10$jwHSeW.gbG5SUEuN9XmnweGobyQFj.vZU0xwVf0jrrE1t7jYwy2Hy")
                    .roles("USER");
            pw = passwordEncoder().encode("hallo");
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
        logger.info(pw);
        model.addAttribute("name", naam);
        model.addAttribute("login", loggedInName);
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