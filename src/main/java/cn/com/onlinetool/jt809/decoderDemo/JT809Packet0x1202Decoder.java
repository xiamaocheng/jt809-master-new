package cn.com.onlinetool.jt809.decoderDemo;


import cn.com.onlinetool.jt809.jt.Decoder;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;



/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/23 15:21
 * @Version 1.0
 * @Describe:
 */
public class JT809Packet0x1202Decoder {
    private static Logger log = LoggerFactory.getLogger(JT809Packet0x1202Decoder.class);

    public JT809BasePacket decoder(byte[] bytes) throws Exception {
        JT809Packet0x1202 jt809Packet0x1202 = new JT809Packet0x1202();
        ByteBuf byteBuf = PacketDecoderUtils.baseDecoder(bytes, jt809Packet0x1202);
        packetDecoder(byteBuf,jt809Packet0x1202);
        return jt809Packet0x1202;
    }

    private void packetDecoder(ByteBuf byteBuf, JT809Packet0x1202 packet) throws Exception{
        ByteBuf msgBodyBuf = null;
        if (packet.getEncryptFlag() == Const.EncryptFlag.NO) {
            msgBodyBuf = PacketDecoderUtils.getMsgBodyBuf(byteBuf);
        } else {

            msgBodyBuf = null;
            return;
        }
        // 车牌号
        byte [] vehicleNoBytes = new byte[21];
        msgBodyBuf.readBytes(vehicleNoBytes);
        packet.setVehicleNo(new String(vehicleNoBytes, Charset.forName("GBK")));
        // 车辆颜色
        packet.setVehicleColor(msgBodyBuf.readByte());
        // 子业务类型标识
        packet.setDataType(msgBodyBuf.readShort());
        // 如果不是定位的数据，抛出空指针错误,解码适配器会对空指针错误做处理。
//        if (packet.getDataType() != Const.SubBusinessDataType.UP_EXG_MSG_REAL_LOCATION ) {
//            throw new NullPointerException();
//        }
        // 后续数据长度
        packet.setDataLength(msgBodyBuf.readInt());
        // 经纬度信息是否按国标进行加密
        packet.setExcrypt(msgBodyBuf.readByte());
        if (packet.getExcrypt() == Const.EncryptFlag.YES ){

        }
        // 跳过时间
//        msgBodyBuf.skipBytes(7);
        int day = Byte.toUnsignedInt(msgBodyBuf.readByte());
        int month = Byte.toUnsignedInt(msgBodyBuf.readByte());
        packet.setDate(LocalDate.of(msgBodyBuf.readShort(),month,day));
        packet.setTime(LocalTime.of(Byte.toUnsignedInt(msgBodyBuf.readByte()),Byte.toUnsignedInt(msgBodyBuf.readByte()),Byte.toUnsignedInt(msgBodyBuf.readByte())));
        // 经纬度
        packet.setLon(msgBodyBuf.readInt());
        packet.setLat(msgBodyBuf.readInt());
        // 速度
        packet.setVec1(msgBodyBuf.readShort());
        // 行驶记录速度
        packet.setVec2(msgBodyBuf.readShort());
        // 车辆当前总里程数
        packet.setVec3(msgBodyBuf.readInt());
        // 方向
        packet.setDirection(msgBodyBuf.readShort());
        // 海拔
        packet.setAltitude(msgBodyBuf.readShort());
        // 车辆状态
        packet.setState(msgBodyBuf.readInt());
        // 报警状态
        packet.setAlarm(msgBodyBuf.readInt());
    }



}
