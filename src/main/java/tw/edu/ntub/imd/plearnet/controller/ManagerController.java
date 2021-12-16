package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.plearnet.bean.TagBean;
import tw.edu.ntub.imd.plearnet.bean.TopicBean;
import tw.edu.ntub.imd.plearnet.service.TagService;
import tw.edu.ntub.imd.plearnet.util.http.BindingResultUtils;
import tw.edu.ntub.imd.plearnet.util.http.ResponseEntityBuilder;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/manager")
public class ManagerController {
    private final TagService tagService;

    @PostMapping(path = "/newTag")
    public ResponseEntity<String> createTag(@Valid @RequestBody TagBean tagBean,
                                            BindingResult bindingResult){
        BindingResultUtils.validate(bindingResult);
        tagService.save(tagBean);
        return ResponseEntityBuilder.success()
                .message("新增成功")
                .build();
    }

    @PatchMapping(path = "/editTag")
    public ResponseEntity<String> updateTag(@RequestBody TagBean tagBean){
        tagService.update(tagBean.getId(), tagBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @DeleteMapping(path = "/delTag")
    public ResponseEntity<String> delTag(@RequestParam(name = "tagId") Integer tagId){
        tagService.delete(tagId);
        return ResponseEntityBuilder.success()
                .message("Tag刪除成功")
                .build();
    }

}
