package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryBean {
    private Integer topicId;
    private Integer userId;
    private LocalDateTime viewTime;
}
