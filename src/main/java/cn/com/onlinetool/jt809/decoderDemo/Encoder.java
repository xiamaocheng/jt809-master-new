package cn.com.onlinetool.jt809.decoderDemo;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 10:56
 * @Version 1.0
 * @Describe:
 */
public interface Encoder {
    /** 编码*/
    void encode(ChannelHandlerContext ctx, JT809BasePacket packet, ByteBuf out);
}
