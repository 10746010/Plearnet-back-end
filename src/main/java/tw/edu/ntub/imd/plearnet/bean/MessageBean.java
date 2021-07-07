package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

@Data
public class MessageBean {
    private Integer topicId;
    private String content;
    private Integer userid;
    private Integer id;
}
