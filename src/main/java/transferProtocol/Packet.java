package transferProtocol;


import lombok.Data;

/**
 * @author dingzhaolei
 * @date 2018/12/14 13:15
 * 通信协议基类
 */
@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;
    /**
     * 指令
     */
    public abstract Byte getCommand();
}
