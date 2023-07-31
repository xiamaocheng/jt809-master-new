package cn.com.onlinetool.jt809.jt;

/***
 * 这里使用对是netty3 的客户端的方式，在这里以后可能需要升级的问题
 *  netty 4不与netty 3向后兼容，但如果“client”表示远程对等，则可以升级服务器并与客户端保持兼容。
 *  客户端只是通过tcp堆栈与服务器通信，它不关心服务器的实现方式。
 *
 */

import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

//import org.apache.commons.lang3.StringUtils;
import io.netty.util.CharsetUtil;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TcpClientDemo {

    private static Logger LOGGER = LoggerFactory.getLogger(TcpClientDemo.class);

    /**
     * 交委指定本公司接入码
     */
    public static int PLANT_CODE;
    /**
     * 交委指定本公司用户名
     */
    public static int COM_ID;
    /**
     * 交委指定本公司密码
     */
    public static String COM_PWD;
    public static String LONGINSTATUS = "";
    public static String LOGINING = "logining";
    private static int LOGIN_FLAG = 0;
    private static String DOWN_LINK_IP = "127.0.0.1";

    //初始化基类
    private static TcpClient tcpClient = TcpClient.getInstence();
    //初始化
    private static TcpClientDemo tcpClientDemo = new TcpClientDemo();
//    //初始化配置,将密码相关的内容写在里面
//    private static Properties property = PropertiesReader.getProperties("properName", true);
    //初始化channel,以便心跳机制及时登录
    private Channel channel = tcpClient.getChannel("127.0.0.1", 11111);


    public static TcpClientDemo getInstance(){

        return tcpClientDemo;
    }


    public static ChannelBuffer buildMessage(Message msg){
        int bodyLength = 0 ;
        if(null != msg.getMsgBody()){
            bodyLength = msg.getMsgBody().readableBytes();
        }
        msg.setMsgGesscenterId(PLANT_CODE);
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(bodyLength + Message.MSG_FIX_LENGTH);

        ChannelBuffer headBuffer = ChannelBuffers.buffer(22);
        //---数据头
        headBuffer.writeInt(buffer.capacity() - 1);//4
        headBuffer.writeInt(msg.getMsgSn());//4
        headBuffer.writeShort((short)msg.getMsgId());//2
        headBuffer.writeInt(msg.getMsgGesscenterId());//4
        headBuffer.writeBytes(msg.getVersionFlag());//3
        headBuffer.writeByte(0);//1
        headBuffer.writeInt(10);//4
        buffer.writeBytes(headBuffer);
        //---数据体
        if(null != msg.getMsgBody()){
            buffer.writeBytes(msg.getMsgBody());
        }
        ChannelBuffer finalBuffer = ChannelBuffers.copiedBuffer(buffer);
        //--crc校验码
        byte[] b = ChannelBuffers.buffer(finalBuffer.readableBytes()).array();
        finalBuffer.getBytes(0, b);

        int crcValue = CRC16CCITT.crc16(b);
        finalBuffer.writeShort((short)crcValue);//2
        //转义
        byte[] bytes = ChannelBuffers.copiedBuffer(finalBuffer).array();
        ChannelBuffer headFormatedBuffer = ChannelBuffers.dynamicBuffer(finalBuffer.readableBytes());
        formatBuffer(bytes, headFormatedBuffer);
        ChannelBuffer buffera = ChannelBuffers.buffer(headFormatedBuffer.readableBytes() + 2);
        buffera.writeByte(Message.MSG_HEAD);
        buffera.writeBytes(headFormatedBuffer);
        buffera.writeByte(Message.MSG_TALL);
        return ChannelBuffers.copiedBuffer(buffera);
    }

//
//    /**
//     * 发送数据到交委接入平台
//     * boolean
//     * @param awsVo
//     * @return
//     * 2016年9月28日 by fox_mt
//     */
    public boolean sendMsg2Gov(Idc2AwsGpsVo awsVo){
        boolean success = false;
//        if(isLogined()){

            //已经登录成功，开始发送数据
//            channel = tcpClient.getChannel(Constants.TCP_ADDRESS, Constants.TCP_PORT);
            tcpClient.getChannel("127.0.0.1", 11111);
            if(null != channel && channel.isWritable()){
                Message msg = buildSendVO(awsVo);
                ChannelBuffer msgBuffer = buildMessage(msg);
                channel.write(msgBuffer);

            }else{
                LONGINSTATUS = "";
            }

//        }else if(LOGIN_FLAG == 0){
//            LOGIN_FLAG ++;
//            login2Gov();
//            LOGGER.error("--------------第一次登录");
//        }else{
//            LOGGER.error("--------------等待登录");
//        }
        return success;
    }
//
//
    /**
     * 转换VO
     * void
     * @param awsVo
     * 2016年9月28日 by fox_mt
     */
    private Message buildSendVO(Idc2AwsGpsVo awsVo){
//        awsVo.setName("南京");
//        Message msg = new Message(JT809Constants.UP_EXG_MSG);
        Message msg = new Message( );
//        ChannelBuffer buffer = ChannelBuffers.buffer(36);
        //是否加密
//        buffer.writeByte((byte)0);//0未加密 // 1
//        //日月年dmyy
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//        buffer.writeByte((byte)cal.get(Calendar.DATE));
//        buffer.writeByte((byte)(cal.get(Calendar.MONTH) + 1));
//        String hexYear = "0" + Integer.toHexString(cal.get(Calendar.YEAR));
//        buffer.writeBytes(hexStringToByte(hexYear));//4
//
//        //时分秒
//        buffer.writeByte((byte)cal.get(Calendar.HOUR_OF_DAY));
//        buffer.writeByte((byte)cal.get(Calendar.MINUTE));
//        buffer.writeByte((byte)cal.get(Calendar.SECOND));//3
//        //经度，纬度
//        buffer.writeInt(formatLonLat(awsVo.getLon()));//4
//        buffer.writeInt(formatLonLat(awsVo.getLat()));//4

//        //速度
//        buffer.writeShort(awsVo.getSpeed().shortValue());//2
//        //行驶记录速度
//        buffer.writeShort(awsVo.getSpeed().shortValue());//2
//        //车辆当前总里程数
//        buffer.writeInt(awsVo.getMileage().intValue());//4
//        //方向
//        buffer.writeShort(awsVo.getDirection().shortValue());//2
//        //海拔
//        buffer.writeShort((short)0);//2
//        //车辆状态
//        int accStatus = null == awsVo.getAcc() ? 0 : awsVo.getAcc();
//        int gpsStatus = null == awsVo.getGpsStatus() ? 0 : awsVo.getGpsStatus();
//        if(accStatus == 0 && gpsStatus == 0){
//            buffer.writeInt(0);//4
//        }else if(accStatus == 1 && gpsStatus == 0){
//            buffer.writeInt(1);//4
//        }else if(accStatus == 0 && gpsStatus == 1){
//            buffer.writeInt(2);//4
//        }else{
//            buffer.writeInt(3);//4
//        }
//        //报警状态
//        buffer.writeInt(0);//0表示正常；1表示报警//4
        ChannelBuffer headBuffer = ChannelBuffers.buffer(64);
//        headBuffer.writeBytes(getBytesWithLengthAfter(21 , awsVo.getVehicleNo().getBytes(Charset.forName("GBK"))));//21
//        headBuffer.writeByte((byte)InitVehicleData.vehicleColor(awsVo.getVehicleNo()));//1
//        headBuffer.writeShort(JT809Constants.UP_EXG_MSG_REAL_LOCATION);//2
//        headBuffer.writeInt(buffer.capacity());//4
//        headBuffer.writeBytes(buffer);
//        msg.setMsgBody(headBuffer);

//        headBuffer.writeShort((awsVo.getCode().intValue()));//2
        headBuffer.writeBytes((awsVo.getName().getBytes(CharsetUtil.UTF_8)));
        headBuffer.writeBytes((awsVo.getFlight().getBytes(CharsetUtil.UTF_8)));
        headBuffer.writeBytes(awsVo.getRemark().getBytes(CharsetUtil.UTF_8));




        msg.setMsgBody(headBuffer);
        return msg;
    }

    /**
     * 报文转义
     * void
     * @param bytes
     * @param formatBuffer
     * 2016年10月12日 by fox_mt
     */
    private static void formatBuffer(byte[] bytes, ChannelBuffer formatBuffer){
        for (byte b : bytes) {
            switch(b){
                case 0x5b:
                    byte[] formatByte0x5b = new byte[2];
                    formatByte0x5b[0] = 0x5a;
                    formatByte0x5b[1] = 0x01;
                    formatBuffer.writeBytes(formatByte0x5b);
                    break;
                case 0x5a:
                    byte[] formatByte0x5a = new byte[2];
                    formatByte0x5a[0] = 0x5a;
                    formatByte0x5a[1] = 0x02;
                    formatBuffer.writeBytes(formatByte0x5a);
                    break;
                case 0x5d:
                    byte[] formatByte0x5d = new byte[2];
                    formatByte0x5d[0] = 0x5e;
                    formatByte0x5d[1] = 0x01;
                    formatBuffer.writeBytes(formatByte0x5d);
                    break;
                case 0x5e:
                    byte[] formatByte0x5e = new byte[2];
                    formatByte0x5e[0] = 0x5e;
                    formatByte0x5e[1] = 0x02;
                    formatBuffer.writeBytes(formatByte0x5e);
                    break;
                default: formatBuffer.writeByte(b);break;
            }
        }
    }

    /**
     * 16进制字符串转换成byte数组
     * byte[]
     * @param hex
     * @return
     * 2016年10月12日 by fox_mt
     */
    public static byte[] hexStringToByte(String hex) {
        hex = hex.toUpperCase();
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 格式化经纬度,保留六位小数
     * int
     * @param needFormat
     * @return
     * 2016年10月12日 by fox_mt
     */
    private int formatLonLat(Double needFormat){
        NumberFormat numFormat = NumberFormat.getInstance();
        numFormat.setMaximumFractionDigits(6);
        numFormat.setGroupingUsed(false);
        String fristFromat = numFormat.format(needFormat);
        Double formatedDouble = Double.parseDouble(fristFromat);
        numFormat.setMaximumFractionDigits(0);
        String formatedValue = numFormat.format(formatedDouble * 1000000);
        return Integer.parseInt(formatedValue);
    }

    /**
     * 补全位数不够的定长参数
     * byte[]
     * @param length
     * @param pwdByte
     * @return
     * 2016年10月12日 by fox_mt
     */
    private byte[] getBytesWithLengthAfter(int length, byte[] pwdByte){
        byte[] lengthByte = new byte[length];
        for(int i = 0; i < pwdByte.length; i ++){
            lengthByte[i] = pwdByte[i];
        }
        for (int i = 0; i < (length- pwdByte.length); i++) {
            lengthByte[pwdByte.length + i] = 0x00;
        }
        return lengthByte;
    }


    public static void main(String[] args) {
        TcpClientDemo s =  TcpClientDemo.getInstance();
        Idc2AwsGpsVo awsVo = new Idc2AwsGpsVo();
//        awsVo.setCode(123);
        awsVo.setName("张三"); //6
        awsVo.setFlight("CHIN"); //4
        awsVo.setRemark("航班起飞");//12







//        awsVo.setSpeed(45D);
//        awsVo.setMileage(10001D);
//        awsVo.setVehicleNo("XXXXX");
          s.sendMsg2Gov(awsVo);
        try {
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.sendMsg2Gov(awsVo);
    }

}