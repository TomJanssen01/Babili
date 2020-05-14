package be.thomasmore.babili.controllers;


import be.thomasmore.babili.audio.JavaSoundPlayer;
import be.thomasmore.babili.audio.JavaSoundRecorder;
import be.thomasmore.babili.model.Opdracht;
import be.thomasmore.babili.repositories.OpdrachtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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


    @GetMapping("/start/{id}")
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

    @GetMapping("/stop/{id}")
    public String stopRec(@PathVariable(required = false) int id, Model model) {
        JavaSoundRecorder.stopRec();
//        evictAllCaches();
        return "redirect:/user/task-details/" + id + "/opname";
    }

//    @Autowired
//    CacheManager cacheManager;
//
//    public void evictAllCaches() {
//        cacheManager.getCacheNames().stream()
//                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
//    }


}