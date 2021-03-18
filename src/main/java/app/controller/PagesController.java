package app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class PagesController {

    @GetMapping("main")
    public ModelAndView getAdminPage() {
        return new ModelAndView("mainPage");
    }

    @GetMapping("login")
    public ModelAndView getLoginPage() {
        return new ModelAndView("loginPage");
    }

    @GetMapping("registration")
    public ModelAndView getRegisterPage() {
        return new ModelAndView("registrationPage");
    }

    @GetMapping("error")
    public ModelAndView getErrorPage() {
        return new ModelAndView("errorPage");
    }
}
