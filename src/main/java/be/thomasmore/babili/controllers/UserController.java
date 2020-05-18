package be.thomasmore.babili.controllers;

import be.thomasmore.babili.model.Cursus;
import be.thomasmore.babili.model.Inlevering;
import be.thomasmore.babili.model.Opdracht;
import be.thomasmore.babili.model.User;
import be.thomasmore.babili.repositories.CursusRepository;
import be.thomasmore.babili.repositories.InleveringRepository;
import be.thomasmore.babili.repositories.OpdrachtRepository;
import be.thomasmore.babili.repositories.UserRepository;
import org.dom4j.rule.Mode;
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

import javax.swing.text.html.Option;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private CursusRepository cursusRepository;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // Logout form
    @RequestMapping("/logout")
    public String logout(Model model) {
        return "/login/logout";
    }

    @GetMapping("/overview-tasks")
    public String overviewTasks(Model model, Principal principal) {
        Optional<User> userFromDB = userRepository.findByUsername(principal.getName());
        Iterable<Opdracht> opdrachtFromDB = null;
        if (userFromDB.isPresent()) {
            User ingelogdeUser = userFromDB.get();
            if (ingelogdeUser.getCursus() == null) {
                opdrachtFromDB = null;
            } else {
                opdrachtFromDB = opdrachtRepository.findByCursus_Id(ingelogdeUser.getCursus().getId());
            }
        }
        Iterable<Cursus> cursussenFromDB = cursusRepository.findByDocent_Username(principal.getName());
        model.addAttribute("opdrachtFromDB", opdrachtFromDB);
        model.addAttribute("cursusFromDB", cursussenFromDB);
        return "overview-tasks";
    }

    @GetMapping({"/task-details/{id}", "/task-details/{id}/{opname}", "/task-details/{id}/{opname}/{id}"})
    public String task(@PathVariable(required = false) int id,
                       @PathVariable(required = false) String opname, Model model, Principal principal) {
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        Opdracht opdrachtFromDB = null;
        String user = null;
        if (principal != null) {
            user = principal.getName();
        }
        if (optionalOpdracht.isPresent()) {
            opdrachtFromDB = optionalOpdracht.get();
        }
        if (opname != null) {
            model.addAttribute("taak", "Jouw opname is bewaard.");
            model.addAttribute("audioPath", "/audioFiles/" + opdrachtFromDB.getTitel() + "/" + user + ".wav");
        }
        model.addAttribute("opdracht", opdrachtFromDB);
        return "task-details";
    }

    @GetMapping("/inlevering/{id}")
    public String inlevering(@PathVariable(required = false) int id, Model model, Principal principal) {
        String userName = null;
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        Opdracht opdrachtFromDB = null;
        if (optionalOpdracht.isPresent()) {
            opdrachtFromDB = optionalOpdracht.get();
        }
        User UserFromDB = null;
        if (principal != null) {
            userName = principal.getName();
            Optional<User> optionalUser = userRepository.findByUsername(userName);
            if (optionalUser.isPresent()) {
                UserFromDB = optionalUser.get();
            }
        }
        String pathName = "/audioFiles/" + opdrachtFromDB.getTitel() + "/" + userName + ".wav";
        Inlevering newInlevering = new Inlevering(pathName, opdrachtFromDB, UserFromDB);
        inleveringRepository.save(newInlevering);
        return "redirect:/user/inlevering/{id}/confirmation";
    }


    @GetMapping("/inlevering/{submissionId}/confirmation")
    public String taskConfirmation(@PathVariable(required = true) Integer submissionId, @RequestParam(required = false) Integer rating, Model model, Principal principal) {
        if (rating != null) {
            String userName = null;
            if (principal != null) {
                userName = principal.getName();
            }
            Optional<User> optionalUser = userRepository.findByUsername(userName);
            User user = null;
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }
            Opdracht opdrachtFromDB = null;
            Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(submissionId);
            if (optionalOpdracht.isPresent()) {
                opdrachtFromDB = optionalOpdracht.get();
            }
            Optional<Inlevering> optionalSubmission = inleveringRepository.findByUser_IdAndOpdracht(user.getId(), opdrachtFromDB);
            if (optionalSubmission.isPresent()) {
                Inlevering currentSubmission = optionalSubmission.get();
                ++rating;
                System.out.println(rating);
                currentSubmission.setBeoordeling(rating.toString());
                inleveringRepository.save(currentSubmission);
            }
            return "redirect:/user/overview-tasks";
        }
        model.addAttribute("submissionId", submissionId);
        return "task-confirmation";
    }

    @GetMapping("/new-course")
    public String newCourse(Model model) {
        model.addAttribute("course", cursusRepository.findAll());
        return "new-course";
    }

    @PostMapping("/new-course")
    public String createCoursePost(@RequestParam String naam,
                                   @RequestParam String beschrijving,
                                   Principal principal,
                                   Model model) {
        Iterable<Cursus> alleCursussen = cursusRepository.findAll();
        model.addAttribute("course", alleCursussen);
        Optional<Cursus> optionalCursus = cursusRepository.findCursusByNaam(naam);
        if (!(optionalCursus.isPresent())) {
            if (naam != null) {
                Cursus cursus = new Cursus();
                cursus.setNaam(naam);
                Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
                if (optionalUser.isPresent()) {
                    cursus.setDocent(optionalUser.get());
                }
                cursus.setBeschrijving(beschrijving);
                cursusRepository.save(cursus);
            }
        }
        return "redirect:/user/overview-tasks"; //later nog aan te passen naar de juiste URL
    }

    @GetMapping("/course/{courseId}/management/new-task")
    public String newTask(@PathVariable(required = true) int courseId, Model model) {
        model.addAttribute("task", opdrachtRepository.findAll());
        return "new-task";
    }

    @PostMapping("/course/{courseId}/management/new-task")
    public String createTaskPost(@RequestParam String titel,
                                 @RequestParam String opgave,
//                                 @RequestParam String voorbeeldzin,
                                 Principal principal,
                                 @PathVariable(required = true) int courseId,
                                 Model model) {
        Iterable<Opdracht> alleOpdrachten = opdrachtRepository.findAll();
        model.addAttribute("task", alleOpdrachten);
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findOpdrachtByTitel(titel);
        if (!(optionalOpdracht.isPresent())) {
            if (titel != null) {
                Opdracht opdracht = new Opdracht();
                opdracht.setTitel(titel);
                opdracht.setOpgave(opgave);
                if (cursusRepository.findById(courseId).isPresent()) {
                    opdracht.setCursus(cursusRepository.findById(courseId).get());
                }
//                opdracht.setVoorbeeld(voorbeeldzin);
                opdrachtRepository.save(opdracht);
            }
        }
        return "redirect:/user/overview-tasks"; //later nog aan te passen naar de juiste URL
    }

    @GetMapping("/course/{courseId}/management")
    public String manageCourse(@PathVariable(required = true) int courseId, Model model) {
        Optional<Cursus> optionalCourse = cursusRepository.findById(courseId);
        if (!optionalCourse.isPresent()) {
            return "/overview-tasks";
        }

        model.addAttribute("course", optionalCourse.get());
        model.addAttribute("tasks", opdrachtRepository.findAll());
        return "course/course-management";
    }

    @GetMapping("/course/{courseId}/management/add-students")
    public String addStudents(@PathVariable(required = true) int courseId, @RequestParam(required = false) int[] selectedStudents, Model model) {
        Cursus givenCourse = null;
        Optional<Cursus> optionalCourse = cursusRepository.findById(courseId);
        if (!optionalCourse.isPresent()) {
            return "/overview-tasks";
        }
        givenCourse = optionalCourse.get();
        model.addAttribute("course", givenCourse);

        if (selectedStudents != null) {
            List<User> studentsToEnroll = new ArrayList<>();
            for (int studentId : selectedStudents) {
                Optional<User> optionalStudent = userRepository.findById(studentId);
                optionalStudent.ifPresent(studentsToEnroll::add);
            }
            studentsToEnroll.forEach((s) -> {
                enrollStudent(s, optionalCourse.get());
            });
            model.addAttribute("addedStudents", studentsToEnroll);
        }

        Iterable<User> allUsers = userRepository.findByRole("STUDENT");
        if (allUsers != null) {
            List<User> studentsThatAreNotEnrolled = getListOfStudentsThatAreNotEnrolled(allUsers, givenCourse);
            model.addAttribute("availableStudents", studentsThatAreNotEnrolled);
        }

        return "course/add-students";
    }

    @GetMapping("/course/{courseId}/task/{taskId}/delete")
    public String deleteTask(@PathVariable(required = true) int courseId, @PathVariable(required = true) int taskId) {
        Optional<Cursus> optionalCursus = cursusRepository.findById(courseId);
        if (optionalCursus.isPresent()) {
            Optional<Opdracht> optionalTask = opdrachtRepository.findById(taskId);

            if(optionalTask.isPresent()) {
                Opdracht task = optionalTask.get();
                Cursus course = optionalCursus.get();
                course.getOpdrachten().remove(task);
                opdrachtRepository.delete(task);
            }
        }
        return "redirect:/user/course/"+courseId+"/management";
    }

    private boolean isAlreadyEnrolled(User student) {

        return student.getCursus() != null;
    }

    private List<User> getListOfStudentsThatAreNotEnrolled(Iterable<User> allStudents, Cursus course) {
        if (allStudents != null) {
            List<User> studentsThatAreNotEnrolled = new ArrayList<>();
            allStudents.forEach((s) -> {
                if (!isAlreadyEnrolled(s)) {
                    studentsThatAreNotEnrolled.add(s);
                }
            });
            return studentsThatAreNotEnrolled;
        }
        return null;
    }

    private void enrollStudent(User student, Cursus course) {
        student.setCursus((course));
        userRepository.save(student);
    }
}
