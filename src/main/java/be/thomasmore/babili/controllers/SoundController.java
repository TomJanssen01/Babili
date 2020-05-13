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
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class SoundController {
    @Autowired
    private OpdrachtRepository opdrachtRepository;


    @GetMapping("/task-details/start/{id}")
    public String startRec(@PathVariable(required = false) int id, Model model, Principal principal) {
        String user = null;
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        Opdracht opdrachtFromDB = null;
        if (optionalOpdracht.isPresent()) {
            opdrachtFromDB = optionalOpdracht.get();
        }
        if (principal != null) {
            user = principal.getName();
        }
        String pathName = "src/main/resources/static/audioFiles/" + opdrachtFromDB.getTitel() + "/" + user + ".wav";
        JavaSoundRecorder.startRec(pathName);
        return "redirect:/user/task-details/" + id;
    }

    @GetMapping("/task-details/stop/{id}")
    public String stopRec(@PathVariable(required = false) int id, Model model) {
        JavaSoundRecorder.stopRec();
        return "redirect:/user/task-details/" + id + "/opname";
    }

    @GetMapping("/startExample/{id}")
    public String startExample(@PathVariable(required = false) int id,
                               Model model) {
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        Opdracht opdrachtFromDB = null;
        if (optionalOpdracht.isPresent()) {
            opdrachtFromDB = optionalOpdracht.get();
        }
        JavaSoundPlayer.play(opdrachtFromDB.getVoorbeeld());
        return "redirect:/user/task-details/" + id;
    }

    @GetMapping("/listenRec/{id}/opname")
    public String listenRec(@PathVariable(required = false) int id,
                            Model model,
                            Principal principal){
        String user = null;
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        Opdracht opdrachtFromDB = null;
        if (optionalOpdracht.isPresent()) {
            opdrachtFromDB = optionalOpdracht.get();
        }
        if (principal != null) {
            user = principal.getName();
        }
        String pathName = "src/main/resources/static/audioFiles/" + opdrachtFromDB.getTitel() + "/" + user + ".wav";
        JavaSoundPlayer.play(pathName);
        return "redirect:/user/task-details/" + id + "/opname";
    }
}