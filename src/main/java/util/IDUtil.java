package util;

import java.util.UUID;

/**
 * @author dingzhaolei
 * @date 2018/12/24 15:01
 **/
public class IDUtil {
    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
