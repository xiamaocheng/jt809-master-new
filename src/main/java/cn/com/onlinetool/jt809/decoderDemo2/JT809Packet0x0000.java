package cn.com.onlinetool.jt809.decoderDemo2;


import cn.com.onlinetool.jt809.decoderDemo.JT809BasePacket;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/22 15:41
 * @Version 1.0
 * @Describe: 实时上传车辆定位信息包
 */
public class JT809Packet0x0000 extends JT809BasePacket {

    private String name;

    private Number code;

    private String Flight;

    private String remark;

    public String getFlight() {
        return Flight;
    }

    public void setFlight(String flight) {
        Flight = flight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Number getCode() {
        return code;
    }

    public void setCode(Number code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public byte[] getMsgBodyByteArr() {
        return new byte[0];
    }


}
