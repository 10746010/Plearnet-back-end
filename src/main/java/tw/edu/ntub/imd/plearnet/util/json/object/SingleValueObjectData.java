package tw.edu.ntub.imd.plearnet.util.json.object;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.lang.Nullable;
import tw.edu.ntub.birc.common.wrapper.date.DateTimePattern;
import tw.edu.ntub.birc.common.wrapper.date.DateWrapper;
import tw.edu.ntub.imd.plearnet.util.json.ResponseData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SingleValueObjectData implements ResponseData {
    private final tw.edu.ntub.imd.plearnet.util.json.object.ObjectData objectData = new tw.edu.ntub.imd.plearnet.util.json.object.ObjectData();

    private SingleValueObjectData() {

    }

    public static SingleValueObjectData create(String key, short value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, @Nullable Short value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, int value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, @Nullable Integer value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, long value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, @Nullable Long value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, float value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, @Nullable Float value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, double value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, @Nullable Double value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, boolean value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, @Nullable Boolean value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, BigDecimal value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, @Nullable BigInteger value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, String value) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, value);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, byte[] bytes) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, bytes);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, DateWrapper dateWrapper, DateTimePattern pattern) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, dateWrapper, pattern);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, LocalDate localDate, DateTimePattern pattern) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, localDate, pattern);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, LocalTime localTime, DateTimePattern pattern) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, localTime, pattern);
        return singleValueObjectData;
    }

    public static SingleValueObjectData create(String key, LocalDateTime localDateTime, DateTimePattern pattern) {
        SingleValueObjectData singleValueObjectData = new SingleValueObjectData();
        singleValueObjectData.objectData.add(key, localDateTime, pattern);
        return singleValueObjectData;
    }

    @Override
    public JsonNode getData() {
        return objectData.getData();
    }

    @Override
    public String toString() {
        return objectData.toString();
    }
}
