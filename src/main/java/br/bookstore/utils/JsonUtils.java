package br.bookstore.utils;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonUtils {

    public static <T> List<T> toList(String json, Class<T> object) {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            var hashMap = jsonb.fromJson(json, Map[].class);
            return Arrays.stream(hashMap).map(map -> toObject(toJson(map, jsonb), object, jsonb)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static String toJson(Object object, Jsonb jsonb) {
        return jsonb.toJson(object);
    }

    private static <T> T toObject(String json, Class<T> object, Jsonb jsonb) {
        return jsonb.fromJson(json, object);
    }
}
