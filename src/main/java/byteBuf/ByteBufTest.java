package byteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("buffer(9, 100)", buf);

        buf.writeBytes(new byte[]{1,2,3,4});
        print("new byte[]{1,2,3,4}", buf);

        buf.writeInt(32);
        print("writeInt(32)", buf);

        buf.writeDouble(89.0);
        print("writeDouble(89.0)", buf);

        System.out.println("getByte(1)"+buf.getByte(1));
        System.out.println("getInt(3)"+buf.getShort(3));
        System.out.println("getDouble(5)"+buf.getDouble(5));

        // read 方法改变读指针
        while (buf.readableBytes()!=0){
            System.out.printf(buf.readByte()+" ");
        }

        //print("readBytes(" + dst.length + ")", buf);
    }

    public static void print(String action, ByteBuf byteBuf){
        System.out.println("after ==============="+action+"======");
        System.out.println("maxCapacity="+byteBuf.maxCapacity());
        System.out.println("capacity="+byteBuf.capacity());
        System.out.println("readerIndex="+byteBuf.readerIndex());
        System.out.println("readableBytes="+byteBuf.readableBytes());
        System.out.println("isReadable="+byteBuf.isReadable());
        System.out.println("writerIndex="+byteBuf.writerIndex());
        System.out.println("writableBytes="+byteBuf.writableBytes());
        System.out.println("isWritable="+byteBuf.isWritable());
        System.out.println("maxWritableBytes="+byteBuf.maxWritableBytes());
        System.out.println("================end===============");

    }
}
