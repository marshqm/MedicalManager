package model.statics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class EnumUtil {
    static Logger logger = LoggerFactory.getLogger(EnumUtil.class);

    static Map<Class<? extends Valuable>, Map<String, Valuable>> ALL = Maps.newHashMap();

   /* public static void init(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends Valuable>> valuableClasses = reflections.getSubTypesOf(Valuable.class);
        logger.info("EnumValuable size: {}", valuableClasses.size());
        for (Class<? extends Valuable> valuableClass : valuableClasses) {
            if(!valuableClass.isInterface()) {
                load(valuableClass);
            }
        }
    }
*/
    static <T extends Valuable> void load(Class<T> clazz) {
        if(clazz.isInterface()) {
            logger.warn("EnumValuable {} is interface", clazz.getName(), Valuable.class.getSimpleName());
            return;
        }
        if(!clazz.isEnum()) {
            throw new RuntimeException( clazz.getName() + " is not enum");
        }
        if(!Valuable.class.isAssignableFrom(clazz)) {
            throw new RuntimeException( clazz.getName() + " is not EnumValuable subclass");
        }
        if(ALL.containsKey(clazz)) {
            logger.warn("EnumValuable {} already init", clazz.getName(), Valuable.class.getSimpleName());
            return;
        }

        logger.debug("EnumValuable init: {}", clazz.getSimpleName());

        Map<String, Valuable> map = ALL.computeIfAbsent(clazz, c -> new TreeMap<>());

        T[] valuables = clazz.getEnumConstants();
        for (Valuable valuable: valuables) {
            String key = valuable.getValue();
            // 确保 key 不重复
            if(map.containsKey(key)) {
                throw new RuntimeException(
                        "EnumValuable(" + clazz.getName() + ") key duplicate: " + key
                                + "=" + ((Enum) map.get(key)).name());
            }
            map.put(valuable.getValue(), valuable);
        }
    }

    public static Set<Class<? extends Valuable>> getRegisterClass() {
        return ALL.keySet();
    }

    public static <T extends Valuable> Map<String, Valuable> get(Class<T> clazz) {
        return ALL.get(clazz);
    }

    /**
     * 转换为对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Valuable> Optional<T> of(String code, Class<T> clazz) {
        Map<String, Valuable> map = ALL.get(clazz);
        if(map == null) {
            return Optional.empty();
        }

        return Optional.ofNullable((T) map.get(code));
    }

    /**
     * 验证并转换为对象
     *
     * @param code 状态码
     * @param in 指定范围
     * @throws RuntimeException 状态码不存在，或不在指定范围内
     */
    public static <T extends Valuable> T ofWithVerify(String code, Class<T> clazz, T... in) {
        Optional<T> state = of(code, clazz);
        if(!state.isPresent()) {
            throw new RuntimeException(clazz.getSimpleName() + " not exist: " + code);
        }
        if(in != null && in.length > 0) {
            boolean include = false;
            for (T cs : in) {
                if(state.get() == cs) {
                    include = true; break;
                }
            }

        }
        return state.get();
    }
}
