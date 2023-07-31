package cn.com.onlinetool.jt809.decoderDemo2;


import cn.com.onlinetool.jt809.decoderDemo.Const;
import cn.com.onlinetool.jt809.decoderDemo.JT809Packet0x1202;
import cn.com.onlinetool.jt809.decoderDemo.PacketDecoderUtils;
import io.netty.buffer.ByteBuf;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;


/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 15:21
 * @Version 1.0
 * @Describe:
 */
public class JT809Packet0x0000Decoder {
    private static Logger log = LoggerFactory.getLogger(JT809Packet0x0000Decoder.class);

    public JT809Packet0x0000 decoder(byte[] bytes) throws Exception {
        JT809Packet0x0000 jt809Packet0x0000 = new JT809Packet0x0000();
        ByteBuf byteBuf = PacketDecoderUtils.baseDecoder(bytes, jt809Packet0x0000);
        JT809Packet0x0000 pkt = packetDecoder(byteBuf, jt809Packet0x0000);
        return pkt;
    }

    private JT809Packet0x0000 packetDecoder(ByteBuf byteBuf, JT809Packet0x0000 packet) throws Exception{
        ByteBuf msgBodyBuf = null;
        if (packet.getEncryptFlag() == Const.EncryptFlag.NO) {
            msgBodyBuf = PacketDecoderUtils.getMsgBodyBuf(byteBuf);
        } else {

            msgBodyBuf = null;

        }
//        // 车牌号
//        byte [] vehicleNoBytes = new byte[21];
//        msgBodyBuf.readBytes(vehicleNoBytes);
//



        //----------------------数据报文解析--------------------------------------

        //1.解析数字
//          msgBodyBuf.readByte();
//       // packet.setCode( msgBodyBuf.readByte());
//        packet.setCode(msgBodyBuf.readInt());

        //2.解析字符串
       /* String  str = new String( msgBodyBuf.array());
        packet.setName(str);*/



//        String  str = new String( msgBodyBuf.readBytes(4).array());

//        byte[] data = new byte[8];

//        packet.setName();
//        String  str = new String( msgBodyBuf.readBytes(data).array());

        ByteBuf aa = msgBodyBuf.readBytes(6);
//        packet.setName(convertByteBufToString(aa));
         packet.setName(aa.toString(CharsetUtil.UTF_8));

        //3.解析出航班
        /*String  str1 = new String( msgBodyBuf.readBytes(4).array());
        packet.setFlight(str1);*/
//        byte[] data1 = new byte[4];
//        packet.setFlight(msgBodyBuf.readBytes(data1).toString());

        ByteBuf bb = msgBodyBuf.readBytes(4);
        packet.setFlight(convertByteBufToString(bb));

        //4.解析出remoark
      /*  String  str12= new String(  msgBodyBuf.readBytes(8).array());
        packet.setRemark(str12);*/
//        byte[] data2 = new byte[8];
//        packet.setRemark(msgBodyBuf.readBytes(data2).toString());

        ByteBuf CC = msgBodyBuf.readBytes(12);
        packet.setRemark(convertByteBufToString(CC));



        return  packet;

    }


    public String convertByteBufToString(ByteBuf buf) {
        String str;
        if(buf.hasArray()) { // 处理堆缓冲区
            str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
        } else { // 处理直接缓冲区以及复合缓冲区
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            str = new String(bytes, 0, buf.readableBytes());
        }
        return str;
    }


}
