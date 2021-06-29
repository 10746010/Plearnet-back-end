package tw.edu.ntub.imd.plearnet.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.plearnet.bean.TopicBean;
import tw.edu.ntub.imd.plearnet.service.TopicService;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/topic")
public class TopicController {
    private final TopicService topicService;

    @GetMapping(path = "/tagSearch")
    public ResponseEntity<String> tagSearch(@RequestParam(name = "tag") Integer tag){
        ArrayData arrayData = new ArrayData();

        for(TopicBean topicBean : topicService.searchAll(tag)){
            ObjectData objectData = arrayData.addObject();
            objectData.add("id",topicBean.getId());
            objectData.add("title",topicBean.getTitle());
        }

        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }
}
