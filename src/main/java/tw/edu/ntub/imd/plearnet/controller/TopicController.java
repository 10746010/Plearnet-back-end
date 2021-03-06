package tw.edu.ntub.imd.plearnet.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.plearnet.bean.*;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Message;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Topic;
import tw.edu.ntub.imd.plearnet.service.*;
import tw.edu.ntub.imd.plearnet.util.http.BindingResultUtils;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;
import tw.edu.ntub.imd.plearnet.util.jwtutil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/topic")
public class TopicController {
    private final TopicService topicService;
    private final TagService tagService;
    private final UserAccountService userAccountService;
    private final MessageService messageService;
    private final CollectService collectService;
    private final HistoryService historyService;

    @GetMapping(path = "/simpleTopic")
    public ResponseEntity<String> simpleTopicGet(@RequestParam(name = "topicID") Integer topicID) {
        ArrayData arrayData = new ArrayData();

        Optional<TopicBean> topicBeanOptional = topicService.getById(topicID);

        topicBeanOptional.orElseThrow(() -> new RuntimeException("查無此筆記"));
        TopicBean topicBean = topicBeanOptional.get();

        ObjectData objectData = arrayData.addObject();
        objectData.add("title", topicBean.getTitle());
        objectData.add("content", topicBean.getContent());

        return  ResponseEntityBuilder.success()
                .message("查詢成功 & 歷史紀錄成功")
                .data(arrayData)
                .build();

    }

    @GetMapping(path = "/topic")
    public ResponseEntity<String> topicGet(@RequestParam(name = "topicID") Integer topicID,
                                           //@RequestParam(name = "userID") Integer userID,
                                           HttpServletRequest request) {
        ArrayData arrayData = new ArrayData();

        jwtutil jwt = new jwtutil();

        String user = jwt.parseToken(request.getHeader("token"));

        Integer id = Integer.parseInt(user.split(" ")[1]);

        for (TopicBean topicBean : topicService.searchMessageByTopicID(topicID)) {
            ObjectData objectData = arrayData.addObject();
            objectData.add("message_id",topicBean.getMessageId());
            objectData.add("user_id", topicBean.getUserId());
            objectData.add("message_content", topicBean.getMessageContent());

            Integer userId = topicBean.getUserId();

            Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(userId);

            userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
            UserAccountBean userAccountBean = userAccountBeanOptional.get();

            objectData.add("user_name", userAccountBean.getName());
        }

        Optional<TopicBean> topicBeanOptional = topicService.getById(topicID);

        topicBeanOptional.orElseThrow(() -> new RuntimeException("查無此筆記"));
        TopicBean topicBean = topicBeanOptional.get();

        topicBean.setView(topicBean.getView()+1);
        topicService.update(topicID, topicBean);

        ObjectData objectData = arrayData.addObject();
        objectData.add("view", topicBean.getView());
        objectData.add("likes", topicBean.getLikes());
        objectData.add("title", topicBean.getTitle());
        objectData.add("content", topicBean.getContent());
        objectData.add("create_date", topicBean.getCreateDate());
        objectData.add("edit_date", topicBean.getEditDate());
        objectData.add("author",topicBean.getAuthor());

        Integer userId = topicBean.getAuthor();

        Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(userId);

        userAccountBeanOptional.orElseThrow(() ->new RuntimeException("查無此用戶"));
        UserAccountBean userAccountBean = userAccountBeanOptional.get();

        objectData.add("author_name", userAccountBean.getName());

        HistoryBean historyBean = new HistoryBean();

        historyBean.setTopicId(topicID);
        historyBean.setUserId(id);
//          BindingResultUtils.validate(bindingResult);
        historyService.save(historyBean);

        return  ResponseEntityBuilder.success()
                .message("查詢成功 & 歷史紀錄成功")
                .data(arrayData)
                .build();

    }

    @GetMapping(path = "/test")
    public ResponseEntity<String> test(HttpServletRequest request){
        ObjectData objectData = new ObjectData();

        jwtutil jwt = new jwtutil();

        String token = request.getHeader("token");

        String ans = jwt.parseToken(token);

        objectData.add("user",ans);

        if(token == null){
            return  ResponseEntityBuilder.success()
                    .message("查詢false")
                    .data(objectData)
                    .build();
        }else {
            return  ResponseEntityBuilder.success()
                    .message("查詢成功")
                    .data(objectData)
                    .build();
        }

    }

    @GetMapping(path = "/test2")
    public ResponseEntity<String> test2(@RequestParam(name = "token")String token){
        ObjectData objectData = new ObjectData();

        jwtutil jwt = new jwtutil();

        String ans = jwt.parseToken(token);

        objectData.add("user",ans);

        return  ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }

    @GetMapping(path = "/tagList")
    public ResponseEntity<String> tagList(@RequestParam(name  = "tagType") Integer tagType){
       ArrayData arrayData = new ArrayData();

       for(TagBean tagBean : tagService.searchAll(tagType)){
           ObjectData objectData = arrayData.addObject();
           objectData.add("id",tagBean.getId());
           objectData.add("name",tagBean.getName());
       }

       return ResponseEntityBuilder.success()
               .message("查詢成功")
               .data(arrayData)
               .build();
    }

    @GetMapping(path = "/tagTypeSearch")
    public ResponseEntity<String> tagTypeSearch(@RequestParam(name  = "tagType") Integer tagType){
        ArrayData arrayData = new ArrayData();

        for(TopicBean topicBean : topicService.searchAll()){

            Optional<TagBean> tagBeanOptional = tagService.getById(topicBean.getTagId());

            TagBean tagBean = tagBeanOptional.get();

            if (tagBean.getTagType() == tagType){
                ObjectData objectData = arrayData.addObject();
                objectData.add("id",topicBean.getId());
                objectData.add("title",topicBean.getTitle());

                Integer author = topicBean.getAuthor();

                Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(author);

                userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
                UserAccountBean userAccountBean = userAccountBeanOptional.get();

                objectData.add("author", userAccountBean.getName());

                Optional<TagBean> tagBeanOptional1 = tagService.getById(topicBean.getTagId());
                tagBeanOptional1.orElseThrow(() -> new RuntimeException("查無此用戶"));
                TagBean tagBean1 = tagBeanOptional.get();

                objectData.add("tag_name", tagBean1.getName());

            }
        }

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    @GetMapping(path = "/pressLike")
    public ResponseEntity<String> pressLike(@RequestParam(name  = "topicId") Integer topicId){
        Optional<TopicBean> topicBeanOptional = topicService.getById(topicId);

        topicBeanOptional.orElseThrow(() -> new RuntimeException("查無此筆記"));
        TopicBean topicBean = topicBeanOptional.get();

        topicBean.setLikes(topicBean.getLikes()+1);
        topicService.save(topicBean);

        ObjectData objectData = new ObjectData();
        objectData.add("likes", topicBean.getLikes());

        return ResponseEntityBuilder.success()
                .message("按讚成功")
                .data(objectData)
                .build();

    }

    @PostMapping(path = "/addCollect")
    public  ResponseEntity<String> createCollect(@Valid @RequestBody CollectBean collectBean,
                                                 BindingResult bindingResult){
        BindingResultUtils.validate(bindingResult);
        collectService.save(collectBean);
        return ResponseEntityBuilder.success()
                .message("收藏筆記成功")
                .build();
    }

    @GetMapping(path = "/tagSearch")
    public ResponseEntity<String> tagSearch(@RequestParam(name = "tag") Integer tag){
        ArrayData arrayData = new ArrayData();

        for(TopicBean topicBean : topicService.searchAll(tag)){
            ObjectData objectData = arrayData.addObject();
            objectData.add("id",topicBean.getId());
            objectData.add("title",topicBean.getTitle());

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

    @GetMapping(path = "/swopSearch")
    public ResponseEntity<String> swopSearch(){
        ArrayData arrayData = new ArrayData();

        for(TopicBean topicBean : topicService.searchAll(13)){
            ObjectData objectData = arrayData.addObject();
            objectData.add("id",topicBean.getId());
            objectData.add("title",topicBean.getTitle());

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

    @GetMapping(path = "/keySearch")
    public ResponseEntity<String> keySearch(@RequestParam(name = "key") String key){
        ArrayData arrayData = new ArrayData();

        for(TopicBean topicBean : topicService.searchAll()){
            String title = topicBean.getTitle();

            if (title.contains(key)){
                ObjectData objectData = arrayData.addObject();

                objectData.add("id",topicBean.getId());
                objectData.add("title",topicBean.getTitle());

                Integer author = topicBean.getAuthor();

                Optional<UserAccountBean> userAccountBeanOptional = userAccountService.getById(author);

                userAccountBeanOptional.orElseThrow(() -> new RuntimeException("查無此用戶"));
                UserAccountBean userAccountBean = userAccountBeanOptional.get();

                objectData.add("author", userAccountBean.getName());
            }
        }

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    @PostMapping(path = "/postNote")
    public ResponseEntity<String> createTopic(@Valid @RequestBody TopicBean topicBean,
                                              BindingResult bindingResult){
        BindingResultUtils.validate(bindingResult);
        topicService.save(topicBean);
        return ResponseEntityBuilder.success()
                .message("新增成功")
                .build();
    }

    @PostMapping(path = "/postMessage")
    public ResponseEntity<String> createMessage(@Valid @RequestBody MessageBean messageBean,
                                                BindingResult bindingResult){
        BindingResultUtils.validate(bindingResult);
        messageService.save(messageBean);
        return ResponseEntityBuilder.success()
                .message("留言成功")
                .build();
    }

    @PatchMapping(path = "/editNote")
    public ResponseEntity<String> updateTopic(@RequestBody TopicBean topicBean){
        topicService.update(topicBean.getId(), topicBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @DeleteMapping(path = "/delNote")
    public ResponseEntity<String> delTopic(@RequestParam(name = "topicId") Integer topicId){
        topicService.delete(topicId);
        return ResponseEntityBuilder.success()
                .message("文章刪除成功")
                .build();
    }

    @DeleteMapping(path = "/delMessage")
    public ResponseEntity<String> delMessage(@RequestParam(name = "messageId") Integer messageId){
        messageService.delete(messageId);
        return ResponseEntityBuilder.success()
                .message("留言刪除成功")
                .build();
    }

}
