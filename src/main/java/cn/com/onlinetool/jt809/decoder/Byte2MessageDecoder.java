package cn.com.onlinetool.jt809.decoder;


import cn.com.onlinetool.jt809.constants.JT809MessageConstants;
import cn.com.onlinetool.jt809.decoderDemo.JT809Packet0x1202Decoder;
import cn.com.onlinetool.jt809.util.ByteArrayUtil;
import cn.com.onlinetool.jt809.util.PacketUtil;
import cn.com.onlinetool.jt809.util.ParseUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author choice
 * @description: 消息解码
 * @date 2018-12-27 14:39
 */
@Slf4j
@Service
public class Byte2MessageDecoder {
    private static Map<String, byte[]> cache = new HashMap<String, byte[]>();

    public void decode(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        String channelKey = ctx.channel().remoteAddress().toString();

        //判断是否有可读的字节
        if (msg.readableBytes() <= 0) {
            return;
        }

        //读取缓冲区数据
        byte[] readDatas = new byte[msg.readableBytes()];
        msg.readBytes(readDatas);
        log.info("接收到数据包, packetLen : {}, packet : {}", readDatas.length, ByteArrayUtil.bytes2HexStr(readDatas));

        //拼接缓存数据
        byte[] cacheDatas = cache.get(channelKey);
        if (null != cacheDatas) {
            readDatas = ByteArrayUtil.append(cacheDatas, readDatas);
            cache.remove(channelKey);
            log.info("拼接后的数据包：{}", ByteArrayUtil.bytes2HexStr(readDatas));
        }

        //校验数据头
//        if (!PacketUtil.checkHeadFlag(readDatas)) {
//            //防止极端情况，请求头校验不通过的情况 丢掉本包数据 同时 丢掉上一包缓存的剩余数据
//            //防止上一包剩余数据不包含数据起始符 会导致拼接后的数据包一直校验不通过
//            cache.remove(channelKey);
//            log.warn("数据包标识符验证失败 : {}", ByteArrayUtil.bytes2HexStr(readDatas));
//            return;
//        }

        //数据转义
        String dataStr = ByteArrayUtil.bytes2FullHexStr(readDatas);
        dataStr = dataStr.replaceAll("0x5a0x01", "0x5b");
        dataStr = dataStr.replaceAll("0x5a0x02", "0x5a");
        dataStr = dataStr.replaceAll("0x5e0x01", "0x5d");
        dataStr = dataStr.replaceAll("0x5e0x02", "0x5e");
        readDatas = ByteArrayUtil.fullHexStr2Bytes(dataStr);
        log.info("转义后的数据包：{}", ByteArrayUtil.bytes2HexStr(readDatas));

        //如果数据小于一整包数据的最小长度
//        if (readDatas.length < JT809MessageConstants.MSG_MIN_LEN) {
//            log.warn("数据长度小于整包数据最小长度，缓存数据：{}", ByteArrayUtil.bytes2HexStr(readDatas));
//            cache.put(channelKey, readDatas);
//            return;
//        }
//
//        //判断是否有完整数据包，没有直接缓存
//        int packetLen = PacketUtil.getPacketLen(readDatas);
//        if (readDatas.length < packetLen) {
//            log.warn("数据长度小于整包数据长度，缓存数据：{}", ByteArrayUtil.bytes2HexStr(readDatas));
//            cache.put(channelKey, readDatas);
//            return;
//        }

        //解析数据
        this.parseAndPushData(ctx, channelKey, 0, readDatas);

    }


    /**
     * 解析并返回数据
     *
     * @param channelKey
     * @param readDatas
     */
    private void parseAndPushData(ChannelHandlerContext ctx, String channelKey, int index, byte[] readDatas) throws Exception {

        byte[] data = ByteArrayUtil.subBytes(readDatas, index, readDatas.length - index);

//        //整包长度
//        int packetLen = PacketUtil.getPacketLen(data);
//        index += packetLen;
//
//        //一个完整包
//        byte[] fullPacket = ByteArrayUtil.subBytes(data, 0, packetLen);
//        log.info("拆包后的单包数据 --> fullPacket : {}", ByteArrayUtil.bytes2HexStr(fullPacket));

        log.info("拆包后的单包数据 --> fullPacket : {}", ByteArrayUtil.bytes2HexStr(data));

//        //验证数据包有效性
//        if (!PacketUtil.checkPacket(fullPacket)) {
//            cache.remove(channelKey);
//            log.info("数据校验失败 --> fullPacket : {}", ByteArrayUtil.bytes2HexStr(fullPacket));
//            return;
//        }
//        ctx.fireChannelRead(PacketUtil.bytes2Message(fullPacket));
//
//        //剩余长度
//        int remainingLen = data.length - packetLen;

        //没有数据，结束
//        if (remainingLen < 1) {
//            return;
//        }
//
//        //剩余数据长度小于一包数据的最小长度，缓存数据
//        if (remainingLen < JT809MessageConstants.MSG_MIN_LEN) {
//            log.warn("剩余数据长度小于整包数据最小长度，缓存数据：{}", ByteArrayUtil.bytes2HexStr(ByteArrayUtil.subBytes(data, data.length - remainingLen, remainingLen)));
//            cache.put(channelKey, ByteArrayUtil.subBytes(data, data.length - remainingLen, remainingLen));
//            return;
//        }
//
//        //下一包数据的总长度
//        packetLen = PacketUtil.getPacketLen(ByteArrayUtil.subBytes(data,data.length - remainingLen, remainingLen));
//        //剩余数据长度小于整包数据长度
//        if (remainingLen < packetLen) {
//            log.warn("剩余数据长度小于整包数据长度，缓存数据：{}", ByteArrayUtil.bytes2HexStr(ByteArrayUtil.subBytes(data, data.length - remainingLen, remainingLen)));
//            cache.put(channelKey, ByteArrayUtil.subBytes(data, data.length - remainingLen, remainingLen));
//            return;
//        }

        //还有完整数据包 递归调用
//        this.parseAndPushData(ctx, channelKey, index, readDatas);


        JT809Packet0x1202Decoder jt809Packet0x1202Decoder=new JT809Packet0x1202Decoder();
        jt809Packet0x1202Decoder.decoder(data);
//        parsePkt(data);



    }


    /***
     *    ACSII 下解析方式
     * @param data
     */
    private void parsePkt(byte[] data) {
        // begin 这里提供方法可供入库使用，根据不同的业务进行字段分段截取.默认解析字段分别是业务字段的含义字段

        String parseData = ByteArrayUtil.bytes2HexStr(data);

        //比如这里增加子业务类型的字段数据：
        if(StringUtil.isNullOrEmpty(parseData)){
             log.info("数据为空");
             return;
        }else {
            //数据头
            String  head = parseData.substring(0, 2); //--头标识
            String  datalength=parseData.substring(2,10);//--数据头->数据长度
            String  dataSeqNo=parseData.substring(10,18);// --数据头->报文序列号
            String  bizdata=parseData.substring(18,22);// --数据头->业务数据类型
            String  code=parseData.substring(22,30); //--数据头->下级平台接入码，上级平台给下级平台分配唯一标识码
            String version=parseData.substring(30,36); //--数据头->协议版本号标识
            String entryFlag=parseData.substring(36,38);//--数据头->报文加密标识位
            String key=parseData.substring(38,46);//--数据头->数据加密的密匙

            //数据体
//            String chepaiHao=parseData.substring(46,50);// --数据体->车牌号
//            String color=parseData.substring(50,52); // --数据体->车辆颜色   //这2者没有
            String biz =parseData.substring(46,50); //--数据体->子业务类型标识
            String lastlength=parseData.substring(50,58);//--数据体->后续数据长度，这里的确是24 位 ，数据长度24位

            //子数据体
            String subData=parseData.substring(58,82); //参看4.5.8.1 车辆定位信息数据体

            //3.CRC
            String crc=parseData.substring(82,86);  //这个每次都变化的
            String tail=parseData.substring(86,88);





            //2.业务数据体这里调用Parse方法，可以封装对应的实体bean,供入库用，提供int ,varchar,time 三种格式进行
//            log.info("总长度是：{}"+subData);









        }


        //end
    }
}
