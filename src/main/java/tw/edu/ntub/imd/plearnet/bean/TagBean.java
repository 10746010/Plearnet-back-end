package tw.edu.ntub.imd.plearnet.bean;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class TagBean {
    @Null(message = "id - 不得填寫")
    private Integer id;
    @NotBlank(message = "tag名稱 - 未填寫")
    private String name;
//    @NotNull(message = "tag類別 - 未填寫")
//    private Integer tagType;
}
