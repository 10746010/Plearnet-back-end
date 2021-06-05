package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAccountBean {
    private String account;
    private String password;
    private String email;
    private Integer id;
    private String name;
    private String sex;
    private LocalDateTime register;
}
