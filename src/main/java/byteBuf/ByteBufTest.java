package byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(9, 100);
    }
}
