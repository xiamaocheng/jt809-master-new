package cn.com.onlinetool.jt809.handle;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/21 20:29
 * @Version 1.0
 * @Describe:
 */
public class JT809ServerInitialzer extends ChannelInitializer<NioSocketChannel> {

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {

//        ch.pipeline().addLast(new JT809IdleStateHandler());
//        ch.pipeline().addLast(new JT809AdapterHandle());
//        ch.pipeline().addLast(new JT809DecoderAdapter());
//        ch.pipeline().addLast(new JT809HeartbeatHandle());
        ch.pipeline().addLast(new JT809LoginHandle());
//        ch.pipeline().addLast(new JT809Packet0x1202Handle());
//        ch.pipeline().addLast(new JT809EncodeAdapter());
    }
}
