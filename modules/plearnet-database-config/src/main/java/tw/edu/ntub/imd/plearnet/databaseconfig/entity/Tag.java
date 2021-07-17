package tw.edu.ntub.imd.plearnet.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.plearnet.databaseconfig.Config;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener.TagListener;

import javax.persistence.*;

@Data
@Entity
@EntityListeners(TagListener.class)
@Table(name = "tag", schema = Config.DATABASE_NAME)
public class Tag {
    /**
     * Tag編號
     *
     * @Since 1.0.1
     */
    @Id
    @Column(name = "id", length = 11, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Tag名稱
     *
     * @since 1.0.0
     */
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    /**
     * Tag類型(0:非學科，1:學科)
     *
     * @since 1.0.0
     */
    @Column(name = "tag_type", nullable = false)
    private Integer tagType;

}
