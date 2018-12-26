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

    /**
     * 列出群聊成员请求
     */
    Byte LIST_GROUP_MEMBERS_REQUEST = 9;
    /**
     * 列出群聊成员响应
     */
    Byte LIST_GROUP_MEMBERS_RESPONSE = 10;

    /**
     * 加入群聊请求
     */
    Byte JOIN_GROUP_REQUEST = 11;

    /**
     * 加入群聊响应
     */
    Byte JOIN_GROUP_RESPONSE = 12;

    /**
     * 退出群聊请求
     */
    Byte QUIT_GROUP_REQUEST = 13;

    /**
     * 退出群聊响应
     */
    Byte QUIT_GROUP_RESPONSE = 14;

    /**
     * 发送群消息请求
     */
    Byte GROUP_MESSAGE_REQUEST = 15;

    /**
     * 发送群消息响应
     */
    Byte GROUP_MESSAGE_RESPONSE = 16;

}
