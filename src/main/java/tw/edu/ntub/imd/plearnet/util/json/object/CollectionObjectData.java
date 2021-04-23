package tw.edu.ntub.imd.plearnet.util.json.object;

import com.fasterxml.jackson.databind.JsonNode;
import tw.edu.ntub.imd.plearnet.dto.CodeEntry;
import tw.edu.ntub.imd.plearnet.util.function.AddObjectDataConsumer;
import tw.edu.ntub.imd.plearnet.util.json.ResponseData;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CollectionObjectData implements ResponseData {
    private final tw.edu.ntub.imd.plearnet.util.json.object.ObjectData objectData;

    public CollectionObjectData() {
        this(new tw.edu.ntub.imd.plearnet.util.json.object.ObjectData());
    }

    protected CollectionObjectData(tw.edu.ntub.imd.plearnet.util.json.object.ObjectData objectData) {
        this.objectData = objectData;
    }

    public CollectionObjectData add(String key, Collection<? extends CodeEntry> resource) {
        return add(key, resource, (objectData, t) -> {
            objectData.add("code", t.getCode());
            objectData.add("value", t.getValue());
        });
    }

    public <T> CollectionObjectData add(String key, Collection<T> resource, BiConsumer<tw.edu.ntub.imd.plearnet.util.json.object.ObjectData, T> addObjectDataConsumer) {
        objectData.addArray(key).addCollectionToThis().add(resource, addObjectDataConsumer);
        return this;
    }

    public <T> CollectionObjectData add(String key, Collection<T> resource, AddObjectDataConsumer<T> addObjectDataConsumer) {
        objectData.addArray(key).addCollectionToThis().add(resource, addObjectDataConsumer);
        return this;
    }

    public <T extends CodeEntry> Consumer<T> addArrayAndAddObjectToNewArray(String key) {
        return addArrayAndAddObjectToNewArray(key, (objectData1, t) -> {
            objectData1.add("code", t.getCode());
            objectData1.add("value", t.getValue());
        });
    }

    public <T> Consumer<T> addArrayAndAddObjectToNewArray(String key, BiConsumer<tw.edu.ntub.imd.plearnet.util.json.object.ObjectData, T> addObjectDataConsumer) {
        ArrayData arrayData = objectData.addArray(key);
        return t -> addObjectDataConsumer.accept(arrayData.addObject(), t);
    }

    public <T> Consumer<T> addArrayAndAddObjectToNewArray(String key, AddObjectDataConsumer<T> addObjectDataConsumer) {
        ArrayData arrayData = objectData.addArray(key);
        AtomicInteger index = new AtomicInteger();
        return t -> addObjectDataConsumer.addObject(arrayData.addObject(), index.getAndIncrement(), t);
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
