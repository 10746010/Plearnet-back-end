package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class MessageBean {
    @NotNull(message = "所屬筆記 - 未填寫")
    private Integer topicId;
    @NotBlank(message = "留言內容 - 未填寫")
    @Size(max = 50, message = "筆記內容 - 輸入字數大於{max}個字")
    private String content;
    @NotNull(message = "留言者 - 未填寫")
    private Integer userid;
    @Null(message = "id - 不得填寫")
    private Integer id;
}
