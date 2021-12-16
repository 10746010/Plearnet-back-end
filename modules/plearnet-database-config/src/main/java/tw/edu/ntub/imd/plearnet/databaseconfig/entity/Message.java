package tw.edu.ntub.imd.plearnet.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.plearnet.databaseconfig.Config;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener.MessageListener;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(MessageListener.class)
@Table(name = "message", schema = Config.DATABASE_NAME)
public class Message {
    /**
     * 對應topic的筆記編號
     *
     * @Since 1.0.1
     */
    @Column(name = "topic_id", nullable = false)
    private Integer topicId;

    /**
     * 留言內容
     *
     * @Since 1.0.1
     */
    @Column(name = "content",length = 60, nullable = false)
    private String content;

    /**
     * 對應useraccount的user_id
     *
     * @Since 1.0.1
     */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /**
     * 留言id
     *
     * @Since 1.0.1
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
