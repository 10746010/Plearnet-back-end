package tw.edu.ntub.imd.plearnet.databaseconfig.entity;


import lombok.Data;
import tw.edu.ntub.imd.plearnet.databaseconfig.Config;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener.TopicListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(TopicListener.class)
@Table(name = "topic", schema = Config.DATABASE_NAME)
public class Topic {
    /**
     * 筆記編號
     *
     * @Since 1.0.1
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 筆記標題
     *
     * @Since 1.0.1
     */
    @Column(name = "title", length = 20, nullable = false)
    private String title;

    /**
     * 筆記內文
     *
     * @Since 1.0.1
     */
    @Column(name= "content", length = 200, nullable = false)
    private String content;

    /**
     * 筆記瀏覽數
     *
     * @Since 1.0.1
     */
    @Column(name = "view", nullable = false)
    private float view;

    /**
     * 筆記按讚數
     *
     * @Since 1.0.1
     */
    @Column(name = "likes", nullable = false)
    private float likes;

    /**
     * 發文時間
     *
     * @Since 1.0.1
     */
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    /**
     * 最後編輯時間
     *
     * @Since 1.0.1
     */
    @Column(name = "edit_date",nullable = false)
    private LocalDateTime editDate;

    /**
     * tag_id
     *
     * @Since 1.0.1
     */
    @Column(name = "tag_id", length = 11, nullable = false)
    private Integer tagId;

}
