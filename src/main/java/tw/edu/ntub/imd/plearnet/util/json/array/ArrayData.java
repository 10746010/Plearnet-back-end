package tw.edu.ntub.imd.plearnet.util.json.array;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.lang.Nullable;
import tw.edu.ntub.birc.common.wrapper.date.DateTimePattern;
import tw.edu.ntub.birc.common.wrapper.date.DateWrapper;
import tw.edu.ntub.imd.plearnet.util.http.ResponseUtils;
import tw.edu.ntub.imd.plearnet.util.json.ResponseData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ArrayData implements ResponseData {
    private final ArrayNode arrayNode;

    public ArrayData() {
        this(ResponseUtils.createMapper().createArrayNode());
    }

    public ArrayData(ArrayNode arrayNode) {
        this.arrayNode = arrayNode;
    }

    public int length() {
        return size();
    }

    public int size() {
        return arrayNode.size();
    }

    public ArrayData add(int value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Integer value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(long value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Long value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(float value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Float value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(double value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Double value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(boolean value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable Boolean value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(BigDecimal value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(@Nullable BigInteger value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(String value) {
        arrayNode.add(value);
        return this;
    }

    public ArrayData add(byte[] bytes) {
        arrayNode.add(bytes);
        return this;
    }

    public ArrayData add(DateWrapper dateWrapper, DateTimePattern pattern) {
        arrayNode.add(dateWrapper.format(pattern));
        return this;
    }

    public ArrayData add(LocalDate localDate, DateTimePattern pattern) {
        arrayNode.add(
                localDate != null ?
                        localDate.format(DateTimeFormatter.ofPattern(pattern.getPattern(), pattern.getLocale())) :
                        ""
        );
        return this;
    }

    public ArrayData add(LocalTime localTime, DateTimePattern pattern) {
        arrayNode.add(
                localTime != null ?
                        localTime.format(DateTimeFormatter.ofPattern(pattern.getPattern(), pattern.getLocale())) :
                        ""
        );
        return this;
    }

    public ArrayData add(LocalDateTime localDateTime, DateTimePattern pattern) {
        arrayNode.add(
                localDateTime != null ?
                        localDateTime.format(DateTimeFormatter.ofPattern(pattern.getPattern(), pattern.getLocale())) :
                        ""
        );
        return this;
    }

    public ArrayData add(ResponseData responseData) {
        return add(responseData.getData());
    }

    public ArrayData add(JsonNode jsonNode) {
        arrayNode.add(jsonNode);
        return this;
    }

    public ObjectData addObject() {
        return new ObjectData(arrayNode.addObject());
    }

    public ArrayData addArray() {
        return new ArrayData(arrayNode.addArray());
    }

    public ArrayData addStringArray(String[] dataArray) {
        return addStringArray(Arrays.asList(dataArray));
    }

    public ArrayData addStringArray(Collection<String> dataCollection) {
        ArrayData arrayData = addArray();
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addByteArray(byte[] dataArray) {
        ArrayData arrayData = addArray();
        arrayData.add(dataArray);
        return arrayData;
    }

    public ArrayData addShortArray(short[] dataArray) {
        ArrayData arrayData = addArray();
        for (short data : dataArray) {
            arrayData.add(data);
        }
        return arrayData;
    }

    public ArrayData addShortArray(Collection<Short> dataCollection) {
        ArrayData arrayData = addArray();
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addIntArray(int[] dataArray) {
        return addIntegerArray(Arrays.stream(dataArray).boxed().collect(Collectors.toList()));
    }

    public ArrayData addIntegerArray(Collection<Integer> dataCollection) {
        ArrayData arrayData = addArray();
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addLongArray(long[] dataArray) {
        return addLongArray(Arrays.stream(dataArray).boxed().collect(Collectors.toList()));
    }

    public ArrayData addLongArray(Collection<Long> dataCollection) {
        ArrayData arrayData = addArray();
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addFloatArray(float[] dataArray) {
        ArrayData arrayData = addArray();
        for (float data : dataArray) {
            arrayData.add(data);
        }
        return arrayData;
    }

    public ArrayData addFloatArray(Collection<Float> dataCollection) {
        ArrayData arrayData = addArray();
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public ArrayData addDoubleArray(double[] dataArray) {
        return addDoubleArray(Arrays.stream(dataArray).boxed().collect(Collectors.toList()));
    }

    public ArrayData addDoubleArray(Collection<Double> dataCollection) {
        ArrayData arrayData = addArray();
        dataCollection.forEach(arrayData::add);
        return arrayData;
    }

    public tw.edu.ntub.imd.plearnet.util.json.array.CollectionArrayData addCollectionToThis() {
        return new tw.edu.ntub.imd.plearnet.util.json.array.CollectionArrayData(this);
    }

    public tw.edu.ntub.imd.plearnet.util.json.array.CollectionArrayData addCollectionToNewArray() {
        return new tw.edu.ntub.imd.plearnet.util.json.array.CollectionArrayData(addArray());
    }

    public MapArrayData addMapToThis() {
        return new MapArrayData(this);
    }

    public MapArrayData addMapToNewArray() {
        return new MapArrayData(addArray());
    }

    public <T> Consumer<T> getAddObjectConsumer(BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return t -> addObjectDataConsumer.accept(addObject(), t);
    }

    public JsonNode get(int index) {
        return arrayNode.get(index);
    }

    @Override
    public ArrayNode getData() {
        return arrayNode;
    }

    @Override
    public String toString() {
        return arrayNode.toString();
    }
}
