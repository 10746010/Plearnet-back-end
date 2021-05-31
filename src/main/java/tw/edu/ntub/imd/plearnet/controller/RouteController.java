package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@RestController
public class RouteController {
//    private final

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("/login");
        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("/login");
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return mav;
    }

//    @GetMapping("/self")
//    public ModelAndView self() {
//        ModelAndView mav = new ModelAndView("/self");
//        return mav;
//    }
}
