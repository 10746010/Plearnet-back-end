package tw.edu.ntub.imd.plearnet.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import tw.edu.ntub.birc.common.exception.ProjectException;
import tw.edu.ntub.imd.plearnet.dto.CodeEntry;
import tw.edu.ntub.imd.plearnet.util.function.AddObjectDataConsumer;
import tw.edu.ntub.imd.plearnet.util.function.AddObjectDataMapConsumer;
import tw.edu.ntub.imd.plearnet.util.function.TripleConsumer;
import tw.edu.ntub.imd.plearnet.util.json.ResponseData;
import tw.edu.ntub.imd.plearnet.util.json.array.ArrayData;
import tw.edu.ntub.imd.plearnet.util.json.array.CollectionArrayData;
import tw.edu.ntub.imd.plearnet.util.json.array.MapArrayData;
import tw.edu.ntub.imd.plearnet.util.json.object.ObjectData;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 回傳標準格式
 * {
 * "result": Boolean,
 * "errorCode": String,
 * "message": String,
 * "data": Object/Array
 * }
 */
@Log4j2
public class ResponseEntityBuilder {
    private final ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
    private boolean success;
    private ProjectException ProjectException;
    private String message;
    private ResponseData responseData;

    private ResponseEntityBuilder(ProjectException ProjectException) {
        this(false);
        this.ProjectException = ProjectException;
        this.message = ProjectException.getMessage();
    }

    private ResponseEntityBuilder(boolean success) {
        this.success = success;
    }

    public static ResponseEntityBuilder success() {
        return new ResponseEntityBuilder(true);
    }

    public static ResponseEntityBuilder error() {
        return new ResponseEntityBuilder(false);
    }

    public static ResponseEntityBuilder error(@NonNull ProjectException projectException) {
        log.error("發生錯誤！", projectException);
        return new ResponseEntityBuilder(projectException);
    }

    public ResponseEntityBuilder result(boolean isSuccess) {
        success = isSuccess;
        return this;
    }

    public ResponseEntityBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntityBuilder emptyObject() {
        return data(new ObjectData());
    }

    public ResponseEntityBuilder emptyArray() {
        return data(new ArrayData());
    }

    public ResponseEntityBuilder data(Collection<? extends CodeEntry> resource) {
        return data(new CollectionArrayData(resource));
    }

    public <T> ResponseEntityBuilder data(Collection<T> resource, BiConsumer<ObjectData, T> addObjectDataConsumer) {
        return data(new CollectionArrayData(resource, addObjectDataConsumer));
    }

    public <T> ResponseEntityBuilder data(Collection<T> resource, AddObjectDataConsumer<T> addObjectDataConsumer) {
        return data(new CollectionArrayData(resource, addObjectDataConsumer));
    }

    public ResponseEntityBuilder data(Map<String, String> resource) {
        return data(new MapArrayData(resource));
    }

    public <K, V> ResponseEntityBuilder data(Map<K, V> resource, TripleConsumer<ObjectData, K, V> addObjectDataConsumer) {
        return data(new MapArrayData(resource, addObjectDataConsumer));
    }

    public <K, V> ResponseEntityBuilder data(Map<K, V> resource, AddObjectDataMapConsumer<K, V> addObjectDataMapConsumer) {
        return data(new MapArrayData(resource, addObjectDataMapConsumer));
    }

    public ResponseEntityBuilder data(ResponseData responseData) {
        this.responseData = responseData;
        return this;
    }

    public ResponseEntityBuilder addHeader(String name, String... valueArray) {
        bodyBuilder.header(name, valueArray);
        return this;
    }

    public ResponseEntity<String> build() {
        return bodyBuilder.body(buildJSONString());
    }

    public String buildJSONString() {
        try {
            ObjectData result = new ObjectData()
                    .add("result", success)
                    .add("errorCode", ProjectException != null ? ProjectException.getErrorCode() : "")
                    .add("message", message)
                    .replace("data",
                            responseData != null ?
                                    responseData.getData() :
                                    new ObjectData().getData()
                    );
            String body = ResponseUtils.createMapper().writeValueAsString(result.getData());
            System.out.println("Response JSON = " + body);
            return body;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{\"result\": false, \"errorCode\": \"Server - JsonProcessError\", \"message\": \"" +
                    e.getMessage() +
                    "\", \"data\": " +
                    (responseData.getData() instanceof ArrayNode ? "[]" : "{}") +
                    "}";
        }
    }
}
