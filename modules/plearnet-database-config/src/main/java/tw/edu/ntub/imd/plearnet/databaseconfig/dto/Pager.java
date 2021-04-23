package tw.edu.ntub.imd.plearnet.databaseconfig.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.edu.ntub.birc.common.util.MathUtils;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Pager {
    private int page;
    private int count;

    public static Pager getInstance(int page, int count) {
        return new Pager(page, count);
    }

    public static Pager infinity() {
        return new Pager(-1, -1);
    }

    public int getFirstResultIndex() {
        return (page - 1) * count;
    }

    public boolean isInfinity() {
        return page <= 0 || count <= 0;
    }

    public int getZeroBasedPage() {
        return MathUtils.translateOneBasedToZeroBased(page);
    }
}
