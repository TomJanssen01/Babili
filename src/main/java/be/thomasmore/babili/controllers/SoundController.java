package be.thomasmore.babili.controllers;

import be.thomasmore.babili.audio.JavaSoundPlayer;
import be.thomasmore.babili.audio.JavaSoundRecorder;
import be.thomasmore.babili.model.Opdracht;
import be.thomasmore.babili.repositories.OpdrachtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class SoundController {
    @Autowired
    private OpdrachtRepository opdrachtRepository;

    @GetMapping("/homeBeta")
    public String homeBeta(Model model) {
        model.addAttribute("rec", "Klik op start om de taak op te nemen.");
        return "homeBeta";
    }

    @GetMapping("/start")
    public String startRec(Model model) {
        model.addAttribute("rec", "Rec");
        JavaSoundRecorder.startRec("D:/Test/Audio/test.wav");
        return "start";
    }

    @GetMapping("/stopRec")
    public String stopRec(Model model) {
        model.addAttribute("rec", "Rec stopped.");
        JavaSoundRecorder.stopRec();
        return "stopRec";
    }

    @GetMapping("/task-details/startExample/{id}")
    public String startExample(@PathVariable(required = false) int id, Model model){
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        Opdracht opdrachtFromDB = null;
        if (optionalOpdracht.isPresent()){
            opdrachtFromDB = optionalOpdracht.get();
        }
        JavaSoundPlayer.play(opdrachtFromDB.getVoorbeeld());
        return "redirect:/task-details/"+id;
    }
}