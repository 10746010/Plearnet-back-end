package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.edu.ntub.imd.plearnet.bean.*;
import tw.edu.ntub.imd.plearnet.service.*;
import tw.edu.ntub.imd.plearnet.util.http.BindingResultUtils;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;
import tw.edu.ntub.imd.plearnet.util.jwtutil;

import java.awt.*;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
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
    private final TagService tagService;

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

    @GetMapping(path = "/getUserId")
    public ResponseEntity<String> getUserId(HttpServletRequest request){

        jwtutil jwt = new jwtutil();

        String user = jwt.parseToken(request.getHeader("token"));

        Integer id = Integer.parseInt(user.split(" ")[1]);

        ObjectData objectData = new ObjectData();

        objectData.add("user_id",id);

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }

    @GetMapping(path = "/historySearch")
    public ResponseEntity<String> historySearch(HttpServletRequest request){
            ArrayData arrayData = new ArrayData();

            jwtutil jwt = new jwtutil();

            String user = jwt.parseToken(request.getHeader("token"));

            Integer id = Integer.parseInt(user.split(" ")[1]);

            for(HistoryBean historyBean : historyService.searchAll(id)){
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

            Optional<TagBean> tagBeanOptional = tagService.getById(topicBean.getTagId());
            tagBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            TagBean tagBean = tagBeanOptional.get();

            objectData.add("tag_name", tagBean.getName());

        }

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    @GetMapping(path = "/collectSearch")
    public ResponseEntity<String> collectSearch(HttpServletRequest request){
        ArrayData arrayData = new ArrayData();

        jwtutil jwt = new jwtutil();

        String user = jwt.parseToken(request.getHeader("token"));

        Integer id = Integer.parseInt(user.split(" ")[1]);

        for(CollectBean collectBean : collectService.searchAll(id)){
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

            Optional<TagBean> tagBeanOptional = tagService.getById(topicBean.getTagId());
            tagBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            TagBean tagBean = tagBeanOptional.get();

            objectData.add("tag_name", tagBean.getName());

        }

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    @PatchMapping(path = "/editUser")
    public ResponseEntity<String> updateUser(@RequestBody UserAccountBean userAccountBean){
        userAccountService.update(userAccountBean.getId(), userAccountBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }


    @DeleteMapping(path = "/collectDelete")
    public ResponseEntity collectDelete(@Valid @RequestBody CollectBean collectBean){
        Optional<CollectBean> collectBeanOptional = collectService.getByUserIdAndTopicId(collectBean.getUserId(),collectBean.getTopicId());

        collectBeanOptional.orElseThrow(() -> new RuntimeException("查無紀錄"));
        CollectBean collectBean1 = collectBeanOptional.get();

        collectService.delete(collectBean1.getId());

        return ResponseEntityBuilder.success()
                .message("刪除成功")
                .build();
    }

    @GetMapping(path = "/myNote")
    public ResponseEntity<String> myNote(HttpServletRequest request){
        ArrayData arrayData = new ArrayData();

        jwtutil jwt = new jwtutil();

        String user = jwt.parseToken(request.getHeader("token"));

        Integer id = Integer.parseInt(user.split(" ")[1]);

        for(TopicBean topicBean : topicService.searchAllByAuthor(id)){
            ObjectData objectData = arrayData.addObject();
            objectData.add("id",topicBean.getId());
            objectData.add("title",topicBean.getTitle());

            Integer author = topicBean.getAuthor();

            Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(author);

            userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            UserAccountBean userAccountBean = userAccountBeanOptional.get();

            objectData.add("author", userAccountBean.getName());

            Optional<TagBean> tagBeanOptional = tagService.getById(topicBean.getTagId());
            tagBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            TagBean tagBean = tagBeanOptional.get();

            objectData.add("tag_name", tagBean.getName());

        }

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    @PostMapping(path = "/uploadUserPic")
    public ResponseEntity<String> uploadUserPic(@RequestParam("file")MultipartFile file,HttpServletRequest request) throws IOException {
        if(!file.isEmpty()){

            Base64 base64 = new Base64();
            String data = base64.encodeToString(file.getBytes());

            jwtutil jwt = new jwtutil();

            String user = jwt.parseToken(request.getHeader("token"));

            Integer id = Integer.parseInt(user.split(" ")[1]);

            Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(id);

            userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            UserAccountBean userAccountBean = userAccountBeanOptional.get();

            userAccountBean.setUserPic(data);

            userAccountService.update(id, userAccountBean);

            return ResponseEntityBuilder.success()
                    .message(data)
                    .build();
        }else{
            return ResponseEntityBuilder.success()
                    .message("上傳失敗")
                    .build();
        }
    }

    @GetMapping(path = "/userSearch")
    public ResponseEntity<String> userSearch(HttpServletRequest request){

        jwtutil jwt = new jwtutil();

        String user = jwt.parseToken(request.getHeader("token"));

        String name = user.split(" ")[0];
        Integer id = Integer.parseInt(user.split(" ")[1]);

        ObjectData objectData = new ObjectData();

        Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(id);

        userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
        UserAccountBean userAccountBean = userAccountBeanOptional.get();

        objectData.add("name", userAccountBean.getName());

        String sex = new String();
        if (userAccountBean.getSex() == 0){
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
