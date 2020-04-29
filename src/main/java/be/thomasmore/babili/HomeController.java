package be.thomasmore.babili;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/login/student")
    public String loginStudent(){
        return "login/login-student";
    }

    @GetMapping("/login/teacher")
    public String loginTeacher(){
        return "login/login-teacher";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

    @GetMapping("/register/student")
    public String registerStudent(){
        return "register/register-student";
    }

    @GetMapping("/register/teacher")
    public String registerTeacher(){
        return "register/register-teacher";
    }

    @GetMapping("/overview-tasks")
    public String overviewTasks(){
        return "overview-tasks";
    }

    @GetMapping("/teacher/overview-students")
    public String overviewStudents(){
        return "teacher/overview-students";
    }

    @GetMapping("/teacher/create-task")
    public String createTask(){
        return "teacher/create-task";
    }
}