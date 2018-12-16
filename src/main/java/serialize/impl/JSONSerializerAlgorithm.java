package serialize.impl;

import com.alibaba.fastjson.JSON;
import serialize.Serializer;
import serialize.SerializerAlgorithm;

/**
 * @author dingzhaolei
 * @date 2018/12/14 17:17
 **/
public class JSONSerializerAlgorithm implements Serializer {

    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    public byte[] serialize(Object object) {
        byte[] result = JSON.toJSONBytes(object);
        return result;
    }

    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        T t = JSON.parseObject(bytes, clazz);
        return t;
    }
}
