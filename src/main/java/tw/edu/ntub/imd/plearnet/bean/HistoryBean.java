package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Data
public class HistoryBean {
    @NotNull(message = "筆記id - 未填寫")
    private Integer topicId;
    @NotNull(message = "所屬使用者 - 未填寫")
    private Integer userId;
    @Null(message = "id - 不可填寫")
    private Integer id;
    @Null(message = "瀏覽時間 - 不可填寫")
    private LocalDateTime viewTime;
}
