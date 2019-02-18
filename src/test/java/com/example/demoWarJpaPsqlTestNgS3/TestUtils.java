package com.example.demoWarJpaPsqlTestNgS3;

import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class TestUtils {

    private static final Logger log = LoggerFactory.getLogger(TestUtils.class);

    // public static String getStringFromJsonObj(JSONObject obj, String key) {
    // return obj.getString(key);
    // }
    //
    // public static JSONObject responseObjToJsonObj(Response response) {
    // return new JSONObject(responseObjToString(response));
    // }
    //
    // public static String responseObjToString(Response response) {
    // return response.readEntity(String.class);
    // }
    //
    public static JSONObject stringToJsonObject(String string) throws JSONException {
        return new JSONObject(string);
    }

    public static <T> T fromJsonToEntityDto(String jsonAsString, Class<T> cls) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JSONObject jsonObject = stringToJsonObject(jsonAsString);
            JSONObject returnObj = jsonObject.optJSONObject("return");
            if (returnObj != null) {
                return mapper.readValue(returnObj.toString(), cls);
            }
        } catch (IOException | JSONException e) {
            log.debug("Something bad has happened while trying to convert JSON to String. Returning null object", e);
        }
        return null;
    }

    public static <T> List<T> fromJsonToEntityDtoList(String jsonAsString, Class<T> clz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JSONObject jsonObject = new JSONObject(jsonAsString);
            JSONArray returnObj = jsonObject.getJSONArray("return");
            TypeFactory typeFactory = TypeFactory.defaultInstance();
            CollectionType collectionType = typeFactory.constructCollectionType(List.class, clz);
            return mapper.readValue(returnObj.toString(), collectionType);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
