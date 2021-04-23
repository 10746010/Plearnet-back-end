package tw.edu.ntub.imd.plearnet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private int totalPage;
    private long totalDataCount;
    private int countPerPage;
}
