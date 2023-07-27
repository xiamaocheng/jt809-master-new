package cn.com.onlinetool.jt809.jt;



import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecevieHandler extends SimpleChannelHandler{

    private static Logger LOG = LoggerFactory.getLogger(RecevieHandler.class);


    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e){
        Message msg = (Message) e.getMessage();
        LOG.error("应答----------------" + "0x" + Integer.toHexString(msg.getMsgId()));
        if(msg.getMsgId() == JT809Constants.UP_CONNECT_RSP){
            ChannelBuffer msgBody = msg.getMsgBody();
            int result = msgBody.readByte();
            if(result == JT809Constants.UP_CONNECT_RSP_SUCCESS){
//                TcpClientFuJian.LONGINSTATUS = Constants.LOGIN_SUCCESS;
                LOG.error("------------------登录成功");
            }else{
                LOG.error("------------------登录异常，请检查" + "0x0" + Integer.toHexString(result));
            }
        }
    }
}