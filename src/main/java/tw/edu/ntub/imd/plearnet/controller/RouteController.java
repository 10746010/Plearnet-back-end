package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;

@AllArgsConstructor
@RestController
public class RouteController {

//    @GetMapping("/")
//    public ModelAndView index() {
//        ModelAndView mav = new ModelAndView("/login");
//        return mav;
//    }

    @GetMapping(path = "/")
    public ResponseEntity<String> index() {
        ArrayData dataNode = new ArrayData();
        return ResponseEntityBuilder.success()
                .message("成功進入首頁")
                .data(dataNode)
                .build();
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
