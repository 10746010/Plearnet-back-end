package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;
import tw.edu.ntub.imd.plearnet.service.UserAccountService;
import tw.edu.ntub.imd.plearnet.util.http.BindingResultUtils;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class RouteController {
    private final UserAccountService userAccountService;

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

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserAccountBean userAccountBean, BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        Boolean usernameExists  =  userAccountService.getByUsername(userAccountBean.getUsername());

        if(usernameExists.equals(true)) {
            return ResponseEntityBuilder.error()
                    .message("此帳號已存在")
                    .build();
        } else{
            userAccountService.save(userAccountBean);
            return ResponseEntityBuilder.success()
                    .message("註冊帳號成功")
                    .build();
        }


    }
//    @GetMapping("/self")
//    public ModelAndView self() {
//        ModelAndView mav = new ModelAndView("/self");
//        return mav;
//    }

//    @PatchMapping(path = "/account", params = {"id"})
//    public ResponseEntity<String> updateAccount(@RequestParam(name = "id") String userid, @RequestBody UserAccountBean userAccountBean) {
//        userAccountService.update(userid, userAccountBean);
//        return ResponseEntityBuilder.success()
//                .message("修改成功")
//                .build();
//    }
}
