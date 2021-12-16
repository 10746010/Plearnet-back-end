package tw.edu.ntub.imd.plearnet.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.plearnet.databaseconfig.Config;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.listener.UserAccountListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(UserAccountListener.class)
@Table(name = "useraccount", schema = Config.DATABASE_NAME)
public class UserAccount {
    /**
     * 編號
     *
     * @since 1.0.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Integer id;

    /**
     * 帳號
     *
     * @since 1.0.0
     */
    @Column(name = "account", length = 50, nullable = false)
    private String account;

    /**
     * 密碼，用BCrypt加密
     *
     * @since 1.0.0
     */
    @Column(name = "password", length = 20, nullable = false)
    private String password;

    /**
     * 電子郵件
     *
     * @since 1.0.0
     */
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    /**
     * 用戶名
     *
     * @since 1.0.0
     */
    @Column(name = "name",length = 10,nullable = false)
    private String name;

    /**
     * 性別(0:男，1:女)
     *
     * @since 1.0.0
     */
    @Column(name = "sex",length = 1,nullable = false)
    private Integer sex;

    /**
     * 註冊時間
     *
     * @since 1.0.0
     */
    @Column(name = "registered",nullable = false)
    private LocalDateTime register;

    /**
     * 頭像
     *
     * @since 1.0.0
     */
    @Column(name = "user_pic",nullable = false)
    private LocalDateTime userPic;
}
