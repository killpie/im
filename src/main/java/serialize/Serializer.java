package serialize;

import serialize.impl.JSONSerializerAlgorithm;

/**
 * @author dingzhaolei
 * @date 2018/12/14 14:16
 * 序列化接口
 **/
public interface Serializer {
    /**
     *  序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * java对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转化为java对象
     */
    <T> T deSerialize(Class<T> clazz, byte[] bytes);
    //默认的序列化算法
    Serializer DEFAULT = new JSONSerializerAlgorithm();
}
