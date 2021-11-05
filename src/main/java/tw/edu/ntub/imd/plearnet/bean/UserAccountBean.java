package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UserAccountBean {
    @Size(max = 50, message = "帳號 - 輸入字數大於{max}個字")
    private String account;
    @Size(max = 50, message = "密碼 - 輸入字數大於{max}個字")
    private String password;
    @Size(max = 50, message = "電子郵件 - 輸入字數大於{max}個字")
    @Email(message = "電子郵件 - 輸入格式不符合電子郵件的格式")
    private String email;
    private Integer id;
    @Size(max = 10, message = "姓名 - 輸入字數大於{max}個字")
    private String name;
    private String sex;
    private LocalDateTime register;
}
