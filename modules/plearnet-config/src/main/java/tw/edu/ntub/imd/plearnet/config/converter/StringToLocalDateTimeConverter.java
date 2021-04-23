package tw.edu.ntub.imd.plearnet.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.DateTimeUtils;
import tw.edu.ntub.birc.common.wrapper.date.DateTimeDetail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(@NonNull String source) {
        DateTimeDetail dateTimeDetail = DateTimeUtils.parseDateTime(source);
        return LocalDateTime.of(
                LocalDate.of(dateTimeDetail.getYear(), dateTimeDetail.getMonthNo(), dateTimeDetail.getDay()),
                LocalTime.of(
                        dateTimeDetail.getHour(),
                        dateTimeDetail.getMinute(),
                        dateTimeDetail.getSecond(),
                        dateTimeDetail.getMillisecondOfNanosecond()
                )
        );
    }
}
