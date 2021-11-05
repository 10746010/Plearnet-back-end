package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;
import tw.edu.ntub.imd.plearnet.service.UserAccountService;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/userAccount")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @GetMapping(path = "/login")
    public ResponseEntity<String> login() {
        return ResponseEntityBuilder.success()
                .message("登入成功")
                .build();
    }




    @GetMapping(path = "")
    public ResponseEntity<String> SearchAll() {
        ArrayData arrayData = new ArrayData();
        for (List<UserAccountBean> userAccountBeanList : userAccountService.searchAll()) {
            ObjectData objectData = arrayData.addObject();
            System.out.println(objectData);
        }
        return ResponseEntityBuilder.success()
                .message("查詢全部成功")
                .data(arrayData)
                .build();
    }

//    @PostMapping(path = "newAccount")
//    public RequestEntity<String> createAccount(@Valid @RequestBody UserAccountBean userAccountBean,
//                                               BindingResult bindingResult){
//        BindingResultUtils.validate(bindingResult);
//        userAccountService.save(userAccountBean);
//        return ResponseEntityBuilder.success()
//                .message("新增帳號成功")
//                .build();
//    }
}
