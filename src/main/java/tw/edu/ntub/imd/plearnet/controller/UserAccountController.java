package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/userAccount")
public class UserAccountController {
//    private final UserAccountService userAccountService;

//    @GetMapping(path = "")
//    public ResponseEntity<String> searchUserId(Pager pager) {
//        ArrayData arrayData = new ArrayData();
//        for (UserAccountBean userAccountBean : userAccountService.searchAll(pager)) {
//            ObjectData objectData = arrayData.addObject();
//
//        }
//    }
}
