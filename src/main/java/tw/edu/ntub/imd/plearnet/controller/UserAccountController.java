package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.plearnet.bean.CollectBean;
import tw.edu.ntub.imd.plearnet.bean.HistoryBean;
import tw.edu.ntub.imd.plearnet.bean.TopicBean;
import tw.edu.ntub.imd.plearnet.bean.UserAccountBean;
import tw.edu.ntub.imd.plearnet.service.CollectService;
import tw.edu.ntub.imd.plearnet.service.HistoryService;
import tw.edu.ntub.imd.plearnet.service.TopicService;
import tw.edu.ntub.imd.plearnet.service.UserAccountService;
import tw.edu.ntub.imd.plearnet.util.http.BindingResultUtils;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/userAccount")
public class UserAccountController {
    private final UserAccountService userAccountService;
    private final CollectService collectService;
    private final TopicService topicService;
    private final HistoryService historyService;

    @GetMapping(path = "/login")
    public ResponseEntity<String> login() {
        return ResponseEntityBuilder.success()
                .message("登入成功")
                .build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> createUserAccount(@Valid @RequestBody UserAccountBean userAccountBean, BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        userAccountService.save(userAccountBean);
        return ResponseEntityBuilder.success()
                .message("新增帳戶成功")
                .build();
    }

    @PatchMapping(path = "/login", params = {"id"})
    public ResponseEntity<String> updateUserAccount(@Valid @RequestParam(name = "id") Integer id, @RequestBody UserAccountBean userAccountBean){
        userAccountService.update(id, userAccountBean);
        return ResponseEntityBuilder.success()
                .message("修改帳戶成功")
                .build();
    }

    @GetMapping(path = "/login/searchAll")
    public ResponseEntity<String> searchAll() {
        List<UserAccountBean> userAccountBeanList =  userAccountService.searchAll();
        UserAccountBean userAccountBean = userAccountBeanList.get(0);
        String username = userAccountBean.getAccount();
        System.out.println(username);
        return ResponseEntityBuilder.success()
                .message("查詢帳戶")
                .build();
    }

    @GetMapping(path = "/login/getById", params = {"id"})
    public ResponseEntity<String> getById(@Valid @RequestParam(name = "id") Integer id) {
        Optional<UserAccountBean> userAccountBeanOptional =  userAccountService.getById(id);
        if(userAccountBeanOptional.isPresent()) {
            return ResponseEntityBuilder.success()
                    .message("查詢指定帳戶成功")
                    .build();
        } else {
            return ResponseEntityBuilder.error()
                    .message("查無此用戶")
                    .build();
        }
    }

    @GetMapping(path = "/historySearch")
    public ResponseEntity<String> historySearch(@RequestParam(name = "userId") Integer userId){
        ArrayData arrayData = new ArrayData();

        for(HistoryBean historyBean : historyService.searchAll(userId)){
            ObjectData objectData = arrayData.addObject();
            objectData.add("topic_id",historyBean.getTopicId());

            Integer topicId = historyBean.getTopicId();

            Optional<TopicBean> topicBeanOptional = topicService.getById(topicId);

            topicBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            TopicBean topicBean = topicBeanOptional.get();

            objectData.add("title", topicBean.getTitle());

            Integer author = topicBean.getAuthor();

            Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(author);

            userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            UserAccountBean userAccountBean = userAccountBeanOptional.get();

            objectData.add("author", userAccountBean.getName());

        }

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    @GetMapping(path = "/collectSearch")
    public ResponseEntity<String> collectSearch(@RequestParam(name = "userId") Integer userId){
        ArrayData arrayData = new ArrayData();

        for(CollectBean collectBean : collectService.searchAll(userId)){
            ObjectData objectData = arrayData.addObject();
            objectData.add("topic_id",collectBean.getTopicId());

            Integer topicId = collectBean.getTopicId();

            Optional<TopicBean> topicBeanOptional = topicService.getById(topicId);

            topicBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            TopicBean topicBean = topicBeanOptional.get();

            objectData.add("title", topicBean.getTitle());

            Integer author = topicBean.getAuthor();

            Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(author);

            userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            UserAccountBean userAccountBean = userAccountBeanOptional.get();

            objectData.add("author", userAccountBean.getName());

        }

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    @GetMapping(path = "/userSearch")
    public ResponseEntity<String> userSearch(@RequestParam(name = "userId") Integer userId){

        ObjectData objectData = new ObjectData();

        Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(userId);

        userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
        UserAccountBean userAccountBean = userAccountBeanOptional.get();

        objectData.add("name", userAccountBean.getName());

        String sex = new String();
        if (userAccountBean.getSex().equals("0")){
            sex = "男";
        }else{
            sex = "女";
        }

        objectData.add("sex", sex);
        objectData.add("email", userAccountBean.getEmail());

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }
}
