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
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }
}
