package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TopicBean {
    private Integer id;
    private String title;
    private String content;
    private float view;
    private float likes;
    private LocalDateTime createDate;
    private LocalDateTime editDate;
    private Integer tagId;
}
