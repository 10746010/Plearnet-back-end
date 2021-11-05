package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;

@AllArgsConstructor
@RestController
public class RouteController {
    private final
//    @GetMapping("/")
//    public ModelAndView index() {
//        ModelAndView mav = new ModelAndView("/login_new");
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

//    @GetMapping("/login")
//    public ModelAndView login() {
//        ModelAndView mav = new ModelAndView("/login");
////        try {
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//        return mav;
//    }

@GetMapping("/login")
public ResponseEntity<String> login() {
    ArrayData dataNode = new ArrayData();
    return ResponseEntityBuilder.success()
            .message("成功進入登入頁面")
            .data(dataNode)
            .build();
}

//    @GetMapping("/register")
//    public ModelAndView register() {
//        ModelAndView mav = new ModelAndView("/register_new");
//        return mav;
//    }

    @GetMapping("/register")
    public ResponseEntity<String> register() {
        ArrayData dataNode = new ArrayData();
        return ResponseEntityBuilder.success()
                .message("成功進入註冊頁面")
                .data(dataNode)
                .build();
    }
//    @GetMapping("/self")
//    public ModelAndView self() {
//        ModelAndView mav = new ModelAndView("/self");
//        return mav;
//    }

    @PatchMapping(path = "/account", params = {"id"})
    public ResponseEntity<String> updateAccount(@RequestParam(name = "id") String userid, @RequestBody UserAccountBean userAccountBean) {
        userAccountService.update(userid, accountBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }
}
