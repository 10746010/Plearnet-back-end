package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class UserAccountBean {
    @Id
    @Null(message = "id - 不得填寫")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "帳號 - 未填寫")
    @Size(max = 50, message = "帳號 - 輸入字數大於{max}個字")
    private String account;

    @NotBlank(message = "密碼 - 未填寫")
    @Size(max = 50, message = "密碼 - 輸入字數大於{max}個字")
    private String password;

    @Size(max = 50, message = "電子郵件 - 輸入字數大於{max}個字")
    @Email(message = "電子郵件 - 輸入格式不符合電子郵件的格式")
    private String email;

    @Size(max = 10, message = "姓名 - 輸入字數大於{max}個字")
    private String name;

    private String sex;

    @Null(message = "註冊時間 - 不得填寫")
    private LocalDateTime register;
}
