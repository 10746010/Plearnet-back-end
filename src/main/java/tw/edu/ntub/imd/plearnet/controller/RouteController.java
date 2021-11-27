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
import java.util.Optional;

@AllArgsConstructor
@RestController
public class RouteController {
    private final UserAccountService userAccountService;

    @GetMapping(path = "/")
    public ResponseEntity<String> index() {
        ArrayData dataNode = new ArrayData();
        return ResponseEntityBuilder.success()
                .message("成功進入首頁")
                .data(dataNode)
                .build();
    }

    @GetMapping("/loginView")
    public ResponseEntity<String> loginView() {
        ArrayData dataNode = new ArrayData();
        return ResponseEntityBuilder.success()
                .message("成功進入登入頁面")
                .data(dataNode)
                .build();
    }

    @PostMapping("/loginUserAccount")
    public ResponseEntity<String> login(@Valid @RequestBody UserAccountBean userAccountBean, BindingResult bindingResult) {

        BindingResultUtils.validate((bindingResult));
        Boolean usernameExists  =  userAccountService.accountExist(userAccountBean.getAccount());

        if(usernameExists.equals(false)) {
            return ResponseEntityBuilder.error()
                    .message("沒有此帳號")
                    .build();
        } else{
            Optional<UserAccountBean> usernameBeanOptional = userAccountService.getByAccount(userAccountBean.getAccount());

            UserAccountBean usernameBean = usernameBeanOptional.get();
            if (userAccountBean.getPassword().equals(usernameBean.getPassword())){
                return ResponseEntityBuilder.success()
                        .message("登入成功")
                        .build();
            } else {
               return ResponseEntityBuilder.error()
                        .message("密碼錯誤")
                        .build();
            }

        }
    }

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
        Boolean usernameExists  =  userAccountService.accountExist(userAccountBean.getAccount());

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
}
