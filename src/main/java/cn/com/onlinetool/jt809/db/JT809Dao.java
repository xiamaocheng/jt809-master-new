package cn.com.onlinetool.jt809.db;

import cn.com.onlinetool.jt809.decoderDemo.JT809Packet0x1202;
import cn.com.onlinetool.jt809.decoderDemo2.JT809Packet0x0000;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/26 18:48
 * @Version 1.0
 * @Describe:
 */
public class JT809Dao {
    private static Logger log = LoggerFactory.getLogger(JT809Dao.class);

    public static int insert0x1202(JT809Packet0x0000 packet) throws SQLException {
        QueryRunner runner = new QueryRunner();
        Connection conn = DataSourceConnectionFactory.getDbConnection();
        String sql = "insert into test1.bus_gps(id,name,Flight,remark)values(?,?,?,?)";
        int insert = 0;
        int i=0;
        try {
            insert = runner.update(conn, sql,i++,  packet.getName().replaceAll("\u0000", ""), packet.getFlight(), packet.getRemark());
        } catch (SQLException e) {
            log.info("数据存储错误：{}",e.getMessage());
        }finally {

        }
        return insert;
    }

    public static int delYesterdayData()  {
        QueryRunner runner = new QueryRunner();
        Connection conn = DataSourceConnectionFactory.getDbConnection();
        String sql = "DELETE FROM test.bus_gps WHERE sendtime < CURRENT_DATE";
        int del = 0;
        try {
            del = runner.update(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }
}
