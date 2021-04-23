package tw.edu.ntub.imd.plearnet.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.DateTimeUtils;
import tw.edu.ntub.birc.common.wrapper.date.DateDetail;

import java.time.LocalDate;

@Component
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(@NonNull String source) {
        DateDetail dateDetail = DateTimeUtils.parseDate(source);
        return LocalDate.of(dateDetail.getYear(), dateDetail.getMonthNo(), dateDetail.getDay());
    }
}
