package be.thomasmore.babili.controllers;

import be.thomasmore.babili.audio.JavaSoundPlayer;
import be.thomasmore.babili.audio.JavaSoundRecorder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SoundController {

    @GetMapping("/homeBeta")
    public String homeBeta(Model model) {
        model.addAttribute("rec", "Klik op start om de taak op te nemen.");
        return "homeBeta";
    }

    @GetMapping("/start")
    public String startRec(Model model) {
        model.addAttribute("rec", "Rec");
        JavaSoundRecorder.startRec();
        return "start";
    }

    @GetMapping("/stopRec")
    public String stopRec(Model model) {
        model.addAttribute("rec", "Rec stopped.");
        JavaSoundRecorder.stopRec();
        return "stopRec";
    }

    @GetMapping("/startExample")
    public String startExample(Model model){
        model.addAttribute("rec", "Example");
        JavaSoundPlayer.play("D:/Test/Audio/Examples/opdracht1.wav");
        return "homeBeta";
    }
}