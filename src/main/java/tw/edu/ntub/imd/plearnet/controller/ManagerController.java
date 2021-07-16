package tw.edu.ntub.imd.plearnet.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.plearnet.bean.TagBean;
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
}
