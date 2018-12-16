package attribute;

import io.netty.util.AttributeKey;

/**
 * @author killpie
 * @date 2018/12/15 23:27
 **/
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
