package be.thomasmore.babili;

import be.thomasmore.babili.recording.JavaSoundRecorder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("rec", "Klik op start om de taak op te nemen.");
        return "home";
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
}