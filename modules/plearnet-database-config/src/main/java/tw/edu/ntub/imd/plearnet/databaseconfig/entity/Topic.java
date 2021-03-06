package tw.edu.ntub.imd.plearnet.databaseconfig.entity;


import lombok.Data;
import tw.edu.ntub.imd.plearnet.databaseconfig.Config;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener.TopicListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @Column(name= "content", length = 8000, nullable = false)
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

    /**
     * 作者
     *
     * @Since 1.0.1
     */
    @Column(name = "author", nullable = false)
    private Integer author;

    /**
     * topic_id
     *
     * @Since 1.0.1
     * @see Message
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<Message> messageByTopicId;

}
