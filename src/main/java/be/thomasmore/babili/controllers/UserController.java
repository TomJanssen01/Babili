package be.thomasmore.babili.controllers;

import be.thomasmore.babili.model.Inlevering;
import be.thomasmore.babili.model.Opdracht;
import be.thomasmore.babili.model.User;
import be.thomasmore.babili.repositories.InleveringRepository;
import be.thomasmore.babili.repositories.OpdrachtRepository;
import be.thomasmore.babili.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OpdrachtRepository opdrachtRepository;
    @Autowired
    private InleveringRepository inleveringRepository;

    // Logout form
    @RequestMapping("/logout")
    public String logout(Model model) {
        return "/login/logout";
    }

    @GetMapping("/overview-tasks")
    public String overviewTasks(Model model) {
        Iterable<Opdracht> opdrachtFromDB = opdrachtRepository.findAll();
        model.addAttribute("opdrachtFromDB",opdrachtFromDB);
        return "overview-tasks";
    }

    @GetMapping({"/task-details/{id}","/task-details/{id}/{opname}"})
    public String task(@PathVariable(required = false) int id,
                       @PathVariable(required = false) String opname, Model model) {
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        Opdracht opdrachtFromDB = null;
        if (optionalOpdracht.isPresent()){
            opdrachtFromDB = optionalOpdracht.get();
        }
        if (opname!=null){
            model.addAttribute("taak","Jouw opname is bewaard.");
        }
        model.addAttribute("opdracht", opdrachtFromDB);
        return "task-details";
    }

    @GetMapping("/inlevering/{id}")
    public String inlevering(@PathVariable(required = false) int id, Model model, Principal principal){
        String userName = null;
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        Opdracht opdrachtFromDB = null;
        if (optionalOpdracht.isPresent()){
            opdrachtFromDB = optionalOpdracht.get();
        }
        User UserFromDB = null;
        if (principal != null){
            userName = principal.getName();
            Optional<User> optionalUser = userRepository.findByUsername(userName);
            if (optionalUser.isPresent()){
                UserFromDB = optionalUser.get();
            }
        }
        String pathName = "D:/Test/Audio/" + opdrachtFromDB.getTitel()+"/"+userName+".wav";
        Inlevering newInlevering = new Inlevering(pathName,opdrachtFromDB,UserFromDB);
        inleveringRepository.save(newInlevering);
        return "redirect:/user/overview-tasks";
    }

    @GetMapping("/task-confirmation")
    public String taskConfirmation() {
        return "task-confirmation";
    }
}
