package cn.linc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 
 * @author xiechenglin
 * json转化工具类
 *
 */

public class JsonSerializeUtil {

	private static ConcurrentHashMap objWriterCache = new ConcurrentHashMap();
	private static ConcurrentHashMap objWriterCacheNoType = new ConcurrentHashMap();
	private static ConcurrentHashMap objWriterCacheNoNull = new ConcurrentHashMap();
	private static ObjectMapper mapper = new ObjectMapper();
	private static ObjectMapper mapperNoType = new ObjectMapper();
	private static ObjectMapper mapperNoNull = new ObjectMapper();
    
	public JsonSerializeUtil() {
	}

	// 将objectMapper对象进行缓存，如果存在就进行返回，如果不存在就创建
	private static ObjectWriter getObjWriter(Class serializationView) {
		if (objWriterCache.get(serializationView) != null) {
			return (ObjectWriter) objWriterCache.get(serializationView);
		} else {
			ObjectWriter temp = mapper.writerWithView(serializationView);
			objWriterCache.put(serializationView, temp);
			return temp;
		}
	}

	private static ObjectWriter getObjWriterNoType(Class serializationView) {
		if (objWriterCacheNoType.get(serializationView) != null) {
			return (ObjectWriter) objWriterCacheNoType.get(serializationView);
		} else {
			ObjectWriter temp = mapperNoType.writerWithView(serializationView);
			objWriterCacheNoType.put(serializationView, temp);
			return temp;
		}
	}

	private static ObjectWriter getObjWriterNoNull(Class serializationView) {
		if (objWriterCacheNoNull.get(serializationView) != null) {
			return (ObjectWriter) objWriterCacheNoNull.get(serializationView);
		} else {
			// 包含非null值，只出不是null的值
			mapperNoNull
					.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
			ObjectWriter temp = mapperNoNull.writerWithView(serializationView);
			objWriterCacheNoNull.put(serializationView, temp);
			return temp;
		}
	}

	/*
	 * 该方法会将整个实体类的所有属性都会出成json文件，包含本身的权限定类名
	 * 如：["cn.linc.json.domain.Student",{"name":"xcl","sex":"nv"}]
	 */
	public static String jsonSerializer(Object originalObject) throws Exception {
		String json;
		// json中默认类型会自动匹配给非最终类型除了一些简单数据类型。
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		ObjectWriter objectWriter = getObjWriter(originalObject.getClass());
		json = objectWriter.writeValueAsString(originalObject);
		return json;

	}

	/*
	 * 该方法会将实体类的属性出出来，但如果为空将设置为null如：{"name":null,"sex":"nv"}
	 */
	public static String jsonSerializerNoType(Object originalObject)
			throws Exception {
		String json;
		ObjectWriter objectWriter = getObjWriterNoType(originalObject
				.getClass());
		json = objectWriter.writeValueAsString(originalObject);
		return json;

	}

	/*
	 * 该方法会将实体类的属性出出来，如果为空将不会出出来 如：{"sex":"nv"}
	 */
	public static String jsonSerializerNoNull(Object originalObject)
			throws Exception {
		String json;
		ObjectWriter objectWriter = getObjWriterNoNull(originalObject
				.getClass());
		json = objectWriter.writeValueAsString(originalObject);
		return json;
	}

	/*
	 * 读取：如：["cn.linc.json.domain.Student",{"name":"xcl","sex":"nv"}]
	 */
	public static Object jsonReSerializer(String jsonStr, Class clazz)
			throws Exception {
		Object object;
		mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		ObjectReader reader = mapper.reader(clazz);
		object = reader.readValue(jsonStr);
		return object;
	}

	/*
	 * 按class反序列化
	 */
	public static Object jsonReSerializerNoType(String jsonStr, Class clazz)
			throws Exception {
		Object object = null;
		ObjectReader reader = null;
		try {
			reader = mapperNoType.reader(clazz);
			object = reader.readValue(jsonStr);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return object;

	}

	/*
	 * 按class反序列化
	 */
	public static Object jsonReSerializerNoNull(String jsonStr, Class clazz)
			throws Exception {
		Object object;
		ObjectReader reader = mapperNoNull.reader(clazz);
		object = reader.readValue(jsonStr);
		return object;

	}
	public static Map<String, Object> json2Map(String json) throws Exception{
		ObjectReader reader = mapper.reader(Map.class);
		 Map<String, Object> map = reader.readValue(json);
		 return map;
	}
	
	
	public static String map2Json(Map<String, Object> map) throws Exception{
		ObjectWriter objectWriter = getObjWriterNoNull(map.getClass());
		return objectWriter.writeValueAsString(map);
	}

	/*
	 * 按类型反序列化
	 */
	public static Object jsonReSerializerNoType(String jsonStr, Type type)
			throws Exception {
		Object object;
		ObjectReader reader = mapperNoType.reader(TypeFactory
				.defaultInstance().constructType(type));
		object = reader.readValue(jsonStr);
		return object;
	}

	/*
	 * 泛型类型反序列化 List<Student> stu = (List<Student>)
	 * JsonSerializeUtil.jsonReSerializerNoType(json, new
	 * TypeReference<List<Student>>() { } );
	 */
	public static Object jsonReSerializerNoType(String jsonStr,
			TypeReference typeReference) throws Exception {
		Object object;
		ObjectReader reader = mapperNoType.reader(typeReference);
		object = reader.readValue(jsonStr);
		return object;
	}

	/*
	 * * constructParametricType
	 * @param collectionClass 泛型的Collection
	 * @param elementClasses 元素类
	 * @return JavaType Java类型
	 */
	public static Object jsonReSerializerNoType(String jsonStr, Class type,
			Class parametric) throws Exception {
		JavaType t = mapperNoType.getTypeFactory().constructParametricType(
				type, new Class[] { parametric });
		return mapperNoType.readValue(jsonStr, t);
	}

	/*
	 * 嵌套类型的json数据类型的转化 实例：方法参数解释看上方constructParametricType Set<List<Student>>
	 * ss = new HashSet<List<Student>>(); Class[] classes = new
	 * Class[]{List.class,Student.class}; Set<List<Student>> sets =
	 * (Set<List<Student>>) JsonSerializeUtil.jsonReSerializerNest(json,
	 * Set.class, classes);
	 */

	public static Object jsonReSerializerNest(String jsonStr, Class type,
			Class parametric[]) throws Exception, IOException {
		JavaType t = getJacaType(type, parametric);
		return mapperNoType.readValue(jsonStr, t);
	}

	private static JavaType getJacaType(Class cls, Class parametric[]) {
		if (parametric == null || parametric.length == 0)
			throw new RuntimeException("序列化方法调用错误导致字符串反序列化失败，请确认！");
		if (parametric.length == 1)
			return mapperNoType.getTypeFactory().constructParametricType(cls,
					new Class[] { parametric[0] });
		JavaType type = null;
		for (int i = parametric.length; i >= 2; i--) {
			if (type == null) {
				type = mapperNoType.getTypeFactory().constructParametricType(
						parametric[i - 2], new Class[] { parametric[i - 1] });
				continue;
			}
			if (i <= parametric.length - 2)
				type = mapperNoType.getTypeFactory().constructParametricType(
						parametric[i - 1], new JavaType[] { type });
		}
		return mapperNoType.getTypeFactory().constructParametricType(cls,
				new JavaType[] { type });
	}

}
