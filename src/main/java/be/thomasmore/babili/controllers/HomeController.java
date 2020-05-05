package be.thomasmore.babili.controllers;

import be.thomasmore.babili.model.Opdracht;
import be.thomasmore.babili.repositories.OpdrachtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home(Principal principal) {
        String loggedInName = principal != null ? principal.getName() : "nobody";
        logger.info("logged in: " + loggedInName);
        return "home";
    }

    @GetMapping("/overview-tasks")
    public String overviewTasks(Model model) {
        Iterable<Opdracht> opdrachtFromDB = opdrachtRepository.findAll();
        model.addAttribute("opdrachtFromDB",opdrachtFromDB);
        return "overview-tasks";
    }

    @GetMapping("/task-details")
    public String task() {
        return "task-details";
    }

    @GetMapping("/task-confirmation")
    public String taskConfirmation() {
        return "task-confirmation";
    }
}