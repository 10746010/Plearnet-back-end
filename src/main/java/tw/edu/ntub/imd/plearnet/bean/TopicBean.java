package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.Message;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TopicBean {
    @Null(message = "id - 不得填寫")
    private Integer id;
    @NotBlank(message = "標題 - 未填寫")
    private String title;
    @NotBlank(message = "筆記內容 - 未填寫")
    @Size(max = 200, message = "筆記內容 - 輸入字數大於{max}個字")
    private String content;
    private float view;
    private float likes;
    @Null(message = "開始時間 - 不得填寫")
    private LocalDateTime createDate;
    @Null(message = "最後修改時間 - 不得填寫")
    private LocalDateTime editDate;
    @NotNull(message = "筆記分類 - 未填寫")
    private Integer tagId;
    private Integer author;
    private List<MessageBean> messageBeanList;
    private Integer messageId;
    private Integer userId;
    private String messageContent;
}
