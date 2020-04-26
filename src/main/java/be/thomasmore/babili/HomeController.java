package be.thomasmore.babili;

import be.thomasmore.babili.recording.JavaSoundRecorder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("rec", "Klik op start om de taak op te nemen.");
        return "home";
    }

    @PostMapping("/")
    public String homeRec(Model model){
        model.addAttribute("rec", "Recording");
        JavaSoundRecorder.startRec();
        return "home";
    }

//    @PostMapping("/stop")
//    public String homeStop(Model model){
//        model.addAttribute("rec", "Saving Recording");
//        JavaSoundRecorder.stopRec();
//        return "redirect:/";
//    }
}