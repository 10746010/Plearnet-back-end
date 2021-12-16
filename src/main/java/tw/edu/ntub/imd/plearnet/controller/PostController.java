package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/post")
public class PostController {
    @GetMapping(path = "")
    public ModelAndView searchPost() {
        ModelAndView mav = new ModelAndView("/post");
        return mav;
    }


}
