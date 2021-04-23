package tw.edu.ntub.imd.plearnet.util.json.array;

import com.fasterxml.jackson.databind.JsonNode;
import tw.edu.ntub.imd.plearnet.util.function.AddObjectDataConsumer;
import tw.edu.ntub.imd.plearnet.util.function.AddObjectDataMapConsumer;
import tw.edu.ntub.imd.plearnet.util.function.TripleConsumer;
import tw.edu.ntub.imd.plearnet.util.json.ResponseData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MapArrayData implements ResponseData {
    private final tw.edu.ntub.imd.plearnet.util.json.array.ArrayData arrayData;

    public MapArrayData() {
        arrayData = new tw.edu.ntub.imd.plearnet.util.json.array.ArrayData();
    }

    protected MapArrayData(tw.edu.ntub.imd.plearnet.util.json.array.ArrayData arrayData) {
        this.arrayData = arrayData;
    }

    public MapArrayData(Map<String, String> resource) {
        this();
        add(resource);
    }

    public <K, V> MapArrayData(Map<K, V> resource, TripleConsumer<ObjectData, K, V> addObjectDataConsumer) {
        this();
        add(resource, addObjectDataConsumer);
    }

    public <K, V> MapArrayData(Map<K, V> resource, AddObjectDataMapConsumer<K, V> addObjectDataMapConsumer) {
        this();
        add(resource, addObjectDataMapConsumer);
    }

    public MapArrayData add(Map<String, String> resource) {
        return add(resource, (objectData, key, value) -> {
            objectData.add("code", key);
            objectData.add("value", value);
        });
    }

    public <K, V> MapArrayData add(Map<K, V> resource, TripleConsumer<ObjectData, K, V> addObjectDataConsumer) {
        if (resource == null || addObjectDataConsumer == null) {
            return this;
        }
        resource.forEach((k, v) -> addObjectDataConsumer.accept(arrayData.addObject(), k, v));
        return this;
    }

    public <K, V> MapArrayData add(Map<K, V> resource, AddObjectDataMapConsumer<K, V> addObjectDataConsumer) {
        if (resource == null || addObjectDataConsumer == null) {
            return this;
        }
        AtomicInteger atomicInteger = new AtomicInteger();
        resource.forEach((k, v) -> addObjectDataConsumer.addObject(arrayData.addObject(), atomicInteger.getAndIncrement(), k, v));
        return this;
    }

    public <T> Consumer<T> add(BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return t -> addObjectDataConsumer.accept(arrayData.addObject(), t);
    }

    public <T> Consumer<T> add(AddObjectDataConsumer<T> addObjectDataConsumer) {
        AtomicInteger atomicInteger = new AtomicInteger();
        return t -> addObjectDataConsumer.accept(arrayData.addObject(), atomicInteger.getAndIncrement(), t);
    }

    public <K, V> MapArrayData addArray(Map<K, V> map, TripleConsumer<ObjectData, K, V> addObjectDataConsumer) {
        tw.edu.ntub.imd.plearnet.util.json.array.ArrayData newArrayData = arrayData.addArray();
        map.forEach(((k, v) -> addObjectDataConsumer.accept(newArrayData.addObject(), k, v)));
        return this;
    }

    public <K, V> MapArrayData addArray(Map<K, V> map, AddObjectDataMapConsumer<K, V> addObjectDataConsumer) {
        tw.edu.ntub.imd.plearnet.util.json.array.ArrayData newArrayData = arrayData.addArray();
        AtomicInteger atomicInteger = new AtomicInteger();
        map.forEach((k, v) -> addObjectDataConsumer.addObject(newArrayData.addObject(), atomicInteger.getAndIncrement(), k, v));
        return this;
    }

    public <K, V> BiConsumer<K, V> addArrayAndAddObjectToNewArray(TripleConsumer<ObjectData, K, V> addObjectDataConsumer) {
        tw.edu.ntub.imd.plearnet.util.json.array.ArrayData newArrayData = arrayData.addArray();
        return (k, v) -> addObjectDataConsumer.accept(newArrayData.addObject(), k, v);
    }

    public <K, V> BiConsumer<K, V> addArrayAndAddObjectToNewArray(AddObjectDataMapConsumer<K, V> addObjectDataMapConsumer) {
        tw.edu.ntub.imd.plearnet.util.json.array.ArrayData newArrayData = arrayData.addArray();
        AtomicInteger atomicInteger = new AtomicInteger();
        return (k, v) -> addObjectDataMapConsumer.addObject(newArrayData.addObject(), atomicInteger.getAndIncrement(), k, v);
    }

    @Override
    public JsonNode getData() {
        return arrayData.getData();
    }

    @Override
    public String toString() {
        return arrayData.toString();
    }
}
