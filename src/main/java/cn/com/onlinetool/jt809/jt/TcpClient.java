package cn.com.onlinetool.jt809.jt;

//包路径，自己填
import java.net.InetSocketAddress;
import java.util.Base64;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.logging.LoggingHandler;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.logging.InternalLogLevel;
import org.jboss.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@SuppressWarnings("AlibabaThreadPoolCreation")
public class TcpClient{

    private static final Logger LOG = LoggerFactory.getLogger(TcpClient.class);

    private static final int DEFAULT_PORT = 11111;

    private long connectTimeoutMillis = 3000;

    private int port = DEFAULT_PORT;

    private boolean tcpNoDelay = false;

    private boolean reuseAddress = true;

    private boolean keepAlive = true;

    private int workerCount = 4;

    private ClientBootstrap bootstrap = null;

    private  static Channel channel = null;

    private Executor bossExecutor = Executors.newCachedThreadPool();

    private Executor workerExecutor = Executors.newCachedThreadPool();


    private static TcpClient instance = new TcpClient();

    private TcpClient() {
        init();
    }

    public static TcpClient getInstence(){
        return instance;
    }


    public void init() {

        bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
                bossExecutor, workerExecutor, workerCount));
        bootstrap.setOption("tcpNoDelay", tcpNoDelay);
        bootstrap.setOption("connectTimeoutMillis", connectTimeoutMillis);
        bootstrap.setOption("reuseAddress", reuseAddress);
        bootstrap.setOption("keepAlive", keepAlive);
    }


    public Channel getChannel(String address, int port) {

        if (null == channel || !channel.isOpen()) {
            bootstrap.setOption("writeBufferHighWaterMark", 64 * 1024);
            bootstrap.setOption("writeBufferLowWaterMark", 32 * 1024);
            bootstrap.setPipelineFactory(new ChannelPipelineFactory(){
                @Override
                public ChannelPipeline getPipeline() throws Exception {
                    ChannelPipeline pipeline = Channels.pipeline();
//					pipeline.addLast("loging", new LoggingHandler(InternalLogLevel.ERROR)); 打印日志信息，上线稳定后可去掉
                    pipeline.addLast("timeout", new IdleStateHandler(new HashedWheelTimer(), 10, 60, 0));//设置空闲心跳机制
                    pipeline.addLast("heartbeat", new HeartBeatHandler());//心跳发送包处理handler
//                    pipeline.addLast("decode", new Base64.Decoder());//解码
                    pipeline.addLast("loginHandler", new RecevieHandler());//反馈数据处理
                    return pipeline;
                }
            });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(
                    address, port));
            future.awaitUninterruptibly();
            if (future.isSuccess()) {
                channel = future.getChannel();
            } else {
//                throw new FrameworkRuntimeException(future.getCause());
            }
        }

        return channel;
    }



    public long getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public void setConnectTimeoutMillis(long connectTimeoutMillis) {
        this.connectTimeoutMillis = connectTimeoutMillis;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public boolean isReuseAddress() {
        return reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getWorkerCount() {
        return workerCount;
    }

    public void setWorkerCount(int workerCount) {
        this.workerCount = workerCount;
    }

}