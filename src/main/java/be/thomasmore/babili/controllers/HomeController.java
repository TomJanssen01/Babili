package be.thomasmore.babili.controllers;

import be.thomasmore.babili.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private BabiliUserRepository babiliUserRepository;
    @Autowired
    private CursistRepository cursistRepository;
    @Autowired
    private CursusRepository cursusRepository;
    @Autowired
    private DocentRepository docentRepository;
    @Autowired
    private InleveringRepository inleveringRepository;
    @Autowired
    private OpdrachtRepository opdrachtRepository;

    @GetMapping("/")
    public String home(Model model) {

        return "home";
    }
}