package cn.com.onlinetool.jt809.dbTest;

import cn.com.onlinetool.jt809.db.JT809Dao;
import cn.com.onlinetool.jt809.decoderDemo2.JT809Packet0x0000;
import org.junit.Test;

import java.sql.SQLException;

public class DbUtils {



    @Test
    public  void insertTet() throws SQLException {

        JT809Packet0x0000 packet=new JT809Packet0x0000();
         packet.setName("zhangsan");
         packet.setFlight("MUc3");
         packet.setRemark("remark");
        JT809Dao.insert0x1202(packet);

    }
}
