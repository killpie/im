package protocol.command;

/**
 * @author dingzhaolei
 * @date 2018/12/14 13:24
 **/
public interface Command {
    /**
     *    登录请求指令
     */
    Byte LOGIN_REQUEST = 1;
    /**
     *    登录响应指令
     */
    Byte LOGIN_RESPONSE = 2;
    /**
     *    消息发送请求指令
     */
    Byte MESSAGE_REQUEST = 3;
    /**
     *    消息发送响应指令
     */
    Byte MESSAGE_RESPONSE = 4;

    /**
     * 登出请求
     */
    Byte LOGOUT_REQUEST = 5;

    /**
     * 登出响应
     */
    Byte LOGOUT_RESPONSE = 6;

    /**
     * 创建群聊请求
     */
    Byte CREATE_GROUP_REQUEST = 7;

    /**
     * 创建群聊响应
     */
    Byte CREATE_GROUP_RESPONSE = 8;

}
