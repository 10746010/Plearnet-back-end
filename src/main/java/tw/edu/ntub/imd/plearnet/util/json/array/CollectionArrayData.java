package tw.edu.ntub.imd.plearnet.util.json.array;

import com.fasterxml.jackson.databind.JsonNode;
import tw.edu.ntub.imd.plearnet.dto.CodeEntry;
import tw.edu.ntub.imd.plearnet.util.function.AddObjectDataConsumer;
import tw.edu.ntub.imd.plearnet.util.json.ResponseData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CollectionArrayData implements ResponseData {
    private final ArrayData arrayData;

    public CollectionArrayData() {
        this(new ArrayData());
    }

    public CollectionArrayData(Collection<? extends CodeEntry> resource) {
        this();
        add(resource);
    }

    public <T> CollectionArrayData(Collection<T> resourceList, BiConsumer<ObjectData, T> addObjectDataConsumer) {
        this();
        add(resourceList, addObjectDataConsumer);
    }

    public <T> CollectionArrayData(Collection<T> resourceList, AddObjectDataConsumer<T> addObjectDataConsumer) {
        this();
        add(resourceList, addObjectDataConsumer);
    }

    protected CollectionArrayData(ArrayData arrayData) {
        this.arrayData = arrayData;
    }

    public CollectionArrayData add(Collection<? extends CodeEntry> resource) {
        if (resource == null) {
            return this;
        }
        add(resource, (objectData, t) -> {
            objectData.add("code", t.getCode());
            objectData.add("value", t.getValue());
        });
        return this;
    }

    public <T> CollectionArrayData add(Collection<T> resourceList, BiConsumer<ObjectData, T> addObjectDataConsumer) {
        if (resourceList == null || addObjectDataConsumer == null) {
            return this;
        }
        for (T t : resourceList) {
            addObjectDataConsumer.accept(arrayData.addObject(), t);
        }
        return this;
    }

    public <T> CollectionArrayData add(Collection<T> resourceList, AddObjectDataConsumer<T> addObjectDataConsumer) {
        if (resourceList == null || addObjectDataConsumer == null) {
            return this;
        }
        AtomicInteger atomicInteger = new AtomicInteger();
        for (T t : resourceList) {
            addObjectDataConsumer.addObject(arrayData.addObject(), atomicInteger.getAndIncrement(), t);
        }
        return this;
    }

    public <T> CollectionArrayData addArray(Collection<T> resource, BiConsumer<ObjectData, T> addObjectDataConsumer) {
        ArrayData newArrayData = arrayData.addArray();
        for (T t : resource) {
            addObjectDataConsumer.accept(newArrayData.addObject(), t);
        }
        return this;
    }

    public <T> CollectionArrayData addArray(Collection<T> resource, AddObjectDataConsumer<T> addObjectDataConsumer) {
        ArrayData newArrayData = arrayData.addArray();
        AtomicInteger atomicInteger = new AtomicInteger();
        for (T t : resource) {
            addObjectDataConsumer.addObject(newArrayData.addObject(), atomicInteger.getAndIncrement(), t);
        }
        return this;
    }

    /**
     * @param addObjectDataConsumer 會傳入新的ObjectData與{@link java.util.List#forEach}迭代對象
     * @param <T>                   集合({@link Collection <T>})類型
     * @return 實作會呼叫 {@link BiConsumer#accept} 而讓使用者能將資料放入{@link ObjectData}中
     * @see ObjectData
     * @see BiConsumer
     * @see Consumer
     * @see java.util.List#forEach(Consumer)
     */
    public <T> Consumer<T> addArrayAndAddObjectToNewArray(BiConsumer<ObjectData, T> addObjectDataConsumer) {
        ArrayData newArrayData = arrayData.addArray();
        return t -> addObjectDataConsumer.accept(newArrayData.addObject(), t);
    }

    public <T> Consumer<T> addArrayAndAddObjectToNewArray(AddObjectDataConsumer<T> addObjectDataConsumer) {
        ArrayData newArrayData = arrayData.addArray();
        AtomicInteger atomicInteger = new AtomicInteger();
        return t -> addObjectDataConsumer.addObject(newArrayData.addObject(), atomicInteger.getAndIncrement(), t);
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
