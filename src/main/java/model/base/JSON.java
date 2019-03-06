package model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 */
public class JSON {
    static Logger logger = LoggerFactory.getLogger(JSON.class);
    static ObjectMapper mapper = new ObjectMapper();

    static {
        feature(mapper, false);
        init();
    }

    public static void feature(ObjectMapper mapper, boolean attach) {
        if(attach) {
            JSON.mapper = mapper;
        }
    }

    static void init() {
        // 输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 自定义
        SimpleModule module = new SimpleModule();
//        module.addDeserializer(IntTuple2.class, new Tuple2Deserializer());
        mapper.registerModule(module);

    }

    /**
     * 将任意的 JavaScript 值序列化成 JSON 字符串
     *
     * @param object 将要序列化成 JSON 字符串的值
     * @return JSON 字符串
     */
    public static String stringify(Object object) {
        return stringify(object, false);
    }

    /**
     * 将任意的 JavaScript 值序列化成 JSON 字符串
     *
     * @param object 将要序列化成 JSON 字符串的值
     * @param pretty true 美化输出格式
     * @return JSON 字符串
     */
    public static String stringify(Object object, boolean pretty) {
        try {
            if(pretty) {
                return mapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
            } else {
                return mapper().writeValueAsString(object);
            }
        } catch (Exception e) {
            logger.warn("JSON stringify ", e);
            return "{}";
        }
    }

    public static JsonNode parse(String text) {
        if (StringUtils.isBlank(text)) {
            return node();
        }

        text = text.trim();
        return text.startsWith("[")? JSON.parse(text, ArrayNode.class) : JSON.parse(text, ObjectNode.class);
    }

    public static <T> T parse(String text, Class<T> clazz) {
        if(StringUtils.isBlank(text)) return null;

        try {
            return mapper().readValue(text, clazz);
        } catch (Exception e) {

            throw new RuntimeException("JSON parse", e);
        }
    }

    public static ObjectNode parse(InputStream in) {
        return parse(in, ObjectNode.class);
    }

    public static <T> T parse(InputStream in, Class<T> clazz) {
        if(in == null) return null;

        try {
            return mapper().readValue(in, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON parse", e);
        }
    }

    public static <T> T parse(JsonNode node, Class<T> clazz) {
        Preconditions.checkNotNull(node, "JSON parse JsonNode is null");

        try {
            return mapper().treeToValue(node, clazz);
        } catch (Exception e) {
            throw new RuntimeException("JSON parse", e);
        }
    }

    /**
     * 将一个 字符串解析成一个 JSON 对象
     * @param text 字符串
     * @param elementClass 集合元素
     * @return List
     */
    public static <T> List<T> parseToList(String text, Class<T> elementClass) {
        if(StringUtils.isBlank(text)) {
            throw new RuntimeException("Json parse fail: text is empty");
        }

        try {
            return mapper().readValue(text, mapper().getTypeFactory().constructCollectionType(List.class, elementClass));
        } catch (Exception e) {
            throw new RuntimeException("Json parse fail", e);
        }
    }

    public static <T> List<T> parseToList(InputStream in, Class<T> elementClass) {
        if(in == null) {
            throw new RuntimeException("Json parse fail: input stream is null");
        }

        try {
            return mapper().readValue(in, mapper().getTypeFactory().constructCollectionType(List.class, elementClass));
        } catch (Exception e) {
            throw new RuntimeException("Json parse fail", e);
        }
    }

    /**
     * 将一个 字符串解析成一个 JSON 对象
     * @param text 字符串
     * @param keyClass key类型
     * @param valueClass value类型
     * @return Map
     */
    public static <K, V> Map<K, V> parseToMap(String text, Class<K> keyClass, Class<V> valueClass) {
        if (StringUtils.isBlank(text)) {
            return Collections.emptyMap();
        }
        try {
            return mapper().readValue(text, mapper().getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (Exception e) {
            logger.warn("JSON parse to map", e);
            return Collections.emptyMap();
        }
    }

    public static <K, V> Map<K, V> parseToMap(InputStream in, Class<K> keyClass, Class<V> valueClass) {
        if (in == null) {
            return Collections.emptyMap();
        }
        try {
            return mapper().readValue(in, mapper().getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (Exception e) {
            logger.warn("JSON parse to map", e);
            return Collections.emptyMap();
        }
    }

    public static ObjectNode convert(Map map) {
        return mapper().convertValue(map, ObjectNode.class);
    }

    public static <K, V> Map convert(TreeNode node, Class<K> keyClass, Class<V> valueClass) {
        return mapper().convertValue(node, mapper().getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
    }

    public static ObjectNode node() {
        return mapper().createObjectNode();
    }

    public static ArrayNode arrayNode() {
        return mapper().createArrayNode();
    }

    public static ObjectNode copy(Object destination) {
        return mapper().valueToTree(destination);
    }

    public static <T> ArrayNode copyArray(List<T> list) {
        return copyArray(list, JSON::copy);
    }

    public static <T> ArrayNode copyArray(List<T> list, Function<T, ObjectNode> convert) {
        ArrayNode an = arrayNode();
        for (T obj : list) {
            an.add(convert.apply(obj));
        }
        return an;
    }

    public static ObjectMapper mapper() {
        return mapper;
    }
}