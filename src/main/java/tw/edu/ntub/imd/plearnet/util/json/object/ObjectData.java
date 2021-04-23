package tw.edu.ntub.imd.plearnet.util.json.object;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.lang.Nullable;
import tw.edu.ntub.birc.common.wrapper.date.DateTimePattern;
import tw.edu.ntub.birc.common.wrapper.date.DateTimeWrapper;
import tw.edu.ntub.birc.common.wrapper.date.DateWrapper;
import tw.edu.ntub.birc.common.wrapper.date.TimeWrapper;
import tw.edu.ntub.imd.plearnet.util.http.ResponseUtils;
import tw.edu.ntub.imd.plearnet.util.json.ResponseData;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ObjectData implements ResponseData {
    private final ObjectNode objectNode;

    public ObjectData() {
        this(ResponseUtils.createMapper().createObjectNode());
    }

    public ObjectData(String jsonString) {
        this((ObjectNode) ResponseUtils.createJsonNode(jsonString));
    }

    public ObjectData(ObjectNode objectNode) {
        this.objectNode = objectNode;
    }

    public ObjectData addNull(String key) {
        objectNode.putNull(key);
        return this;
    }

    public ObjectData add(String key, ObjectData objectData) {
        return add(key, objectData.objectNode);
    }

    public ObjectData add(String key, ArrayData arrayData) {
        return add(key, arrayData.getData());
    }

    public ObjectData add(String key, ResponseData responseData) {
        return add(key, responseData.getData());
    }

    public ObjectData add(String key, JsonNode jsonNode) {
        return replace(key, jsonNode);
    }

    public ObjectData replace(String key, JsonNode jsonNode) {
        objectNode.set(key, jsonNode);
        return this;
    }

    public ObjectData remove(String key) {
        objectNode.remove(key);
        return this;
    }

    public ObjectData add(String key, short value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Short value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, int value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Integer value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, long value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Long value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, float value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Float value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, double value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Double value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, boolean value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable Boolean value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, BigDecimal value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, @Nullable BigInteger value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, String value) {
        objectNode.put(key, value);
        return this;
    }

    public ObjectData add(String key, byte[] bytes) {
        objectNode.put(key, bytes);
        return this;
    }

    public ObjectData add(String key, DateWrapper dateWrapper) {
        return add(key, dateWrapper, DateTimePattern.DEFAULT_DATE);
    }

    public ObjectData add(String key, DateWrapper dateWrapper, DateTimePattern builder) {
        objectNode.put(key, dateWrapper != null ? dateWrapper.format(builder) : null);
        return this;
    }

    public ObjectData add(String key, TimeWrapper timeWrapper) {
        return add(key, timeWrapper, DateTimePattern.DEFAULT_TIME);
    }

    public ObjectData add(String key, TimeWrapper timeWrapper, DateTimePattern builder) {
        objectNode.put(key, timeWrapper != null ? timeWrapper.format(builder) : null);
        return this;
    }

    public ObjectData add(String key, DateTimeWrapper dateTimeWrapper) {
        return add(key, dateTimeWrapper, DateTimePattern.DEFAULT_DATE_TIME);
    }

    public ObjectData add(String key, DateTimeWrapper dateTimeWrapper, DateTimePattern builder) {
        objectNode.put(key, dateTimeWrapper != null ? dateTimeWrapper.format(builder) : null);
        return this;
    }

    public ObjectData add(String key, LocalDate localDate) {
        return add(key, localDate, DateTimePattern.DEFAULT_DATE);
    }

    public ObjectData add(String key, LocalDate localDate, DateTimePattern pattern) {
        objectNode.put(
                key,
                localDate != null ?
                        localDate.format(DateTimeFormatter.ofPattern(pattern.getPattern(), pattern.getLocale())) :
                        ""
        );
        return this;
    }

    public ObjectData add(String key, LocalTime localTime) {
        return add(key, localTime, DateTimePattern.DEFAULT_TIME);
    }

    public ObjectData add(String key, LocalTime localTime, DateTimePattern pattern) {
        objectNode.put(
                key,
                localTime != null ?
                        localTime.format(DateTimeFormatter.ofPattern(pattern.getPattern(), pattern.getLocale())) :
                        ""
        );
        return this;
    }

    public ObjectData add(String key, LocalDateTime localDateTime) {
        return add(key, localDateTime, DateTimePattern.DEFAULT_DATE_TIME);
    }

    public ObjectData add(String key, LocalDateTime localDateTime, DateTimePattern pattern) {
        objectNode.put(
                key,
                localDateTime != null ?
                        localDateTime.format(DateTimeFormatter.ofPattern(pattern.getPattern(), pattern.getLocale())) :
                        ""
        );
        return this;
    }

    public ObjectData add(String key, Year year) {
        if (year == null) {
            return addNull(key);
        }
        add(key, year.getValue());
        return this;
    }

    public ObjectData addObject(String key) {
        return new ObjectData(objectNode.putObject(key));
    }

    public ArrayData addArray(String key) {
        return new ArrayData(objectNode.putArray(key));
    }

    public ArrayData addStringArray(String key, String[] dataArray) {
        return addStringArray(key, Arrays.asList(dataArray));
    }

    public ArrayData addStringArray(String key, Collection<String> dataCollection) {
        ArrayData arrayData = addArray(key);
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addByteArray(String key, byte[] dataArray) {
        ArrayData arrayData = addArray(key);
        arrayData.add(dataArray);
        return arrayData;
    }

    public ArrayData addShortArray(String key, short[] dataArray) {
        ArrayData arrayData = addArray(key);
        for (short data : dataArray) {
            arrayData.add(data);
        }
        return arrayData;
    }

    public ArrayData addShortArray(String key, Collection<Short> dataCollection) {
        ArrayData arrayData = addArray(key);
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addIntArray(String key, int[] dataArray) {
        return addIntegerArray(key, Arrays.stream(dataArray).boxed().collect(Collectors.toList()));
    }

    public ArrayData addIntegerArray(String key, Collection<Integer> dataCollection) {
        ArrayData arrayData = addArray(key);
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addLongArray(String key, long[] dataArray) {
        return addLongArray(key, Arrays.stream(dataArray).boxed().collect(Collectors.toList()));
    }

    public ArrayData addLongArray(String key, Collection<Long> dataCollection) {
        ArrayData arrayData = addArray(key);
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addFloatArray(String key, float[] dataArray) {
        ArrayData arrayData = addArray(key);
        for (float data : dataArray) {
            arrayData.add(data);
        }
        return arrayData;
    }

    public ArrayData addFloatArray(String key, Collection<Float> dataCollection) {
        ArrayData arrayData = addArray(key);
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addDoubleArray(String key, double[] dataArray) {
        return addDoubleArray(key, Arrays.stream(dataArray).boxed().collect(Collectors.toList()));
    }

    public ArrayData addDoubleArray(String key, Collection<Double> dataCollection) {
        ArrayData arrayData = addArray(key);
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public CollectionObjectData createCollectionData() {
        return new CollectionObjectData(this);
    }

    public CollectionObjectData createCollectionData(String key) {
        return new CollectionObjectData(addObject(key));
    }

    public MapObjectData addMapToThis() {
        return new MapObjectData(this);
    }

    public MapObjectData addMapToNewObject(String key) {
        return new MapObjectData(addObject(key));
    }

    public <T> Consumer<T> getAddObjectConsumer(String key, BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return t -> addObjectDataConsumer.accept(addObject(key), t);
    }

    @Override
    public ObjectNode getData() {
        return objectNode;
    }

    public Integer getInt(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asInt();
        } else {
            return null;
        }
    }

    public Long getLong(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asLong();
        } else {
            return null;
        }
    }

    public Double getDouble(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asDouble();
        } else {
            return null;
        }
    }

    public Boolean getBoolean(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asBoolean();
        } else {
            return null;
        }
    }

    public String getString(String key) {
        JsonNode nodeValue = objectNode.findValue(key);
        if (nodeValue != null && !nodeValue.isNull()) {
            return nodeValue.asText();
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return objectNode.toString();
    }
}
