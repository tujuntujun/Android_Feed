package transsocket;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;




public class TcpClient {

    public static String HOST = "47.94.238.110";
    public static int PORT = 2345;

    public Bootstrap bootstrap = getBootstrap();
    public Channel channel = getChannel(HOST, PORT);

    public TcpClient() {
        this.bootstrap = getBootstrap();
        channel = getChannel(HOST, PORT);
    }

    /**
     * 初始化Bootstrap
     */
    public final Bootstrap getBootstrap() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group);
        b.channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {
            @Override
            public void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast("handler", new TcpClientHandler());
            }
        });
        b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    //    连接端口
    public final Channel getChannel(String host, int port) {
        Channel channel = null;
        try {
            channel = bootstrap.connect(host, port).sync().channel();
            System.out.println("TCP Client 已经在" + host + "的" + port + "端口建立Channel");
        } catch (Exception e) {
            System.out.println("连接Server(IP{},PORT{})失败" + "host:" + host + "port:" + port + "e:" + e);
            return null;
        }
        return channel;
    }

    /**
     * 发送信息
     *
     * @return void
     * @author 张超 teavamc
     * @date 2019/5/2
     */
    public void sendMsg(String msg) throws Exception {
        if (channel != null) {
            channel.writeAndFlush(msg).sync();
            System.out.println("发送成功!");
        } else {
            System.out.println("消息发送失败,连接尚未建立!");
        }
    }

//    public static void main(String[] args) throws Exception {
//        try {
//            TcpClient.sendMsg("01");
//
//        } catch (Exception e) {
//            log.info("main err:"+ e);
//        }
//    }
}
