package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

@Data
public class UserAccountBean {
    private String account;
    private String password;
    private String email;
    private Integer id;
}
