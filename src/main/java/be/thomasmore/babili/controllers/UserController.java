package be.thomasmore.babili.controllers;

import be.thomasmore.babili.model.Cursus;
import be.thomasmore.babili.model.Inlevering;
import be.thomasmore.babili.model.Opdracht;
import be.thomasmore.babili.model.User;
import be.thomasmore.babili.repositories.CursusRepository;
import be.thomasmore.babili.repositories.InleveringRepository;
import be.thomasmore.babili.repositories.OpdrachtRepository;
import be.thomasmore.babili.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.File;
import java.security.Principal;
import java.util.*;

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
                opdrachtFromDB = opdrachtRepository.findAllByCursus_Id(ingelogdeUser.getCursus().getId());
            }
        }
        Iterable<Cursus> cursussenFromDB = cursusRepository.findAllByDocent_Username(principal.getName());
        model.addAttribute("username", principal.getName());
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
            model.addAttribute("audioPath", "/audioFiles/" + cursusPath(id) + "/" + user + ".wav");
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
        String pathName = "/audioFiles/" + cursusPath(id) + "/" + userName + ".wav";
        Inlevering newInlevering = new Inlevering(pathName, opdrachtFromDB, UserFromDB);
        inleveringRepository.save(newInlevering);
        return "redirect:/user/inlevering/{id}/confirmation";
    }

    @GetMapping("/inlevering/{submissionId}/confirmation")
    public String taskConfirmation(@PathVariable(required = true) Integer submissionId, @RequestParam(required = false) Integer rating, Model model, Principal principal) {

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
        model.addAttribute("task", opdrachtFromDB);
        Optional<Inlevering> optionalSubmission = inleveringRepository.findByUser_IdAndOpdracht(user.getId(), opdrachtFromDB);
        if (rating != null) {
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
                String path = naam.replaceAll(" ", "").toLowerCase();
                File file = new File("src/main/resources/static/audioFiles/" + cursus.getId() + path);
                file.mkdir();
            }
        }
        return "redirect:/user/overview-tasks";
    }

    @GetMapping("/course/{courseId}/task/{id}/edit")
    public String editCourse(Model model, @PathVariable(required = true) int courseId, @PathVariable(required = true) Integer id) {
        Opdracht opdrachtFromDB = null;
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        if (optionalOpdracht.isPresent()) {
            opdrachtFromDB = optionalOpdracht.get();
        }
        model.addAttribute("opdracht", opdrachtFromDB);
        model.addAttribute("cursussen",cursusRepository.findById(courseId));
        return "/course/edit-task";
    }

    @PostMapping("/course/{courseId}/task/{id}/edit")
    public String postEditCourse(Model model,
                                 @PathVariable(required = true) Integer id,
                                 @PathVariable(required = true) int courseId,
                                 @RequestParam(required = false) String opgave) {
        Opdracht opdrachtFromDB = null;
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(id);
        if (optionalOpdracht.isPresent()) {
            opdrachtFromDB = optionalOpdracht.get();
        }
        opdrachtFromDB.setOpgave(opgave);
        opdrachtRepository.save(opdrachtFromDB);
        model.addAttribute("opdracht", opdrachtFromDB);
        return "redirect:/user/course/"+courseId+"/management";
    }

    @GetMapping("/course/{courseId}/management/new-task")
    public String newTask(@PathVariable(required = true) int courseId, Model model) {
        model.addAttribute("task", opdrachtRepository.findAll());
        model.addAttribute("cursussen", cursusRepository.findById(courseId));
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
        Cursus cursus = cursusRepository.findById(courseId).get();
        String cursusPath = cursus.getNaam().replaceAll(" ", "").toLowerCase();
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findOpdrachtByTitel(titel);
        if (optionalOpdracht.isEmpty()) {
            if (titel != null) {
                Opdracht opdracht = new Opdracht();
                opdracht.setTitel(titel);
                opdracht.setOpgave(opgave);
                if (cursusRepository.findById(courseId).isPresent()) {
                    opdracht.setCursus(cursus);
                }
//                opdracht.setVoorbeeld(voorbeeldzin);
                opdrachtRepository.save(opdracht);
                File file = new File("src/main/resources/static/audioFiles/" + cursus.getId() + cursusPath + "/" + titel.replaceAll(" ", "").toLowerCase());
                file.mkdir();
            }
        }
        return "redirect:/user/course/"+courseId+"/management";
    }

    @GetMapping("/course/{courseId}/management")
    public String manageCourse(@PathVariable(required = true) int courseId, Model model) {
        Optional<Cursus> optionalCourse = cursusRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            return "/overview-tasks";
        }

        model.addAttribute("course", optionalCourse.get());
        model.addAttribute("tasks", opdrachtRepository.findAllByCursus_Id(courseId));
        model.addAttribute("student", userRepository.findAllByCursus_Id(courseId));
        return "course/course-management";
    }

    @GetMapping("/course/{courseId}/management/edit-course")
    public String editCourse(@PathVariable int courseId, Model model) {
        Optional<Cursus> optionalCursus = cursusRepository.findById(courseId);
        Cursus cursusFromDb = null;
        if (optionalCursus.isPresent())
            cursusFromDb = optionalCursus.get();
        model.addAttribute("cursussen", cursusFromDb);
        model.addAttribute("cursus", cursusRepository.findAll());
        return "/course/edit-course";
    }

    @PostMapping({"/course/{courseId}/management/edit-course"})
    public String editCourse(@PathVariable(required = false) int courseId,
                             @RequestParam String beschrijving,
                             Model model) {
        Optional<Cursus> cursusFromDb = cursusRepository.findById(courseId);
        if (cursusFromDb.isPresent()) {
            Cursus cursus = cursusFromDb.get();
            //cursus.setNaam(naam);
            cursus.setBeschrijving(beschrijving);
            cursusRepository.save(cursus);
        }
        return "redirect:/user/course/" + courseId + "/management";
    }

    @GetMapping("/course/{courseId}/management/add-students")
    public String addStudents(@PathVariable(required = true) int courseId, @RequestParam(required = false) int[] selectedStudents, Model model) {
        Cursus givenCourse = null;
        Optional<Cursus> optionalCourse = cursusRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            return "course/overview-tasks";
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

        Iterable<User> allUsers = userRepository.findAllByRole("STUDENT");
        if (allUsers != null) {
            List<User> studentsThatAreNotEnrolled = getListOfStudentsThatAreNotEnrolled(allUsers, givenCourse);
            model.addAttribute("availableStudents", studentsThatAreNotEnrolled);
        }
        return "course/add-students";
    }

    @Transactional
    @GetMapping("/course/{courseId}/task/{taskId}/delete")
    public String deleteTask(@PathVariable(required = true) int courseId, @PathVariable(required = true) int taskId) {
        Optional<Cursus> optionalCursus = cursusRepository.findById(courseId);
        if (optionalCursus.isPresent()) {
            Optional<Opdracht> optionalTask = opdrachtRepository.findById(taskId);
            if (optionalTask.isPresent()) {
                Opdracht task = optionalTask.get();
                Cursus course = optionalCursus.get();
                course.getOpdrachten().remove(task);
                inleveringRepository.deleteByOpdracht(task);
                opdrachtRepository.delete(task);

                File index = new File("src/main/resources/static/audioFiles/" + course.getId() + course.getNaam().replaceAll(" ", "").toLowerCase() + "/" + task.getTitel().replaceAll(" ", "").toLowerCase());
                if (index.exists()) {
                    String[] entries = index.list();
                    for (String s : entries) {
                        File currentFile = new File(index.getPath(), s);
                        currentFile.delete();
                    }
                    index.delete();
                }
            }
        }
        return "redirect:/user/course/" + courseId + "/management";
    }

    @GetMapping("/course/{courseId}/management/delete-students")
    public String deleteStudents(@PathVariable(required = true) int courseId, @RequestParam(required = false) int[] selectedStudents, Model model) {
        Cursus givenCourse = null;
        Optional<Cursus> optionalCourse = cursusRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            return "/overview-tasks";
        }
        givenCourse = optionalCourse.get();
        model.addAttribute("course", givenCourse);

        if (selectedStudents != null) {
            List<User> studentsToDelete = new ArrayList<>();
            for (int studentId : selectedStudents) {
                Optional<User> optionalStudent = userRepository.findById(studentId);
                optionalStudent.ifPresent(studentsToDelete::add);
            }
            studentsToDelete.forEach((s) -> {
                deleteStudent(s);
            });
            model.addAttribute("addedStudents", studentsToDelete);
        }

        Iterable<User> allUsers = userRepository.findAllByRole("STUDENT");
        if (allUsers != null) {
            List<User> studentsThatAreEnrolled = getListOfStudentsThatAreEnrolled(allUsers, givenCourse);
            model.addAttribute("availableStudents", studentsThatAreEnrolled);
        }

        return "course/delete-students";
    }

    @GetMapping("/course/{courseId}/management/{userId}/overview-submissions")
    public String overviewSubmissions(@PathVariable(required = true) int userId, Model model, Principal principal) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User givenUser = optionalUser.get();
            Collection<Inlevering> submissions = inleveringRepository.findAllByUser_Id(givenUser.getId());
            model.addAttribute("submissions", submissions);
        } else {
            return "home";
        }
        return "course/overview-submissions";
    }

    private boolean isAlreadyEnrolled(User student) {
        return student.getCursus() != null;
    }

    private List<User> getListOfStudentsThatAreEnrolled(Iterable<User> allStudents, Cursus course) {
        if (allStudents != null) {
            List<User> studentsThatAreEnrolled = new ArrayList<>();
            allStudents.forEach((s) -> {
                if (isAlreadyEnrolled(s) && s.getCursus().equals(course)) {
                    studentsThatAreEnrolled.add(s);
                }
            });
            return studentsThatAreEnrolled;
        }
        return null;
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

    private void deleteStudent(User student) {
        student.setCursus(null);
        userRepository.save(student);
    }

    private String cursusPath(int opdrachtId) {
        Opdracht opdrachtFromDB = null;
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(opdrachtId);
        if (optionalOpdracht.isPresent()) {
            opdrachtFromDB = optionalOpdracht.get();
        }
        return opdrachtFromDB.getCursus().getId() + opdrachtFromDB.getCursus().getNaam().replaceAll(" ", "").toLowerCase() + "/" + opdrachtFromDB.getTitel().replaceAll(" ", "").toLowerCase();
    }

    @GetMapping("/{opdrachtId}/{userId}/task-feedback")
    public String taskFeedback(Model model, @PathVariable int opdrachtId, @PathVariable int userId, Principal principal) {
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(opdrachtId);
        Opdracht opdracht = null;
        if (optionalOpdracht.isPresent()) opdracht = optionalOpdracht.get();
        Optional<User> optionalUser = userRepository.findById(userId);
        User userFromDB = null;
        if (optionalUser.isPresent()) userFromDB = optionalUser.get();
        String user = userFromDB.getUsername();
        model.addAttribute("user", userFromDB);
        model.addAttribute("opdracht", opdracht);
        model.addAttribute("audioPath", "/audioFiles/" + cursusPath(opdrachtId) + "/" + user + ".wav");
        return "task-feedback";
    }

    @PostMapping({"/{opdrachtId}/{userId}/task-feedback"})
    public String postTaskFeedback(@RequestParam String feedback,
                                   @PathVariable int opdrachtId, @PathVariable int userId) {
        Optional<Opdracht> optionalOpdracht = opdrachtRepository.findById(opdrachtId);
        Opdracht opdracht = null;
        if (optionalOpdracht.isPresent()){
            opdracht = optionalOpdracht.get();
        }
        Optional<Inlevering> optionalInlevering = inleveringRepository.findByUser_IdAndOpdracht(userId, opdracht);
        if (optionalInlevering.isPresent()){
            Inlevering inlevering = optionalInlevering.get();
            inlevering.setFeedback(feedback);
            inleveringRepository.save(inlevering);
        }
        return "redirect:/user/course/" + optionalInlevering.get().getOpdracht().getCursus().getId() + "/management/" + userId + "/overview-submissions";
    }
}
