package cn.com.onlinetool.jt809.decoderDemo2;


import cn.com.onlinetool.jt809.decoderDemo.Const;
import cn.com.onlinetool.jt809.decoderDemo.JT809Packet0x1202;
import cn.com.onlinetool.jt809.decoderDemo.PacketDecoderUtils;
import io.netty.buffer.ByteBuf;
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
//        packet.setCode( msgBodyBuf.readByte());
//        packet.setCode(msgBodyBuf.readInt());

        //2.解析字符串
        String  str = new String( msgBodyBuf.array());
        packet.setName(str);




        return  packet;

    }





}
