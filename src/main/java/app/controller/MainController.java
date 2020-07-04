package app.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("admin")
    public ModelAndView getAdminPage() {
        return new ModelAndView("mainPage");
    }

    @GetMapping("user")
    public ModelAndView getUserPage() {
        return new ModelAndView("mainPage");
    }
}
