package tw.edu.ntub.imd.plearnet.util.http;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.text.StringEscapeUtils;
import tw.edu.ntub.birc.common.exception.NullParameterException;
import tw.edu.ntub.birc.common.exception.UnknownException;
import tw.edu.ntub.birc.common.exception.date.ParseDateException;
import tw.edu.ntub.birc.common.util.DateTimeUtils;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.birc.common.wrapper.date.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ResponseUtils {
    // JSON的Content-Type字串
    public static final String JSON_MIME_TYPE = "application/json; charset=UTF-8";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        SimpleModule simpleModule = new DateModule();
        simpleModule.addDeserializer(Boolean.class, new JsonDeserializer<>() {
            @Override
            public Boolean deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String value = p.getValueAsString();
                if (value == null || value.isBlank()) {
                    return null;
                }
                return value.equals("1") || value.equalsIgnoreCase("true");
            }
        });
        simpleModule.addDeserializer(String.class, new JsonDeserializer<>() {
            @Override
            public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                if (p.getCurrentValue() instanceof Boolean) {
                    return p.getValueAsBoolean() ? "1" : "0";
                }
                return p.getValueAsString();
            }
        });
        mapper.registerModule(simpleModule);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private ResponseUtils() {

    }

    public static JsonNode createJsonNode(String jsonString) {
        try {
            return mapper.readTree(jsonString);
        } catch (IOException e) {
            throw new UnknownException(e);
        }
    }

    public static JsonNode createJsonNode(Map<?, ?> map) {
        return mapper.valueToTree(map);
    }

    public static ObjectMapper createMapper() {
        return mapper;
    }

    public static <D> D readValue(String jsonString, Class<D> jsonStringClass) {
        try {
            String unEscapeString = StringEscapeUtils.unescapeJson(jsonString);
            if (unEscapeString.startsWith("\"")) {
                unEscapeString = unEscapeString.replaceFirst("\"", "");
            }
            if (unEscapeString.endsWith("\"")) {
                unEscapeString = unEscapeString.substring(0, unEscapeString.length() - 1);
            }
            return mapper.readValue(unEscapeString, jsonStringClass);
        } catch (IOException e) {
            throw new UnknownException(e);
        }
    }

    public static class DateModule extends SimpleModule {
        public DateModule() {
            super("DateModule", new Version(1, 0, 0, null, null, null));
            addSerializer(LocalDate.class, new JsonSerializer<>() {
                @Override
                public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString(value.format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE.getPattern())));
                }
            });
            addSerializer(LocalTime.class, new JsonSerializer<>() {
                @Override
                public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString(value.format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_TIME.getPattern())));
                }
            });
            addSerializer(LocalDateTime.class, new JsonSerializer<>() {
                @Override
                public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString(value.format(DateTimeFormatter.ofPattern(DateTimePattern.DEFAULT_DATE_TIME.getPattern())));
                }
            });
            addDeserializer(LocalDate.class, new JsonDeserializer<>() {
                @Override
                public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    if (StringUtils.isBlank(p.getValueAsString())) {
                        return null;
                    }
                    DateDetail dateDetail = DateTimeUtils.parseDate(p.getValueAsString());
                    return LocalDate.of(dateDetail.getYear(), dateDetail.getMonthNo(), dateDetail.getDay());
                }
            });
            addDeserializer(DateWrapper.class, new JsonDeserializer<>() {
                @Override
                public DateWrapper deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    try {
                        return DateTimeUtils.parseDate(p.getValueAsString());
                    } catch (ParseDateException | NullParameterException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            });
            addDeserializer(LocalTime.class, new JsonDeserializer<>() {
                @Override
                public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    if (StringUtils.isBlank(p.getValueAsString())) {
                        return null;
                    }
                    TimeDetail timeDetail = DateTimeUtils.parseTime(p.getValueAsString());
                    return LocalTime.of(
                            timeDetail.getHour(),
                            timeDetail.getMinute(),
                            timeDetail.getSecond(),
                            timeDetail.getMillisecondOfNanosecond()
                    );
                }
            });
            addDeserializer(TimeWrapper.class, new JsonDeserializer<>() {
                @Override
                public TimeWrapper deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    try {
                        return DateTimeUtils.parseTime(p.getValueAsString());
                    } catch (ParseDateException | NullParameterException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            });
            addDeserializer(LocalDateTime.class, new JsonDeserializer<>() {
                @Override
                public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    if (StringUtils.isBlank(p.getValueAsString())) {
                        return null;
                    }
                    DateTimeDetail dateTimeDetail = DateTimeUtils.parseDateTime(p.getValueAsString());
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
            });
            addDeserializer(DateTimeWrapper.class, new JsonDeserializer<>() {
                @Override
                public DateTimeWrapper deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    try {
                        return DateTimeUtils.parseDateTime(p.getValueAsString());
                    } catch (ParseDateException | NullParameterException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            });
            addDeserializer(Year.class, new JsonDeserializer<>() {
                @Override
                public Year deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                    if (StringUtils.isBlank(p.getValueAsString())) {
                        return null;
                    }
                    int year = Integer.parseInt(p.getValueAsString());
                    return Year.of(year);
                }
            });
        }
    }
}
