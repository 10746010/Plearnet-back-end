package tw.edu.ntub.imd.plearnet.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.plearnet.databaseconfig.Config;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener.HistoryListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(HistoryListener.class)
@Table(name = "history", schema = Config.DATABASE_NAME)
public class History {

    /**
     * 對應topic的筆記編號
     *
     * @Since 1.0.1
     */
    @Column(name = "topic_id",nullable = false)
    private Integer topicId;

    /**
     * 對應useraccount的user_id
     *
     * @Since 1.0.1
     */
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    /**
     * History的ID
     *
     * @Since 1.0.1
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 瀏覽時間
     *
     * @Since 1.0.1
     */
    @Column(name = "view_time",nullable = false)
    private LocalDateTime viewTime;
}
