package tw.edu.ntub.imd.plearnet.util.json;

import com.fasterxml.jackson.databind.JsonNode;

@FunctionalInterface
public interface ResponseData {
    JsonNode getData();
}
